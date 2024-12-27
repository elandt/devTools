package com.elandt.lil.hplus.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.elandt.lil.hplus.data.Order;
import com.elandt.lil.hplus.data.OrderRepository;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @QueryMapping
    public Iterable<Order> orders() {
        return this.orderRepository.findAll();
    }
}
