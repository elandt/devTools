package com.elandt.lil.spring_demo.decorator;

import java.math.BigDecimal;

public abstract class Pizza {
    protected String description;

    public String getDescription() {
        return this.description;
    }

    public abstract BigDecimal getCost();
}
