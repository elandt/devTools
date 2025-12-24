package com.linkedin.events.customer;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerService
{

    private final CustomerRepository customerRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void register(Customer customer)
    {
        customerRepository.save(customer);
        eventPublisher.publishEvent(new CustomerRegisteredEvent(customer));
    }

    public void remove(Customer customer)
    {
        customerRepository.delete(customer);
        eventPublisher.publishEvent(new CustomerRemovedEvent(customer));
    }
}
