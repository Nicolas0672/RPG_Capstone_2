package com.pluralsight.model.enhancement;

import com.pluralsight.model.Priceable;
import com.pluralsight.model.Rarity;

public abstract class Enhancement implements Priceable, Describable {
    private final String name;
    private final Rarity rarity;
    private double baseCost;

    public Enhancement(String name,double baseCost, Rarity rarity) {
        this.name = name;
        this.rarity = rarity;
        this.baseCost = baseCost;
    }

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }
}
