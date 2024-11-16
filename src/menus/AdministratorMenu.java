/*
* The AdministratorMenu class is responsible for displaying options for the
administrator
* and handling user input. This class calls methods from the Administrator
class to perform actions.
 */
package menus;

import inventory.Medicine;
import inventory.ReplenishmentRequest;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import users.Administrator;

public class AdministratorMenu {
    private Administrator administrator;
    private Scanner scanner;

    // Constructor
    public AdministratorMenu(Administrator administrator) {
        this.administrator = administrator;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Inventory");
            System.out.println("3. Add New Medication");
            System.out.println("4. Remove Medication");
            System.out.println("5. Approve Replenishment Requests");
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
                    viewAndManageStaff();
                    break;
                case 2:
                    viewInventory();
                    break;
                case 3:
                    addNewMedication();
                    break;
                case 4:
                    removeMedication();
                    break;
                case 5:
                    approveReplenishmentRequests();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private void viewAndManageStaff() {
        System.out.println("\n=== Manage Hospital Staff ===");
        administrator.getUserDB().getAll().forEach(user -> {
            if (user.getRole().equalsIgnoreCase("Doctor") ||
                user.getRole().equalsIgnoreCase("Pharmacist") ||
                user.getRole().equalsIgnoreCase("Administrator")) {
                System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Role: " + user.getRole());
            }
        });
    }

    private void viewInventory() {
        System.out.println("=== Inventory ===");
        administrator.getInventory().displayInventory();
    }

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
        administrator.getInventory().addMedicine(newMedicine);
    }

    private void removeMedication() {
        System.out.print("\nEnter the ID of the medication to remove: ");
        String id = scanner.nextLine().trim();
        administrator.getInventory().removeMedicine(id);
    }

    private void approveReplenishmentRequests() {
        System.out.println("\nApproving replenishment requests...");
        List<ReplenishmentRequest> requests = administrator.getReplenishmentDB().getAll();
        if (requests.isEmpty()) {
            System.out.println("No replenishment requests to approve.");
        } else {
            requests.forEach(request -> {
                administrator.getInventory().replenishStock(request.getMedicineId(), request.getQuantity());
                administrator.getReplenishmentDB().delete(request.getMedicineId());
                try {
                    administrator.getReplenishmentDB().save();
                } catch (IOException e) {
                    System.out.println("Error saving replenishment data: " + e.getMessage());
                }
            });
            System.out.println("Replenishment requests approved.");
        }
    }

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

