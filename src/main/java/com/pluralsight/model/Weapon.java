package com.pluralsight.model;

public abstract class Weapon extends Item{
    private int damage;

    public Weapon(String name, double baseCost, Rarity rarity, int damage) {
        super(name, baseCost, rarity);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }
}
