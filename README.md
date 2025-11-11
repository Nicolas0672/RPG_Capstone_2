# ⚔️ RPG Weapon Forge

A Java-based console application for crafting custom RPG weapons with enhancements, managing inventory, and generating detailed receipts for your fantasy arsenal.

## 📖 Project Description

RPG Weapon Forge is an interactive shop management system where players can:
- 🛠️ **Craft Custom Weapons** - Choose from Swords, Axes, and Katanas
- ✨ **Add Enhancements** - Customize weapons with Buffs, Gems, Quirks, and visual Customizations
- 🧪 **Purchase Potions** - Stock up on consumables for your adventures
- 🐾 **Acquire Companions** - Recruit loyal companions like Dogs, Horses, Owls, and Dragons
- 🧾 **Generate Receipts** - Get detailed invoices with itemized costs and rarity multipliers
- 💎 **Rarity System** - Items come in COMMON (1.0x), RARE (1.5x), and LEGENDARY (2.0x) rarities

## 🎮 Application Screenshots

### Main Menu
<img width="448" height="323" alt="RPGShopMenu" src="https://github.com/user-attachments/assets/27e00687-c67f-432f-9189-71da008bcba8" />

*The starting screen where players choose their action*

### Weapon Builder
<img width="341" height="342" alt="RPGShopWeapon" src="https://github.com/user-attachments/assets/b2483936-dca8-49da-a72d-17636d2d8f6b" />

*Interactive weapon customization interface*

### Shopping Cart
<img width="392" height="428" alt="RPGShopCart" src="https://github.com/user-attachments/assets/c57ccd79-2c4d-46ee-9478-bfb8055096b2" />

*Review your items before checkout*

### Receipt
<img width="277" height="456" alt="RPGShopReceipt" src="https://github.com/user-attachments/assets/925ed4a3-d029-4d2f-934d-b92ebc7daddf" />

*Detailed invoice generated after purchase*

## 🗂️ Project Structure

```
RPG-Weapon-Forge/
├── 📁 src/
│   └── 📁 main/
│       └── 📁 java/
│           └── 📁 com/pluralsight/
│               ├── 📁 model/
│               │   ├── 📁 core/
│               │   │   ├── Item.java
│               │   │   └── Rarity.java
│               │   ├── 📁 weapons/
│               │   │   ├── Weapon.java
│               │   │   ├── Sword.java
│               │   │   ├── Axe.java
│               │   │   └── Katana.java
│               │   ├── 📁 enhancements/
│               │   │   ├── Enhancement.java
│               │   │   ├── Buffs.java
│               │   │   ├── Gem.java
│               │   │   ├── Quirks.java
│               │   │   └── Customization.java
│               │   ├── 📁 potions/
│               │   │   └── Potion.java
│               │   └── 📁 companions/
│               │       ├── Companion.java
│               │       ├── Dog.java
│               │       ├── Horse.java
│               │       ├── Owl.java
│               │       └── Dragon.java
│               ├── 📁 service/
│               │   ├── WeaponBuilder.java
│               │   ├── OrderService.java
│               │   └── ReceiptWriter.java
│               ├── 📁 interfaces/
│               │   ├── Priceable.java
│               │   └── Describable.java
│               └── Main.java
├── 📁 receipts/
│   └── (generated receipt files)
└── README.md
```

## 🏗️ Class Diagram

<img width="3753" height="2069" alt="UMLRPGShop" src="https://github.com/user-attachments/assets/75526e62-25b3-45df-82d2-45107e935f5f" />

The application follows object-oriented design principles with clear separation of concerns:

- **Core Package**: Base `Item` class and `Rarity` enum with multipliers
- **Weapons Package**: Abstract `Weapon` class with concrete implementations (Sword, Axe, Katana)
- **Enhancements Package**: Four types of enhancements (Buffs, Gems, Quirks, Customization) with their respective enums
- **Potions & Companions Packages**: Additional purchasable items that extend `Item` or standalone entities
- **Service Package**: Business logic for building weapons, managing orders, and generating receipts
- **Interfaces**: `Priceable` for cost calculation and `Describable` for detailed descriptions

## 💡 Interesting Code Highlight

### Generic Pricing Method with Type Bounds

One of the most flexible pieces of code in this project is the `getTotalPrice` method in the `WeaponBuilder` class:

```java
public <T extends Priceable> double getTotalPrice(double baseCost, List<T> priceables) {
    // Sum all enhancement costs and add to base cost
    double total = priceables.stream()
            .mapToDouble(Priceable::calculateCost)
            .sum();
    return baseCost + total;
}
```

**Why this is interesting:**

- **Generic Type with Bounds** (`<T extends Priceable>`): This method can work with *any* class that implements the `Priceable` interface
- **Polymorphic Flexibility**: Works seamlessly with Weapons, Enhancements, Potions, and Companions
- **Stream API**: Uses functional programming for clean, readable cost aggregation
- **Single Responsibility**: One method handles pricing for all priceable items in the system
- **Open/Closed Principle**: New `Priceable` types can be added without modifying this method

This demonstrates how interfaces and generics create flexible, maintainable code that adapts to future requirements.

## ⚙️ Features

- ✅ Interactive console-based UI
- ✅ Builder pattern for weapon construction
- ✅ Dynamic pricing based on rarity multipliers
- ✅ Comprehensive enhancement system 
- ✅ Receipt generation with itemized costs
- ✅ Input validation and error handling
- ✅ Clean separation of concerns using OOP principles

## 🎯 Design Patterns Used

- **Builder Pattern**: `WeaponBuilder` for step-by-step weapon construction
- **Strategy Pattern**: Different `Priceable` implementations for varied cost calculations
- **Template Method**: Abstract `Item` and `Enhancement` classes define common structure
- **Factory-like Methods**: `OrderService.weaponBuild()` creates weapons based on type strings

## 📦 Dependencies

- Java 17+
- No external libraries required (pure Java implementation)

## 👨‍💻 Author

Created as a capstone project demonstrating object-oriented programming principles, design patterns, and clean code architecture.

## 📝 License

This project is for educational purposes.

---

*Forge your legend, one weapon at a time.* ⚔️✨
