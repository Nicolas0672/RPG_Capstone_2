package com.pluralsight.model.weapon;

import com.pluralsight.model.core.Item;
import com.pluralsight.model.interfaces.Priceable;
import com.pluralsight.model.core.Rarity;
import com.pluralsight.model.enhancement.Enhancement;

import java.util.List;

public abstract class Weapon extends Item implements Priceable {
    private int damage;
    private List<Enhancement> enhancement;
    private final boolean hasAttributes;
    private boolean isSpecial;
    private Double finalCost;

    public Weapon(String name, double baseCost, Rarity rarity, int damage, boolean hasAttributes, Double finalCost) {
        super(name, baseCost, rarity);
        this.damage = damage;
        this.hasAttributes = hasAttributes;
        this.finalCost = finalCost;
        this.isSpecial = false;
    }

    public Double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(Double finalCost) {
        this.finalCost = finalCost;
    }

    public void setSpecial(boolean special) { this.isSpecial = special; }

    public boolean hasSpecial() {
        return isSpecial;
    }

    public boolean isHasAttributes() {
        return hasAttributes;
    }

    public List<Enhancement> getEnhancement() {
        return enhancement;
    }

    public void setEnhancement(List<Enhancement> enhancement) {
        this.enhancement = enhancement;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }
}
