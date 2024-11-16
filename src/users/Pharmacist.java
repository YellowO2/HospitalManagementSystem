package users;

import appointments.AppointmentOutcomeRecord;
import database.AppointmentOutcomeRecordDB;
import database.ReplenishmentDB;
import inventory.Inventory;
import inventory.Medicine;
import inventory.ReplenishmentRequest;
import java.io.IOException;
import java.util.List;

public class Pharmacist extends User {
    private AppointmentOutcomeRecordDB appointmentOutcomeRecordDB;
    // private List<AppointmentOutcomeRecord> appointmentOutcomeRecords;
    private Inventory inventory;
    private ReplenishmentDB replenishmentDB;

    public Pharmacist(String id, String name, String dateOfBirth, String gender, String phoneNumber,
            String emailAddress, String password) {
        super(id, name, "Pharmacist", password, phoneNumber, emailAddress, dateOfBirth, gender);
        // TODO: This wont work because it doesnt link to other users like doctors. This
        // is just a DB you created here. Ideally you would need to follow what we did,
        // that is accessing through a manager via the Users's MENU, not the user class

        // this.appointmentOutcomeRecordDB = new AppointmentOutcomeRecordDB();
        this.inventory = new Inventory();
        this.replenishmentDB = new ReplenishmentDB();

        // Load data during initialization without printing unnecessary messages
        try {
            // this.appointmentOutcomeRecordDB.load();
            this.replenishmentDB.load();
            this.inventory.loadFromMedicineDB();
            // this.appointmentOutcomeRecords = appointmentOutcomeRecordDB.getAll();
        } catch (IOException e) {
            // Print an error message only if an exception occurs
            System.err.println("Error during Pharmacist initialization: " + e.getMessage());
        }
    }

    // Method to view all appointment outcome records
    public void viewAppointmentOutcomeRecords() {
        // if (appointmentOutcomeRecords.isEmpty()) {
        // System.out.println("No appointment outcome records available.");
        // } else {
        // for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
        // System.out.println(record); // Display record in an appropriate format
        // }
        // }
    }

    // Method to update prescription status in appointment outcome records
    public void updatePrescriptionStatus(String appointmentId, String newStatus) {
        // try {
        // appointmentOutcomeRecords = appointmentOutcomeRecordDB.getAll();
        // AppointmentOutcomeRecord recordToUpdate = null;

        // for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
        // if (record.getAppointmentId().equals(appointmentId)) {
        // recordToUpdate = record;
        // break;
        // }
        // }

        // if (recordToUpdate != null) {
        // if ("Pending".equalsIgnoreCase(recordToUpdate.getPrescribedStatus())
        // && "Fulfilled".equalsIgnoreCase(newStatus)) {
        // recordToUpdate.setPrescribedStatus("Fulfilled");
        // System.out.println(
        // "Updated status for prescriptions in Appointment ID " + appointmentId + " to
        // 'Fulfilled'.");
        // appointmentOutcomeRecordDB.save();
        // } else {
        // System.out.println(
        // "The current prescription status is already '" + newStatus + "'. No changes
        // made.");
        // }
        // } else {
        // System.out.println("Appointment ID not found.");
        // }
        // } catch (IOException e) {
        // System.out.println("Error updating prescription status: " + e.getMessage());
        // }
    }

    // Method to view medication inventory levels
    public void viewMedicationInventory() {
        inventory.displayInventory();
    }

    // Method to submit a replenishment request if stock is low
    public void submitReplenishmentRequest(String medicationId, int quantity) {
        try {
            Medicine medicine = inventory.getMedicineById(medicationId);
            if (medicine != null && medicine.isStockLow()) {
                ReplenishmentRequest existingRequest = replenishmentDB.getById(medicationId);

                if (existingRequest != null) {
                    // Update the quantity of the existing request
                    existingRequest.setQuantity(existingRequest.getQuantity() + quantity);
                    if (replenishmentDB.update(existingRequest)) {
                        replenishmentDB.save();
                        System.out.println("Replenishment request updated for " + quantity + " additional units of "
                                + medicine.getName());
                    } else {
                        System.out.println("Failed to update the replenishment request.");
                    }
                } else {
                    // Create a new request if one does not already exist
                    ReplenishmentRequest newRequest = new ReplenishmentRequest(medicationId, quantity);
                    if (replenishmentDB.create(newRequest)) {
                        replenishmentDB.save();
                        System.out.println(
                                "Replenishment request submitted for " + quantity + " units of " + medicine.getName());
                    } else {
                        System.out.println("Failed to create replenishment request.");
                    }
                }
            } else {
                System.out.println("Stock levels for " + (medicine != null ? medicine.getName() : "specified medicine")
                        + " are sufficient.");
            }
        } catch (IOException e) {
            System.out.println("Error handling replenishment request: " + e.getMessage());
        }
    }

    // Method to display all replenishment requests from ReplenishmentDB
    public void displayReplenishmentRequests() {
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