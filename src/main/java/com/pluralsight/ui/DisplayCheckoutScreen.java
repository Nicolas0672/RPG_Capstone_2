package com.pluralsight.ui;

import com.pluralsight.model.Priceable;
import com.pluralsight.model.companion.Companion;
import com.pluralsight.model.potion.Potion;
import com.pluralsight.model.weapon.Weapon;
import com.pluralsight.services.OrderService;
import com.pluralsight.services.ReceiptWriter;
import com.pluralsight.ui.utils.Helper;
import com.pluralsight.ui.utils.RPGDisplay;

import java.util.List;
import java.util.Scanner;

public class DisplayCheckoutScreen {
    private final Scanner scanner = new Scanner(System.in);

    public void displayCheckout(OrderService orderService) {

        // Weapons
        List<Weapon> weaponList = orderService.getTotalWeaponList();
        List<Potion> potionList = orderService.getPotionList();
        List<Companion> companionList = orderService.getCompanionList();

        // Helper boolean
        boolean bought = false;

        if(!weaponList.isEmpty() || !potionList.isEmpty() || !companionList.isEmpty()){
            bought = true;
            System.out.println("🛒 Your Total Order:");
            System.out.println("\n⚔️ Weapons:");
            for (Weapon w : weaponList) RPGDisplay.printFinalWeaponCard(w);
            System.out.println("\n🧪 Potions:");
            for (Potion p : potionList) RPGDisplay.printFinalPotionCard(p);
            System.out.println("\n🐾 Companions:");
            for (Companion c : companionList) RPGDisplay.printFinalCompanionCard(c);
        } else {
            boolean isValid = false;
            while(!isValid){
                RPGDisplay.printOptions("You must purchase either purchase a Potion or Companion!\n1) Potion\n2) Companion\n3) Exit");
                String input = scanner.nextLine();
                switch (input){
                    case "1": bought = new AddPotionScreen().displayAddPotion(orderService);
                    isValid = true;
                    break;
                    case "2": bought = new AddCompanionScreen().displayAddCompanion(orderService);
                    isValid = true;
                    break;
                    case "3":
                        RPGDisplay.printSuccess("Leaving the checkout..");
                            return;
                    default:
                        RPGDisplay.printWarning("Invalid option! Please try again");
                }
            }
        }
        boolean isValid = false;
        while(!isValid && bought){
            System.out.printf("Total Cost: %.2f\n", orderService.getTotalCartPrice());
            RPGDisplay.printStory("Would you like to pay now or edit your order.");
            RPGDisplay.printOption(1, "Pay now");
            RPGDisplay.printOption(2, "Edit");
            RPGDisplay.printOption(3, "Return");

            String input = scanner.nextLine();
            switch (input){
                case "1": displayPaidCart(orderService);
                isValid = true;
                    break;
                case "2": displayEditCart(orderService);
                    isValid = true;
                    break;
                case "3":
                    RPGDisplay.printSuccess("Leaving...");
                    isValid = true;
                    return;
                default:
                    RPGDisplay.printWarning("Invalid option! Please try again");
            }
        }
    }

    public void displayPaidCart(OrderService orderService){
        RPGDisplay.printSuccess("Thank you for doing business with us, young traveler!");
        ReceiptWriter.saveReceipt(orderService.getTotalWeaponList(), orderService.getPotionList(),
                orderService.getCompanionList());
        orderService.clearCart();
    }

    public void displayEditCart(OrderService orderService) {
        while(true){
            RPGDisplay.printStory("What would you like to edit?");
            RPGDisplay.printOption(1, "Weapon");
            RPGDisplay.printOption(2, "Potion");
            RPGDisplay.printOption(3, "Companion");
            RPGDisplay.printOption(4, "Return");

            String input = scanner.nextLine();
            switch (input){
                case "1": displayEditSword(orderService);
                break;
                case "4":
                    RPGDisplay.printSuccess("Leaving...");
                    return;
            }
        }
    }

    public void displayEditSword(OrderService orderService){
        List<Weapon> weaponList = orderService.getTotalWeaponList();
        EditScreen editScreen = new EditScreen();

        for (int i = 0; i < weaponList.size(); i++) {
            RPGDisplay.printOptions(String.format("\t\t#%d Order", i + 1));
            RPGDisplay.printDivider();
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
               case "1":
                   editScreen.displayEditGem(orderService, weapon);
                   break;
               case "2":

                   break;
               case "3":
                   break;
               case "4":
                   break;
               case "5":
                   RPGDisplay.printSuccess("Leaving...");
                   return;
               default :
                   RPGDisplay.printWarning("Invalid option. Please try again");
           }
       }

    }



}
