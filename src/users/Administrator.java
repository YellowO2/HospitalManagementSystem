package users;

import data.ReadFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import medicine.Inventory;
import medicine.Medicine;

public class Administrator extends User {

    // List to store staff members (Doctors & Pharmacists)
    private List<Staff> staffList;

    // Inventory object for managing medication
    private Inventory inventory;

    // Constructor to initialize Administrator with user properties and inventory
    public Administrator(String id, String name, String password, String phoneNumber, String emailAddress,
                         LocalDate dateOfBirth, String gender) throws IOException {
        super(id, name, "Administrator", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.staffList = new ArrayList<>();
        this.inventory = new Inventory("Medicine_List.csv");
    }

    // Initialize the staff list from a CSV file
    public void initializeStaffList() throws IOException {
        this.staffList = new ArrayList<>();
        Staff[] loadedStaff = ReadFile.readStaffListFile("Staff_List.csv");
        for (Staff staff : loadedStaff) {
            staffList.add(staff);
        }
        System.out.println("Staff list initialized from file.");
    }

    // Method to add a new staff member
    public void addStaff(Staff staff) {
        staffList.add(staff);
        System.out.println("Staff member added successfully: " + staff.getName());
    }

    // Method to remove an existing staff member
    public void removeStaff(Staff staff) {
        if (staffList.remove(staff)) {
            System.out.println("Staff member removed successfully: " + staff.getName());
        } else {
            System.out.println("Staff member not found.");
        }
    }

    // Method to update staff information
    public void updateStaff(Staff oldStaff, Staff newStaff) {
        int index = staffList.indexOf(oldStaff);
        if (index >= 0) {
            staffList.set(index, newStaff);
            System.out.println("Staff information updated for: " + newStaff.getName());
        } else {
            System.out.println("Old staff member not found.");
        }
    }

    // Method to display the staff list
    public void displayStaffList() {
        System.out.println("=== Staff List ===");
        for (Staff staff : staffList) {
            System.out.println(staff);
        }
    }

    // Method to view appointments (placeholder for future implementation)
    public void viewAppointments() {
        System.out.println("Viewing appointments... (Work in progress)");
        // Implement logic to display appointment details if needed
    }

    // Method to view the entire inventory
    public void viewInventory() {
        Medicine[] inventoryList = inventory.GetInventory();
        System.out.println("=== Inventory List ===");
        for (Medicine medicine : inventoryList) {
            System.out.println("Medicine: " + medicine.getName() + ", Stock: " + medicine.getStock() +
                               ", Low Stock Alert Level: " + medicine.getLowStockLevelAlert());
        }
    }

    // Method to update stock for an existing medicine
    public void updateInventoryStock(String medicineName, int stockNum) {
        int updated = inventory.setInventory(medicineName, stockNum);
        if (updated == 1) {
            System.out.println("Inventory updated for " + medicineName);
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    }

    // Method to update stock and low stock alert for an existing medicine
    public void updateInventoryStockWithAlert(String medicineName, int stockNum, int lowStockAlert) {
        int updated = inventory.setInventory(medicineName, stockNum, lowStockAlert);
        if (updated == 1) {
            System.out.println("Inventory and low stock alert updated for " + medicineName);
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    }

    // Method to add a new medicine to the inventory
    public void addNewMedicine(String medicineName, int initialStock, int lowStockAlert) {
        inventory.addInventory(medicineName, initialStock, lowStockAlert);
        System.out.println("New medicine added to the inventory: " + medicineName);
    }

    // Method to remove a medicine from the inventory
    public void removeMedicine(String medicineName) {
        int updated = inventory.removeInventory(medicineName);
        if (updated == 1) {
            System.out.println(medicineName + " removed from the inventory.");
        } else {
            System.out.println("Medicine not found in the inventory.");
        }
    }
}
