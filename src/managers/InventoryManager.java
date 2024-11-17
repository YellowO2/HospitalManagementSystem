package managers;

import database.MedicineDB;
import database.ReplenishmentDB;
import java.io.IOException;
import java.util.List;
import medicine.Medicine;
import medicine.ReplenishmentRequest;

/**
 * The InventoryManager class provides methods for managing the inventory of
 * medicines,
 * including adding, updating, removing, and displaying medicines. It also
 * handles
 * replenishment requests and related operations, interacting with the
 * underlying databases
 * for medicines and replenishment requests.
 */
public class InventoryManager {

    private MedicineDB medicineDB; // Reference to MedicineDB
    private ReplenishmentDB replenishmentDB; // Reference to ReplenishmentDB

    /**
     * Constructs an InventoryManager object with the given database references.
     *
     * @param medicineDB      the database handling medicine data
     * @param replenishmentDB the database handling replenishment requests
     */
    public InventoryManager(MedicineDB medicineDB, ReplenishmentDB replenishmentDB) {
        this.medicineDB = medicineDB;
        this.replenishmentDB = replenishmentDB;
    }

    /**
     * Adds a new medicine directly to the database.
     *
     * @param medicine the Medicine object to add
     */
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

    /**
     * Updates the stock level of a medicine in the database.
     *
     * @param id            the ID of the medicine to update
     * @param newStockLevel the new stock level to set
     */
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

    /**
     * Removes a medicine from the database.
     *
     * @param id the ID of the medicine to remove
     */
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

    /**
     * Checks if a specific medicine's stock is low and creates a replenishment
     * request if needed.
     *
     * @param id       the ID of the medicine to check
     * @param quantity the quantity to request for replenishment
     * @return true if a replenishment request was successfully submitted, false
     *         otherwise
     */
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

    /**
     * Displays the current inventory from the database.
     */
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

    /**
     * Displays medicines that are low on stock.
     */
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

    /**
     * Displays all replenishment requests from the database.
     */
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

    /**
     * Gets a list of all replenishment requests.
     *
     * @return a list of all replenishment requests
     */
    public List<ReplenishmentRequest> getReplenishmentRequests() {
        return replenishmentDB.getAll();
    }

    /**
     * Saves changes to replenishment requests to the database.
     */
    public void saveReplenishmentRequests() {
        try {
            replenishmentDB.save();
        } catch (IOException e) {
            System.out.println("Error saving replenishment requests: " + e.getMessage());
        }
    }

    /**
     * Retrieves a medicine by its ID.
     *
     * @param id the ID of the medicine to retrieve
     * @return the Medicine object if found, or null if not found
     */
    public Medicine getMedicineById(String id) {
        Medicine medicine = medicineDB.getById(id);
        if (medicine != null) {
            return medicine;
        } else {
            System.out.println("Medicine with ID " + id + " not found.");
            return null;
        }
    }

    /**
     * Increases the stock level of a medicine.
     *
     * @param id     the ID of the medicine to increase stock for
     * @param amount the amount to increase the stock by
     */
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

    /**
     * Removes a replenishment request by its associated medicine ID.
     *
     * @param medicineId the ID of the medicine associated with the replenishment
     *                   request
     * @return true if the request was successfully removed, false otherwise
     */
    public boolean removeReplenishmentRequest(String medicineId) {
        try {
            if (replenishmentDB.delete(medicineId)) {
                replenishmentDB.save();
                return true;
            } else {
                System.out.println("No replenishment request found for medicine ID " + medicineId);
            }
        } catch (IOException e) {
            System.out.println("Error removing replenishment request: " + e.getMessage());
        }
        return false;
    }
}
