package users;

import database.MedicineDB;
import database.ReplenishmentDB;
import database.UserDB;
import inventory.Inventory;
import inventory.Medicine;
import inventory.ReplenishmentRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Administrator extends User {
    private Inventory inventory;
    private UserDB userDB;
    private ReplenishmentDB replenishmentDB;
    private MedicineDB medicineDB;

    public Administrator(String id, String name, String dateOfBirth, String gender, String phoneNumber,
                         String emailAddress, String password) throws IOException {
        super(id, name, "Administrator", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.inventory = new Inventory();
        this.userDB = new UserDB(); // Initialize UserDB
        this.replenishmentDB = new ReplenishmentDB();
        this.medicineDB = new MedicineDB();

        try {
            replenishmentDB.load();
        } catch (IOException e) {
            System.out.println("Error loading replenishment requests: " + e.getMessage());
        }
        try {
            medicineDB.load();
        } catch (IOException e) {
            System.out.println("Error loading Inventory or UserDB: " + e.getMessage());
        }
    }
    public void loadUserDB() {
        try {
            userDB.getAll().clear();
            userDB.load();
            System.out.println("UserDB loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading UserDB: " + e.getMessage());
        }
    }
    // Method to add a new medication
    public void addNewMedication(Medicine medicine) {
        if (inventory.getMedicineById(medicine.getId()) == null) {
            inventory.addMedicine(medicine); // Add the new medicine to the inventory

            try {
                if (medicineDB.create(medicine)) { // Ensure the new medicine is added successfully
                    medicineDB.save(); // Save changes to persist the new medicine
                    System.out.println("New medication added and saved to Inventory_List.csv: " + medicine.getName());
                } else {
                    System.out.println("Failed to add new medication to the database.");
                }
            } catch (IOException e) {
                System.out.println("Error saving new medication to Inventory_List.csv: " + e.getMessage());
            }
        } else {
            System.out.println("Medication with ID " + medicine.getId() + " already exists in inventory.");
        }
    }

    // Method to remove a medication by ID
    public void removeMedication(String id) {
        Medicine medicine = inventory.getMedicineById(id);
        if (medicine != null) {
            inventory.removeMedicine(id); // Remove the medicine from the in-memory inventory

            try {
                if (medicineDB.delete(id)) { // Ensure the medicine is deleted from the database
                    medicineDB.save(); // Save changes to persist the removal
                    System.out.println("Medication with ID " + id + " removed and changes saved to Inventory_List.csv.");
                } else {
                    System.out.println("Failed to remove the medication from the database.");
                }
            } catch (IOException e) {
                System.out.println("Error saving changes to Inventory_List.csv: " + e.getMessage());
            }
        } else {
            System.out.println("Medication with ID " + id + " not found.");
        }
    }

    // Method to update medication stock
    public void updateMedicationStock(String id, int newStockLevel) {
        Medicine medicine = inventory.getMedicineById(id);
        if (medicine != null) {
            inventory.updateMedicine(id, newStockLevel);
            System.out.println("Stock level updated for medication: " + medicine.getName());
        } else {
            System.out.println("Medication not found in inventory.");
        }
    }

    // Method to display all staff
    public void displayAllStaff(){
        loadUserDB();
        System.out.println("=== Staff List ===");
        List<User> allUsers = userDB.getAll();
        
        boolean hasStaff = false;
        for (User user : allUsers) {
            if (user.getRole().equalsIgnoreCase("Doctor") || 
                user.getRole().equalsIgnoreCase("Pharmacist") || 
                user.getRole().equalsIgnoreCase("Administrator")) {
                System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Role: " + user.getRole());
                hasStaff = true;
            }
        }
        
        if (!hasStaff) {
            System.out.println("No staff members found.");
        }
    }

    public void viewInventory() {
        System.out.println("=== Inventory List ===");
        inventory.displayInventory();
    }

    public UserDB getUserDB() {
        return userDB;
    }

    // Method to approve replenishment requests directly from ReplenishmentDB
    public void approveRequest() {
        List<ReplenishmentRequest> requests = replenishmentDB.getAll();
        if (requests.isEmpty()) {
            System.out.println("No replenishment requests to approve.");
            return;
        }

        for (ReplenishmentRequest request : new ArrayList<>(requests)) {
            Medicine medicine = inventory.getMedicineById(request.getMedicineId());
            if (medicine != null) {
                int newStockLevel = medicine.getStockLevel() + request.getQuantity();
                inventory.updateMedicine(request.getMedicineId(), newStockLevel);

                boolean isDeleted = replenishmentDB.delete(request.getMedicineId());
                if (isDeleted) {
                    try {
                        replenishmentDB.save(); // Save changes after deletion
                        System.out.println("Replenishment request approved and processed for medicine ID: " + request.getMedicineId());
                    } catch (IOException e) {
                        System.out.println("Error saving replenishment database: " + e.getMessage());
                    }
                } else {
                    System.out.println("Failed to delete the replenishment request for medicine ID: " + request.getMedicineId());
                }
            } else {
                System.out.println("Medicine with ID " + request.getMedicineId() + " not found in inventory. Skipping request.");
            }
        }
    }

    public void addNewStaff(User newStaff) {
        if (userDB.getById(newStaff.getId()) == null) {
            userDB.create(newStaff);
            try {
                userDB.save();
                System.out.println("New staff member added: " + newStaff.getName());
                displayAllStaff();
            } catch (IOException e) {
                System.out.println("Error saving new staff member: " + e.getMessage());
            }
        } else {
            System.out.println("Staff member with ID " + newStaff.getId() + " already exists.");
        }
    }

// Method to remove a staff member by ID
    public void removeStaff(String id) {
        User staff = userDB.getById(id);
        if (staff != null && (staff.getRole().equalsIgnoreCase("Doctor")
                || staff.getRole().equalsIgnoreCase("Pharmacist")
                || staff.getRole().equalsIgnoreCase("Administrator"))) {
            userDB.delete(id);
            try {
                userDB.save();
                System.out.println("Staff member with ID " + id + " removed.");
            } catch (IOException e) {
                System.out.println("Error saving after removing staff member: " + e.getMessage());
            }
        } else {
            System.out.println("Staff member with ID " + id + " not found or cannot be removed.");
        }
    }
}
