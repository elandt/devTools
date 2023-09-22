package com.elandt.lil.bones;

import lombok.Getter;

@Getter
public class DicePairRoll {
    private Die die1 = new Die();
    private Die die2 = new Die();
    private int total;

    public void rollDice() {
        die1.roll();
        die2.roll();
        total = die1.getValue() + die2.getValue();
    }
}
