package com.pluralsight.ui;

import com.pluralsight.model.Priceable;
import com.pluralsight.model.companion.Companion;
import com.pluralsight.model.potion.Potion;
import com.pluralsight.model.weapon.Weapon;
import com.pluralsight.services.OrderService;
import com.pluralsight.services.ReceiptWriter;
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

        if(!weaponList.isEmpty() || !potionList.isEmpty() || !companionList.isEmpty()){
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
                System.out.println("You must purchase either purchase a Potion or Companion!\n1) Potion\n2) Companion\n3) Exit");
                String input = scanner.nextLine();
                switch (input){
                    case "1": new AddPotionScreen().displayAddPotion(orderService);
                    isValid = true;
                    break;
                    case "2": new AddCompanionScreen().displayAddCompanion(orderService);
                    isValid = true;
                    break;
                    case "3":
                        System.out.println("Leaving the checkout..");
                            return;
                    default:
                        System.out.println("Invalid option! Please try again");
                }
            }
        }
        System.out.printf("Total Cost: %.2f\n", orderService.getTotalCartPrice());
        boolean isValid = false;
        while(!isValid){
            System.out.println("Would you like to pay now or edit your sword.\n1) Pay now\n2) Edit\n3) Return");
            String input = scanner.nextLine();
            switch (input){
                case "1": displayPaidCart(orderService);
                isValid = true;
                    break;
                case "2": // displayEditCart
                    isValid = true;
                    break;
                case "3":
                    System.out.println("Leaving...");
                    isValid = true;
                    return;
                default:
                    System.out.println("Invalid option! Please try again");
            }
        }
    }

    public void displayPaidCart(OrderService orderService){
        System.out.println("Thank you for doing business with us, young traveler!");
        ReceiptWriter.saveReceipt(orderService.getTotalWeaponList(), orderService.getPotionList(),
                orderService.getCompanionList());
        orderService.clearCart();
    }

    public void displayEditCart() {

    }



}
