package com.pluralsight.ui;

import com.pluralsight.model.*;
import com.pluralsight.model.enhancement.*;
import com.pluralsight.model.weapon.Weapon;
import com.pluralsight.services.OrderService;
import com.pluralsight.services.WeaponBuilder;
import com.pluralsight.ui.utils.Helper;
import com.pluralsight.ui.utils.RPGDisplay;

import java.util.List;
import java.util.Scanner;

/**
 * AddSwordScreen
 * ----------------
 * Handles weapon creation workflow:
 * - Allows selecting weapon type, rarity, and enhancements
 * - Adds enhancements: Gem, Buff, Quirk, Customization
 * - Optionally applies "special" legendary status
 * - Updates and displays total weapon cost dynamically
 */
public class AddSwordScreen {

    private final Scanner scanner = new Scanner(System.in);

    // ---------------------
    // MAIN DISPLAY METHOD
    // ---------------------
    public void displayAddWeapon(OrderService orderService) {
        double currentWeaponPrice = 0;

        RPGDisplay.printSubTitle("⚔️ Forge Your Weapon ⚔️");
        RPGDisplay.printStory("Every hero needs a weapon worthy of legend!");

        // Select weapon type
        String weaponType = displayWeaponType(orderService);
        if(weaponType == null) return;

        // Select rarity
        Rarity rarity = displayRarity();

        // Build base weapon
        Weapon weapon = orderService.weaponBuild(weaponType, rarity, true);
        currentWeaponPrice = weapon.getBaseCost();

        displayTotalCartPrice(currentWeaponPrice);

        // Add premium gem (optional)
        Gem gem = displayPremiumGem(orderService);
        if (gem != null) {
            RPGDisplay.printEnhancementEffect("Gem: " + gem.getName());
            currentWeaponPrice += gem.getBaseCost();
        }
        displayTotalCartPrice(currentWeaponPrice);

        // Add quirk (optional)
        Quirks quirk = displayQuirks(orderService);
        if (quirk != null) {
            RPGDisplay.printEnhancementEffect("Quirk: " + quirk.getName());
            currentWeaponPrice += quirk.getBaseCost();
        }
        displayTotalCartPrice(currentWeaponPrice);

        // Add buff (optional)
        Buffs buff = displayBuffs(orderService);
        if (buff != null) {
            RPGDisplay.printEnhancementEffect("Buff: " + buff.getName());
            currentWeaponPrice += buff.getBaseCost();
        }
        displayTotalCartPrice(currentWeaponPrice);

        // Add customization (optional)
        Customization customization = displayCustomize(orderService);
        if (customization != null) RPGDisplay.printEnhancementEffect("Customization: " + customization.getName());
        displayTotalCartPrice(currentWeaponPrice);

        // Special legendary option
        boolean isSpecial = displaySpecial();

        // Final weapon build
        Weapon finalWeapon = new WeaponBuilder(weapon)
                .addEnhancement(buff)
                .addEnhancement(gem)
                .addEnhancement(customization)
                .addEnhancement(quirk)
                .build(isSpecial);

        orderService.addWeaponToCart(finalWeapon);
        RPGDisplay.printSuccess("🎉 Your weapon has been forged and added to your arsenal!\n");
        RPGDisplay.printDivider();
    }

    // ---------------------
    // WEAPON SELECTION
    // ---------------------
    public String displayWeaponType(OrderService orderService) {
        RPGDisplay.printStory("Select the weapon that calls to you:");

        List<Weapon> weapons = orderService.getAllWeapons();
        displayWeaponCards(weapons, orderService);

        Weapon selectedWeapon = Helper.getSelectionFromList(weapons, scanner);
        if (selectedWeapon == null) return null;

        return selectedWeapon.getName();
    }

    public Rarity displayRarity() {
        RPGDisplay.printStory("Choose a rarity worthy of your destiny:");

        List<Rarity> rarities = List.of(Rarity.values());
        return Helper.displaySelection(rarities, Rarity::printRarityCost, scanner);
    }

    // ---------------------
    // BUFF SELECTION
    // ---------------------
    public Buffs displayBuffs(OrderService orderService) {
        List<Buffs> buffsList = orderService.getAllBuffs();
        RPGDisplay.printStory("Select magical buffs to empower your weapon:");

        displayBuffCards(buffsList);

        Buffs selectedBuff = Helper.getSelectionFromList(buffsList, scanner);
        if (selectedBuff == null) return null;

        // Optionally increase buff potency
        while (true) {
            RPGDisplay.printStory("Would you like to increase the strength of the buff? (1.5X cost) ⚡");
            RPGDisplay.printOption(1, "Yes");
            RPGDisplay.printOption(2, "No");

            String input = scanner.nextLine().trim();
            System.out.println();

            try {
                int choice = Integer.parseInt(input);
                double multiplier = (choice == 1) ? 1.5 : 1.0;

                // Create a new Buff instance with adjusted cost
                Buffs buff = new Buffs(selectedBuff.getName(),
                        0,
                        selectedBuff.getRarity(),
                        selectedBuff.getType());
                buff.setBaseCost(buff.calculateCost() * multiplier);
                return buff;

            } catch (NumberFormatException e) {
                RPGDisplay.printWarning("Please enter 1 or 2.\n");
            }
        }
    }

    // ---------------------
    // GEM SELECTION
    // ---------------------
    public Gem displayPremiumGem(OrderService orderService) {
        List<Gem> gemList = orderService.getAllGems();
        RPGDisplay.printStory("Select a gem to imbue your weapon with mystical power:");

        displayGemCards(gemList);

        Gem selectedGem = Helper.getSelectionFromList(gemList, scanner);
        if (selectedGem == null) return null;

        // Optionally increase gem potency
        while (true) {
            RPGDisplay.printStory("Increase the gem's potency? (1.5X cost) 💎");
            RPGDisplay.printOption(1, "Yes");
            RPGDisplay.printOption(2, "No");

            String input = scanner.nextLine().trim();
            System.out.println();

            try {
                int choice = Integer.parseInt(input);
                double multiplier = (choice == 1) ? 1.5 : 1.0;

                Gem gem = new Gem(selectedGem.getName(),
                        0,
                        selectedGem.getRarity(),
                        selectedGem.getGemType());
                gem.setBaseCost(gem.calculateCost() * multiplier);
                return gem;

            } catch (NumberFormatException e) {
                RPGDisplay.printWarning("Please enter 1 or 2.\n");
            }
        }
    }

    // ---------------------
    // QUIRK SELECTION
    // ---------------------
    public Quirks displayQuirks(OrderService orderService) {
        List<Quirks> quirkList = orderService.getAllQuirks();
        RPGDisplay.printStory("Add a quirky trait to your weapon (optional):");

        displayQuirkCards(quirkList);
        return Helper.getSelectionFromList(quirkList, scanner);
    }

    // ---------------------
    // CUSTOMIZATION SELECTION
    // ---------------------
    public Customization displayCustomize(OrderService orderService) {
        List<Customization> customList = orderService.getAllCustomizations();
        RPGDisplay.printStory("Add a personal touch to your weapon (optional):");

        displayCustomizationCards(customList);
        return Helper.getSelectionFromList(customList, scanner);
    }

    // ---------------------
    // CURRENT CART PRICE DISPLAY
    // ---------------------
    public void displayTotalCartPrice(double currentWeaponPrice){
        RPGDisplay.printCurrentCartTotal(currentWeaponPrice);
    }

    // ---------------------
    // SPECIAL LEGENDARY OPTION
    // ---------------------
    public boolean displaySpecial() {
        while (true) {
            RPGDisplay.printStory("Add legendary essence to your weapon to unlock special powers?");
            RPGDisplay.printOption(1, "Yes ✨");
            RPGDisplay.printOption(2, "No");

            String input = scanner.nextLine().trim();
            System.out.println();

            switch (input) {
                case "1": return true;
                case "2": return false;
                default: RPGDisplay.printWarning("Incorrect option! Try again.\n");
            }
        }
    }

    // ---------------------
    // HELPER DISPLAY METHODS (Cards)
    // ---------------------
    private void displayWeaponCards(List<Weapon> weapons, OrderService orderService) {
        System.out.println();
        for (int i = 0; i < weapons.size(); i++) {
            System.out.println(RPGDisplay.YELLOW + "Option " + (i + 1) + ":" + RPGDisplay.RESET);
            RPGDisplay.printWeaponCard(weapons.get(i));
            System.out.println();
        }
    }

    private void displayBuffCards(List<Buffs> buffs) {
        RPGDisplay.printStory("Make your selection (0 to skip):\n");
        for (int i = 0; i < buffs.size(); i++) {
            System.out.println(RPGDisplay.BLUE + "Option " + (i + 1) + ":" + RPGDisplay.RESET);
            RPGDisplay.printBuffCard(buffs.get(i));
            System.out.println();
        }
    }

    private void displayGemCards(List<Gem> gems) {
        RPGDisplay.printStory("Make your selection (0 to skip):\n");
        for (int i = 0; i < gems.size(); i++) {
            System.out.println(RPGDisplay.PURPLE + "Option " + (i + 1) + ":" + RPGDisplay.RESET);
            RPGDisplay.printGemCard(gems.get(i));
            System.out.println();
        }
    }

    private void displayQuirkCards(List<Quirks> quirks) {
        RPGDisplay.printStory("Make your selection (0 to skip):\n");
        for (int i = 0; i < quirks.size(); i++) {
            System.out.println(RPGDisplay.BLUE + "Option " + (i + 1) + ":" + RPGDisplay.RESET);
            RPGDisplay.printQuirkCard(quirks.get(i));
            System.out.println();
        }
    }

    private void displayCustomizationCards(List<Customization> customizations) {
        RPGDisplay.printStory("Make your selection (0 to skip):\n");
        for (int i = 0; i < customizations.size(); i++) {
            System.out.println(RPGDisplay.CYAN + "Option " + (i + 1) + ":" + RPGDisplay.RESET);
            RPGDisplay.printCustomizationCard(customizations.get(i));
            System.out.println();
        }
    }
}
