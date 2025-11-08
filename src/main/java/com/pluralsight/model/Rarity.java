package com.pluralsight.model;

public enum Rarity {
    COMMON(1.0),
    RARE(1.5),
    LEGENDARY(2.0);

    private final double multiplier;

    Rarity(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }

    @Override
    public String toString() {
        // Custom display formatting
        return name();
    }
}
