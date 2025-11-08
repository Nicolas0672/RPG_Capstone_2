package com.pluralsight.model.weapon;

import com.pluralsight.model.Rarity;

public class Sword extends Weapon{

    public Sword(String name, Rarity rarity, boolean bleed) {
        super(name, 25, rarity, 30, bleed, null);
    }

    @Override
    public double calculateCost() {
        return getBaseCost() * rarity.getMultiplier();
    }

}
