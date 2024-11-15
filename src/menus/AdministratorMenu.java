/*
* The AdministratorMenu class is responsible for displaying options for the
administrator
* and handling user input. This class calls methods from the Administrator
class to perform actions.
 */
package menus;

import inventory.Medicine;
import java.io.IOException;
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

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.println(); // Add a line break for spacinggggg

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
        scanner.close();
    }

    // Methods for each menu option
    private void viewAndManageStaff() {
        int choice;
        do {
            System.out.println("\n=== Manage Hospital Staff ===");
            administrator.displayAllStaff();
            System.out.println("                       ");
            System.out.println("1. Add New Staff Member");
            System.out.println("2. Remove Staff Member");
            System.out.println("3. Go Back");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewStaffMember();
                    break;
                case 2:
                    removeStaffMember();
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

// Method to add a new staff member
    private void addNewStaffMember() {
    System.out.println("\nEnter details for new staff member:");
    System.out.print("ID: ");
    String id = scanner.nextLine().trim();
    System.out.print("Name: ");
    String name = scanner.nextLine().trim();
    System.out.print("Date of Birth (YYYY-MM-DD): ");
    String dob = scanner.nextLine().trim();
    System.out.print("Gender: ");
    String gender = scanner.nextLine().trim();
    System.out.print("Phone Number: ");
    String phoneNumber = scanner.nextLine().trim();
    System.out.print("Email Address: ");
    String emailAddress = scanner.nextLine().trim();
    System.out.print("Password: ");
    String password = scanner.nextLine().trim();
    System.out.print("Role (Doctor/Pharmacist/Administrator): ");
    String role = scanner.nextLine().trim();

    // Validate the role
    if (!role.equalsIgnoreCase("Doctor") && !role.equalsIgnoreCase("Pharmacist") && !role.equalsIgnoreCase("Administrator")) {
        System.out.println("Invalid role. Staff member not added.");
        return; // Exit the method if the role is invalid
    }

    // Check if the ID already exists in the database
    if (!administrator.getUserDB().exists(id)) {
        try {
            // Create a CSV line directly and add it to the database
            String csvLine = String.join(",", id, name, dob, gender, phoneNumber, emailAddress, password, role);
            administrator.getUserDB().addCsvEntry(csvLine); // You need to implement this method in UserDB
            administrator.getUserDB().save(); // Save changes to persist the new entry
            System.out.println("New staff member added and saved to CSV: " + name);
        } catch (IOException e) {
            System.out.println("Error saving new staff member to CSV: " + e.getMessage());
        }
    } else {
        System.out.println("A staff member with ID " + id + " already exists. No new entry created.");
    }
}

// Method to remove a staff member by ID
    private void removeStaffMember() {
        System.out.print("\nEnter the ID of the staff member to remove: ");
        String id = scanner.nextLine().trim();
        administrator.removeStaff(id);
    }

    private void viewAppointmentsDetails() {
        System.out.println("Viewing appointment details...");
        // Implement logic to view appointment details
    }

    private void manageMedicationInventory(){
        int choice;
        do {
            System.out.println("\n=== Manage Medication Inventory ===");
            System.out.println("1. View Inventory");
            System.out.println("2. Add New Medication");
            System.out.println("3. Remove Medication");
            System.out.println("4. Go Back");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    administrator.viewInventory();
                    break;
                case 2:
                    addNewMedication();
                    break;
                case 3:
                    removeMedication();
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private void addNewMedication() {
        System.out.println("\nEnter details for new medication:");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Dosage: ");
        String dosage = scanner.nextLine().trim();
        //System.out.print("Stock Level: ");
        int stockLevel = getIntInput("Stock Level:");
        //System.out.print("Low Stock Alert Level: ");
        int lowStockAlertLevel = getIntInput("Low Stock Alert Level:");

        Medicine newMedicine = new Medicine(id, name, dosage, stockLevel, lowStockAlertLevel);
        administrator.addNewMedication(newMedicine);
    }

    private void removeMedication() {
        System.out.print("\nEnter the ID of the medication to remove: ");
        String id = scanner.nextLine().trim();
        administrator.removeMedication(id);
    }

    private void approveReplenishmentRequests() {
        System.out.println("Approving replenishment requests...");
        administrator.approveRequest();
    }

    // Helper method to get validated integer input
    private int getIntInput(String prompt) {
        int input;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Consume invalid input
            }
        }
        return input;
    }
}
