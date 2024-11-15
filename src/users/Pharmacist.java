package users;

import appointments.AppointmentOutcomeRecord;
import database.ReplenishmentDB;
import inventory.Inventory;
import inventory.Medicine;
import inventory.ReplenishmentRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import medicalrecords.Prescription;

public class Pharmacist extends User {
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords;
    private Inventory inventory;
    private ReplenishmentDB replenishmentDB; // Ensure this is initialized

    public Pharmacist(String id, String name, String dateOfBirth, String gender, String phoneNumber,
            String emailAddress, String password) {
        super(id, name, "Pharmacist", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.appointmentOutcomeRecords = new ArrayList<>();
        this.inventory = new Inventory();
        this.replenishmentDB = new ReplenishmentDB(); // Properly initialize ReplenishmentDB

        try {
            replenishmentDB.load();
            System.out.println("Replenishment requests loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading replenishment requests: " + e.getMessage());
        }
    }

    // Method to view all appointment outcome records
    public void viewAppointmentOutcomeRecords() {
        if (appointmentOutcomeRecords.isEmpty()) {
            System.out.println("No appointment outcome records available.");
        } else {
            for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
                System.out.println(record);
            }
        }
    }

    // Method to update prescription status in appointment outcome records
    public void updatePrescriptionStatus(String medicationName, int newStatus) {
        for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
            for (Prescription medication : record.getPrescriptions()) {
                if (medication.getMedicationName().equals(medicationName)) {
                    medication.setStatus(newStatus);
                    System.out.println("Updated status for " + medicationName + " to " + newStatus);
                    return;
                }
            }
        }
        System.out.println("Medication not found in records.");
    }

    // Method to view medication inventory levels
    public void viewMedicationInventory() {
        inventory.displayInventory();
    }

    // Method to submit a replenishment request if stock is low
    public void submitReplenishmentRequest(String medicationId, int quantity) {
        Medicine medicine = inventory.getMedicineById(medicationId);
        if (medicine != null && medicine.isStockLow()) {
            ReplenishmentRequest request = new ReplenishmentRequest(medicationId, quantity);
            if (replenishmentDB.create(request)) { // Ensure the request is created successfully
                try {
                    replenishmentDB.save(); // Save changes to persist the new request
                    System.out.println(
                            "Replenishment request submitted for " + quantity + " units of " + medicine.getName());
                } catch (IOException e) {
                    System.out.println("Error saving replenishment request: " + e.getMessage());
                }
            } else {
                System.out.println("Failed to create replenishment request.");
            }
        } else {
            System.out.println("Stock levels for " + (medicine != null ? medicine.getName() : "specified medicine")
                    + " are sufficient.");
        }
    }

    // Method to display all replenishment requests from ReplenishmentDB
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
}