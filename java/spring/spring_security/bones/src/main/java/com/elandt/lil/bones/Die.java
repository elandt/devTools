package com.elandt.lil.bones;

import java.util.Random;

import lombok.Getter;

@Getter
public class Die {

    private int value;

    public void roll() {
        Random random = new Random();
        value = random.nextInt(6) + 1;
    }
}
