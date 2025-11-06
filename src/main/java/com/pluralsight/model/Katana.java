package com.pluralsight.model;

public class Katana extends Weapon{
    private final boolean poison;
    public Katana(String name, double baseCost, Rarity rarity, int damage, boolean poison) {
        super(name, baseCost, rarity, damage);
        this.poison = poison;
    }

    public boolean isPoison() {
        return poison;
    }
}
