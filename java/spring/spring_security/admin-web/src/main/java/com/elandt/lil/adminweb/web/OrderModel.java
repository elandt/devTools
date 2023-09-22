package com.elandt.lil.adminweb.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderModel {

    private long orderId;
    private String customer;
    private long customerId;
    private String orderDetails;
}
