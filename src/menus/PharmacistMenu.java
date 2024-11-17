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

/**
 * The PharmacistMenu class provides an interface for a pharmacist to manage
 * various tasks such as viewing appointment outcomes, updating prescription
 * statuses, managing medication inventory, and submitting replenishment requests.
 */
public class PharmacistMenu {

    private Pharmacist pharmacist;
    private AppointmentOutcomeManager appointmentOutcomeManager;
    private Inventory inventory;
    private Scanner scanner;

    /**
     * Constructs a PharmacistMenu with the specified pharmacist, appointment outcome manager, and inventory.
     *
     * @param pharmacist the pharmacist using the menu
     * @param appointmentOutcomeManager the manager for appointment outcomes
     * @param inventory the inventory of medicines
     */
    public PharmacistMenu(Pharmacist pharmacist, AppointmentOutcomeManager appointmentOutcomeManager, Inventory inventory) {
        this.pharmacist = pharmacist;
        this.appointmentOutcomeManager = appointmentOutcomeManager;
        this.inventory = inventory;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the pharmacist menu and handles user input for different actions.
     */
    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== Pharmacist Menu ===");
            System.out.println("1. View Appointment Outcome Records");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. View Replenishment Requests");
            System.out.println("6. Change Password");
            System.out.println("7. Logout");
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
                    changePassword();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    /**
     * Displays all appointment outcome records managed by the appointment outcome manager.
     */
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

    /**
     * Allows the pharmacist to update the prescription status for a specific appointment.
     */
    private void inputPrescriptionStatus() {
        System.out.print("Enter the Appointment ID: ");
        String appointmentId = scanner.nextLine();
        System.out.print("Enter the new status (0 for Pending, 1 for Dispensed): ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        int statusInput = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String newStatus = (statusInput == 1) ? "Dispensed" : "Pending";

        boolean updated = appointmentOutcomeManager.updatePrescriptionStatus(appointmentId, newStatus);

        if (updated) {
            try {
                appointmentOutcomeManager.getAppointmentOutcomeRecordDB().save();
                System.out.println("Prescription status updated and saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving updated status: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to update prescription status. Check the Appointment ID or status.");
        }
    }

    /**
     * Submits a replenishment request for a specific medication if its stock is low.
     */
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
            if (medicine.isStockLow()) {
                if (inventory.replenishStock(medicationId, quantity)) {
                    System.out.println("Replenishment request submitted successfully for " + medicine.getName() + ".");
                } else {
                    System.out.println("Failed to submit replenishment request for " + medicine.getName() + ".");
                }
            } else {
                System.out.println("Stock level for " + medicine.getName() + " is sufficient. No need for a replenishment request.");
            }
        } else {
            System.out.println("Medicine with ID " + medicationId + " not found.");
        }
    }

    /**
     * Displays all replenishment requests from the inventory.
     */
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

    /**
     * Changes the pharmacist's password.
     */
    private void changePassword() {
        System.out.println("Changing password...");
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();
        boolean success = pharmacist.changePassword(newPassword);
        if (success) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Error: Failed to change password.");
        }
    }
}

