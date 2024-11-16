package inventory;

import database.MedicineDB;
import database.ReplenishmentDB;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private Map<String, Medicine> medicines;
    private MedicineDB medicineDB; // Reference to MedicineDB
    private ReplenishmentDB replenishmentDB; // Reference to ReplenishmentDB

    public Inventory(MedicineDB medicineDB, ReplenishmentDB replenishmentDB) {
        this.medicines = new HashMap<>();
        this.medicineDB = medicineDB;
        this.replenishmentDB = replenishmentDB;

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

    // Add a new medicine to the inventory and save to the database
    public void addMedicine(Medicine medicine) {
        medicines.put(medicine.getId(), medicine);
        try {
            medicineDB.create(medicine);
            medicineDB.save();
            System.out.println("Added medicine: " + medicine.getName());
        } catch (IOException e) {
            System.out.println("Error saving new medicine to the database: " + e.getMessage());
        }
    }

    // Update stock level for a medicine and save the changes
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
                medicineDB.update(medicine);
                medicineDB.save();
            } catch (IOException e) {
                System.out.println("Error saving updates to MedicineDB: " + e.getMessage());
            }
        } else {
            System.out.println("Medicine not found.");
        }
    }

    // Remove a medicine from the inventory and the database
    public void removeMedicine(String id) {
        if (medicines.remove(id) != null) {
            try {
                medicineDB.delete(id);
                medicineDB.save();
                System.out.println("Removed medicine with ID: " + id);
            } catch (IOException e) {
                System.out.println("Error saving removal to MedicineDB: " + e.getMessage());
            }
        } else {
            System.out.println("Medicine not found.");
        }
    }

    // Replenish stock and create a request in the replenishment database if needed
    public boolean replenishStock(String id, int quantity) {
    if (medicines.containsKey(id)) {
        Medicine medicine = medicines.get(id);
        medicine.setStockLevel(medicine.getStockLevel() + quantity);
        System.out.println("Replenished " + quantity + " units of " + medicine.getName());

        if (!medicine.isStockLow()) {
            System.out.println("Stock level for " + medicine.getName() + " is now above the low stock alert level.");
        }

        // Create or update a replenishment request using ReplenishmentDB and ReplenishmentRequest
        try {
            ReplenishmentRequest requestManager = new ReplenishmentRequest(replenishmentDB);
            boolean requestSuccessful = requestManager.submitRequest(id, quantity, medicine);
            return requestSuccessful; // Return true if the request was successfully submitted
        } catch (Exception e) {
            System.out.println("Error handling replenishment request: " + e.getMessage());
        }
    } else {
        System.out.println("Medicine not found.");
    }
    return false; // Return false if the process failed
    }

    // Display current inventory
    public void displayInventory() {
        if (medicines.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        for (Medicine medicine : medicines.values()) {
            System.out.println(medicine);
        }
    }

    // Check if a specific medicine's stock is low
    public boolean isLow(String id) {
        if (medicines.containsKey(id)) {
            return medicines.get(id).isStockLow();
        }
        return false;
    }

    // Display medicines that are low on stock
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

    // Get a specific medicine by ID
    public Medicine getMedicineById(String id) {
        return medicines.get(id);
    }

    // Display all replenishment requests
    public void displayReplenishmentRequests() {
        List<ReplenishmentRequest> requests = replenishmentDB.getAll();
        if (requests.isEmpty()) {
            System.out.println("No replenishment requests have been submitted.");
        } else {
            System.out.println("Replenishment Requests:");
            for (ReplenishmentRequest request : requests) {
                System.out.println(request);
            }
        }
    }

    // Get a list of all replenishment requests
    public List<ReplenishmentRequest> getReplenishmentRequests() {
        return replenishmentDB.getAll();
    }
}
