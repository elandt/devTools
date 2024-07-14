package com.elandt.featureflagstarter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for LaunchDarkly.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "launchdarkly")
@Configuration
public class LaunchDarklyProperties {

    /**
     * The SDK key for LaunchDarkly.
     */
    private String sdkKey;

    /**
     * The proxy host for connecting to LaunchDarkly.
     * Defaults to app.proxy.com.
     */
    @DefaultValue("app.proxy.com")
    private String proxyHost;

    /**
     * The proxy port for connecting to LaunchDarkly.
     * Defaults to 8080.
     */
    @DefaultValue("8080")
    private int proxyPort;

    /**
     * The proxy user for connecting to LaunchDarkly.
     */
    private String proxyUser;

    /**
     * The proxy password for connecting to LaunchDarkly.
     */
    private String proxyPassword;

    /**
     * The path to the local feature flag file.
     * Defaults to classpath:featureFlags.json.
     */
    @DefaultValue("classpath:featureFlags.json")
    private String localFeatureFlagFile;
}