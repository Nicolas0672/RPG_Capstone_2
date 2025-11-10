package com.pluralsight.ui;

import com.pluralsight.model.Priceable;
import com.pluralsight.model.companion.Companion;
import com.pluralsight.model.potion.Potion;
import com.pluralsight.model.weapon.Weapon;
import com.pluralsight.services.OrderService;
import com.pluralsight.services.WeaponBuilder;
import com.pluralsight.ui.utils.Helper;
import com.pluralsight.ui.utils.RPGDisplay;

import java.util.List;
import java.util.Scanner;

/**
 * AddExistingOrder
 * -----------------
 * Allows the user to browse pre-forged legendary orders and add them to their cart.
 */
public class AddExistingOrder {

    private final Scanner scanner = new Scanner(System.in);

    // -------------------------
    // DISPLAY ALL PRE-FORGED ORDERS
    // -------------------------
    public void displayAddExisting(OrderService orderService) {

        List<List<Priceable>> allOrderList = orderService.getAllTypeOfOrders();

        RPGDisplay.printTitle("💎 Pre-Forged Legendary Orders 💎");
        RPGDisplay.printDivider();

        int index = 1;
        for (List<Priceable> orderSet : allOrderList) {

            double total = 0;

            // Generate order header, optionally using weapon name
            String orderName = getOrderTitle(orderSet, index);
            RPGDisplay.printSubTitle(orderName);
            RPGDisplay.printDivider();

            // Display all items in the current order and accumulate total cost
            for (Priceable item : orderSet) {
                if (item instanceof Weapon w) {
                    total += w.getFinalCost();
                    RPGDisplay.printFinalWeaponCard(w);
                } else if (item instanceof Potion p) {
                    total += p.getBaseCost();
                    RPGDisplay.printFinalPotionCard(p);
                } else if (item instanceof Companion c) {
                    total += c.getPrice();
                    RPGDisplay.printFinalCompanionCard(c);
                }
            }

            // Show total cost of the order
            System.out.printf(RPGDisplay.GREEN + "🧾 Total Order Cost: %.2f 💰%n" + RPGDisplay.RESET, total);
            RPGDisplay.printDivider();
            index++;
        }

        // Let the user select a full pre-forged order to add to their cart
        List<Priceable> selectedList = Helper.getSelectionFromNestedList(allOrderList, scanner);
        if (selectedList != null) {
            for (Priceable priceable : selectedList) {
                if (priceable instanceof Weapon w) {
                    Weapon cloneWeapon = WeaponBuilder.cloneWeapon(w); // clone to avoid shared state
                    orderService.addWeaponToCart(cloneWeapon);
                } else if (priceable instanceof Potion p) {
                    orderService.addPotionToCart(p);
                } else if (priceable instanceof Companion c) {
                    orderService.addCompanionToCart(c);
                }
            }

            RPGDisplay.printSuccess("Your forged set has been added to the inventory!");
            RPGDisplay.printDivider();
        }
    }

    // -------------------------
    // HELPER METHOD
    // -------------------------
    /**
     * Generates an order title based on the first weapon found, else uses generic numbering.
     */
    public String getOrderTitle(List<Priceable> orderSet, int index) {
        for (Priceable item : orderSet) {
            if (item instanceof Weapon w) {
                return "Order #" + index + " — " + w.getName();
            }
        }
        return "Order #" + index;
    }
}
