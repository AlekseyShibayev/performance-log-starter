package io.github.alekseyshibayev.infrastructure.log.performance.interceptor.model;

import io.github.alekseyshibayev.infrastructure.log.performance.PerformanceLogConfiguration;


/**
 * Log with hibernate call statistics.
 * {@link PerformanceLogConfiguration}
 */
public class QueryInfo {

    private final PreparedSQLCallsStatistic preparedSQLCallsStatistic = new PreparedSQLCallsStatistic();
    private final EntityStorageOnLoadStatistic entityStorageOnLoadStatistic = new EntityStorageOnLoadStatistic();
    private final QueryWithValuesStatistic queryWithValuesStatistic = new QueryWithValuesStatistic();

    public void changeOnPrepareStatement(String sql) {
        preparedSQLCallsStatistic.changeOnPrepareStatement(sql);
    }

    public void changeOnLoad(Object entity) {
        entityStorageOnLoadStatistic.changeOnLoad(entity);
    }

    public void addQueryWithValue(String sql) {
        queryWithValuesStatistic.add(sql);
    }

    @Override
    public String toString() {
        return ("%s%s%s").formatted(preparedSQLCallsStatistic, entityStorageOnLoadStatistic, queryWithValuesStatistic);
    }

}