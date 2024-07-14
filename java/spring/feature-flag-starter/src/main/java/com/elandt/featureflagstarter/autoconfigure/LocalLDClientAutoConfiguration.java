package com.elandt.featureflagstarter.autoconfigure;

import com.launchdarkly.sdk.server.LDClient;
import com.launchdarkly.sdk.server.LDConfig;
import com.launchdarkly.sdk.server.integrations.FileData;
import com.example.featureflagstarter.properties.LaunchDarklyProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * Auto-configuration for creating a LaunchDarkly client in local mode.
 */
@AutoConfiguration
@ConditionalOnClass(LDClient.class)
@Profile("LOCAL")
public class LocalLDClientAutoConfiguration {

    /**
     * Creates a LaunchDarkly client in local mode using a local feature flag file.
     *
     * @param properties the LaunchDarkly properties
     * @return the configured LDClient
     */
    @Bean
    @ConditionalOnMissingBean
    private LDClient ldClient(LaunchDarklyProperties properties) {
        LDConfig config = new LDConfig.Builder()
                .dataSource(FileData.dataSource()
                        .filePaths(properties.getLocalFeatureFlagFile())
                        .build())
                .build();
        return new LDClient(properties.getSdkKey(), config);
    }
}
