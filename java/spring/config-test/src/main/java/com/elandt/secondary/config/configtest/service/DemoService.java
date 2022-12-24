package com.elandt.secondary.config.configtest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.elandt.secondary.config.configtest.domain.Demo;
import com.elandt.secondary.config.configtest.repository.DemoRepository;

@Service
public class DemoService {

    private final DemoRepository demoRepository;

    public DemoService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    public List<Demo> getAllDemoRecords() {
        return demoRepository.findAll();
    }
}
