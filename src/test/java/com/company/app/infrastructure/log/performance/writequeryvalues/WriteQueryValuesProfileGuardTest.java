package com.company.app.infrastructure.log.performance.writequeryvalues;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import com.company.app.infrastructure.log.performance.test.common.MockitoUnitTest;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MockitoUnitTest
class WriteQueryValuesProfileGuardTest {

    @Test
    void starts_when_active_profile_is_allowed() {
        var environment = environment("dev");

        assertThatCode(() -> WriteQueryValuesProfileGuard.validateProfiles(environment))
                .doesNotThrowAnyException();
    }

    @Test
    void starts_when_all_active_profiles_are_allowed() {
        var environment = environment("local", "test");

        assertThatCode(() -> WriteQueryValuesProfileGuard.validateProfiles(environment))
                .doesNotThrowAnyException();
    }

    @Test
    void starts_when_profiles_have_any_case_or_spaces() {
        var environment = environment(" DEV ", "Test");

        assertThatCode(() -> WriteQueryValuesProfileGuard.validateProfiles(environment))
                .doesNotThrowAnyException();
    }

    @Test
    void fails_when_no_profile_is_active() {
        var environment = environment();

        assertThatThrownBy(() -> WriteQueryValuesProfileGuard.validateProfiles(environment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("<none>")
                .hasMessageContaining("local,test,dev");
    }

    @Test
    void fails_on_production_profile() {
        var environment = environment("prod");

        assertThatThrownBy(() -> WriteQueryValuesProfileGuard.validateProfiles(environment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("[prod]");
    }

    @Test
    void normalizes_profile_names_in_error_message() {
        var environment = environment(" PROD ");

        assertThatThrownBy(() -> WriteQueryValuesProfileGuard.validateProfiles(environment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Active profiles: [prod]");
    }

    @Test
    void fails_on_any_profile_outside_the_hardcoded_list() {
        var environment = environment("staging");

        assertThatThrownBy(() -> WriteQueryValuesProfileGuard.validateProfiles(environment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("[staging]");
    }

    @Test
    void fails_when_at_least_one_active_profile_is_not_allowed() {
        var environment = environment("dev", "prod");

        assertThatThrownBy(() -> WriteQueryValuesProfileGuard.validateProfiles(environment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("dev,prod");
    }

    private MockEnvironment environment(String... activeProfiles) {
        var environment = new MockEnvironment();
        environment.setActiveProfiles(activeProfiles);
        return environment;
    }

}
