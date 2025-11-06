package com.pluralsight.model;

public class Sword extends Weapon{
    private boolean bleed;

    public Sword(String name, double baseCost, Rarity rarity, int damage, boolean bleed) {
        super(name, baseCost, rarity, damage);
        this.bleed = bleed;
    }

    public boolean isBleed() {
        return bleed;
    }
}
