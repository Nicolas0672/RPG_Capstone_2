package com.pluralsight.services;

import com.pluralsight.model.core.BuffType;
import com.pluralsight.model.core.GemType;
import com.pluralsight.model.interfaces.Priceable;
import com.pluralsight.model.core.Rarity;
import com.pluralsight.model.enhancement.Buffs;
import com.pluralsight.model.enhancement.Gem;
import com.pluralsight.model.weapon.Sword;
import com.pluralsight.model.weapon.Weapon;
import com.pluralsight.model.potion.Potion;
import com.pluralsight.model.companion.Dog;
import com.pluralsight.model.companion.Companion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService orderService;
    private Weapon sword;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
        sword = new Sword("⚔️ Sword", Rarity.COMMON, false);
        sword.setBaseCost(20);
        sword.setFinalCost(sword.calculateCost());
    }

    // ------------------------------
    // Cart total calculation
    // ------------------------------
    @Test
    void testGetTotalCartPrice() {
        orderService.addWeaponToCart(sword);
        Potion potion = new Potion("Healing Potion", 10, Rarity.COMMON);
        orderService.addPotionToCart(potion);
        Dog dog = new Dog("Dog", 5, Rarity.COMMON);
        orderService.addCompanionToCart(dog);

        double expected = sword.getBaseCost() + 10 + 5;
        assertEquals(expected, orderService.getTotalCartPrice(), "Price should match");
    }

    // ------------------------------
    // Enhancement management
    // ------------------------------
    @Test
    void testAddEnhancementToWeapon() {
        Buffs buff = new Buffs("💥 Mighty Blow", 10, Rarity.COMMON, BuffType.DAMAGE_BOOST);
        Gem gem = new Gem("🔥 Blazing Edge", 15, Rarity.COMMON, GemType.FIRE);

        // Add buff
        OrderService.addEnhancementToWeapon(sword, buff);
        assertTrue(sword.getEnhancement().contains(buff));

        // Adding same name again should replace old one
        Buffs duplicateBuff = new Buffs("💥 Mighty Blow", 20, Rarity.RARE, BuffType.DAMAGE_BOOST);
        OrderService.addEnhancementToWeapon(sword, duplicateBuff);
        assertTrue(sword.getEnhancement().contains(duplicateBuff));
        assertEquals(1, sword.getEnhancement().stream().filter(e -> e.getName().equals("💥 Mighty Blow")).count());

        // Add gem
        OrderService.addEnhancementToWeapon(sword, gem);
        assertTrue(sword.getEnhancement().contains(gem));
    }

    @Test
    void testRemoveEnhancementFromWeapon() {
        Buffs buff = new Buffs("💥 Mighty Blow", 10, Rarity.COMMON, BuffType.DAMAGE_BOOST);
        Gem gem = new Gem("🔥 Blazing Edge", 15, Rarity.COMMON, GemType.FIRE);
        sword.setEnhancement(List.of(buff, gem));

        // Remove buff
        OrderService.removeEnhancementFromWeapon(sword, "💥 Mighty Blow", "buff");
        assertFalse(sword.getEnhancement().contains(buff));
        assertTrue(sword.getEnhancement().contains(gem));

        // Remove gem
        OrderService.removeEnhancementFromWeapon(sword, "🔥 Blazing Edge", "gem");
        assertFalse(sword.getEnhancement().contains(gem));
    }

    // ------------------------------
    // Weapon builder
    // ------------------------------
    @Test
    void testWeaponBuild() {
        Weapon builtSword = orderService.weaponBuild("⚔️ Sword", Rarity.LEGENDARY, true);
        assertNotNull(builtSword);
        assertInstanceOf(Sword.class, builtSword);
        assertEquals(Rarity.LEGENDARY, builtSword.getRarity());

        Weapon invalid = orderService.weaponBuild("Nonexistent", Rarity.COMMON, false);
        assertNull(invalid);
    }

    // ------------------------------
    // Pre-made orders
    // ------------------------------
    @Test
    void testAllTypeOfOrders() {
        List<List<Priceable>> orders = orderService.getAllTypeOfOrders();
        assertFalse(orders.isEmpty(), "There should be pre-made orders");

        for (List<Priceable> order : orders) {
            assertTrue(order.stream().anyMatch(p -> p instanceof Weapon), "Each order should contain a Weapon");
            assertTrue(order.stream().anyMatch(p -> p instanceof Potion), "Each order should contain a Potion");
            assertTrue(order.stream().anyMatch(p -> p instanceof Companion), "Each order should contain a Companion");
        }
    }
}
