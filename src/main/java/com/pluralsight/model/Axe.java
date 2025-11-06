package com.pluralsight.model;

public class Axe extends Weapon{
    private final boolean stun;

    public Axe(String name, double baseCost, Rarity rarity, int damage, boolean stun) {
        super(name, baseCost, rarity, damage);
        this.stun = stun;
    }

    public boolean isStun() {
        return stun;
    }
}
