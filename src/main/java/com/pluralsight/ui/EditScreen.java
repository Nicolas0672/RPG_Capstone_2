package com.pluralsight.ui;

import com.pluralsight.model.enhancement.Buffs;
import com.pluralsight.model.enhancement.Enhancement;
import com.pluralsight.model.enhancement.Gem;
import com.pluralsight.model.weapon.Weapon;
import com.pluralsight.services.OrderService;
import com.pluralsight.ui.utils.Helper;
import com.pluralsight.ui.utils.RPGDisplay;

import java.util.List;
import java.util.Scanner;

public class EditScreen {
    private final Scanner scanner = new Scanner(System.in);

    public void displayEditGem(OrderService orderService, Weapon weapon) {
        List<Enhancement> enhancementList = weapon.getEnhancement();

        String gemName = enhancementList.stream()
                .filter(e -> e instanceof Gem)
                .map(n -> n.getName())
                .findFirst()
                .orElse("No gem");
        boolean isValid = false;
        while (!isValid) {

            RPGDisplay.printStory(String.format("Would you like to remove %s out of your weapon", gemName));
            RPGDisplay.printOption(1, "Yes");
            RPGDisplay.printOption(2, "No");

            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    OrderService.removeEnhancementFromWeapon(weapon, gemName, "gem");
                    isValid = true;
                    RPGDisplay.printSuccess(gemName + " has been successfully removed!\n");
                    displayAddGemToSword(orderService, weapon);
                    break;
                case "2":
                    RPGDisplay.printSuccess("Leaving...");
                    return;
                case "3":
                    RPGDisplay.printWarning("Invalid option! Please try again");
            }
        }
    }

    private void displayAddGemToSword(OrderService orderService, Weapon weapon) {
        List<Gem> gemList = orderService.getAllGems();

        for (int i = 0; i < gemList.size(); i++) {
            RPGDisplay.printOptions(String.format("\t\t#%d Order", i + 1));
            RPGDisplay.printDivider();
            RPGDisplay.printGemCard(gemList.get(i));
        }
        boolean isValid = false;
        while (!isValid) {
            RPGDisplay.printStory("\nWould you like to add new gems to your weapon?");
            RPGDisplay.printOption(1, "Yes");
            RPGDisplay.printOption(2, "No");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    Gem gem = Helper.getSelectionFromList(gemList, scanner);
                    if (gem == null) break;
                    OrderService.addEnhancementToWeapon(weapon, gem);
                    RPGDisplay.printSuccess(String.format("Congratulations! %s has been imbued to the sword.", gem.getName()));
                    isValid = true;
                    break;
                case "2":
                    RPGDisplay.printSuccess("Leaving");
                    return;
                default:
                    RPGDisplay.printWarning("Invalid options! Please try again");
            }
        }
    }

    public void displayEditBuff(OrderService orderService, Weapon weapon) {
        List<Enhancement> enhancementList = weapon.getEnhancement();
        List<String> buffList = enhancementList.stream()
                .filter(b -> b instanceof Buffs)
                .map(b -> b.getName())
                .toList();

        for (String name : buffList) {
            System.out.printf("Current Buffs: %s\n", name);
        }

        boolean isValid = false;
        while (!isValid) {
            RPGDisplay.printStory("What would you like to do with your weapon?");
            RPGDisplay.printOption(1, "Add Buff");
            RPGDisplay.printOption(2, "Remove Buff");
            RPGDisplay.printOption(3, "Return");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    displayAddBuff(orderService, weapon);
                    break;
                case "2":
            }


        }
    }

    public void displayAddBuff(OrderService orderService, Weapon weapon) {
        List<Buffs> buffsList = orderService.getAllBuffs();

        for (int i = 0; i < buffsList.size(); i++) {
            RPGDisplay.printOptions(String.format("\t\t#%d Order", i + 1));
            RPGDisplay.printDivider();
            RPGDisplay.printBuffCard(buffsList.get(i));
        }

        Buffs buff = Helper.getSelectionFromList(buffsList, scanner);
        if (buff == null) {
            RPGDisplay.printWarning("No Buff was selected");
            return;
        }
        OrderService.addEnhancementToWeapon(weapon, buff);
        RPGDisplay.printSuccess(String.format("Congratulations! %s has been added to the sword.", buff.getName()));
    }

    public void displayRemoveBuff(OrderService orderService, Weapon weapon, List<String> buffList) {

        RPGDisplay.printStory("Which buff would you like to remove?");
        String removedBuff = Helper.getSelectionFromList(buffList, scanner);
        OrderService.removeEnhancementFromWeapon(weapon, removedBuff, "buff");
        RPGDisplay.printSuccess(removedBuff + " has been successfully removed!\n");

    }


}
