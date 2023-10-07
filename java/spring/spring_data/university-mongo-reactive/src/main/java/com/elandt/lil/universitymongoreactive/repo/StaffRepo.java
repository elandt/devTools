package com.elandt.lil.universitymongoreactive.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.elandt.lil.universitymongoreactive.domain.Staff;

import reactor.core.publisher.Flux;

// Could also extend only ReactiveCrudRepository, but extending ReactiveMongoRepository also brings in ReactiveSortingRepository and ReactiveQueryByExampleExecutor
public interface StaffRepo  extends ReactiveMongoRepository<Staff, String> {

    Flux<Staff> findByMemberLastName(String lastName);

}
