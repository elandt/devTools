package com.elandt.secondary.config.configtest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class GreetingController {

    @GetMapping
    public String greeting(@Value("${greeting}") String greeting) {
        return greeting;
    }
}
