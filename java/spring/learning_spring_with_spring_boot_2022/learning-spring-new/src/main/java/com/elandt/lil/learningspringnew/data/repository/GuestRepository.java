package com.elandt.lil.learningspringnew.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elandt.lil.learningspringnew.data.entity.Guest;

@Repository
public interface GuestRepository extends CrudRepository<Guest, Long> {
    
}
