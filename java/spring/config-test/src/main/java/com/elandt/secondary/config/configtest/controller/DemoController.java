package com.elandt.secondary.config.configtest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elandt.secondary.config.configtest.domain.Demo;
import com.elandt.secondary.config.configtest.service.DemoService;

@RestController
@RequestMapping(path = "/demos")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping
    public List<Demo> getAllDemoRecords() {
        return demoService.getAllDemoRecords();
    }
}
