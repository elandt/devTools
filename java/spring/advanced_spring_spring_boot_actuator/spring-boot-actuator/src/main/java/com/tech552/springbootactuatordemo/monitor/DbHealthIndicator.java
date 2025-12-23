package com.tech552.springbootactuatordemo.monitor;

import java.util.Random;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DbHealthIndicator implements HealthIndicator {
    
    @Override
    public Health health() {
        if (isDBHealthy()) {
            return Health.up().withDetail("External Db svc", "Healthy").build();
        } else {
            return Health.down().withDetail("External Db Svc", "NOT Healthy").build();
        }
    }

    // Mimics a call to an external server or DB
    private boolean isDBHealthy() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
