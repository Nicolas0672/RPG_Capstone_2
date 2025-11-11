package com.pluralsight.ui.utils;

import com.pluralsight.model.companion.Companion;
import com.pluralsight.model.enhancement.*;
import com.pluralsight.model.potion.Potion;
import com.pluralsight.model.weapon.Weapon;

import java.util.List;

/**
 * RPGDisplay
 * -----------
 * Handles all console output for the RPG game:
 * - Titles, subtitles, menus, warnings, and success messages
 * - Weapon, Potion, Companion, and Enhancement displays
 * - Cart totals and leaving messages
 * Color-coded with emojis and formatting for clarity.
 */
public class RPGDisplay {

    // ---------------------
    // ANSI color codes
    // ---------------------
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT = "\u001B[1m";

    // ---------------------
    // Titles & Dividers
    // ---------------------
    public static void printTitle(String text) {
        System.out.println(PURPLE + BRIGHT + "✨=== " + text + " ===✨" + RESET);
    }

    public static void printSubTitle(String text) {
        System.out.println(CYAN + "\n--- " + text + " ---" + RESET);
    }

    public static void printDivider() {
        System.out.println(PURPLE + "════════════════════════════════════" + RESET);
    }

    // ---------------------
    // Menu options & story
    // ---------------------
    public static void printOption(int index, String option) {
        System.out.println(YELLOW + index + ") " + option + RESET);
    }

    public static void printOptions(String text) {
        System.out.println(YELLOW + text + RESET);
    }

    public static void printWarning(String text) {
        System.out.println(RED + "⚠️  " + text + RESET);
    }

    public static void printSuccess(String text) {
        System.out.println(GREEN + "✅ " + text + RESET);
    }

    public static void printStory(String text) {
        System.out.println(BLUE + "📜 " + text + RESET);
    }

    public static void printLeavingMessage() {
        System.out.println(BRIGHT + PURPLE + "🚪 Leaving..." + RESET);
    }

    // ---------------------
    // Cart totals
    // ---------------------
    public static void printCurrentCartTotal(double totalPrice) {
        System.out.printf("🛒 Current Cart Total: %.2f 💰\n\n", totalPrice);
    }

    public static void printCartTotal(double totalPrice) {
        System.out.println(BRIGHT + PURPLE + "💼=== TOTAL CART SUMMARY ===💼" + RESET);
        System.out.println(PURPLE + "════════════════════════════════════" + RESET);

        String color = totalPrice < 500 ? GREEN : totalPrice < 1500 ? YELLOW : RED;
        System.out.printf(color + BRIGHT + "💰 Total Price: %.2f 💰\n" + RESET, totalPrice);
        System.out.println(PURPLE + "════════════════════════════════════" + RESET);
    }

    // ---------------------
    // Headers for orders
    // ---------------------
    public static void printWeaponOrderHeader(int index) {
        System.out.println(BRIGHT + CYAN + String.format("⚔️ Weapon #%d", index + 1) + RESET);
        printDivider();
    }

    public static void printPotionOrderHeader(int index) {
        System.out.println(BRIGHT + GREEN + String.format("🧪 Potion #%d", index + 1) + RESET);
        printDivider();
    }

    public static void printCompanionOrderHeader(int index) {
        System.out.println(BRIGHT + YELLOW + String.format("🐾 Companion #%d", index + 1) + RESET);
        printDivider();
    }

    public static void printEnhancementOrderHeader(String type, int index) {
        String icon, color, borderColor;

        switch (type.toLowerCase()) {
            case "gem" -> { icon = "💎"; color = BRIGHT + PURPLE; borderColor = PURPLE; }
            case "buff" -> { icon = "✨"; color = BRIGHT + YELLOW; borderColor = YELLOW; }
            case "quirk" -> { icon = "⚡"; color = BRIGHT + CYAN; borderColor = CYAN; }
            case "customization" -> { icon = "🖌️"; color = BRIGHT + BLUE; borderColor = BLUE; }
            default -> { icon = "🔹"; color = BRIGHT + WHITE; borderColor = WHITE; }
        }

        System.out.println(color + String.format("\n%s %s #%d", icon, capitalize(type), index + 1) + RESET);
        System.out.println(borderColor + "══════════════════════════════════════════════" + RESET);
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    // ---------------------
    // Weapon display
    // ---------------------
    public static void printWeaponCard(Weapon w) {
        String glow = w.getRarity().name().equalsIgnoreCase("LEGENDARY") ? "🌟" : "";
        System.out.println(CYAN + w.getName() + " " + glow + RESET);
        System.out.println("Damage: " + w.getDamage() + " ⚡");
        System.out.println("Total price: " + String.format("%.2f 💰", w.getBaseCost()));
    }

    public static void printFinalWeaponCard(Weapon w) {
        System.out.println(CYAN + w.getName() + RESET);
        System.out.println("Damage: " + w.getDamage() + " ⚡");
        System.out.println("Price: " + String.format("%.2f 💰", w.getBaseCost()));
        if (!w.getEnhancement().isEmpty()) {
            System.out.println("Enhancements:");
            for (Enhancement e : w.getEnhancement()) {
                System.out.println(" - " + e.getClass().getSimpleName() + ": " + e.getName()
                        + " (" + String.format("%.2f 💰", e.getBaseCost()) + ")");
            }
        }
        System.out.println();
    }

    // ---------------------
    // Potion display
    // ---------------------
    public static void printFinalPotionCard(Potion p) {
        System.out.println(GREEN + p.getName() + RESET);
        System.out.println("Price: " + String.format("%.2f 💰", p.getBaseCost()));
        System.out.println("✨ Rarity: " + p.getRarity());
        System.out.println();
    }

    // ---------------------
    // Companion display
    // ---------------------
    public static void printFinalCompanionCard(Companion c) {
        System.out.println(YELLOW + c.getName() + RESET);
        System.out.println("Price: " + String.format("%.2f 💰", c.getPrice()));
        System.out.println("✨ Rarity: " + c.getRarity());
        System.out.println();
    }

    // ---------------------
    // Enhancements display
    // ---------------------
    public static void printBuffCard(Buffs b) {
        if (b == null) return;
        System.out.println(YELLOW + b.getName() + RESET);
        System.out.println("Price: " + String.format("%.1f 💰", b.calculateCost()));
        System.out.println(b.getDescription());
    }

    public static void printGemCard(Gem g) {
        if (g == null) return;
        System.out.println(PURPLE + g.getName() + RESET);
        System.out.println("Price: " + String.format("%.1f 💰", g.calculateCost()));
    }

    public static void printQuirkCard(Quirks q) {
        if (q == null) return;
        System.out.println(YELLOW + q.getName() + RESET);
        System.out.println("Price: " + String.format("%.1f 💰", q.calculateCost()));
        System.out.println(q.getDescription());
    }

    public static void printCustomizationCard(Customization c) {
        if (c == null) return;
        System.out.println(CYAN + c.getName() + RESET);
        System.out.println(c.getDescription());
    }

    public static void printEnhancementEffect(String enhancement) {
        System.out.println(GREEN + BRIGHT + enhancement + " has been imbued into your weapon! " + RESET);
    }
}
