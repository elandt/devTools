package com.elandt.lil.spring_demo.decorator;

import java.math.BigDecimal;

public class Sausage extends PizzaIngredient {

    private Pizza pizza;

    public Sausage(Pizza pizza) {
        super();
        this.pizza = pizza;
    }

    @Override
    public String getDescription() {
        return this.pizza.getDescription() + " + sausage";
    }

    @Override
    public BigDecimal getCost() {
        return BigDecimal.valueOf(1.00).add(this.pizza.getCost());
    }

}
