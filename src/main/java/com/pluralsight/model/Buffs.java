package com.pluralsight.model;

public class Buffs extends Enhancement{

    enum BuffType{
        KNOCKBACK, LOOTING, UNBREAKING
    }
    BuffType type;

    public Buffs(String name, double baseCost, Rarity rarity, double extraCost, BuffType type) {
        super(name, baseCost, rarity, extraCost);
        this.type = type;
    }

    public double calculateCost() {
        return switch (type) {
            case KNOCKBACK -> 5;
            case UNBREAKING -> 10;
            case LOOTING -> 1;
        };
    }


}
