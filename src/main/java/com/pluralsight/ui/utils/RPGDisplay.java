package com.pluralsight.ui.utils;

import com.pluralsight.model.companion.Companion;
import com.pluralsight.model.enhancement.*;
import com.pluralsight.model.potion.Potion;
import com.pluralsight.model.weapon.Weapon;

import java.util.List;

public class RPGDisplay {

    // ANSI escape codes for colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT = "\u001B[1m";

    // Print main title
    public static void printTitle(String text) {
        System.out.println(PURPLE + BRIGHT + "✨=== " + text + " ===✨" + RESET);
    }

    // Print subtitle
    public static void printSubTitle(String text) {
        System.out.println(CYAN + "\n--- " + text + " ---" + RESET);
    }

    // Print menu option
    public static void printOption(int index, String option) {
        System.out.println(YELLOW + index + ") " + option + RESET);
    }

    public static void printOptions(String text) {
        System.out.println(YELLOW + text + RESET);
    }

    // Print warning
    public static void printWarning(String text) {
        System.out.println(RED + "⚠️  " + text + RESET);
    }

    // Print success
    public static void printSuccess(String text) {
        System.out.println(GREEN + "✅ " + text + RESET);
    }

    // Print story flavor
    public static void printStory(String text) {
        System.out.println(BLUE + "📜 " + text + RESET);
    }

    // Divider
    public static void printDivider() {
        System.out.println(PURPLE + "════════════════════════════════════" + RESET);
    }

    // Print Weapon card
    public static void printWeaponCard(Weapon w) {
        String name = w.getName();
        String damage = w.getDamage() + " ⚡";
        String price = String.format("%.2f 💰", w.getBaseCost());
        String glow = w.getRarity().name().equalsIgnoreCase("LEGENDARY") ? "🌟" : "";

        System.out.println(CYAN + "⚔️ " + name + " " + glow + RESET);
        System.out.println("Damage: " + damage);
        System.out.println("Total price: " + price);
    }

    public static void printFinalWeaponCard(Weapon w) {
        List<Enhancement> enhancementList = w.getEnhancement();
        String name = w.getName();
        String damage = w.getDamage() + " ⚡";
        String price = String.format("%.2f 💰", w.getBaseCost());
        String special = w.hasSpecial() ? "Yes ✨" : "No";

        System.out.println(CYAN + "╔════════════════════════════════════╗" + RESET);
        System.out.printf(CYAN + "║ ⚔️ %-32s ║\n" + RESET, name);
        System.out.println(CYAN + "╠════════════════════════════════════╣" + RESET);
        System.out.printf(CYAN + "║ %-32s ║\n" + RESET, "Damage: " + damage);
        System.out.printf(CYAN + "║ %-32s ║\n" + RESET, "Price: " + price);
        System.out.printf(CYAN + "║ %-32s ║\n" + RESET, "Special: " + special);
        System.out.println(CYAN + "╠════════ Enhancements ═════════════╣" + RESET);

        if (enhancementList.isEmpty()) {
            System.out.printf(CYAN + "║ %-32s ║\n" + RESET, "None");
        } else {
            for (Enhancement e : enhancementList) {
                String type = e.getClass().getSimpleName(); // Buff, Gem, Quirk, Customization
                String eName = e.getName();
                String ePrice = String.format("%.2f 💰", e.calculateCost());
                System.out.printf(CYAN + "║ %-10s: %-19s %7s ║\n" + RESET, type, eName, ePrice);
            }
        }

        System.out.println(CYAN + "╚════════════════════════════════════╝" + RESET);
    }
    public static void printFinalPotionCard(Potion p) {
        System.out.println(GREEN + "╔════════════════════════════════════╗" + RESET);
        System.out.printf(GREEN + "║ 🧪 %-32s ║\n" + RESET, p.getName());
        System.out.println(GREEN + "╠════════════════════════════════════╣" + RESET);
        System.out.printf(GREEN + "║ %-32s ║\n" + RESET, "Price: " + String.format("%.2f 💰", p.getBaseCost()));
        System.out.printf(GREEN + "║ %-32s ║\n" + RESET, "Rarity: " + p.getRarity());
        System.out.println(GREEN + "╚════════════════════════════════════╝" + RESET);
    }

    // Print companion card
    public static void printFinalCompanionCard(Companion c) {
        System.out.println(YELLOW + "╔════════════════════════════════════╗" + RESET);
        System.out.printf(YELLOW + "║ 🐾 %-32s ║\n" + RESET, c.getName());
        System.out.println(YELLOW + "╠════════════════════════════════════╣" + RESET);
        System.out.printf(YELLOW + "║ %-32s ║\n" + RESET, "Price: " + String.format("%.2f 💰", c.getPrice()));
        System.out.printf(YELLOW + "║ %-32s ║\n" + RESET, "Rarity: " + c.getRarity());
        System.out.println(YELLOW + "╚════════════════════════════════════╝" + RESET);
    }

    // Print Buff card
    public static void printBuffCard(Buffs b) {
        if (b == null) return;
        String name = b.getName();
        String rarity = b.getRarity().toString();
        String price = String.format("%.1f 💰", b.calculateCost());
        String description = b.getDescription();

        System.out.println(YELLOW + "✨ " + name + RESET);
        System.out.println("Price: " + price);
        System.out.println(description);
    }

    // Print Gem card
    public static void printGemCard(Gem g) {
        if (g == null) return;
        String name = g.getName();
        String rarity = g.getRarity().toString();
        String price = String.format("%.1f 💰", g.calculateCost());
        String gemType = g.getGemType().toString();

        System.out.println(PURPLE + "💎 " + name + RESET);
        System.out.println("Price: " + price);
    }

    // Print Quirk card
    public static void printQuirkCard(Quirks q) {
        if (q == null) return;
        String name = q.getName();
        String rarity = q.getRarity().toString();
        String price = String.format("%.1f 💰", q.calculateCost());
        String description = q.getDescription();

        System.out.println(YELLOW + "⚡ " + name + RESET);
        System.out.println("Price: " + price);
        System.out.println(description);
    }

    // Print Customization card
    public static void printCustomizationCard(Customization c) {
        if (c == null) return;
        String name = c.getName();
        String rarity = c.getRarity().toString();
        String description = c.getDescription();

        System.out.println(CYAN + "🖌️ " + name + RESET);
        System.out.println(description);
    }

    // Print magical effect when adding enhancement
    public static void printEnhancementEffect(String enhancement) {
        System.out.println(GREEN + BRIGHT + "✨ " + enhancement + " has been imbued into your weapon! ✨" + RESET);
    }
}