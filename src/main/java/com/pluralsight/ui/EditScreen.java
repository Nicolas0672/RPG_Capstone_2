package com.pluralsight.ui;

import com.pluralsight.model.enhancement.*;
import com.pluralsight.model.weapon.Weapon;
import com.pluralsight.services.OrderService;
import com.pluralsight.ui.utils.Helper;
import com.pluralsight.ui.utils.RPGDisplay;

import java.util.List;
import java.util.Scanner;

/**
 * EditScreen
 * -----------
 * Handles editing of weapon enhancements:
 * - Gems, Buffs, Quirks, and Customizations
 * - Allows adding/removing enhancements interactively
 * - Uses RPGDisplay for consistent UI output
 */
public class EditScreen {

    private final Scanner scanner = new Scanner(System.in);

    // ---------------------
    // GEM Editing
    // ---------------------

    /**
     * Display gem editing options for a weapon:
     * - Remove existing gem
     * - Add a new gem
     */
    public void displayEditGem(OrderService orderService, Weapon weapon) {
        List<Enhancement> enhancementList = weapon.getEnhancement();

        // Get first gem in weapon or display "No gem"
        String gemName = enhancementList.stream()
                .filter(e -> e instanceof Gem)
                .map(Enhancement::getName)
                .findFirst()
                .orElse("No gem");

        boolean noGem = gemName.equalsIgnoreCase("no gem");

        boolean isValid = false;
        while (!isValid && !noGem) {
            RPGDisplay.printStory(String.format("Would you like to remove %s out of your weapon?", gemName));
            RPGDisplay.printOption(1, "Yes");
            RPGDisplay.printOption(2, "No");

            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    orderService.removeEnhancementFromWeapon(weapon, gemName, "gem");
                    isValid = true;
                    RPGDisplay.printSuccess(gemName + " has been successfully removed!");
                    displayAddGemToSword(orderService, weapon);
                    break;
                case "2":
                    RPGDisplay.printLeavingMessage();
                    return;
                case "3":
                    RPGDisplay.printWarning("Invalid option! Please try again");
            }
        }
        if(noGem){
            displayAddGemToSword(orderService, weapon);
        }
    }

    /**
     * Display gem selection menu to add a gem to the weapon
     */
    private void displayAddGemToSword(OrderService orderService, Weapon weapon) {
        List<Gem> gemList = orderService.getAllGems();

        // Display all gems with headers
        for (int i = 0; i < gemList.size(); i++) {
            RPGDisplay.printEnhancementOrderHeader("gem", i);
            RPGDisplay.printGemCard(gemList.get(i));
        }

        boolean isValid = false;
        while (!isValid) {
            RPGDisplay.printStory("Would you like to add new gems to your weapon?");
            RPGDisplay.printOption(1, "Yes");
            RPGDisplay.printOption(2, "No");

            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    Gem gem = Helper.getSelectionFromList(gemList, scanner);
                    if (gem == null) break;
                    orderService.addEnhancementToWeapon(weapon, gem);
                    RPGDisplay.printSuccess(String.format("Congratulations! %s has been imbued to the weapon.", gem.getName()));
                    isValid = true;
                    break;
                case "2":
                    RPGDisplay.printLeavingMessage();
                    return;
                default:
                    RPGDisplay.printWarning("Invalid options! Please try again");
            }
        }
    }

    // ---------------------
    // BUFF Editing
    // ---------------------

    /**
     * Display buff editing menu for a weapon:
     * - Add Buff
     * - Remove Buff (if any)
     * - Return
     */
    public void displayEditBuff(OrderService orderService, Weapon weapon) {
        List<Enhancement> enhancementList = weapon.getEnhancement();

        // Extract current buffs
        List<String> buffList = enhancementList.stream()
                .filter(b -> b instanceof Buffs)
                .map(Enhancement::getName)
                .toList();

        boolean hasNoBuffs = buffList.isEmpty();

        // Show current buffs if available
        if (hasNoBuffs) {
            RPGDisplay.printOptions("Your weapon currently has no buffs.");
        } else {
            RPGDisplay.printStory("Current Buffs:");
            for (int i = 0; i < buffList.size(); i++) {
                System.out.printf("%d) %s%n", i + 1, buffList.get(i));
            }
        }

        boolean isValid = false;
        while (!isValid) {
            RPGDisplay.printStory("What would you like to do with your weapon?");
            RPGDisplay.printOption(1, "Add Buff");
            if (!hasNoBuffs) RPGDisplay.printOption(2, "Remove Buff");
            RPGDisplay.printOption(3, "Return");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> { displayAddBuff(orderService, weapon); isValid = true; }
                case "2" -> {
                    if (hasNoBuffs) RPGDisplay.printWarning("You have no buffs to remove.");
                    else { displayRemoveBuff(orderService, weapon, buffList); isValid = true; }
                }
                case "3" -> isValid = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Add a buff to a weapon
     */
    public void displayAddBuff(OrderService orderService, Weapon weapon) {
        List<Buffs> buffsList = orderService.getAllBuffs();

        for (int i = 0; i < buffsList.size(); i++) {
            RPGDisplay.printEnhancementOrderHeader("buff", i);
            RPGDisplay.printBuffCard(buffsList.get(i));
        }

        Buffs buff = Helper.getSelectionFromList(buffsList, scanner);
        if (buff == null) {
            RPGDisplay.printWarning("No Buff was selected");
            return;
        }

        orderService.addEnhancementToWeapon(weapon, buff);
        RPGDisplay.printSuccess(String.format("Congratulations! %s has been added to the weapon.", buff.getName()));
    }

    /**
     * Remove a buff from a weapon
     */
    public void displayRemoveBuff(OrderService orderService, Weapon weapon, List<String> buffList) {
        RPGDisplay.printStory("Which buff would you like to remove?");
        String removedBuff = Helper.getSelectionFromList(buffList, scanner);
        if (removedBuff == null) {
            RPGDisplay.printWarning("No Buff was selected");
            return;
        }

        orderService.removeEnhancementFromWeapon(weapon, removedBuff, "buff");
        RPGDisplay.printSuccess(removedBuff + " has been successfully removed!\n");
    }

    // ---------------------
    // QUIRK Editing
    // ---------------------

    public void displayEditQuirk(OrderService orderService, Weapon weapon) {
        List<Enhancement> enhancementList = weapon.getEnhancement();

        // Extract current quirks
        List<String> quirkList = enhancementList.stream()
                .filter(q -> q instanceof Quirks)
                .map(Enhancement::getName)
                .toList();

        boolean hasNoQuirks = quirkList.isEmpty();

        if (hasNoQuirks) {
            RPGDisplay.printOptions("Your weapon currently has no quirks.");
        } else {
            RPGDisplay.printStory("Current Quirks:");
            for (int i = 0; i < quirkList.size(); i++) {
                System.out.printf("%d) %s%n", i + 1, quirkList.get(i));
            }
        }

        boolean isValid = false;
        while (!isValid) {
            RPGDisplay.printStory("What would you like to do with your weapon?");
            RPGDisplay.printOption(1, "Add Quirk");
            if (!hasNoQuirks) RPGDisplay.printOption(2, "Remove Quirk");
            RPGDisplay.printOption(3, "Return");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> { displayAddQuirk(orderService, weapon); isValid = true; }
                case "2" -> {
                    if (hasNoQuirks) RPGDisplay.printWarning("You have no quirks to remove.");
                    else { displayRemoveQuirk(orderService, weapon, quirkList); isValid = true; }
                }
                case "3" -> isValid = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public void displayAddQuirk(OrderService orderService, Weapon weapon) {
        List<Quirks> quirkList = orderService.getAllQuirks();

        for (int i = 0; i < quirkList.size(); i++) {
            RPGDisplay.printEnhancementOrderHeader("quirk", i);
            RPGDisplay.printQuirkCard(quirkList.get(i));
        }

        Quirks quirk = Helper.getSelectionFromList(quirkList, scanner);
        if (quirk == null) {
            RPGDisplay.printWarning("No Quirk was selected");
            return;
        }

        orderService.addEnhancementToWeapon(weapon, quirk);
        RPGDisplay.printSuccess(String.format("Congratulations! %s has been added to the weapon.", quirk.getName()));
    }

    public void displayRemoveQuirk(OrderService orderService, Weapon weapon, List<String> quirkList) {
        RPGDisplay.printStory("Which quirk would you like to remove?");
        String removedQuirk = Helper.getSelectionFromList(quirkList, scanner);
        if (removedQuirk == null) {
            RPGDisplay.printWarning("No Quirk was selected");
            return;
        }

        orderService.removeEnhancementFromWeapon(weapon, removedQuirk, "quirk");
        RPGDisplay.printSuccess(removedQuirk + " has been successfully removed!\n");
    }

    // ---------------------
    // CUSTOMIZATION Editing
    // ---------------------

    public void displayEditCustomization(OrderService orderService, Weapon weapon) {
        List<Enhancement> enhancementList = weapon.getEnhancement();

        // Extract current customizations
        List<String> customizationList = enhancementList.stream()
                .filter(c -> c instanceof Customization)
                .map(Enhancement::getName)
                .toList();

        boolean hasNoCustomizations = customizationList.isEmpty();

        if (hasNoCustomizations) {
            RPGDisplay.printOptions("Your weapon currently has no customizations.");
        } else {
            RPGDisplay.printStory("Current Customizations:");
            for (int i = 0; i < customizationList.size(); i++) {
                System.out.printf("%d) %s%n", i + 1, customizationList.get(i));
            }
        }

        boolean isValid = false;
        while (!isValid) {
            RPGDisplay.printStory("What would you like to do with your weapon?");
            RPGDisplay.printOption(1, "Add Customization");
            if (!hasNoCustomizations) RPGDisplay.printOption(2, "Remove Customization");
            RPGDisplay.printOption(3, "Return");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> { displayAddCustomization(orderService, weapon); isValid = true; }
                case "2" -> {
                    if (hasNoCustomizations) RPGDisplay.printWarning("You have no customizations to remove.");
                    else { displayRemoveCustomization(orderService, weapon, customizationList); isValid = true; }
                }
                case "3" -> isValid = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public void displayAddCustomization(OrderService orderService, Weapon weapon) {
        List<Customization> customizationList = orderService.getAllCustomizations();

        for (int i = 0; i < customizationList.size(); i++) {
            RPGDisplay.printEnhancementOrderHeader("customization", i);
            RPGDisplay.printCustomizationCard(customizationList.get(i));
        }

        Customization customization = Helper.getSelectionFromList(customizationList, scanner);
        if (customization == null) {
            RPGDisplay.printWarning("No Customization was selected");
            return;
        }

        orderService.addEnhancementToWeapon(weapon, customization);
        RPGDisplay.printSuccess(String.format("Congratulations! %s has been added to the weapon.", customization.getName()));
    }

    public void displayRemoveCustomization(OrderService orderService, Weapon weapon, List<String> customizationList) {
        RPGDisplay.printStory("Which customization would you like to remove?");
        String removedCustomization = Helper.getSelectionFromList(customizationList, scanner);
        if (removedCustomization == null) {
            RPGDisplay.printWarning("No Customization was selected");
            return;
        }

        orderService.removeEnhancementFromWeapon(weapon, removedCustomization, "customization");
        RPGDisplay.printSuccess(removedCustomization + " has been successfully removed!\n");
    }
}
