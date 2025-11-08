package com.pluralsight.model.weapon;

import com.pluralsight.model.Rarity;

public class Axe extends Weapon{

    public Axe(String name, Rarity rarity, boolean stun) {
        super(name, 15, rarity, 18, stun, null);
    }

    @Override
    public double calculateCost() {
        return getBaseCost() * rarity.getMultiplier();
    }
}
