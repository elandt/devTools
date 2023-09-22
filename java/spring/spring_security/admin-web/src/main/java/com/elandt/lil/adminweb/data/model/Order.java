package com.elandt.lil.adminweb.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="order_id")
    private long id;
    @Column(name="customer_id")
    private long customerId;
    @Column(name="order_info")
    private String orderInfo;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Order{");
        sb.append("id=").append(id);
        sb.append(", customerId=").append(customerId);
        sb.append(", orderInfo='").append(orderInfo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
