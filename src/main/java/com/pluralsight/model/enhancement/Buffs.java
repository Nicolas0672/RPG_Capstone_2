package com.pluralsight.model.enhancement;

import com.pluralsight.model.BuffType;
import com.pluralsight.model.Rarity;

public class Buffs extends Enhancement {

    private BuffType type;

    public Buffs(String name, double baseCost, Rarity rarity, BuffType type) {
        super(name, baseCost, rarity);
        this.type = type;
    }

    public double calculateCost() {
        return switch (type) {
            case KNOCKBACK -> 5;
            case UNBREAKING -> 10;
            case LOOTING -> 15;
            case DAMAGE_BOOST -> 12;
            case CRITICAL_CHANCE -> 8;
            case ATTACK_SPEED -> 7;
            case LIFESTEAL -> 20;
            case ARMOR_PENETRATION -> 18;
        };
    }

    @Override
    public String getDescription() {
        return switch (type) {
            case KNOCKBACK -> "Increases chance to push enemies back on hit.";
            case LOOTING -> "Increases the amount or quality of loot dropped.";
            case UNBREAKING -> "Reduces durability loss, making the weapon last longer.";
            case DAMAGE_BOOST -> "Adds extra damage to each attack.";
            case CRITICAL_CHANCE -> "Increases chance of a critical hit.";
            case ATTACK_SPEED -> "Makes attacks faster.";
            case LIFESTEAL -> "Steals a small amount of health from enemies on hit.";
            case ARMOR_PENETRATION -> "Ignores a portion of the enemy's armor.";
        };
    }

    // Optional: getter for type if needed
    public BuffType getType() {
        return type;
    }
}
