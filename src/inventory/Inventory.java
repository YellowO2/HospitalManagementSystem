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
            medicines.get(id).setStockLevel(newStockLevel);
            System.out.println("Updated stock level for " + medicines.get(id).getName() + " to " + newStockLevel);
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

    public boolean isLow(String id, int threshold) {
        if (medicines.containsKey(id)) {
			return medicines.get(id).getStockLevel() < threshold ;
        }
        return false;
    }
}