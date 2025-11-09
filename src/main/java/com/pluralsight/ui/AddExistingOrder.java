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

public class AddExistingOrder {
    private final Scanner scanner = new Scanner(System.in);

    public void displayAddExisting(OrderService orderService) {
        List<List<Priceable>> allOrderList = orderService.getAllTypeOfOrders();

        System.out.println("\n");
        RPGDisplay.printTitle("💎 Pre-Forged Legendary Orders 💎");
        RPGDisplay.printDivider();

        int index = 1;
        for (List<Priceable> orderSet : allOrderList) {
            double total = 0;
            // Give each order group a header (e.g. “Order #1” or derived from weapon name)
            String orderName = getOrderTitle(orderSet, index);
            RPGDisplay.printSubTitle(orderName);
            RPGDisplay.printDivider();

            // Print all items in this order
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

            // End each order with a total line
            System.out.printf(RPGDisplay.GREEN + "🧾 Total Order Cost: %.2f 💰%n" + RPGDisplay.RESET, total);
            RPGDisplay.printDivider();
            index++;
        }
        List<Priceable> list = Helper.getSelectionFromNestedList(allOrderList, scanner);
        if(list != null){
            for(Priceable priceable : list){
                if(priceable instanceof Weapon w){
                    Weapon cloneWeapon = WeaponBuilder.cloneWeapon(w);
                    orderService.addWeaponToCart(cloneWeapon);
                } else if(priceable instanceof Potion p){
                    orderService.addPotionToCart(p);
                } else if(priceable instanceof Companion c){
                    orderService.addCompanionToCart(c);
                }
            }
            RPGDisplay.printSuccess("Your forged set has been added to the inventory!");
            RPGDisplay.printDivider();
        }
    }


    // Helper
    public String getOrderTitle(List<Priceable> orderSet, int index) {
        // Try to name order based on weapon name if present
        for (Priceable item : orderSet) {
            if (item instanceof Weapon w) {
                return "Order #" + index + " — " + w.getName();
            }
        }
        return "Order #" + index;
    }

}
