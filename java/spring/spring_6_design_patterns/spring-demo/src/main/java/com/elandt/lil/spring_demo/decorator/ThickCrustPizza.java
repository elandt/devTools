package com.elandt.lil.spring_demo.decorator;

import java.math.BigDecimal;

public class ThickCrustPizza extends Pizza {

    public ThickCrustPizza() {
        super();
        this.description = "Thick Crust Pizza";
    }

    @Override
    public BigDecimal getCost() {
        return BigDecimal.valueOf(15.00);
    }
}
