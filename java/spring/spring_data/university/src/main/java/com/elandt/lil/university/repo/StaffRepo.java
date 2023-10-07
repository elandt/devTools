package com.elandt.lil.university.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.elandt.lil.university.domain.Staff;

@RepositoryRestResource(path = "staff", collectionResourceRel = "staff")
public interface StaffRepo extends JpaRepository<Staff, Integer> {

    @Query("Select s FROM Staff s WHERE s.member.lastName = :lastName")
    public List<Staff> findByLastName(@Param("lastName") String lastName);

    // Equivalent property expression query method
    public List<Staff> findByMemberLastName(String lastName);

    // Sorting with property expressions
    public List<Staff> findAllByOrderByMemberLastNameAscMemberFirstNameAsc();

}
