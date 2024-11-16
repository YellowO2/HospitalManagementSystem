package inventory;

import database.ReplenishmentDB;
import java.io.IOException;
import java.util.List;

public class ReplenishmentRequest {
    private String medicineId;
    private int quantity;
    private ReplenishmentDB replenishmentDB; // Instance variable for dependency injection

    // Constructor to create a ReplenishmentRequest with a given database
    public ReplenishmentRequest(ReplenishmentDB replenishmentDB) {
        this.replenishmentDB = replenishmentDB;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    @Override
    public String toString() {
        return medicineId + "," + quantity;
    }

    // Method to load the database
    public void loadDB() throws IOException {
        replenishmentDB.load();
    }

    // Method to submit a replenishment request
    public boolean submitRequest(String medicationId, int quantity, Medicine medicine) {
        try {
            if (medicine != null && medicine.isStockLow()) {
                ReplenishmentRequest existingRequest = replenishmentDB.getById(medicationId);
                if (existingRequest != null) {
                    // Update the existing request's quantity
                    existingRequest.setQuantity(existingRequest.getQuantity() + quantity);
                    if (replenishmentDB.update(existingRequest)) {
                        replenishmentDB.save();
                        return true;
                    }
                } else {
                    // Create a new replenishment request
                    ReplenishmentRequest newRequest = new ReplenishmentRequest(replenishmentDB);
                    newRequest.medicineId = medicationId;
                    newRequest.quantity = quantity;
                    if (replenishmentDB.create(newRequest)) {
                        replenishmentDB.save();
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling replenishment request: " + e.getMessage());
        }
        return false;
    }

    // Method to display all replenishment requests
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
