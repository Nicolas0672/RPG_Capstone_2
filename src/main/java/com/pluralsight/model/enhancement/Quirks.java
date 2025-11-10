package com.pluralsight.model.enhancement;

import com.pluralsight.model.core.BonusType;
import com.pluralsight.model.core.Rarity;

public class Quirks extends Enhancement {

    private final BonusType bonus;

    public Quirks(String name, double baseCost, Rarity rarity, BonusType bonus) {
        super(name, baseCost, rarity);
        this.bonus = bonus;
    }

    public double calculateCost() {
        return switch (bonus) {
            case WEIGHTED_EDGE -> 2;
            case REINFORCED_HILT -> 3;
            case ELEMENTAL_RESIDUE -> 4;
            case FLEXIBLE_GRIP -> 2;
            case SILENT_SWING -> 1;
            case VIBRATION_TUNING -> 3;
        };
    }

    @Override
    public String getDescription() {
        return switch (bonus) {
            case WEIGHTED_EDGE -> "Slower swings, but increases chance to stagger enemies.";
            case REINFORCED_HILT -> "Weapon loses less durability when used.";
            case ELEMENTAL_RESIDUE -> "Adds a tiny elemental effect even without a gem.";
            case FLEXIBLE_GRIP -> "Improves handling, slightly increases attack speed.";
            case SILENT_SWING -> "Attacks are quieter, useful for stealth.";
            case VIBRATION_TUNING -> "Small chance to hit multiple enemies in one swing.";
        };
    }

    public BonusType getBonusType() {
        return bonus;
    }
}
