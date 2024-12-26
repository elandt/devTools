package com.elandt.lil.spring_demo.controller;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.elandt.lil.spring_demo.builder.Contact;
import com.elandt.lil.spring_demo.factory.Pet;
import com.elandt.lil.spring_demo.factory.PetFactory;
import com.elandt.lil.spring_demo.repository.PresidentEntity;
import com.elandt.lil.spring_demo.repository.PresidentRepository;




@RestController
public class AppController {

    private final PetFactory petFactory;
    private final PresidentRepository presidentRepository;
    private final RestTemplate restTemplate;

    public AppController(PetFactory petFactory, PresidentRepository presidentRepository, RestTemplate restTemplate) {
        this.petFactory = petFactory;
        this.presidentRepository = presidentRepository;
        this.restTemplate = restTemplate;
    }

    @PostMapping(path = "adopt-pet/{type}/{name}")
    public Pet adoptPet(@PathVariable String type, @PathVariable String name) {
        var pet = this.petFactory.createPet(type);
        pet.setName(name);
        pet.feed();

        return pet;
    }

    @PostMapping("contact")
    public Contact createContact(@RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String emailAddress) {
        return Contact.builder()
                .firstName(firstName)
                .lastName(lastName)
                .emailAddress(emailAddress)
                .build();
    }

    @GetMapping("presidents/{id}")
    public PresidentEntity findPresidentById(@PathVariable Long id) {
        return this.presidentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No president could be found with ID: " + id));
    }

    @GetMapping("president-contact/{id}")
    public Contact getPresidentContactById(@PathVariable Long id) {
        var president = this.restTemplate.getForEntity("http://localhost:8080/presidents/{id}", PresidentEntity.class, id).getBody();

        if (president == null) {
            throw new NoSuchElementException("No president could be found with ID: " + id);
        }

        return Contact.builder()
                .firstName(president.getFirstName())
                .lastName(president.getLastName())
                .emailAddress(president.getEmailAddress())
                .build();
    }

}
