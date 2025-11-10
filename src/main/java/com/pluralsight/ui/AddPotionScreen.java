package com.pluralsight.ui;

import com.pluralsight.model.potion.Potion;
import com.pluralsight.services.OrderService;
import com.pluralsight.ui.utils.Helper;
import com.pluralsight.ui.utils.RPGDisplay;

import java.util.List;
import java.util.Scanner;

/**
 * AddPotionScreen
 * ----------------
 * Handles adding and removing potions from the player's cart.
 * Uses RPGDisplay for formatted output and Helper for user selections.
 */
public class AddPotionScreen {

    private final Scanner scanner = new Scanner(System.in);

    // ---------------------
    // ADD POTION TO CART
    // ---------------------
    public boolean displayAddPotion(OrderService orderService) {
        List<Potion> potionList = orderService.getAllPotions();

        RPGDisplay.printSubTitle("🧪 Potion Stand 🧪");
        RPGDisplay.printStory("Welcome, traveler! Browse our collection of magical potions below.\n");

        // Display all available potions
        for (int i = 0; i < potionList.size(); i++) {
            Potion potion = potionList.get(i);
            System.out.printf("%d) %s\n   💰 Price: %.2f\n   ✨ Rarity: %s\n\n",
                    i + 1, potion.getName(), potion.getBaseCost(), potion.getRarity());
        }

        // Let the user select a potion
        Potion selectedPotion = Helper.getSelectionFromList(potionList, scanner);

        // If user chose 0 (skip)
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
                    RPGDisplay.printSuccess(selectedPotion.getName() + " has been added to your inventory!\n");
                    return true;
                case "2":
                    RPGDisplay.printStory("You decide not to buy that potion.\n");
                    return false;
                default:
                    RPGDisplay.printWarning("Please enter 1 or 2.\n");
            }
        }
    }

    // ---------------------
    // REMOVE POTION FROM CART
    // ---------------------
    public void displayRemovePotion(OrderService orderService, List<Potion> potionList){

        // Show potions in cart with order numbers and dividers
        for (int i = 0; i < potionList.size(); i++) {
            RPGDisplay.printOptions(String.format("\t\t#%d Order", i + 1));
            RPGDisplay.printDivider();
            RPGDisplay.printFinalPotionCard(potionList.get(i));
            RPGDisplay.printDivider();
        }

        // Let the user select a potion to remove
        Potion potion = Helper.getSelectionFromList(potionList, scanner);
        if(potion == null){
            return;
        }

        orderService.removePotionFromCart(potion);
        RPGDisplay.printSuccess(String.format("Congratulations! %s has been removed from cart", potion.getName()));
    }

}
