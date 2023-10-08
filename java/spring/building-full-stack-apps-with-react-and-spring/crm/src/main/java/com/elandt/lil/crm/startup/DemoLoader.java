package com.elandt.lil.crm.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.elandt.lil.crm.model.Contact;
import com.elandt.lil.crm.repo.ContactRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DemoLoader implements CommandLineRunner {

    private final ContactRepository repository;

    @Override
    public void run(String... args) throws Exception {
        // Populate the H2 database on start-up
        this.repository.save(new Contact("Bobby", "Jones", "bobby.jones@augustanational.com"));
    }

}
