/*
 * The AdministratorMenu class is responsible for displaying options for the
 * administrator and handling user input. This class calls methods from the 
 * Administrator class and other related classes to perform various actions.
 */
package menus;

import database.UserDB;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import managers.AppointmentManager;
import managers.InventoryManager;
import medicine.Medicine;
import medicine.ReplenishmentRequest;
import users.Administrator;
import users.Doctor;
import users.Pharmacist;
import users.User;

/**
 * The AdministratorMenu class provides the interface and functionality for an
 * administrator to manage hospital staff, inventory, appointments, and more.
 */
public class AdministratorMenu {

    private Administrator administrator;
    private UserDB userDB;
    private InventoryManager inventory;
    private Scanner scanner;
    private AppointmentManager appointmentManager;

    /**
     * Constructs an AdministratorMenu with the specified administrator, user
     * database, inventory, and appointment manager.
     *
     * @param administrator      the administrator using the menu
     * @param userDB             the database of users
     * @param inventory          the inventory of medicines
     * @param appointmentManager the manager handling appointments
     */
    public AdministratorMenu(Administrator administrator, UserDB userDB, InventoryManager inventory,
                             AppointmentManager appointmentManager) {
        this.administrator = administrator;
        this.userDB = userDB;
        this.inventory = inventory;
        this.appointmentManager = appointmentManager;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the administrator menu and handles user input for different actions.
     */
    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. View, Add, or Remove Hospital Staff");
            System.out.println("2. View Appointment Details");
            System.out.println("3. View, Add, or Remove Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Change Password");
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
                    manageStaff();
                    break;
                case 2:
                    viewAppointmentsDetails();
                    break;
                case 3:
                    manageInventory();
                    break;
                case 4:
                    approveReplenishmentRequests();
                    break;
                case 5:
                    changePassword();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    /**
     * Manages hospital staff by displaying them and allowing the addition or
     * removal of staff.
     */
    private void manageStaff() {
        System.out.println("\n=== Hospital Staff ===");
        userDB.getAll().forEach(user -> {
            if (user.getRole().equalsIgnoreCase("Doctor")
                    || user.getRole().equalsIgnoreCase("Pharmacist")
                    || user.getRole().equalsIgnoreCase("Administrator")) {
                System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Role: " + user.getRole());
            }
        });

        System.out.println("\nSelect an action:");
        System.out.println("1. Add New Staff Member");
        System.out.println("2. Remove Staff Member");
        System.out.println("3. Go Back");
        System.out.print("Enter your choice: ");

        int action = getIntInput("");

        switch (action) {
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
                System.out.println("Invalid choice. Returning to main menu...");
        }
    }

    /**
     * Adds a new staff member to the user database.
     */
    private void addNewStaffMember() {
        System.out.println("\nEnter details for new staff member:");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Role (Doctor/Pharmacist/Administrator): ");
        String role = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine().trim();
        System.out.print("Email Address: ");
        String email = scanner.nextLine().trim();
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine().trim();
        System.out.print("Gender: ");
        String gender = scanner.nextLine().trim();

        // Create instances based on the role input
        if (role.equalsIgnoreCase("Doctor")) {
            Doctor newDoctor = new Doctor(id, name, dob, gender, phoneNumber, email, password);
            userDB.create(newDoctor);
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            Pharmacist newPharmacist = new Pharmacist(id, name, dob, gender, phoneNumber, email, password);
            userDB.create(newPharmacist);
        } else if (role.equalsIgnoreCase("Administrator")) {
            Administrator newAdministrator = new Administrator(id, name, dob, gender, phoneNumber, email, password);
            userDB.create(newAdministrator);
        } else {
            System.out.println("Invalid role. Please use Doctor, Pharmacist, or Administrator.");
            return; // Exit the method early if the role is invalid
        }

        // Save the new user to the database
        try {
            userDB.save();
            System.out.println("New staff member added successfully.");
        } catch (IOException e) {
            System.out.println("Error saving new staff member: " + e.getMessage());
        }
    }

    /**
     * Removes a staff member from the user database by their ID.
     */
    private void removeStaffMember() {
        System.out.print("\nEnter the ID of the staff member to remove: ");
        String id = scanner.nextLine().trim();
        User user = userDB.getById(id);
        if (user != null && (user.getRole().equalsIgnoreCase("Doctor")
                || user.getRole().equalsIgnoreCase("Pharmacist")
                || user.getRole().equalsIgnoreCase("Administrator"))) {
            userDB.delete(id);
            try {
                userDB.save();
                System.out.println("Staff member removed successfully.");
            } catch (IOException e) {
                System.out.println("Error saving changes after removal: " + e.getMessage());
            }
        } else {
            System.out.println("Staff member not found or cannot be removed.");
        }
    }

    /**
     * Displays all appointment details from the appointment manager.
     */
    public void viewAppointmentsDetails() {
        System.out.println("=== Viewing All Appointments ===");
        List<String> allAppointments = appointmentManager.viewAllAppointments();
        if (allAppointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (String appointment : allAppointments) {
                System.out.println(appointment);
            }
        }
    }

    /**
     * Manages the medication inventory by displaying it and allowing the addition
     * or removal of medications.
     */
    private void manageInventory() {
        System.out.println("=== Inventory ===");
        inventory.displayInventory();
        System.out.println("\nSelect an action:");
        System.out.println("1. Add New Medication");
        System.out.println("2. Remove Medication");
        System.out.println("3. Go Back");
        System.out.print("Enter your choice: ");

        int action = getIntInput("");

        switch (action) {
            case 1:
                addNewMedication();
                break;
            case 2:
                removeMedication();
                break;
            case 3:
                System.out.println("Returning to main menu...");
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu...");
        }
    }

    /**
     * Adds new medication to the inventory.
     */
    private void addNewMedication() {
        System.out.println("\nEnter details for new medication:");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Dosage: ");
        String dosage = scanner.nextLine().trim();
        int stockLevel = getIntInput("Stock Level: ");
        int lowStockAlertLevel = getIntInput("Low Stock Alert Level: ");

        Medicine newMedicine = new Medicine(id, name, dosage, stockLevel, lowStockAlertLevel);
        inventory.addMedicine(newMedicine);
    }

    /**
     * Removes medication from the inventory by its ID.
     */
    private void removeMedication() {
        System.out.print("\nEnter the ID of the medication to remove: ");
        String id = scanner.nextLine().trim();
        inventory.removeMedicine(id);
    }

    /**
     * Approves replenishment requests by processing and removing them from the
     * database.
     */
    private void approveReplenishmentRequests() {
        List<ReplenishmentRequest> requests = inventory.getReplenishmentRequests();
        if (requests.isEmpty()) {
            System.out.println("No replenishment requests to approve.");
        } else {
            for (ReplenishmentRequest request : requests) {
                inventory.increaseStock(request.getMedicineId(), request.getQuantity());

                boolean removed = inventory.removeReplenishmentRequest(request.getMedicineId());
                if (removed) {
                    inventory.saveReplenishmentRequests();
                    System.out.println("Replenishment request for " + request.getMedicineId() + " processed and removed.");
                } else {
                    System.out.println("Failed to remove the replenishment request for " + request.getMedicineId() + ".");
                }
            }
        }
    }

    /**
     * Changes the administrator's password.
     */
    private void changePassword() {
        System.out.println("Changing password...");
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();
        boolean success = administrator.changePassword(newPassword);
        if (success) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Error: Failed to change password.");
        }
    }

    /**
     * Gets a valid integer input from the user.
     *
     * @param prompt the prompt to display to the user
     * @return the valid integer input
     */
    private int getIntInput(String prompt) {
        int input;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return input;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear invalid input
            }
        }
    }
}
