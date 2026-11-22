package io.github.alekseyshibayev.infrastructure.log.performance.writequeryvalues;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Slf4j
@UtilityClass
public class WriteQueryValuesProfileGuard {

    static final List<String> ALLOWED_PROFILES = List.of("local", "test", "dev");

    public static void validateProfiles(Environment environment) {
        List<String> active = Arrays.stream(environment.getActiveProfiles())
                .map(profile -> profile.trim().toLowerCase(Locale.ROOT))
                .toList();

        if (active.isEmpty() || !ALLOWED_PROFILES.containsAll(active)) {
            throw new IllegalStateException(errorMessage(active));
        }

        log.warn("SQL values logging is ENABLED. SQL parameter values may contain PII. "
                        + "Allowed profiles: [{}], active profiles: [{}]. Do not use production data.",
                String.join(",", ALLOWED_PROFILES), String.join(",", active));
    }

    private static String errorMessage(List<String> active) {
        String activeProfiles = active.isEmpty() ? "<none>" : String.join(",", active);
        return ("SQL values logging is enabled, but it is allowed only for profiles [%s]. "
                + "Active profiles: [%s]. "
                + "Refusing to start to prevent PII leakage into logs. "
                + "Use this flag only on a non-production stand with one of the allowed profiles.")
                .formatted(String.join(",", ALLOWED_PROFILES), activeProfiles);
    }

}
