package com.elandt.featureflagstarter.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.elandt.featureflagstarter.properties.LaunchDarklyProperties;
import com.launchdarkly.sdk.server.Components;
import com.launchdarkly.sdk.server.LDClient;
import com.launchdarkly.sdk.server.LDConfig;
import com.launchdarkly.sdk.server.integrations.FileData;

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
                        .autoUpdate(true)
                        )
                .events(Components.noEvents())
                .build();
        return new LDClient(properties.getSdkKey(), config);
    }
}
