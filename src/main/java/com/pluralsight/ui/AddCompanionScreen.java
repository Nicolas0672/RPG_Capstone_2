package com.pluralsight.ui;

import com.pluralsight.model.companion.Companion;
import com.pluralsight.services.OrderService;
import com.pluralsight.ui.utils.Helper;
import com.pluralsight.ui.utils.RPGDisplay;

import java.util.List;
import java.util.Scanner;

/**
 * AddCompanionScreen
 * -------------------
 * Handles the addition and removal of companions from the user's cart.
 */
public class AddCompanionScreen {

    private final Scanner scanner = new Scanner(System.in);

    // -------------------------
    // DISPLAY AND ADD COMPANION
    // -------------------------
    public boolean displayAddCompanion(OrderService orderService) {

        List<Companion> companionList = orderService.getAllCompanions();

        RPGDisplay.printSubTitle("🐾 Companion Stall 🐾");
        RPGDisplay.printStory("Greetings, adventurer! Choose a loyal companion to aid you on your journey.\n");

        // Show all available companions with price and rarity
        for (int i = 0; i < companionList.size(); i++) {
            Companion companion = companionList.get(i);
            System.out.printf("%d) %s\n   💰 Price: %.2f\n   ✨ Rarity: %s\n\n",
                    i + 1, companion.getName(), companion.getPrice(), companion.getRarity());
        }

        // Let the user select a companion using generic selection helper
        Companion selectedCompanion = Helper.getSelectionFromList(companionList, scanner);

        // If user selected 0 (return), exit without adding
        if (selectedCompanion == null) {
            RPGDisplay.printStory("You step away from the companion stall.\n");
            return false;
        }

        // Confirm purchase
        while (true) {
            RPGDisplay.printStory("Would you like to recruit this companion?");
            RPGDisplay.printOption(1, "Yes");
            RPGDisplay.printOption(2, "No");
            String confirm = scanner.nextLine().trim();
            System.out.println();

            switch (confirm) {
                case "1":
                    orderService.addCompanionToCart(selectedCompanion);
                    RPGDisplay.printSuccess(selectedCompanion.getName() + " has joined your journey!\n");
                    return true;
                case "2":
                    RPGDisplay.printStory("You decide not to recruit that companion.\n");
                    return false;
                default:
                    RPGDisplay.printWarning("Please enter 1 or 2.\n");
            }
        }
    }

    // -------------------------
    // REMOVE COMPANION FROM CART
    // -------------------------
    public void displayRemoveCompanion(OrderService orderService, List<Companion> companionList) {

        // Show all companions currently in the cart
        for (int i = 0; i < companionList.size(); i++) {
            RPGDisplay.printOptions(String.format("\t\t#%d Order", i + 1));
            RPGDisplay.printDivider();
            RPGDisplay.printFinalCompanionCard(companionList.get(i));
            RPGDisplay.printDivider();
        }

        // Let the user select a companion to remove
        Companion companion = Helper.getSelectionFromList(companionList, scanner);

        // If user selected 0 (return), exit without removing
        if (companion == null) {
            return;
        }

        // Remove companion from cart and confirm
        orderService.removeCompanionFromCart(companion);
        RPGDisplay.printSuccess(String.format("Congratulations! %s has been removed from cart", companion.getName()));
    }
}
