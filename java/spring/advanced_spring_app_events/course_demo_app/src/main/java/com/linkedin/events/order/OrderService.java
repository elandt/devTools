package com.linkedin.events.order;

import static com.linkedin.events.order.Order.OrderStatus.COMPLETED;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderService
{
    private final OrderRepository orderRepository;

    private final ApplicationEventPublisher publisher;

    @Transactional
    public void placeOrder(Order order)
    {
        log.info("Placing and order {}", order);
        order.setStatus(COMPLETED);
        orderRepository.save(order);

        log.info("Publishing order completed event");
        publisher.publishEvent(new OrderCompletedEvent(order));
    }
}
