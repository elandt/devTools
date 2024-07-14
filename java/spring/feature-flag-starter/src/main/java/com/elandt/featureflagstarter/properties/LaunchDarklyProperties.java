package com.elandt.featureflagstarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

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
    private String proxyHost = "app.proxy.com";

    /**
     * The proxy port for connecting to LaunchDarkly.
     * Defaults to 8080.
     */
    private int proxyPort = 8080;

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
    private String localFeatureFlagFile = "classpath:featureFlags.json";
}