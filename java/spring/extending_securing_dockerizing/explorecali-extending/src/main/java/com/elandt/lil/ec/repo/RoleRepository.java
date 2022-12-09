package com.elandt.lil.ec.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elandt.lil.ec.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByRoleName(String name);
}
