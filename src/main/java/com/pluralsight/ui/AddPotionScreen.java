package com.pluralsight.ui;

import com.pluralsight.model.potion.Potion;
import com.pluralsight.services.OrderService;
import com.pluralsight.ui.utils.Helper;
import com.pluralsight.ui.utils.RPGDisplay;

import java.util.List;
import java.util.Scanner;

public class AddPotionScreen {
    private final Scanner scanner = new Scanner(System.in);

    public boolean displayAddPotion(OrderService orderService) {
        List<Potion> potionList = orderService.getAllPotions();

        RPGDisplay.printSubTitle("🧪 Potion Stand 🧪");
        RPGDisplay.printStory("Welcome, traveler! Browse our collection of magical potions below.\n");

        // Show potion list
        for (int i = 0; i < potionList.size(); i++) {
            Potion potion = potionList.get(i);
            System.out.printf("%d) %s\n   💰 Price: %.2f\n   ✨ Rarity: %s\n\n",
                    i + 1, potion.getName(), potion.getBaseCost(), potion.getRarity());
        }
        RPGDisplay.printOption(0, "Return to Quest Board\n");

        // Let user select potion using the generic selector
        Potion selectedPotion = Helper.getSelectionFromList(potionList, scanner);

        // If user chose 0 (return)
        if (selectedPotion == null) {
            RPGDisplay.printStory("You step away from the potion stand.\n");
            return false;
        }

        // Confirm purchase
        while (true) {
            RPGDisplay.printStory("Would you like to purchase this potion?");
            RPGDisplay.printOption(1, "Yes");
            RPGDisplay.printOption(2, "No");
            String confirm = scanner.nextLine().trim();
            System.out.println();

            switch (confirm) {
                case "1":
                    orderService.addPotionToCart(selectedPotion);
                    RPGDisplay.printSuccess("🧴 " + selectedPotion.getName() + " has been added to your inventory!\n");
                    return true;
                case "2":
                    RPGDisplay.printStory("You decide not to buy that potion.\n");
                    return false;
                default:
                    RPGDisplay.printWarning("Please enter 1 or 2.\n");
            }
        }
    }

}
