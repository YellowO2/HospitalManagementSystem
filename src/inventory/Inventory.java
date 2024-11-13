package inventory;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Medicine> medicines;

    public Inventory() {
        this.medicines = new HashMap<>();
    }

    public void addMedicine(Medicine medicine) {
        medicines.put(medicine.getId(), medicine);
        System.out.println("Added medicine: " + medicine.getName());
    }

    public void updateMedicine(String id, int newStockLevel) {
        if (medicines.containsKey(id)) {
            Medicine medicine = medicines.get(id);
            medicine.setStockLevel(newStockLevel);
            System.out.println("Updated stock level for " + medicine.getName() + " to " + newStockLevel);

            if (!medicine.isStockLow()) {
                System.out.println("Stock level for " + medicine.getName() + " is now above the low stock alert level.");
            }
        } else {
            System.out.println("Medicine not found.");
        }
    }

    public void removeMedicine(String id) {
        if (medicines.remove(id) != null) {
            System.out.println("Removed medicine with ID: " + id);
        } else {
            System.out.println("Medicine not found.");
        }
    }

    public void replenishStock(String id, int quantity) {
        if (medicines.containsKey(id)) {
            Medicine medicine = medicines.get(id);
            medicine.setStockLevel(medicine.getStockLevel() + quantity);
            System.out.println("Replenished " + quantity + " units of " + medicine.getName());

            if (!medicine.isStockLow()) {
                System.out.println("Stock level for " + medicine.getName() + " is now above the low stock alert level.");
            }
        } else {
            System.out.println("Medicine not found.");
        }
    }

    public void displayInventory() {
        if (medicines.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        for (Medicine medicine : medicines.values()) {
            System.out.println(medicine);
        }
    }

    // Check if a specific medicine is below its low stock alert level
    public boolean isLow(String id) {
        if (medicines.containsKey(id)) {
            return medicines.get(id).isStockLow();
        }
        return false;
    }

    // Display all medicines that are below their low stock alert levels
    public void displayLowStockMedicines() {
        boolean lowStockFound = false;
        for (Medicine medicine : medicines.values()) {
            if (medicine.isStockLow()) {
                System.out.println("Low stock alert for: " + medicine.getName() + 
                                   " (Current Stock: " + medicine.getStockLevel() + 
                                   ", Alert Level: " + medicine.getLowStockLevelAlert() + ")");
                lowStockFound = true;
            }
        }
        if (!lowStockFound) {
            System.out.println("No medicines are currently below their low stock levels.");
        }
    }
    
    public Medicine getMedicineById(String id) {
        return medicines.get(id);
    }
}