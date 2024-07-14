package com.elandt.featureflagstarter.autoconfigure;

import com.launchdarkly.sdk.server.LDClient;
import com.example.featureflagstarter.properties.LaunchDarklyProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("!LOCAL")
class RemoteLDClientAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(RemoteLDClientAutoConfiguration.class)
            .withPropertyValues(
                    "launchdarkly.sdk-key=test-sdk-key",
                    "launchdarkly.proxy-host=app.proxy.com",
                    "launchdarkly.proxy-port=8080",
                    "launchdarkly.proxy-user=test-user",
                    "launchdarkly.proxy-password=test-password"
            );

    @Test
    void testRemoteLDClientBeanCreated() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(LDClient.class);
            LDClient ldClient = context.getBean(LDClient.class);
            assertThat(ldClient).isNotNull();
            // Additional assertions to verify proxy configuration can be added here
        });
    }
}
