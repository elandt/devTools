package com.elandt.lil.sfid.springframeworkindepth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.elandt.lil.sfid.springframeworkindepth.aspect.Countable;

@Service
public class OutputService {

    @Value("${app.name}")
    private String name;

    private final GreetingService greetingService;
    private final TimeService timeService;

    // Can use `@Autowired` here, but it's not required because there's only 1 constructor
    public OutputService(GreetingService greetingService, TimeService timeService) {
        this.greetingService = greetingService;
        this.timeService = timeService;
    }

    @Countable
    public void generateOutput() {
        System.out.println(timeService.getCurrentTime() + " " + greetingService.getGreeting(name));
    }
    
}
