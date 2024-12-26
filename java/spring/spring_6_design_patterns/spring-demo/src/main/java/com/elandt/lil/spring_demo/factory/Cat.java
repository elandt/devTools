package com.elandt.lil.spring_demo.factory;

public class Cat implements Pet {

    private String name;
    private boolean hungry;

    public Cat() {
        this.hungry = true;
    }

    @Override
    public void setName(String name) {
       this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isHungry() {
        return hungry;
    }

    @Override
    public void feed() {
        this.hungry = false;
    }

    @Override
    public String getType() {
        return "CAT";
    }
}
