package com.pluralsight.ui.utils;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Helper
 * ----------------
 * Provides reusable utility methods for:
 * - Selecting items from lists or nested lists
 * - Displaying selection menus with input validation
 *
 * All methods handle invalid input gracefully and allow skipping with 0.
 */
public class Helper {

    // ---------------------
    // SELECT SINGLE ITEM FROM A LIST
    // ---------------------
    public static <T> T getSelectionFromList(List<T> items, Scanner scanner) {
        while (true) {
            RPGDisplay.printStory("Enter your selection number: (0 to skip)");

            String input = scanner.nextLine().trim();
            System.out.println();

            try {
                int choice = Integer.parseInt(input);

                if (choice == 0) return null; // User skipped
                if (choice >= 1 && choice <= items.size()) return items.get(choice - 1);

                // Invalid number outside range
                RPGDisplay.printWarning("Enter a valid number.\n");

            } catch (NumberFormatException e) {
                // User did not enter a number
                RPGDisplay.printWarning("Please enter a number.\n");
            }
        }
    }

    // ---------------------
    // SELECT A SUB-LIST FROM A NESTED LIST
    // ---------------------
    public static <T> List<T> getSelectionFromNestedList(List<List<T>> listOfLists, Scanner scanner) {
        while (true) {
            RPGDisplay.printStory("Enter the order number: (0 to skip)");

            String input = scanner.nextLine().trim();
            System.out.println();

            try {
                int choice = Integer.parseInt(input);

                if (choice == 0) return null; // User skipped
                if (choice >= 1 && choice <= listOfLists.size())
                    return listOfLists.get(choice - 1);

                // Invalid number outside range
                RPGDisplay.printWarning("Enter a valid number.\n");

            } catch (NumberFormatException e) {
                // User did not enter a number
                RPGDisplay.printWarning("Please enter a number.\n");
            }
        }
    }

    // ---------------------
    // DISPLAY A MENU WITH CUSTOM LABELS AND SELECT ITEM
    // ---------------------
    public static <T> T displaySelection(List<T> items, Function<T, String> displayFunction, Scanner scanner) {
        while (true) {

            // Print all items with custom labels
            for (int i = 0; i < items.size(); i++) {
                RPGDisplay.printOption(i + 1, displayFunction.apply(items.get(i)));
            }

            String input = scanner.nextLine().trim();
            System.out.println();

            try {
                int choice = Integer.parseInt(input);

                if (choice == 0) return null; // User skipped
                if (choice >= 1 && choice <= items.size()) return items.get(choice - 1);

                // Invalid number outside range
                RPGDisplay.printWarning("Enter a valid number.\n");

            } catch (NumberFormatException e) {
                // User did not enter a number
                RPGDisplay.printWarning("Please enter a number.\n");
            }
        }
    }
}
