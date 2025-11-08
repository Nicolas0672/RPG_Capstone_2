package com.pluralsight.services;

import com.pluralsight.model.*;
import com.pluralsight.model.enhancement.Buffs;
import com.pluralsight.model.enhancement.Customization;
import com.pluralsight.model.enhancement.Gem;
import com.pluralsight.model.enhancement.Quirks;
import com.pluralsight.model.potion.Potion;
import com.pluralsight.model.weapon.*;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    List<Weapon> totalWeaponList = new ArrayList<>();
    List<Double> totalPriceOrder = new ArrayList<>();
    List<Potion> potionList = new ArrayList<>();

    private static final List<Weapon> ALL_WEAPONS = List.of(
          new Sword("Sword", Rarity.LEGENDARY, true),
          new Axe("Axe", Rarity.COMMON, false), new Katana("Katana", Rarity.RARE, true)
    );

    public static final List<Buffs> ALL_BUFFS = List.of(
            new Buffs("Forceful Strike", 50, Rarity.COMMON, BuffType.KNOCKBACK),
            new Buffs("Treasure Hunter", 75, Rarity.COMMON, BuffType.LOOTING),
            new Buffs("Everlasting", 60, Rarity.COMMON, BuffType.UNBREAKING),
            new Buffs("Mighty Blow", 80, Rarity.RARE, BuffType.DAMAGE_BOOST),
            new Buffs("Deadly Precision", 70, Rarity.RARE, BuffType.CRITICAL_CHANCE),
            new Buffs("Swift Strikes", 65, Rarity.COMMON, BuffType.ATTACK_SPEED),
            new Buffs("Bloodthirst", 100, Rarity.LEGENDARY, BuffType.LIFESTEAL),
            new Buffs("Armor Piercer", 90, Rarity.LEGENDARY, BuffType.ARMOR_PENETRATION)
    );

    public static final List<Gem> ALL_GEMS = List.of(
            new Gem("Frost Edge", 50, Rarity.COMMON, GemType.ICE),
            new Gem("Blazing Edge", 60, Rarity.COMMON, GemType.FIRE),
            new Gem("Tidal Strike", 70, Rarity.COMMON, GemType.WATER),
            new Gem("Stone Edge", 80, Rarity.RARE, GemType.EARTH),
            new Gem("Thunder Slash", 100, Rarity.RARE, GemType.LIGHTNING),
            new Gem("Venom Blade", 120, Rarity.LEGENDARY, GemType.POISON)
    );

    public static final List<Quirks> ALL_QUIRKS = List.of(
            new Quirks("Balanced Edge", 10, Rarity.COMMON, BonusType.WEIGHTED_EDGE),
            new Quirks("Reinforced Grip", 12, Rarity.COMMON, BonusType.REINFORCED_HILT),
            new Quirks("Elemental Residue", 15, Rarity.LEGENDARY, BonusType.ELEMENTAL_RESIDUE),
            new Quirks("Flexible Grip", 8, Rarity.COMMON, BonusType.FLEXIBLE_GRIP),
            new Quirks("Silent Swing", 5, Rarity.COMMON, BonusType.SILENT_SWING),
            new Quirks("Vibration Tuning", 12, Rarity.RARE, BonusType.VIBRATION_TUNING)
    );

    public static final List<Potion> ALL_POTIONS = List.of(
            new Potion("Healing Potion", 10, Rarity.COMMON),
            new Potion("Strength Potion", 15, Rarity.COMMON),
            new Potion("Speed Potion", 12, Rarity.COMMON),
            new Potion("Fire Resistance Potion", 18, Rarity.RARE),
            new Potion("Invisibility Potion", 25, Rarity.RARE),
            new Potion("Night Vision Potion", 20, Rarity.RARE),
            new Potion("Regeneration Potion", 30, Rarity.LEGENDARY)
    );


    public static final List<Customization> ALL_CUSTOMIZATIONS = List.of(
            new Customization("Arcane Glow", 0, Rarity.COMMON, CustomizationType.GLOW_EFFECT),
            new Customization("Royal Engravings", 0, Rarity.RARE, CustomizationType.ENGRAVED_SYMBOLS),
            new Customization("Crimson Grip", 0, Rarity.COMMON, CustomizationType.WRAPPED_HANDLE),
            new Customization("Hero’s Mark", 0, Rarity.RARE, CustomizationType.WEAPON_INSCRIPTION),
            new Customization("Flame Trail", 0, Rarity.LEGENDARY, CustomizationType.TRAIL_EFFECT),
            new Customization("Mirror Finish", 0, Rarity.COMMON, CustomizationType.SHEEN_FINISH)
    );

    public List<Customization> getAllCustomizations(){
        return ALL_CUSTOMIZATIONS;
    }

    public List<Potion> getAllPotions(){
        return ALL_POTIONS;
    }

    public List<Gem> getAllGems(){
        return ALL_GEMS;
    }

    public List<Quirks> getAllQuirks(){
        return ALL_QUIRKS;
    }


    public List<Buffs> getAllBuffs() {
        return ALL_BUFFS;
    }

    public List<Weapon> getAllWeapons() {
        return ALL_WEAPONS;
    }

    public void addWeaponToCart(Weapon finalWeapon){
        totalWeaponList.add(finalWeapon);
        totalPriceOrder.add(finalWeapon.getBaseCost());
    }

    public void addPotionToCart(Potion potion){
        potionList.add(potion);
        totalPriceOrder.add(potion.getBaseCost());
    }



    public List<Weapon> getTotalWeaponList() {
        return totalWeaponList;
    }

    public Weapon weaponBuild(String name, Rarity rarity, boolean hasSpecialAttributes){
        if(name.equalsIgnoreCase("sword")){
            return new Sword("Sword", rarity, hasSpecialAttributes);
        } else if(name.equalsIgnoreCase("axe")){
            return new Axe("Axe", rarity, hasSpecialAttributes);
        } else if(name.equalsIgnoreCase("Katana")){
            return new Katana("Katana", rarity, hasSpecialAttributes);
        } else {
            System.out.println("Invalid weapon");
            return null;
        }
    }
}
