package com.elandt.lil.adminweb.data.repository;

import org.springframework.data.repository.CrudRepository;

import com.elandt.lil.adminweb.data.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Iterable<Order> findAllByCustomerId(long customerId);
}
