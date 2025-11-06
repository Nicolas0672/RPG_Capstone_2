package com.pluralsight.ui;

import java.util.Scanner;

public class DisplayScreen {
Scanner scanner = new Scanner(System.in);

    public void displayHome() {
        System.out.println("Welcome to RPG");
        boolean valid = false;
        while(!valid){
            System.out.println("1) New Order\n0) Exist");
            String input = scanner.nextLine();
            switch (input) {
                case "1" : displayNewOrder();
                valid = true;
                break;
                case "2" : System.exit(0);
                valid = true;
                break;
                default:
                    System.out.println("Invalid choice, please try again");
            }
        }
    }

    public void displayNewOrder(){
        // Display newest entry first

        boolean valid = false;
        while(!valid){
            System.out.println("1) Add a weapon\n2) Add a potion\n3) Add your traveling companion\n4) Checkout\n5) Cancel");
            String input = scanner.nextLine();
            switch (input) {
                case "1": displayAddWeapon();
                valid = true;
                break;
                case "2": displayAddPotion();
                valid = true;
                break;
                case "3": displayAddCompanion();
                valid = true;
                break;
                case "4": displayCheckout();
                valid = true;
                break;
                case "0": displayCancelOrder();
                valid = true;
                break;
                default:
                    System.out.println("Invalid option, please try again");
            }
        }
    }

    public void displayAddWeapon() {
        boolean isValid = false;
        while(!isValid) {
            System.out.println("What kind of weapons would you like to add?\n1) Sword\n2) Axe\n3) Katana ");
        }
    }
}
