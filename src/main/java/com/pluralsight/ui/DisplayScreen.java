package com.pluralsight.ui;


import com.pluralsight.services.OrderService;
import com.pluralsight.ui.utils.Helper;
import com.pluralsight.ui.utils.RPGDisplay;
import java.util.Scanner;

import com.pluralsight.model.weapon.Weapon;
import com.pluralsight.model.core.Item;
import com.pluralsight.model.core.Rarity;
import com.pluralsight.model.interfaces.Priceable;
import com.pluralsight.model.enhancement.Enhancement;


public class DisplayScreen {
    Scanner scanner = new Scanner(System.in);
    OrderService orderService = new OrderService();

    public void displayHome() {
        RPGDisplay.printTitle("🌌 Eldoria Awaits 🌌");
        RPGDisplay.printStory("You are a long-lost traveler returning to a land of magic and valor.");
        RPGDisplay.printStory("Your destiny awaits — choose wisely, adventurer!\n");

        while (true) {
            RPGDisplay.printOption(1, "Embark on a new quest ⚔️ (New Order)");
            RPGDisplay.printOption(0, "Rest for now 🛌 (Exit)");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    displayNewOrder();
                    break;
                case "0":
                    RPGDisplay.printStory("Farewell, traveler. May the winds guide your path. 🌬️\n");
                    System.exit(0);
                    break;
                default:
                    RPGDisplay.printWarning("Invalid choice, please try again!\n");
            }
        }
    }

    public void displayNewOrder() {
        RPGDisplay.printSubTitle("🗺️ Quest Board");
        RPGDisplay.printStory("Prepare yourself with the best equipment before venturing out!\n");

        boolean valid = false;
        while (!valid) {
            RPGDisplay.printOption(1, "Forge a legendary weapon ⚔️");
            RPGDisplay.printOption(2, "Find a forged set");
            RPGDisplay.printOption(3, "Brew a magical potion ⚗️");
            RPGDisplay.printOption(4, "Recruit a traveling companion 🐉");
            RPGDisplay.printOption(5, "Venture to checkout 🏹");
            RPGDisplay.printOption(6, "Abandon quest ❌\n");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    new AddSwordScreen().displayAddWeapon(orderService);
                    valid = true;
                    break;
                case "2":
                    new AddExistingOrder().displayAddExisting(orderService);
                    valid = true;
                    break;
                case "3":
                    new AddPotionScreen().displayAddPotion(orderService);
                    valid = true;
                    break;
                case "4":
                  new AddCompanionScreen().displayAddCompanion(orderService);
                    valid = true;
                    break;
                case "5":
                   new DisplayCheckoutScreen().displayCheckout(orderService);
                    valid = true;
                    break;
                case "6":
                    RPGDisplay.printStory("You abandon the quest. Another time, perhaps...\n");
                    valid = true;
                    break;
                default:
                    RPGDisplay.printWarning("Invalid option, try again!\n");
            }
        }
    }

}