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

/**
 * Auto-configuration for creating a LaunchDarkly client in remote mode.
 */
@AutoConfiguration
@ConditionalOnClass(LDClient.class)
@Profile("!LOCAL")
public class RemoteLDClientAutoConfiguration {

    /**
     * Creates a LaunchDarkly client in remote mode using a proxy.
     *
     * @param properties the LaunchDarkly properties
     * @return the configured LDClient
     */
    @Bean
    @ConditionalOnMissingBean
    private LDClient ldClient(LaunchDarklyProperties properties) {
        LDConfig config = new LDConfig.Builder()
                .http(Components.httpConfiguration()
                        .proxyHostAndPort(properties.getProxyHost(), properties.getProxyPort())
                        .proxyAuth(Components.httpBasicAuthentication(properties.getProxyUser(), properties.getProxyPassword()))
                        )
                .build();
        return new LDClient(properties.getSdkKey(), config);
    }
}
