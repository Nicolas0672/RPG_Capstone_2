package com.pluralsight.services;

import com.pluralsight.model.Priceable;
import com.pluralsight.model.enhancement.*;
import com.pluralsight.model.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;

public class WeaponBuilder {
    private final Weapon baseWeapon;
    private final List<Enhancement> enhancements = new ArrayList<>();

    public WeaponBuilder(Weapon baseWeapon) {
        this.baseWeapon = baseWeapon;
    }

    public WeaponBuilder addEnhancement(Enhancement enhancement) {
        enhancements.add(enhancement);
        return this;
    }

    public Weapon build(boolean special) {

        baseWeapon.setEnhancement(enhancements);
        double totalCost = getTotalPrice(baseWeapon.getBaseCost(), enhancements);
        baseWeapon.setSpecial(special);
        return baseWeapon;
    }

    public <T extends Priceable> double getTotalPrice(double baseCost, List<T> priceables){
        double total = priceables.stream()
                .mapToDouble(Priceable::calculateCost)
                .sum();
        return baseCost + total;
    }


}

