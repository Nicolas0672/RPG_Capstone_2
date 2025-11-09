package com.pluralsight.ui;

import com.pluralsight.model.companion.Companion;
import com.pluralsight.services.OrderService;
import com.pluralsight.ui.utils.Helper;
import com.pluralsight.ui.utils.RPGDisplay;

import java.util.List;
import java.util.Scanner;

public class AddCompanionScreen {
    private final Scanner scanner = new Scanner(System.in);

    public boolean displayAddCompanion(OrderService orderService) {
        List<Companion> companionList = orderService.getAllCompanions();

        RPGDisplay.printSubTitle("🐾 Companion Stall 🐾");
        RPGDisplay.printStory("Greetings, adventurer! Choose a loyal companion to aid you on your journey.\n");

        // Show companion list
        for (int i = 0; i < companionList.size(); i++) {
            Companion companion = companionList.get(i);
            System.out.printf("%d) %s\n   💰 Price: %.2f\n   ✨ Rarity: %s\n\n",
                    i + 1, companion.getName(), companion.getPrice(), companion.getRarity());
        }
        RPGDisplay.printOption(0, "Return to Quest Board\n");

        // Let user select companion using the generic selector
        Companion selectedCompanion = Helper.getSelectionFromList(companionList, scanner);

        // If user chose 0 (return)
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
                    RPGDisplay.printSuccess("🐾 " + selectedCompanion.getName() + " has joined your journey!\n");
                    return true;
                case "2":
                    RPGDisplay.printStory("You decide not to recruit that companion.\n");
                    return false;
                default:
                    RPGDisplay.printWarning("Please enter 1 or 2.\n");
            }
        }
    }

}
