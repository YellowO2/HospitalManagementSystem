package inventory;

import database.MedicineDB;
import database.ReplenishmentDB;
import java.io.IOException;
import java.util.List;

public class Inventory {

    private MedicineDB medicineDB; // Reference to MedicineDB
    private ReplenishmentDB replenishmentDB; // Reference to ReplenishmentDB

    public Inventory(MedicineDB medicineDB, ReplenishmentDB replenishmentDB) {
        this.medicineDB = medicineDB;
        this.replenishmentDB = replenishmentDB;
    }

    // Add a new medicine directly to the database
    public void addMedicine(Medicine medicine) {
        try {
            if (medicineDB.create(medicine)) {
                medicineDB.save();
                System.out.println("Added medicine: " + medicine.getName());
            } else {
                System.out.println("Failed to add medicine.");
            }
        } catch (IOException e) {
            System.out.println("Error saving new medicine to the database: " + e.getMessage());
        }
    }

    // Update a medicine directly in the database
    public void updateMedicine(String id, int newStockLevel) {
        Medicine medicine = medicineDB.getById(id);
        if (medicine != null) {
            medicine.setStockLevel(newStockLevel);
            System.out.println("Updated stock level for " + medicine.getName() + " to " + newStockLevel);

            try {
                if (medicineDB.update(medicine)) {
                    medicineDB.save();
                    System.out.println("Medicine stock updated and saved.");
                } else {
                    System.out.println("Failed to update medicine stock.");
                }
            } catch (IOException e) {
                System.out.println("Error saving updates to MedicineDB: " + e.getMessage());
            }
        } else {
            System.out.println("Medicine not found.");
        }
    }

    // Remove a medicine from the database
    public void removeMedicine(String id) {
        try {
            if (medicineDB.delete(id)) {
                medicineDB.save();
                System.out.println("Removed medicine with ID: " + id);
            } else {
                System.out.println("Failed to remove medicine. Medicine not found.");
            }
        } catch (IOException e) {
            System.out.println("Error saving removal to MedicineDB: " + e.getMessage());
        }
    }

    // Check if a specific medicine's stock is low and create a replenishment request
    public boolean replenishStock(String id, int quantity) {
    Medicine medicine = medicineDB.getById(id);
    if (medicine != null) {
        if (medicine.isStockLow()) {
            ReplenishmentRequest requestManager = new ReplenishmentRequest(replenishmentDB);
            if (requestManager.submitRequest(id, quantity, medicine)) {
                return true;
            }
        }
    }
    return false;
}

    // Display current inventory from the database
    public void displayInventory() {
        List<Medicine> medicineList = medicineDB.getAll();
        if (medicineList.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        for (Medicine medicine : medicineList) {
            System.out.println(medicine);
        }
    }

    // Display medicines that are low on stock
    public void displayLowStockMedicines() {
        List<Medicine> medicineList = medicineDB.getAll();
        boolean lowStockFound = false;
        for (Medicine medicine : medicineList) {
            if (medicine.isStockLow()) {
                System.out.println("Low stock alert for: " + medicine.getName()
                        + " (Current Stock: " + medicine.getStockLevel()
                        + ", Alert Level: " + medicine.getLowStockLevelAlert() + ")");
                lowStockFound = true;
            }
        }
        if (!lowStockFound) {
            System.out.println("No medicines are currently below their low stock levels.");
        }
    }

    // Display all replenishment requests from the database
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

    // Save changes to replenishment requests
    public void saveReplenishmentRequests() {
        try {
            replenishmentDB.save();
            System.out.println("Replenishment requests saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving replenishment requests: " + e.getMessage());
        }
    }

    public Medicine getMedicineById(String id) {
        Medicine medicine = medicineDB.getById(id);
        if (medicine != null) {
            return medicine;
        } else {
            System.out.println("Medicine with ID " + id + " not found.");
            return null;
        }
    }
    public void increaseStock(String id, int amount) {
    Medicine medicine = medicineDB.getById(id);
    if (medicine != null) {
        int newStockLevel = medicine.getStockLevel() + amount;
        medicine.setStockLevel(newStockLevel);

        try {
            if (medicineDB.update(medicine)) {
                medicineDB.save();
                System.out.println("Stock for " + medicine.getName() + " increased by " + amount + " units.");
            } else {
                System.out.println("Failed to update stock for " + medicine.getName() + ".");
            }
        } catch (IOException e) {
            System.out.println("Error updating stock for " + medicine.getName() + ".");
        }
    } else {
        System.out.println("Medicine with ID " + id + " not found.");
    }
}
    public boolean removeReplenishmentRequest(String medicineId) {
    try {
        // Remove the replenishment request from the database
        if (replenishmentDB.delete(medicineId)) {
            // Save the updated state to persist the changes
            replenishmentDB.save();
            System.out.println("Replenishment request for medicine ID " + medicineId + " removed.");
            return true;
        } else {
            System.out.println("No replenishment request found for medicine ID " + medicineId);
        }
    } catch (IOException e) {
        System.out.println("Error removing replenishment request: " + e.getMessage());
    }
    return false; // Return false if the process failed
}
}
