package com.elandt.lil.hplus.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.elandt.lil.hplus.data.Salesperson;
import com.elandt.lil.hplus.data.SalespersonRepository;

@Controller
public class SalespersonController {

    private final SalespersonRepository salespersonRepository;

    public SalespersonController(SalespersonRepository salespersonRepository) {
        this.salespersonRepository = salespersonRepository;
    }

    // Unless the @QueryMapping specifies a deviation, the name of the method _needs_ to match
    // the query name defined in the schema
    @QueryMapping
    public Iterable<Salesperson> salespeople() {
        return this.salespersonRepository.findAll();
    }

    @QueryMapping
    public Salesperson salespersonById(@Argument Long id)  {
        return this.salespersonRepository.findById(id).orElseThrow();
    }

    @QueryMapping
    public Salesperson salespersonByEmail(@Argument String email) {
        return this.salespersonRepository.findSalespersonByEmail(email);
    }
}
