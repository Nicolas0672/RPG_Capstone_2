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
import java.util.function.Function;

public class AddSwordScreen {
    private final Scanner scanner = new Scanner(System.in);
    private final OrderService orderService = new OrderService();
    private final Helper helper = new Helper();

    public void displayAddWeapon() {
        RPGDisplay.printSubTitle("⚔️ Forge Your Weapon ⚔️");
        RPGDisplay.printStory("Every hero needs a weapon worthy of legend!");

        String weaponType = displayWeaponType();
        Rarity rarity = displayRarity();

        Weapon weapon = orderService.weaponBuild(weaponType, rarity, true);

        Gem gem = displayPremiumGem();
        if (gem != null) RPGDisplay.printEnhancementEffect("Gem: " + gem.getName());

        Quirks quirk = displayQuirks();
        if (quirk != null) RPGDisplay.printEnhancementEffect("Quirk: " + quirk.getName());

        Buffs buff = displayBuffs();
        if (buff != null) RPGDisplay.printEnhancementEffect("Buff: " + buff.getName());

        Customization customization = displayCustomize();
        if (customization != null) RPGDisplay.printEnhancementEffect("Customization: " + customization.getName());

        boolean isSpecial = displaySpecial();

        Weapon finalWeapon = new WeaponBuilder(weapon)
                .addEnhancement(buff)
                .addEnhancement(gem)
                .addEnhancement(customization)
                .addEnhancement(quirk)
                .build(isSpecial);

        orderService.addWeaponToCart(finalWeapon);
        RPGDisplay.printSuccess("🎉 Your weapon has been forged and added to your arsenal!\n");
        RPGDisplay.printDivider();
        RPGDisplay.printWeaponCard(finalWeapon);
        System.out.println();
    }

    public String displayWeaponType() {
        RPGDisplay.printStory("Select the weapon that calls to you:");

        List<Weapon> weapons = orderService.getAllWeapons();
        displayWeaponCards(weapons);

        Weapon selectedWeapon = helper.getSelectionFromList(weapons, scanner);
        return selectedWeapon.getName();
    }

    public Rarity displayRarity() {
        RPGDisplay.printStory("Choose a rarity worthy of your destiny:");

        List<Rarity> rarities = List.of(Rarity.values());
        return helper.displaySelection(rarities, Rarity::name, scanner);
    }

    public Buffs displayBuffs() {
        List<Buffs> buffsList = orderService.getAllBuffs();
        RPGDisplay.printStory("Select magical buffs to empower your weapon:");

        displayBuffCards(buffsList);

        Buffs selectedBuff = helper.getSelectionFromList(buffsList, scanner);
        if (selectedBuff == null) return null;

        while (true) {
            RPGDisplay.printStory("Would you like to increase the strength of the buff? (1.5X cost) ⚡");
            RPGDisplay.printOption(1, "Yes");
            RPGDisplay.printOption(2, "No");
            String input = scanner.nextLine().trim();
            System.out.println();
            try {
                int choice = Integer.parseInt(input);
                double multiplier = (choice == 1) ? 1.5 : 1.0;
                return new Buffs(selectedBuff.getName(),
                        selectedBuff.getBaseCost() * multiplier,
                        selectedBuff.getRarity(),
                        selectedBuff.getType());
            } catch (NumberFormatException e) {
                RPGDisplay.printWarning("Please enter 1 or 2.\n");
            }
        }
    }

    public Gem displayPremiumGem() {
        List<Gem> gemList = orderService.getAllGems();
        RPGDisplay.printStory("Select a gem to imbue your weapon with mystical power:");

        displayGemCards(gemList);

        Gem selectedGem = helper.getSelectionFromList(gemList, scanner);
        if (selectedGem == null) return null;

        while (true) {
            RPGDisplay.printStory("Increase the gem's potency? (1.5X cost) 💎");
            RPGDisplay.printOption(1, "Yes");
            RPGDisplay.printOption(2, "No");
            String input = scanner.nextLine().trim();
            System.out.println();
            try {
                int choice = Integer.parseInt(input);
                double multiplier = (choice == 1) ? 1.5 : 1.0;
                return new Gem(selectedGem.getName(),
                        selectedGem.getBaseCost() * multiplier,
                        selectedGem.getRarity(),
                        selectedGem.getGemType());
            } catch (NumberFormatException e) {
                RPGDisplay.printWarning("Please enter 1 or 2.\n");
            }
        }
    }

    public Quirks displayQuirks() {
        List<Quirks> quirkList = orderService.getAllQuirks();
        RPGDisplay.printStory("Add a quirky trait to your weapon (optional):");
        displayQuirkCards(quirkList);
        return helper.getSelectionFromList(quirkList, scanner);
    }

    public Customization displayCustomize() {
        List<Customization> customList = orderService.getAllCustomizations();
        RPGDisplay.printStory("Add a personal touch to your weapon (optional):");
        displayCustomizationCards(customList);
        return helper.getSelectionFromList(customList, scanner);
    }

    public boolean displaySpecial() {
        while (true) {
            RPGDisplay.printStory("Would you like your weapon to hold a legendary essence?");
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

    // Helper display methods
    private void displayWeaponCards(List<Weapon> weapons) {
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
            System.out.println(RPGDisplay.YELLOW + "Option " + (i + 1) + ":" + RPGDisplay.RESET);
            RPGDisplay.printBuffCard(buffs.get(i));
            System.out.println();
        }
        RPGDisplay.printOption(0, "Skip\n");
    }

    private void displayGemCards(List<Gem> gems) {
        RPGDisplay.printStory("Make your selection (0 to skip):\n");
        for (int i = 0; i < gems.size(); i++) {
            System.out.println(RPGDisplay.PURPLE + "Option " + (i + 1) + ":" + RPGDisplay.RESET);
            RPGDisplay.printGemCard(gems.get(i));
            System.out.println();
        }
        RPGDisplay.printOption(0, "Skip\n");
    }

    private void displayQuirkCards(List<Quirks> quirks) {
        System.out.println();
        RPGDisplay.printStory("Make your selection (0 to skip):\n");
        for (int i = 0; i < quirks.size(); i++) {
            System.out.println(RPGDisplay.RED + "Option " + (i + 1) + ":" + RPGDisplay.RESET);
            RPGDisplay.printQuirkCard(quirks.get(i));
            System.out.println();
        }
        RPGDisplay.printOption(0, "Skip\n");
    }

    private void displayCustomizationCards(List<Customization> customizations) {
        RPGDisplay.printStory("Make your selection (0 to skip):\n");
        for (int i = 0; i < customizations.size(); i++) {
            System.out.println(RPGDisplay.CYAN + "Option " + (i + 1) + ":" + RPGDisplay.RESET);
            RPGDisplay.printCustomizationCard(customizations.get(i));
            System.out.println();
        }
        RPGDisplay.printOption(0, "Skip\n");
    }

}
