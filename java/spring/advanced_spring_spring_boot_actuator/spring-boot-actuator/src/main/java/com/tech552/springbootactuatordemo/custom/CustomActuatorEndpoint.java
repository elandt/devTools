package com.tech552.springbootactuatordemo.custom;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Endpoint(id = "custom")
@Component
public class CustomActuatorEndpoint {
    
    // Does not _need_ to accept parameters, but if it does, they must be provided as query parameters
    @ReadOperation
    public Map<String, String> customEndpoint(String id) {
        var map = new HashMap<String, String>();
        map.put("id", id);
        return map;
    }
}
