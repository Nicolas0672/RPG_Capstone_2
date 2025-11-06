package com.pluralsight.model;

public abstract class Item {
    protected final String name;
    protected final double baseCost;
    protected final Rarity rarity;

    public Item(String name, double baseCost, Rarity rarity) {
        this.name = name;
        this.baseCost = baseCost;
        this.rarity = rarity;
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

    public double calculatePrice() {
        return switch(rarity){
            case COMMON -> baseCost * 1;
            case RARE -> baseCost * 1.5;
            case LEGENDARY -> baseCost * 2;
        };
    }
}
