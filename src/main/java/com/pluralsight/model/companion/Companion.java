package com.pluralsight.model.companion;

import com.pluralsight.model.interfaces.Priceable;
import com.pluralsight.model.core.Rarity;

public class Companion implements Priceable {
        private final String name;
        private final double price;
        private final Rarity rarity;

    public Companion(String name, double price, Rarity rarity) {
            this.name = name;
            this.price = price;
            this.rarity = rarity;
        }

        @Override
        public double calculateCost() {
            return price * rarity.getMultiplier();
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public Rarity getRarity() {
            return rarity;
        }

}
