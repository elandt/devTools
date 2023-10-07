package com.elandt.lil.universitymongoreactive.repo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.elandt.lil.universitymongoreactive.domain.Department;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Could also extend only ReactiveCrudRepository, but extending ReactiveMongoRepository also brings in ReactiveSortingRepository and ReactiveQueryByExampleExecutor
public interface DepartmentRepo extends ReactiveMongoRepository<Department, String> {

    Mono<Department> findByName(String name);

    Flux<Department> findByChairId(String chairId);

    @Query("{ 'name' : { $regex: ?0 } }")
    Flux<Department> findNameByPattern(String pattern);
}
