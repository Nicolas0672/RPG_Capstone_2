package com.pluralsight.model.potion;

import com.pluralsight.model.Item;
import com.pluralsight.model.Priceable;
import com.pluralsight.model.Rarity;

public class Potion extends Item implements Priceable {

    public Potion(String name, double baseCost, Rarity rarity) {
        super(name, baseCost, rarity);
    }

    @Override
    public double calculateCost() {
        return 0;
    }
}
