package com.elandt.lil.adminweb.data.repository;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.elandt.lil.adminweb.data.model.Customer;
import com.elandt.lil.adminweb.data.model.Order;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testGetAllOrders(){
        Iterable<Order> orders = orderRepository.findAll();
        assert(IterableUtils.size(orders)==3);
    }

    @Test
    public void testGetAllCustomers(){
        Iterable<Customer> customers = customerRepository.findAll();
        assert(IterableUtils.size(customers)==7);
    }
}
