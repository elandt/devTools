package com.elandt.lil.crm.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.elandt.lil.crm.model.Contact;

@RepositoryRestResource
public interface ContactRepository extends CrudRepository<Contact, Long> {

}
