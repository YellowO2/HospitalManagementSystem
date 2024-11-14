/*
* The AdministratorMenu class is responsible for displaying options for the
administrator
* and handling user input. This class calls methods from the Administrator
class to perform actions.
 */
package menus;

import java.util.Scanner;
import users.Administrator;

public class AdministratorMenu {

    private Administrator administrator; // The currently logged-in administrator
    private Scanner scanner;

// Constructor
    public AdministratorMenu(Administrator administrator) {
        this.administrator = administrator;
        this.scanner = new Scanner(System.in);
    }

// Method to display the menu and handle user input
    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println(); // Add a line break for spacing

            switch (choice) {
                case 1:
                    viewAndManageStaff();
                    break;
                case 2:
                    viewAppointmentsDetails();
                    break;
                case 3:
                    manageMedicationInventory();
                    break;
                case 4:
                    approveReplenishmentRequests();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println(); // Add a line break after the action is completed

        } while (choice != 5);
    }

// Placeholder methods for each menu option
    private void viewAndManageStaff() {
        System.out.println("Viewing and managing hospital staff...");
// Implement logic to view and manage staff
        administrator.viewStaffList(); // Example method call
    }

    private void viewAppointmentsDetails() {
        System.out.println("Viewing appointment details...");
// Implement logic to view appointment details
        administrator.viewAppointments(); // Example method call
    }

    private void manageMedicationInventory() {
        System.out.println("Viewing and managing medication inventory...");
// Implement logic to view and manage inventory
        administrator.manageInventory(); // Example method call
    }

    private void approveReplenishmentRequests() {
        System.out.println("Approving replenishment requests...");
// Implement logic to approve replenishment requests
        administrator.approveReplenishmentRequest(); // Example method call
    }
}
