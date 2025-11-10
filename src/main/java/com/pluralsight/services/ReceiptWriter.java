package com.pluralsight.services;

import com.pluralsight.model.companion.Companion;
import com.pluralsight.model.potion.Potion;
import com.pluralsight.model.weapon.Weapon;
import com.pluralsight.model.enhancement.Enhancement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReceiptWriter {

    public static void saveReceipt(List<Weapon> weapons, List<Potion> potions, List<Companion> companions) {
        // Format timestamp for unique filename
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String fileName = "receipts/" + timestamp + ".txt";


        double totalCost = 0.0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("=========================================\n");
            writer.write("        ⚔️  RPG Armory Receipt ⚔️\n");
            writer.write("=========================================\n");
            writer.write("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n\n");

            // ---- Weapons ----
            if (!weapons.isEmpty()) {
                writer.write("🗡️ Weapons Purchased:\n");
                for (Weapon w : weapons) {
                    writer.write(" - " + w.getName() + " (" + w.getRarity() + ")\n");
                    writer.write("   💰 Price: " + String.format("%.2f", w.getBaseCost()) + "\n");
                    totalCost += w.getBaseCost();

                    if (w.getEnhancement() != null && !w.getEnhancement().isEmpty()) {
                        writer.write("   🔧 Enhancements:\n");
                        for (Enhancement e : w.getEnhancement()) {
                            writer.write("     • " + e.getName() + " (" + e.getRarity() + ") +" + e.calculateCost() + "\n");
                            totalCost += e.calculateCost();
                        }
                    }
                    writer.write("\n");
                }
            }

            // ---- Potions ----
            if (!potions.isEmpty()) {
                writer.write("🧪 Potions Purchased:\n");
                for (Potion p : potions) {
                    writer.write(" - " + p.getName() + " (" + p.getRarity() + ")\n");
                    writer.write("   💰 Price: " + String.format("%.2f", p.getBaseCost()) + "\n\n");
                    totalCost += p.getBaseCost();
                }
            }

            // ---- Companions ----
            if (!companions.isEmpty()) {
                writer.write("🦉 Companions Purchased:\n");
                for (Companion c : companions) {
                    writer.write(" - " + c.getName() + " (" + c.getRarity() + ")\n");
                    writer.write("   💰 Price: " + String.format("%.2f", c.getPrice()) + "\n\n");
                    totalCost += c.getPrice();
                }
            }

            writer.write("=========================================\n");
            writer.write("💵 Total Cost: " + String.format("%.2f", totalCost) + "\n");
            writer.write("=========================================\n");
            writer.write("Thank you for shopping at the RPG Armory!\n");
            writer.write("May your journey be victorious!\n");
            writer.write("=========================================\n");

            writer.flush();
            System.out.println("🧾 Receipt saved successfully at: " + fileName);

        } catch (IOException e) {
            System.out.println("⚠️ Error saving receipt: " + e.getMessage());
        }
    }
}
