package com.pluralsight.model;

public abstract class Item {
    protected final String name;
    protected double baseCost;
    protected final Rarity rarity;

    public Item(String name, double baseCost, Rarity rarity) {
        this.name = name;
        this.baseCost = baseCost;
        this.rarity = rarity;
    }

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    public String getName() {
        return name;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public Rarity getRarity() {
        return rarity;
    }

}
