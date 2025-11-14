package com.pluralsight.services;

import com.pluralsight.model.core.BuffType;
import com.pluralsight.model.core.Rarity;
import com.pluralsight.model.enhancement.Buffs;
import com.pluralsight.model.enhancement.Enhancement;
import com.pluralsight.model.weapon.Sword;
import com.pluralsight.model.weapon.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponBuilderTest {
    private Weapon baseWeapon;
    private WeaponBuilder builder;

    @BeforeEach
    void setUp() {
        baseWeapon = new Sword("Excalibur", Rarity.LEGENDARY, false);
        builder = new WeaponBuilder(baseWeapon);
    }

    @Test
    void addEnhancement() {
        Enhancement buff = new Buffs("Stronger will", 20, Rarity.RARE, BuffType.DAMAGE_BOOST);
        builder.addEnhancement(buff);

        List<Enhancement> enhancements = builder.build(false).getEnhancement();
        assertTrue(enhancements.contains(buff), "Enhancement should be added");

        builder.addEnhancement(null);
        assertEquals(1, enhancements.size(), "Null enhancement should not be added");
    }

    @Test
    void build() {
        Enhancement buff1 = new Buffs("Fire will", 20, Rarity.RARE, BuffType.DAMAGE_BOOST);
        Enhancement buff2 = new Buffs("Stronger will", 30, Rarity.LEGENDARY, BuffType.ARMOR_PENETRATION);

        builder.addEnhancement(buff1).addEnhancement(buff2);
        Weapon builtWeapon = builder.build(true);

        assertEquals(List.of(buff1, buff2), builtWeapon.getEnhancement(), "Enhancement should match");
        assertTrue(baseWeapon.hasSpecial(), "Weapon should be special");

        double expectedPrice = baseWeapon.getBaseCost() + buff1.getBaseCost() + buff2.getBaseCost();
        assertEquals(expectedPrice, baseWeapon.getFinalCost(), "Price should be same");
    }

   @Test
    void getTotalPrice() {
       Enhancement buff1 = new Buffs("Fire will", 20, Rarity.RARE, BuffType.DAMAGE_BOOST);
       Enhancement buff2 = new Buffs("Stronger will", 30, Rarity.LEGENDARY, BuffType.ARMOR_PENETRATION);

       double totalPrice = builder.getTotalPrice(baseWeapon.getBaseCost(), List.of(buff1, buff2));
       assertEquals(baseWeapon.getBaseCost() + buff1.calculateCost() + buff2.calculateCost(), totalPrice, "Price should be same");

       double totalEmpty = builder.getTotalPrice(baseWeapon.getBaseCost(), List.of());
       assertEquals(baseWeapon.getBaseCost(), totalEmpty, 0.001);
   }

    @Test
    void cloneWeapon() {
        Enhancement buff1 = new Buffs("Fire will", 20, Rarity.RARE, BuffType.DAMAGE_BOOST);
        Enhancement buff2 = new Buffs("Stronger will", 30, Rarity.LEGENDARY, BuffType.ARMOR_PENETRATION);
        baseWeapon.setEnhancement(List.of(buff1, buff2));
        baseWeapon.setBaseCost(40);
        baseWeapon.setFinalCost(50.0);

        Weapon cloned = WeaponBuilder.cloneWeapon(baseWeapon);

        assertNotSame(baseWeapon, cloned, "Cloned weapon should be a different object");
        assertEquals(baseWeapon.getBaseCost(), cloned.getBaseCost(), "Base cost should be equal");
        assertEquals(baseWeapon.getFinalCost(), cloned.getFinalCost(), "Final cost should be equal");
        assertEquals(baseWeapon.getEnhancement(), cloned.getEnhancement(), "Enhancements should match");

        // Changing the clone should not affect original
        cloned.getEnhancement().clear();
        assertFalse(baseWeapon.getEnhancement().isEmpty(), "Original enhancements should remain intact");

    }
}