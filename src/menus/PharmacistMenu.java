package menus;

import java.util.Scanner;
import users.Pharmacist;

public class PharmacistMenu {
    private Pharmacist pharmacist;
    private Scanner scanner;

    public PharmacistMenu(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== Pharmacist Menu ===");
            System.out.println("1. View Appointment Outcome Records");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println(); // Add a line break for spacingggg

            switch (choice) {
                case 1:
                    viewAppointmentOutcomeRecords();
                    break;
                case 2:
                    InputPrescriptionStatus();
                    break;
                case 3:
                    viewMedicationInventory();
                    break;
                case 4:
                    submitReplenishmentRequest();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println(); // Add a line break after the action is completed

        } while (choice != 5);

        scanner.close();
    }

    private void viewAppointmentOutcomeRecords() {
        pharmacist.viewAppointmentOutcomeRecords();
    }

    private void InputPrescriptionStatus() {
        // Display all available AORs before updating
        pharmacist.viewAppointmentOutcomeRecords();

        // Prompt the user for input
        System.out.print("Enter the Appointment ID: ");
        String appointmentId = scanner.nextLine();
        System.out.print("Enter the new status (0 for Pending, 1 for Fulfilled): ");
        int statusInput = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Convert the status input to a string ("Pending" or "Fulfilled")
        String newStatus = (statusInput == 1) ? "Fulfilled" : "Pending";

        // Call the method in the Pharmacist class to update the status
        pharmacist.updatePrescriptionStatus(appointmentId, newStatus);
    }

    private void viewMedicationInventory() {
        pharmacist.viewMedicationInventory();
    }

    private void submitReplenishmentRequest() {
        System.out.print("Enter the medication ID: ");
        String medicationId = scanner.nextLine();
        System.out.print("Enter the quantity to replenish: ");
        int quantity = scanner.nextInt();
        pharmacist.submitReplenishmentRequest(medicationId, quantity);
    }
}
