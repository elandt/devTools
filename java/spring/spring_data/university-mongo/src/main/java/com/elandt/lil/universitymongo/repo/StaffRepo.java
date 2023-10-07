package com.elandt.lil.universitymongo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.elandt.lil.universitymongo.domain.Staff;

public interface StaffRepo  extends MongoRepository<Staff, String> {

    List<Staff> findByMemberLastName(String lastName);

    @Query("{ 'member.firstName' : ?0 }")
    List<Staff> findByFirstName(String firstName);
}
