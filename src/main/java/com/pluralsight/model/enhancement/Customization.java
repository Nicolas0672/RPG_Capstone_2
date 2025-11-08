package com.pluralsight.model.enhancement;

import com.pluralsight.model.CustomizationType;
import com.pluralsight.model.Rarity;

public class Customization extends Enhancement {

    private final CustomizationType type;

    public Customization(String name, double baseCost, Rarity rarity, CustomizationType type) {
        super(name, baseCost, rarity);
        this.type = type;
    }

    public double calculateCost() {
        return switch (type) {
            case GLOW_EFFECT -> 0;
            case ENGRAVED_SYMBOLS -> 0;
            case WRAPPED_HANDLE -> 0;
            case WEAPON_INSCRIPTION -> 0;
            case TRAIL_EFFECT -> 0;
            case SHEEN_FINISH -> 0;
        };
    }

    @Override
    public String getDescription() {
        return switch (type) {
            case GLOW_EFFECT -> "Adds a faint magical glow.";
            case ENGRAVED_SYMBOLS -> "Etched with runes or sigils.";
            case WRAPPED_HANDLE -> "Custom leather grip wrap.";
            case WEAPON_INSCRIPTION -> "Carved name or motto.";
            case TRAIL_EFFECT -> "Leaves a trail on swing.";
            case SHEEN_FINISH -> "Polished or matte finish.";
        };
    }

    public CustomizationType getType() {
        return type;
    }
}
