package com.pluralsight.model.weapon;

import com.pluralsight.model.Item;
import com.pluralsight.model.Priceable;
import com.pluralsight.model.Rarity;
import com.pluralsight.model.enhancement.Enhancement;

import java.util.List;

public abstract class Weapon extends Item implements Priceable {
    private int damage;
    private List<Enhancement> enhancement;
    private final boolean hasAttributes;
    private boolean isSpecial;

    public Weapon(String name, double baseCost, Rarity rarity, int damage, boolean hasAttributes) {
        super(name, baseCost, rarity);
        this.damage = damage;
        this.hasAttributes = hasAttributes;
        this.isSpecial = false;
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
