package com.elandt.lil.hplus.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.elandt.lil.hplus.data.Customer;
import com.elandt.lil.hplus.data.CustomerRepository;

@Controller
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Unless the @QueryMapping specifies a deviation, the name of the method _needs_ to match
    // the query name defined in the schema
    @QueryMapping
    public Iterable<Customer> customers() {
        return this.customerRepository.findAll();
    }

    @QueryMapping
    public Customer customerById(@Argument Long id)  {
        return this.customerRepository.findById(id).orElseThrow();
    }

    @QueryMapping
    public Customer customerByEmail(@Argument String email) {
        return this.customerRepository.findCustomerByEmail(email);
    }

    @MutationMapping
    public Customer addCustomer(@Argument(name = "input") CustomerInput customerInput) {
        return this.customerRepository.save(customerInput.getCustomerEntity());
    }
}
