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

            System.out.println(); // Add a line break for spacing

            switch (choice) {
                case 1:
                    viewAppointmentOutcomeRecords();
                    break;
                case 2:
                    updatePrescriptionStatus();
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

    private void updatePrescriptionStatus() {
        System.out.print("Enter the medication name: ");
        String medicationName = scanner.nextLine();
        System.out.print("Enter the new status (0 for Pending, 1 for Fulfilled): ");
        int newStatus = scanner.nextInt();
        pharmacist.updatePrescriptionStatus(medicationName, newStatus);
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

