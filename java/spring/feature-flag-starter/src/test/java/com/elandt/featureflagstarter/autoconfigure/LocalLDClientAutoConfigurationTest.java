package com.elandt.featureflagstarter.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.ActiveProfiles;

import com.launchdarkly.sdk.server.LDClient;

@ActiveProfiles("LOCAL")
class LocalLDClientAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(LocalLDClientAutoConfiguration.class)
            .withPropertyValues(
                    "launchdarkly.sdk-key=test-sdk-key",
                    "launchdarkly.local-feature-flag-file=classpath:featureFlags.json"
            );

    @Test
    void testLocalLDClientBeanCreated() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(LDClient.class);
            LDClient ldClient = context.getBean(LDClient.class);
            assertThat(ldClient).isNotNull();
            assertThat(ldClient.isOffline()).isTrue();
        });
    }
}
