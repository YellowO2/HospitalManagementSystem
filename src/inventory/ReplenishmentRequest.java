package inventory;

import database.ReplenishmentDB;
import java.io.IOException;
import java.util.List;

/**
 * The ReplenishmentRequest class handles the creation, updating, and display of
 * replenishment requests for medicines. It interacts with the ReplenishmentDB
 * to manage these operations.
 */
public class ReplenishmentRequest {

    private String medicineId;
    private int quantity;
    private ReplenishmentDB replenishmentDB; // Instance variable for dependency injection

    /**
     * Constructs a ReplenishmentRequest with a given database reference.
     *
     * @param replenishmentDB the database handling replenishment request data
     */
    public ReplenishmentRequest(ReplenishmentDB replenishmentDB) {
        this.replenishmentDB = replenishmentDB;
    }

    /**
     * Gets the ID of the medicine associated with the request.
     *
     * @return the ID of the medicine
     */
    public String getMedicineId() {
        return medicineId;
    }

    /**
     * Gets the quantity requested for replenishment.
     *
     * @return the quantity requested
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity requested for replenishment.
     *
     * @param quantity the new quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the ID of the medicine associated with the request.
     *
     * @param medicineId the ID of the medicine to set
     */
    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    /**
     * Returns a string representation of the replenishment request.
     *
     * @return a string containing the medicine ID and quantity
     */
    @Override
    public String toString() {
        return medicineId + "," + quantity;
    }

    /**
     * Loads the database for replenishment requests.
     *
     * @throws IOException if an error occurs while loading the database
     */
    public void loadDB() throws IOException {
        replenishmentDB.load();
    }

    /**
     * Submits a replenishment request if the stock is low. If a request for the
     * medicine already exists, the quantity is updated; otherwise, a new request is created.
     *
     * @param medicationId the ID of the medicine to request replenishment for
     * @param quantity     the quantity to request
     * @param medicine     the Medicine object to check for stock status
     * @return true if the request was successfully submitted, false otherwise
     */
    public boolean submitRequest(String medicationId, int quantity, Medicine medicine) {
        try {
            if (medicine != null) {
                System.out.println("Stock check for medicine ID " + medicationId + ": " + medicine.isStockLow());
                if (medicine.isStockLow()) {
                    ReplenishmentRequest existingRequest = replenishmentDB.getById(medicationId);
                    System.out.println("Existing request found: " + existingRequest);

                    if (existingRequest != null) {
                        existingRequest.setQuantity(existingRequest.getQuantity() + quantity);
                        if (replenishmentDB.update(existingRequest)) {
                            replenishmentDB.save();
                            System.out.println("Updated existing request and saved.");
                            return true;
                        } else {
                            System.out.println("Failed to update existing request.");
                        }
                    } else {
                        ReplenishmentRequest newRequest = new ReplenishmentRequest(replenishmentDB);
                        newRequest.medicineId = medicationId;
                        newRequest.quantity = quantity;
                        if (replenishmentDB.create(newRequest)) {
                            replenishmentDB.save();
                            System.out.println("Created new request and saved.");
                            return true;
                        } else {
                            System.out.println("Failed to create new request.");
                        }
                    }
                } else {
                    System.out.println("Stock level is not low; no request submitted.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling replenishment request: " + e.getMessage());
        }
        return false;
    }

    /**
     * Displays all replenishment requests stored in the database.
     */
    public void displayAllRequests() {
        List<ReplenishmentRequest> requests = replenishmentDB.getAll();
        if (requests.isEmpty()) {
            System.out.println("No replenishment requests have been submitted.");
        } else {
            for (ReplenishmentRequest request : requests) {
                System.out.println(request);
            }
        }
    }
}
