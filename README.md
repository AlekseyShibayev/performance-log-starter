# performance-log-starter

Стартер для поиска проблем производительности JPA-слоя в Spring Boot приложениях.

## Зачем это

Показывает, что JPA-слой делает во время конкретного вызова:
время метода, SQL-запросы, повторы и загруженные сущности.
Помогает найти медленный запрос и понять, откуда он взялся.

По умолчанию всё выключено: бины стартера не создаются, на производительность
это не влияет.

## Как включить

1. Добавить зависимость `com.company.app:performance-log-starter` в вашу систему
   сборки (Maven, Gradle — как принято в проекте).

2. Включить модуль. Рабочий вариант — выключено на проде, включено на dev/test,
   пишем только то, что дольше 500 мс:

```yaml
log:
  performance:
    enable: ${LOG_PERFORMANCE_ENABLE:false}
    pointcut-expression: >-
      ${LOG_PERFORMANCE_POINTCUT_EXPRESSION:@annotation(org.springframework.scheduling.annotation.Scheduled)
      || @within(org.springframework.web.bind.annotation.RestController)}
    do-not-write-to-log-methods-whose-execution-time-is-less-than-X-ms: >-
      ${LOG_PERFORMANCE_DO_NOT_WRITE_TO_LOG_METHODS_WHOSE_EXECUTION_TIME_IS_LESS_THAN_X_MS:500}
    write-query-values:
      enable: ${LOG_PERFORMANCE_WRITE_QUERY_VALUES_ENABLE:false}
```

3. Если свой `pointcut-expression` не задан — пометить нужные классы или методы `@PerformanceLog`.

Больше ничего настраивать не нужно: при старте стартер сам поднимает `DEBUG`
для своего пакета и регистрирует Hibernate interceptor и statement inspector.

## Как выглядит лог

![Пример лога PerformanceLog](docs/log-example.svg)

В примере: 183 мс, `Slice` из 20 элементов, 2 одинаковых select, 2 сущности.
Внизу — блок `sql with values` с реальными значениями вместо `?`.

В логирование входит:

1. время выполнения метода, в мс;
2. размер результата, если это `Collection` или `Slice` (родитель `Page`);
3. источник — полное имя класса и метода;
4. `QueryInfo`: сколько было подготовок запросов (из них select/update/insert), какие SQL и сколько раз,
   сколько сущностей загрузилось и каких типов;
5. при включённом `write-query-values` — реальные SQL с подставленными значениями.

## Как читать статистику

- Каждый залогированный select — запрос, реально ушедший в БД.
- Если запроса в логе нет, значит Hibernate обошёлся без него: сущность уже в persistence context
  или сработал кеш `@Cacheable`.
- `onLoad` показывает, сколько сущностей Hibernate инициализировал, и каких типов.
- Вложенные прокси-методы аккумулируются: внешний вызов видит запросы
  всех вложенных вызовов.

## Реальные SQL со значениями

> Реальные значения пишутся только для профилей `local`, `test`, `dev`.
> При другом профиле приложение не стартует. Это PII — не включайте на проде.

Флаг `write-query-values.enable` заменяет `?` в логе на реальные значения параметров:

```yaml
log:
  performance:
    enable: true
    write-query-values:
      enable: true
```

```text
sql with values: 
[2026-11-22T14:32:07.481] execution time [12] ms, real sql:
HikariProxyPreparedStatement@230035651 wrapping select u1_0.id, u1_0.name from users u1_0 where u1_0.name in ('Ivan', 'Petr') and u1_0.id in (1, 2)
[2026-11-22T14:32:08.002] interrupt at [1002] ms, real sql:
HikariProxyPreparedStatement@1479770936 wrapping select pg_sleep(5) from users
reason:[ERROR: canceling statement due to statement timeout]
```

Строка берётся из `toString()` JDBC-драйвера, формат зависит от драйвера.
Флаг замедляет горячие участки — включайте точечно.

## Параметры

| Параметр | По умолчанию | Описание |
|---|---|---|
| `log.performance.enable` | `false` | включает модуль; при `false` его бины не создаются |
| `log.performance.pointcut-expression` | методы и классы с `@PerformanceLog` | AspectJ pointcut для проксирования |
| `log.performance.do-not-write-to-log-methods-whose-execution-time-is-less-than-X-ms` | `0` | писать только методы дольше N мс |
| `log.performance.write-query-values.enable` | `false` | реальные SQL со значениями (нужен `enable=true`) |

Примеры `pointcut-expression`:

| Что проксировать | Выражение |
|---|---|
| все методы пакета | `execution(* com.company.app.user.*.*(..))` |
| все методы класса | `execution(* com.company.app.user.UserService.*(..))` |
| методы с `@Scheduled` | `@annotation(org.springframework.scheduling.annotation.Scheduled)` |
| методы классов с `@RestController` | `@within(org.springframework.web.bind.annotation.RestController)` |
| методы классов, реализующих интерфейс | `execution(* *(..)) && within(com.company.app.api.Processor+)` |

## Подводные камни

1. `final` класс без интерфейсов не проксируется: CGLIB не может наследоваться
   от `final`, JDK-прокси требует интерфейс. Приложение не поднимется.
2. Hibernate может не выполнить запрос, пока не запросят данные из результата.
3. Внутренний вызов метода (`this.method()`) идёт мимо прокси, лога не будет.

## Как пользоваться в тестах

1. Взять конфиг из `src/test/resources/test.properties`: там уже включены
   `log.performance.enable` и `log.performance.write-query-values.enable`, а `pointcut-expression`
   закомментирован — значит проксируются только методы и классы с `@PerformanceLog`.
2. Повесить `@PerformanceLog` на тестируемый класс или метод.
3. Запустить тест.
4. Искать в логе по фразе `PerformanceLogAspect`.

## Тесты

`mvn test` — unit-тесты и интеграционные тесты на Testcontainers (нужен Docker).

Подробное описание — в javadoc `PerformanceLogConfiguration`.
