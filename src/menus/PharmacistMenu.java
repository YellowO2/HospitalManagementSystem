package menus;

import appointments.AppointmentOutcomeManager;
import appointments.AppointmentOutcomeRecord;
import inventory.Inventory;
import inventory.Medicine;
import inventory.ReplenishmentRequest;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import users.Pharmacist;

public class PharmacistMenu {
    private Pharmacist pharmacist;
    private AppointmentOutcomeManager appointmentOutcomeManager;
    private Inventory inventory;
    private Scanner scanner;

    public PharmacistMenu(Pharmacist pharmacist, AppointmentOutcomeManager appointmentOutcomeManager, Inventory inventory) {
        this.pharmacist = pharmacist;
        this.appointmentOutcomeManager = appointmentOutcomeManager;
        this.inventory = inventory;
        this.scanner = new Scanner(System.in);

        // Load data during initialization
        try {
            inventory.loadFromMedicineDB(); // Inventory handles ReplenishmentDB internally
        } catch (IOException e) {
            System.err.println("Error during database initialization: " + e.getMessage());
        }
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== Pharmacist Menu ===");
            System.out.println("1. View Appointment Outcome Records");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. View Replenishment Requests");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAppointmentOutcomeRecords();
                    break;
                case 2:
                    inputPrescriptionStatus();
                    break;
                case 3:
                    inventory.displayInventory();
                    break;
                case 4:
                    submitReplenishmentRequest();
                    break;
                case 5:
                    displayReplenishmentRequests();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private void viewAppointmentOutcomeRecords() {
        List<AppointmentOutcomeRecord> records = appointmentOutcomeManager.getAllOutcomeRecords();
        if (records.isEmpty()) {
            System.out.println("No appointment outcome records available.");
        } else {
            for (AppointmentOutcomeRecord record : records) {
                System.out.println(record);
            }
        }
    }

    private void inputPrescriptionStatus() {
    System.out.print("Enter the Appointment ID: ");
    String appointmentId = scanner.nextLine();
    System.out.print("Enter the new status (0 for Pending, 1 for Fulfilled): ");
    while (!scanner.hasNextInt()) {
        System.out.println("Invalid input. Please enter a number.");
        scanner.next();
    }
    int statusInput = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    String newStatus = (statusInput == 1) ? "Fulfilled" : "Pending";

    boolean updated = appointmentOutcomeManager.updatePrescriptionStatus(appointmentId, newStatus);

    if (updated) {
        try {
            // Save the updated data to persist changes
            appointmentOutcomeManager.getAppointmentOutcomeRecordDB().save();
            System.out.println("Prescription status updated and saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving updated status: " + e.getMessage());
        }
    } else {
        // This 'else' is connected to the 'if' outside the 'try-catch' block
        System.out.println("Failed to update prescription status. Check the Appointment ID or status.");
    }
}
    
    private void submitReplenishmentRequest() {
        System.out.print("Enter the medication ID: ");
        String medicationId = scanner.nextLine();
        System.out.print("Enter the quantity to replenish: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Medicine medicine = inventory.getMedicineById(medicationId);

        if (medicine != null) {
            boolean requestSubmitted = inventory.replenishStock(medicationId, quantity);
            if (requestSubmitted) {
                System.out.println("Replenishment request submitted successfully.");
            } else {
                System.out.println("Failed to submit replenishment request. Stock may not be low, or there was an error.");
            }
        } else {
            System.out.println("Medicine with ID " + medicationId + " not found in inventory.");
        }
    }

    private void displayReplenishmentRequests() {
        List<ReplenishmentRequest> requests = inventory.getReplenishmentRequests();
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

