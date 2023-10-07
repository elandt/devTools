package com.elandt.lil.universitymongo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.elandt.lil.universitymongo.domain.Department;


public interface DepartmentRepo extends MongoRepository<Department, String> {

    Optional<Department> findByName(String name);

    List<Department> findByChairId(String chairId);

    @Query("{ 'name' : { $regex: ?0 } }")
    List<Department> findNameByPattern(String pattern);
}
