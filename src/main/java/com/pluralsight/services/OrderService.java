package com.pluralsight.services;

import com.pluralsight.model.*;
import com.pluralsight.model.companion.*;
import com.pluralsight.model.enhancement.*;
import com.pluralsight.model.potion.Potion;
import com.pluralsight.model.weapon.*;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final List<Weapon> totalWeaponList = new ArrayList<>();
    private final List<Double> totalPriceOrder = new ArrayList<>();
    private final List<Potion> potionList = new ArrayList<>();
    private final List<Companion> companionList = new ArrayList<>();

    // ⚔️ ALL WEAPONS
    private static final List<Weapon> ALL_WEAPONS = List.of(
            new Sword("⚔️ Sword", Rarity.LEGENDARY, true),
            new Axe("🪓 Axe", Rarity.COMMON, false),
            new Katana("🗡️ Katana", Rarity.RARE, true)
    );

    // 💫 ALL BUFFS
    public static final List<Buffs> ALL_BUFFS = List.of(
            new Buffs("💥 Forceful Strike", 5, Rarity.COMMON, BuffType.KNOCKBACK),
            new Buffs("💎 Treasure Hunter", 15, Rarity.COMMON, BuffType.LOOTING),
            new Buffs("🛡️ Everlasting", 10, Rarity.COMMON, BuffType.UNBREAKING)
    );

    // 🔮 ALL GEMS
    public static final List<Gem> ALL_GEMS = List.of(
            new Gem("❄️ Frost Edge", 5, Rarity.COMMON, GemType.ICE),
            new Gem("🔥 Blazing Edge", 10, Rarity.COMMON, GemType.FIRE),
            new Gem("🌊 Tidal Strike", 15, Rarity.COMMON, GemType.WATER)
    );

    // ⚡ ALL QUIRKS
    public static final List<Quirks> ALL_QUIRKS = List.of(
            new Quirks("⚖️ Balanced Edge", 2, Rarity.COMMON, BonusType.WEIGHTED_EDGE),
            new Quirks("✊ Reinforced Grip", 3, Rarity.COMMON, BonusType.REINFORCED_HILT),
            new Quirks("🌈 Elemental Residue", 4, Rarity.LEGENDARY, BonusType.ELEMENTAL_RESIDUE)
    );

    // 🧪 ALL POTIONS
    public static final List<Potion> ALL_POTIONS = List.of(
            new Potion("❤️ Healing Potion", 10, Rarity.COMMON),
            new Potion("💪 Strength Potion", 15, Rarity.COMMON),
            new Potion("⚡ Speed Potion", 12, Rarity.COMMON),
            new Potion("🔥 Fire Resistance Potion", 18, Rarity.RARE)
    );

    // 🐾 ALL COMPANIONS
    public static final List<Companion> ALL_COMPANIONS = List.of(
            new Dog("🐶 Dog", 5, Rarity.COMMON),
            new Horse("🐎 Horse", 20, Rarity.COMMON),
            new Owl("🦉 Owl", 35, Rarity.RARE),
            new Dragon("🐉 Dragon", 50, Rarity.LEGENDARY)
    );

    // 🎨 ALL CUSTOMIZATIONS
    public static final List<Customization> ALL_CUSTOMIZATIONS = List.of(
            new Customization("💫 Arcane Glow", 0, Rarity.COMMON, CustomizationType.GLOW_EFFECT),
            new Customization("👑 Royal Engravings", 0, Rarity.RARE, CustomizationType.ENGRAVED_SYMBOLS),
            new Customization("🩸 Crimson Grip", 0, Rarity.COMMON, CustomizationType.WRAPPED_HANDLE)
    );

    // ===========================================
    //           GETTERS FOR EACH TYPE
    // ===========================================

    public List<Customization> getAllCustomizations(){ return ALL_CUSTOMIZATIONS; }
    public List<List<Priceable>> getAllTypeOfOrders() { return ALL_TYPE_OF_ORDERS; }
    public List<Potion> getAllPotions(){ return ALL_POTIONS; }
    public List<Gem> getAllGems(){ return ALL_GEMS; }
    public List<Quirks> getAllQuirks(){ return ALL_QUIRKS; }
    public List<Double> getTotalPriceOrder() { return totalPriceOrder; }
    public List<Potion> getPotionList() { return potionList; }
    public List<Companion> getCompanionList() { return companionList; }
    public List<Buffs> getAllBuffs() { return ALL_BUFFS; }
    public List<Weapon> getAllWeapons() { return ALL_WEAPONS; }
    public List<Companion> getAllCompanions(){ return ALL_COMPANIONS; }

    // ===========================================
    //              CART LOGIC
    // ===========================================

    public void addWeaponToCart(Weapon finalWeapon){
        totalWeaponList.add(finalWeapon);
        totalPriceOrder.add(finalWeapon.getFinalCost());
    }

    public void addPotionToCart(Potion potion){
        potionList.add(potion);
        totalPriceOrder.add(potion.getBaseCost());
    }

    public void addCompanionToCart(Companion companion){
        companionList.add(companion);
        totalPriceOrder.add(companion.getPrice());
    }

    public List<Weapon> getTotalWeaponList() { return totalWeaponList; }

    public void clearCart(){
        totalWeaponList.clear();
        companionList.clear();
        totalPriceOrder.clear();
        potionList.clear();
    }

    public double getTotalCartPrice(){
        return totalPriceOrder.stream().reduce(0.0, Double::sum);
    }

    // ===========================================
    //           ENHANCEMENT MANAGEMENT
    // ===========================================

    public static void removeEnhancementFromWeapon(Weapon w, String enhancementName, String type){
        List<Enhancement> enhancementList = new ArrayList<>(w.getEnhancement());
        switch (type){
            case "gem" -> enhancementList.removeIf(e -> e instanceof Gem && e.getName().equalsIgnoreCase(enhancementName));
            case "buff" -> enhancementList.removeIf(b -> b instanceof Buffs && b.getName().equalsIgnoreCase(enhancementName));
            case "quirk" -> enhancementList.removeIf(q -> q instanceof Quirks && q.getName().equalsIgnoreCase(enhancementName));
            case "customization" -> enhancementList.removeIf(c -> c instanceof Customization && c.getName().equalsIgnoreCase(enhancementName));
        }
        w.setEnhancement(enhancementList);
    }

    public void removePotionFromCart(Potion potion){
        potionList.remove(potion);
        totalPriceOrder.remove(potion.getBaseCost());
    }

    public static void addEnhancementToWeapon(Weapon weapon, Enhancement enhancement){
        List<Enhancement> enhancementList = weapon.getEnhancement();
        List<Enhancement> newEnhancementList = new ArrayList<>();
        for(Enhancement enhancement1 : enhancementList){
            String name = enhancement1.getName();
            if(!enhancement.getName().equalsIgnoreCase(name)){
                newEnhancementList.add(enhancement1);
            }
        }
        newEnhancementList.add(enhancement);
        weapon.setEnhancement(newEnhancementList);
    }

    // ===========================================
    //              WEAPON BUILDER
    // ===========================================

    public Weapon weaponBuild(String name, Rarity rarity, boolean hasSpecialAttributes){

        if(name.equalsIgnoreCase("⚔️ Sword")){
            Sword sword=  new Sword("⚔️ Sword", rarity, hasSpecialAttributes);
            sword.setBaseCost(sword.calculateCost());
            return sword;
        } else if(name.equalsIgnoreCase("🪓 Axe")){
            Axe axe =  new Axe("🪓 Axe", rarity, hasSpecialAttributes);
            axe.setBaseCost(axe.calculateCost());
            return axe;
        } else if(name.equalsIgnoreCase("🗡️ Katana")){
            Katana katana = new Katana("🗡️ Katana", rarity, hasSpecialAttributes);
            katana.setBaseCost(katana.calculateCost());
            return katana;
        } else {
            System.out.println("Invalid weapon");
            return null;
        }
    }

    // ===========================================
    //         EXISTING PREMADE ORDERS
    // ===========================================

    private static final List<List<Priceable>> ALL_TYPE_OF_ORDERS = createExistingOrders();

    private static List<List<Priceable>> createExistingOrders() {
        List<List<Priceable>> orderList = new ArrayList<>();
        WeaponBuilder weaponBuilder = new WeaponBuilder();

        // 1️⃣ FLAMEBRINGER — Sword with fire theme
        Weapon flamebringer = new Sword("🔥 Flamebringer (LEGENDARY)", Rarity.LEGENDARY, true);
        flamebringer.setEnhancement(List.of(
                new Buffs("💥 Mighty Blow", 13, Rarity.RARE, BuffType.DAMAGE_BOOST),
                new Gem("🔥 Blazing Edge", 10, Rarity.COMMON, GemType.FIRE)
        ));
        flamebringer.setBaseCost(flamebringer.calculateCost());
        flamebringer.setDamage(40);
        flamebringer.setFinalCost(weaponBuilder.getTotalPrice(flamebringer.getBaseCost(), flamebringer.getEnhancement()));

        Potion flamePotion = new Potion("🔥 Fire Resistance Potion", 10, Rarity.RARE);
        Companion horse = new Horse("🐎 Horse", 30, Rarity.RARE);
        orderList.add(List.of(flamebringer, flamePotion, horse));

        // 2️⃣ FROSTBITE — Axe with ice theme
        Weapon frostbite = new Axe("❄️ Frostbite (RARE)", Rarity.RARE, true);
        frostbite.setEnhancement(List.of(
                new Buffs("⚡ Swift Strikes", 20, Rarity.COMMON, BuffType.ATTACK_SPEED),
                new Gem("❄️ Frost Edge", 5, Rarity.COMMON, GemType.ICE),
                new Customization("✨ Mirror Finish", 0, Rarity.COMMON, CustomizationType.SHEEN_FINISH)
        ));
        frostbite.setBaseCost(frostbite.calculateCost());
        frostbite.setDamage(30);
        frostbite.setFinalCost(weaponBuilder.getTotalPrice(frostbite.getBaseCost(), frostbite.getEnhancement()));

        Potion potion = new Potion("💨 Swift Potion", 15, Rarity.COMMON);
        Companion dog = new Dog("🐶 Dog", 5, Rarity.COMMON);
        orderList.add(List.of(frostbite, potion, dog));

        // 3️⃣ NIGHTSTALKER — Katana with stealth theme
        Weapon nightstalker = new Katana("🌑 Nightstalker (LEGENDARY)", Rarity.LEGENDARY, true);
        nightstalker.setEnhancement(List.of(
                new Buffs("🎯 Deadly Precision", 30, Rarity.RARE, BuffType.CRITICAL_CHANCE)
        ));
        nightstalker.setDamage(60);
        nightstalker.setBaseCost(nightstalker.calculateCost());
        nightstalker.setFinalCost(weaponBuilder.getTotalPrice(nightstalker.getBaseCost(), nightstalker.getEnhancement()));

        Potion nightVision = new Potion("🌙 Night Resistance", 25, Rarity.LEGENDARY);
        Companion dragon = new Dragon("🐉 Dragon", 50, Rarity.LEGENDARY);
        orderList.add(List.of(nightstalker, nightVision, dragon));

        return orderList;
    }

    public void removeCompanionFromCart(Companion companion) {
        companionList.remove(companion);
        totalPriceOrder.remove(companion.getPrice());
    }
}

/*
=============================
       JUnit TEST SUGGESTIONS
=============================

1️⃣ Cart Total Calculation:
   - getTotalCartPrice
       • Verify sum matches all items in cart (weapons, potions, companions).

2️⃣ Enhancement Management:
   - removeEnhancementFromWeapon
       • Test removal for each enhancement type (gem, buff, quirk, customization).
       • Ensure weapon's enhancement list updates correctly.
   - addEnhancementToWeapon
       • Test adding enhancements and prevent duplicates.

3️⃣ Weapon Builder:
   - weaponBuild
       • Test creation of Sword, Axe, Katana with correct baseCost and attributes.
       • Test invalid weapon name returns null.

4️⃣ Pre-made Orders:
   - createExistingOrders / ALL_TYPE_OF_ORDERS
       • Verify each pre-made order contains correct types (Weapon, Potion, Companion).
       • Verify weapon finalCost = baseCost + enhancements.
*/

