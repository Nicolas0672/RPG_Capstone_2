package com.pluralsight.ui.utils;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Helper {
    public static <T> T getSelectionFromList(List<T> items, Scanner scanner) {
        while (true) {
            RPGDisplay.printStory("Enter your selection number: (0 to skip)");
            String input = scanner.nextLine().trim();
            System.out.println();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 0) return null;
                if (choice >= 1 && choice <= items.size()) return items.get(choice - 1);
                RPGDisplay.printWarning("Enter a valid number.\n");
            } catch (NumberFormatException e) {
                RPGDisplay.printWarning("Please enter a number.\n");
            }
        }
    }

    public static <T> List<T> getSelectionFromNestedList(List<List<T>> listOfLists, Scanner scanner) {
        while (true) {
            RPGDisplay.printStory("Enter the order number: (0 to skip)");
            String input = scanner.nextLine().trim();
            System.out.println();

            try {
                int choice = Integer.parseInt(input);
                if (choice == 0) return null;
                if (choice >= 1 && choice <= listOfLists.size())
                    return listOfLists.get(choice - 1);
                RPGDisplay.printWarning("Enter a valid number.\n");
            } catch (NumberFormatException e) {
                RPGDisplay.printWarning("Please enter a number.\n");
            }
        }
    }


    public <T> T displaySelection(List<T> items, Function<T, String> displayFunction, Scanner scanner) {
        while (true) {
            for (int i = 0; i < items.size(); i++) {
                RPGDisplay.printOption(i + 1, displayFunction.apply(items.get(i)));
            }
            String input = scanner.nextLine().trim();
            System.out.println();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 0) return null;
                if (choice >= 1 && choice <= items.size()) return items.get(choice - 1);
                RPGDisplay.printWarning("Enter a valid number.\n");
            } catch (NumberFormatException e) {
                RPGDisplay.printWarning("Please enter a number.\n");
            }
        }
    }
}
