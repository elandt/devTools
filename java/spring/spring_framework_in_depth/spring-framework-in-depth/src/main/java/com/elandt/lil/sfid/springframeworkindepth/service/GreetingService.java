package com.elandt.lil.sfid.springframeworkindepth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.elandt.lil.sfid.springframeworkindepth.aspect.Countable;
import com.elandt.lil.sfid.springframeworkindepth.aspect.Loggable;

@Service
public class GreetingService {

    @Value("${app.greeting}")
    private String greeting;

    @Loggable
    @Countable
    public String getGreeting(String name){
        return greeting + " " + name;
    }
}
