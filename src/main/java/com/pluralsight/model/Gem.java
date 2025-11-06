package com.pluralsight.model;


public class Gem extends Enhancement{
    enum GemType {
        FIRE, ICE, WATER, EARTH
    }
    private final GemType gem;
    public Gem(String name, double baseCost, Rarity rarity, double extraCost, GemType gem) {
        super(name, baseCost, rarity, extraCost);
        this.gem = gem;
    }

    public double calculateCost() {
        return switch (gem) {
            case ICE -> 5;
            case FIRE -> 10;
            case WATER -> 15;
            case EARTH -> 20;
        };
    }


}
