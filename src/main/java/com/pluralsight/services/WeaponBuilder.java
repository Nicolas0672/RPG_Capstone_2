package com.pluralsight.services;

import com.pluralsight.model.interfaces.Priceable;
import com.pluralsight.model.enhancement.*;
import com.pluralsight.model.weapon.Axe;
import com.pluralsight.model.weapon.Katana;
import com.pluralsight.model.weapon.Sword;
import com.pluralsight.model.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;

public class WeaponBuilder {

    private Weapon baseWeapon;
    private final List<Enhancement> enhancements = new ArrayList<>();

    // ---------- Constructors ----------

    public WeaponBuilder(Weapon baseWeapon) {
        this.baseWeapon = baseWeapon;
    }

    public WeaponBuilder() {
    }

    // ---------- Builder Methods ----------

    public WeaponBuilder addEnhancement(Enhancement enhancement) {
        if (enhancement != null) {
            enhancements.add(enhancement);
        }
        return this;
    }

    public Weapon build(boolean special) {
        // Attach enhancements and calculate final cost
        baseWeapon.setEnhancement(enhancements);
        baseWeapon.setFinalCost(getTotalPrice(baseWeapon.getBaseCost(), enhancements));
        baseWeapon.setSpecial(special);
        return baseWeapon;
    }

    // ---------- Utility Methods ----------

    public <T extends Priceable> double getTotalPrice(double baseCost, List<T> priceables) {
        // Sum all enhancement costs and add to base cost
        double total = priceables.stream()
                .mapToDouble(Priceable::calculateCost)
                .sum();
        return baseCost + total;
    }

    public static Weapon cloneWeapon(Weapon original) {
        Weapon copy;

        // Determine weapon type
        if (original instanceof Sword sword) {
            copy = new Sword(sword.getName(), sword.getRarity(), sword.isHasAttributes());
        } else if (original instanceof Axe axe) {
            copy = new Axe(axe.getName(), axe.getRarity(), axe.isHasAttributes());
        } else if (original instanceof Katana katana) {
            copy = new Katana(katana.getName(), katana.getRarity(), katana.isHasAttributes());
        } else {
            throw new IllegalArgumentException("Unknown Weapon");
        }

        // Deep copy enhancements and cost values
        copy.setEnhancement(new ArrayList<>(original.getEnhancement()));
        copy.setBaseCost(original.getBaseCost());
        copy.setFinalCost(original.getFinalCost());

        return copy;
    }

    // ---------- TEST NOTES ----------
    // 1. addEnhancement: null and valid enhancements
    // 2. build: check enhancements, finalCost, and special flag
    // 3. getTotalPrice: sum correctness with empty/multiple enhancements
    // 4. cloneWeapon: deep copy, original remains unchanged
}
