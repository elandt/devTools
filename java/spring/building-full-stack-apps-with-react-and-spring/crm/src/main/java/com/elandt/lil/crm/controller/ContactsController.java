package com.elandt.lil.crm.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elandt.lil.crm.model.Contact;
import com.elandt.lil.crm.repo.ContactRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api")
// Allow cross origin requests from the 'client' React app
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ContactsController {

    private final ContactRepository repository;

    @GetMapping(path = "/contacts")
    Collection<Contact> contacts() {
        return (Collection<Contact>) repository.findAll();
    }

    @PostMapping(path = "/contacts")
    ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        Contact result = repository.save(contact);
        return ResponseEntity.ok().body(result);
    }
}
