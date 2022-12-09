package com.elandt.lil.ec.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.elandt.lil.ec.domain.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByUsername(String userName);
}
