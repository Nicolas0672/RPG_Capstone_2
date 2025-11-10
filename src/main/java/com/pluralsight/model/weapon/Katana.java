package com.pluralsight.model.weapon;

import com.pluralsight.model.core.Rarity;

public class Katana extends Weapon{

    public Katana(String name, Rarity rarity, boolean poison) {
        super(name, 30, rarity, 35, poison, null);
    }

    @Override
    public double calculateCost() {
        return getBaseCost() * rarity.getMultiplier();
    }
}
