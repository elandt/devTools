package com.elandt.lil.spring_demo.factory;

public interface Pet {

    void setName(String name);

    String getName();

    boolean isHungry();

    void feed();

    String getType();
}
