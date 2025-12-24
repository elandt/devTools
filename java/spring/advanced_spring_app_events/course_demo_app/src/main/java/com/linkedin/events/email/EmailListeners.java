package com.linkedin.events.email;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.linkedin.events.customer.CustomerRegisteredEvent;
import com.linkedin.events.customer.CustomerRemovedEvent;
import com.linkedin.events.order.OrderCompletedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailListeners {

    private final EmailService emailService;

    @EventListener
    public void onRegisterEvent(CustomerRegisteredEvent event) {
        emailService.sendRegisterEmail(event.getCustomer());
    }

    @EventListener
    public void onRemovedEvent(CustomerRemovedEvent event) {
        emailService.sendCustomerRemovedEmail(event.getCustomer());
    }

    // By default, this is set to respond only AFTER_COMMIT
    @TransactionalEventListener
    public void onOrderCompleted(OrderCompletedEvent event) {
        emailService.sendOrderEmail(event.getOrder());
    }
}
