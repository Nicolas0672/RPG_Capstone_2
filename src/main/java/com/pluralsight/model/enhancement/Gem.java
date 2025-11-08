package com.pluralsight.model.enhancement;

import com.pluralsight.model.GemType;
import com.pluralsight.model.Rarity;

public class Gem extends Enhancement {

    private final GemType gem;

    public Gem(String name, double baseCost, Rarity rarity, GemType gem) {
        super(name, baseCost, rarity);
        this.gem = gem;
    }

    public double calculateCost() {
        return switch (gem) {
            case ICE -> 5;
            case FIRE -> 10;
            case WATER -> 15;
            case EARTH -> 20;
            case LIGHTNING -> 25;
            case POISON -> 30;
        };
    }

    @Override
    public String getDescription() {
        String description = switch (gem) {
            case ICE -> "Adds ice damage to the weapon, slowing enemies on hit.";
            case FIRE -> "Adds fire damage, burning enemies over time.";
            case WATER -> "Adds water damage, may reduce enemy fire resistance.";
            case EARTH -> "Adds earth damage, can slightly stun enemies.";
            case LIGHTNING -> "Adds lightning damage, with a chance to chain to nearby enemies.";
            case POISON -> "Adds poison damage over time to enemies.";
        };
       return description;
    }

    public GemType getGemType() {
        return gem;
    }
}
