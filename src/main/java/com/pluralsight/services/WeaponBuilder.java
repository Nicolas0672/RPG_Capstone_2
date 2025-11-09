package com.pluralsight.services;

import com.pluralsight.model.Priceable;
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

    public WeaponBuilder(Weapon baseWeapon) {
        this.baseWeapon = baseWeapon;
    }

    public WeaponBuilder() {
    }

    public WeaponBuilder addEnhancement(Enhancement enhancement) {
        if(enhancement != null){
            enhancements.add(enhancement);
        }
        return this;
    }

    public Weapon build(boolean special) {

        baseWeapon.setEnhancement(enhancements);
        baseWeapon.setFinalCost(getTotalPrice(baseWeapon.getBaseCost(), enhancements));
        baseWeapon.setSpecial(special);
        return baseWeapon;
    }

    public <T extends Priceable> double getTotalPrice(double baseCost, List<T> priceables){
        double total = priceables.stream()
                .mapToDouble(Priceable::calculateCost)
                .sum();
        return baseCost + total;
    }

    public static Weapon cloneWeapon(Weapon original){
        Weapon copy;
        if(original instanceof Sword sword){
            copy = new Sword(sword.getName(), sword.getRarity(), sword.isHasAttributes());
        } else if(original instanceof Axe axe){
            copy = new Axe(axe.getName(), axe.getRarity(), axe.isHasAttributes());
        } else if(original instanceof Katana katana){
            copy = new Katana(katana.getName(), katana.getRarity(), katana.isHasAttributes());
        } else {
            throw new IllegalArgumentException("Unknown Weapon");
        }
        copy.setEnhancement(new ArrayList<>(original.getEnhancement()));
        copy.setBaseCost(original.getBaseCost());
        copy.setFinalCost(original.getFinalCost());

        return copy;
    }


}

