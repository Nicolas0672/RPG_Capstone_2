package com.pluralsight.model;

public abstract class Enhancement extends Item{
    private final double extraCost;

    public Enhancement(String name, double baseCost, Rarity rarity, double extraCost) {
        super(name, baseCost, rarity);
        this.extraCost = extraCost;
    }

    public double getExtraCost() {
        return extraCost;
    }
}
