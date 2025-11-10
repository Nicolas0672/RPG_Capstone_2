package com.pluralsight.model.potion;

import com.pluralsight.model.core.Item;
import com.pluralsight.model.interfaces.Priceable;
import com.pluralsight.model.core.Rarity;

public class Potion extends Item implements Priceable {

    public Potion(String name, double baseCost, Rarity rarity) {
        super(name, baseCost, rarity);
    }

    @Override
    public double calculateCost() {
        return 0;
    }
}
