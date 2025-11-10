package com.pluralsight.ui;

import com.pluralsight.model.companion.Companion;
import com.pluralsight.model.potion.Potion;
import com.pluralsight.model.weapon.Weapon;
import com.pluralsight.services.OrderService;
import com.pluralsight.services.ReceiptWriter;
import com.pluralsight.ui.utils.Helper;
import com.pluralsight.ui.utils.RPGDisplay;

import java.util.List;
import java.util.Scanner;

/**
 * DisplayCheckoutScreen
 * ---------------------
 * Handles the checkout flow for weapons, potions, and companions:
 * - Shows the current cart
 * - Allows editing or adding items
 * - Handles payment and receipt generation
 */
public class DisplayCheckoutScreen {
    private final Scanner scanner = new Scanner(System.in);

    // ---------------------
    // MAIN CHECKOUT DISPLAY
    // ---------------------
    public void displayCheckout(OrderService orderService) {

        List<Weapon> weaponList = orderService.getTotalWeaponList();
        List<Potion> potionList = orderService.getPotionList();
        List<Companion> companionList = orderService.getCompanionList();

        boolean bought = false;

        // ---------------------
        // Show current cart if any items
        // ---------------------
        if(!weaponList.isEmpty() || !potionList.isEmpty() || !companionList.isEmpty()){
            bought = true;
            displayTotalItemInCart(weaponList, potionList, companionList);

            // ---------------------
            // Prompt to add at least a potion or companion if cart is empty
            // ---------------------
        } else {
            boolean isValid = false;
            while(!isValid){
                RPGDisplay.printOptions("You must purchase either a Potion or Companion!\n1) Potion\n2) Companion\n3) Exit");
                String input = scanner.nextLine();

                switch (input){
                    case "1":
                        bought = new AddPotionScreen().displayAddPotion(orderService);
                        isValid = true;
                        break;
                    case "2":
                        bought = new AddCompanionScreen().displayAddCompanion(orderService);
                        isValid = true;
                        break;
                    case "3":
                        RPGDisplay.printLeavingMessage();
                        return;
                    default:
                        RPGDisplay.printWarning("Invalid option! Please try again");
                }
            }
        }

        // ---------------------
        // Prompt to pay or edit cart
        // ---------------------
        boolean isValid = false;
        while(!isValid && bought){
            RPGDisplay.printCartTotal(orderService.getTotalCartPrice());
            RPGDisplay.printStory("Would you like to pay now or edit your order?");
            RPGDisplay.printOption(1, "Pay now");
            RPGDisplay.printOption(2, "Edit");
            RPGDisplay.printOption(3, "Show cart");
            RPGDisplay.printOption(4, "Return");

            String input = scanner.nextLine();
            switch (input){
                case "1":
                    displayPaidCart(orderService);
                    isValid = true;
                    break;
                case "2":
                    displayEditCart(orderService);
                    break;
                case "3":
                    displayTotalItemInCart(weaponList, potionList, companionList);
                    break;
                case "4":
                    RPGDisplay.printLeavingMessage();
                    return;
                default:
                    RPGDisplay.printWarning("Invalid option! Please try again");
            }
        }
    }

    // ---------------------
    // PAYMENT FLOW
    // ---------------------
    public void displayPaidCart(OrderService orderService){
        RPGDisplay.printSuccess("Thank you for doing business with us, young traveler!");
        ReceiptWriter.saveReceipt(orderService.getTotalWeaponList(),
                orderService.getPotionList(),
                orderService.getCompanionList());
        orderService.clearCart();
    }

    public void displayTotalItemInCart(List<Weapon> weaponList, List<Potion> potionList, List<Companion> companionList){
        System.out.println("🛒 Your Total Order:");

        if(!weaponList.isEmpty()){
            System.out.println("\n⚔️ Weapons:");
            for (Weapon w : weaponList) RPGDisplay.printFinalWeaponCard(w);
        }
        if(!potionList.isEmpty()){
            System.out.println("🧪 Potions:");
            for (Potion p : potionList) RPGDisplay.printFinalPotionCard(p);
        }
        if(!companionList.isEmpty()){
            System.out.println("🐾 Companions:");
            for (Companion c : companionList) RPGDisplay.printFinalCompanionCard(c);
        }
    }

    // ---------------------
    // EDIT CART OPTIONS
    // ---------------------
    public void displayEditCart(OrderService orderService) {
        EditScreen editScreen = new EditScreen();

        while(true){
            RPGDisplay.printStory("What would you like to edit?");
            RPGDisplay.printOption(1, "Weapon");
            RPGDisplay.printOption(2, "Potion");
            RPGDisplay.printOption(3, "Companion");
            RPGDisplay.printOption(4, "Return");

            String input = scanner.nextLine();
            switch (input){
                case "1": displayEditSword(orderService, editScreen); break;
                case "2": displayEditPotion(orderService); break;
                case "3": displayEditCompanion(orderService); break;
                case "4":
                    RPGDisplay.printLeavingMessage();
                    return;
            }
        }
    }

    // ---------------------
    // EDIT INDIVIDUAL WEAPON
    // ---------------------
    public void displayEditSword(OrderService orderService, EditScreen editScreen){
        List<Weapon> weaponList = orderService.getTotalWeaponList();

        for (int i = 0; i < weaponList.size(); i++) {
            RPGDisplay.printWeaponOrderHeader(i);
            RPGDisplay.printFinalWeaponCard(weaponList.get(i));
        }

        RPGDisplay.printOptions("What weapon would you like to edit?");
        Weapon weapon =  Helper.getSelectionFromList(weaponList, scanner);

        while(true){
            if(weapon == null) return;

            RPGDisplay.printStory(String.format("What changes would you like to make to %s", weapon.getName()));
            RPGDisplay.printOption(1, "Gem");
            RPGDisplay.printOption(2, "Buff");
            RPGDisplay.printOption(3, "Quirks");
            RPGDisplay.printOption(4, "Customization");
            RPGDisplay.printOption(5, "Return");

            String input = scanner.nextLine();
            switch (input){
                case "1": editScreen.displayEditGem(orderService, weapon); break;
                case "2": editScreen.displayEditBuff(orderService, weapon); break;
                case "3": editScreen.displayEditQuirk(orderService, weapon); break;
                case "4": editScreen.displayEditCustomization(orderService, weapon); break;
                case "5":
                    RPGDisplay.printLeavingMessage();
                    return;
                default:
                    RPGDisplay.printWarning("Invalid option. Please try again");
            }
        }
    }

    // ---------------------
    // EDIT COMPANIONS
    // ---------------------
    public void displayEditCompanion(OrderService orderService){
        List<Companion> companionList = orderService.getCompanionList();
        AddCompanionScreen companionScreen = new AddCompanionScreen();

        for (int i = 0; i < companionList.size(); i++) {
            RPGDisplay.printCompanionOrderHeader(i);
            RPGDisplay.printFinalCompanionCard(companionList.get(i));
        }

        while(true){
            RPGDisplay.printStory("Would you like to add or remove companions from your cart?");
            RPGDisplay.printOption(1, "Add");
            RPGDisplay.printOption(2, "Remove");
            RPGDisplay.printOption(3, "Return");

            String input = scanner.nextLine();
            switch (input){
                case "1" -> companionScreen.displayAddCompanion(orderService);
                case "2" -> companionScreen.displayRemoveCompanion(orderService, companionList);
                case "3" -> {
                    RPGDisplay.printLeavingMessage();
                    return;
                }
            }
        }
    }

    // ---------------------
    // EDIT POTIONS
    // ---------------------
    public void displayEditPotion(OrderService orderService) {
        List<Potion> potionList = orderService.getPotionList();
        AddPotionScreen potionScreen = new AddPotionScreen();

        for (int i = 0; i < potionList.size(); i++) {
            RPGDisplay.printPotionOrderHeader(i);
            RPGDisplay.printFinalPotionCard(potionList.get(i));
        }

        while(true){
            RPGDisplay.printStory("Would you like to add or remove potions from your cart?");
            RPGDisplay.printOption(1, "Add");
            RPGDisplay.printOption(2, "Remove");
            RPGDisplay.printOption(3, "Return");

            String input = scanner.nextLine();
            switch (input){
                case "1" -> potionScreen.displayAddPotion(orderService);
                case "2" -> potionScreen.displayRemovePotion(orderService, potionList);
                case "3" -> {
                    RPGDisplay.printLeavingMessage();
                    return;
                }
            }
        }
    }
}
