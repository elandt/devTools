package com.elandt.lil.adminweb.data.repository;

import org.springframework.data.repository.CrudRepository;

import com.elandt.lil.adminweb.data.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
