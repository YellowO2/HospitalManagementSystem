package inventory;

import database.MedicineDB;
import database.ReplenishmentDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private Map<String, Medicine> medicines;
    private List<String> replenishmentRequests;
    private MedicineDB medicineDB; // Reference to the MedicineDB
    private ReplenishmentDB replenishmentDB;

    public Inventory() {
        this.medicines = new HashMap<>();
        this.replenishmentRequests = new ArrayList<>();
        this.medicineDB = new MedicineDB(); // Initialize MedicineDB
        this.replenishmentDB = new ReplenishmentDB();

        try {
            loadFromMedicineDB(); // Load data from MedicineDB when Inventory is created
        } catch (IOException e) {
            System.out.println("Error loading medicines from the database: " + e.getMessage());
        }
    }

    // Load medicines from MedicineDB
    public void loadFromMedicineDB() throws IOException {
        medicines.clear(); // Clear current data before loading new data
        medicineDB.load(); // Load the data from the CSV file
        List<Medicine> medicineList = medicineDB.getAll();
        for (Medicine medicine : medicineList) {
            medicines.put(medicine.getId(), medicine);
        }
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

            // Save the updated data to the MedicineDB
            try {
                medicineDB.save();
            } catch (IOException e) {
                System.out.println("Error saving to MedicineDB: " + e.getMessage());
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

    public boolean isLow(String id) {
        if (medicines.containsKey(id)) {
            return medicines.get(id).isStockLow();
        }
        return false;
    }

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

    public void displayReplenishmentRequests() {
        List<ReplenishmentRequest> requests = replenishmentDB.getAll();
        if (replenishmentRequests.isEmpty()) {
            System.out.println("No replenishment requests have been submitted.");
        } else {
            System.out.println("Replenishment Requests:");
            for (String request : replenishmentRequests) {
                System.out.println(request);
            }
        }
    }

    public List<String> getReplenishmentRequests() {
        return replenishmentRequests;
    }
}