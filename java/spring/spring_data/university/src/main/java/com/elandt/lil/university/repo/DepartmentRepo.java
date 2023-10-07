package com.elandt.lil.university.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elandt.lil.university.domain.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

    public Optional<Department> findByName(String name);
}
