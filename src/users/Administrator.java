package users;

import database.ReplenishmentDB;
import database.UserDB;
import inventory.Inventory;
import inventory.Medicine;
import inventory.ReplenishmentRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Administrator extends User {
    private List<Doctor> staffList;
    private Inventory inventory;
    private UserDB userDB;
    private ReplenishmentDB replenishmentDB; // Ensure this is initialized

    public Administrator(String id, String name, String dateOfBirth, String gender, String phoneNumber,
            String emailAddress, String password) throws IOException {
        super(id, name, "Administrator", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.staffList = new ArrayList<>();
        this.inventory = new Inventory();
        this.userDB = new UserDB(); // Initialize userDB properly
        this.replenishmentDB = new ReplenishmentDB(); // Properly initialize ReplenishmentDB

        try {
            replenishmentDB.load();
            System.out.println("Replenishment requests loaded successfully.");
    }   catch (IOException e) {
            System.out.println("Error loading replenishment requests: " + e.getMessage());
    }
    }

    // Method to initialize the staff list using UserDB
    public void initializeStaffList() {
        if (userDB != null) {
            List<User> allUsers = userDB.getAll(); // Get all users from UserDB

            this.staffList.clear(); // Clear current list
            for (User user : allUsers) {
                if (user instanceof Doctor) {
                    this.staffList.add((Doctor) user); // Add only staff members
                }
            }
            System.out.println("Staff list initialized from UserDB.");
        } else {
            System.out.println("UserDB reference is null. Cannot initialize staff list.");
        }
    }

    // Method to add a new user to the database
    public void addUser(User user) {
        if (userDB.create(user)) {
            System.out.println("User added successfully: " + user.getName());
        } else {
            System.out.println("Failed to add user.");
        }
    }

    // Method to remove an existing user from the database by ID
    public void removeUser(String userId) {
        if (userDB.delete(userId)) {
            System.out.println("User with ID " + userId + " removed successfully.");
        } else {
            System.out.println("User not found or could not be removed.");
        }
    }

    // Method to update user information in the database
    public void updateUser(User updatedUser) {
        if (userDB.update(updatedUser)) {
            System.out.println("User information updated successfully for: " + updatedUser.getName());
        } else {
            System.out.println("Failed to update user information.");
        }
    }

    // Method to get a user by ID from the database
    public User getUserById(String userId) {
        User user = userDB.getById(userId);
        if (user != null) {
            System.out.println("User found: " + user);
            return user;
        } else {
            System.out.println("User not found.");
            return null;
        }
    }

    // Method to display all users
    public void displayAllUsers() {
        List<User> users = userDB.getAll();
        if (users.isEmpty()) {
            System.out.println("No users found in the database.");
        } else {
            System.out.println("=== User List ===");
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

    public void displayAllDoctors() {
    if (staffList.isEmpty()) {
        System.out.println("No doctors found in the staff list.");
    } else {
        System.out.println("=== Doctor List ===");
        for (Doctor doctor : staffList) {
            System.out.println(doctor);
        }
    }
}
        //Method to view the entire inventory
    public void viewInventory() {
        System.out.println("=== Inventory List ===");
        inventory.displayInventory();
    }
    public void displayReplenishmentRequests() {
        List<ReplenishmentRequest> requests = replenishmentDB.getAll();
        if (requests.isEmpty()) {
            System.out.println("No replenishment requests have been submitted.");
        } else {
            System.out.println("Replenishment Requests:");
            for (ReplenishmentRequest request : requests) {
                System.out.println(request);
            }
        }
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
                inventory.updateMedicine(request.getMedicineId(), medicine.getStockLevel() + request.getQuantity());
                replenishmentDB.delete(request.getMedicineId()); // Remove approved request from ReplenishmentDB
                System.out.println("Replenishment request approved and processed for medicine ID: " + request.getMedicineId());
            } else {
                System.out.println("Medicine with ID " + request.getMedicineId() + " not found in inventory. Skipping request.");
            }
        }
    }
}
    // Existing methods related to inventory and staff management remain unchanged

    // Method to view the entire inventory
    // public void viewInventory() {
    //     Medicine[] inventoryList = Inventory.displayInventory();
    //     System.out.println("=== Inventory List ===");
    //     for (Medicine medicine : inventoryList) {
    //         System.out.println("Medicine: " + medicine.getName() + ", Stock: " + medicine.getStock() +
    //                 ", Low Stock Alert Level: " + medicine.getLowStockLevelAlert());
    //     }
    // }

    // Method to update stock for an existing medicine
 /*   public void updateInventoryStock(String medicineName, int stockNum) {
        int updated = inventory.setInventory(medicineName, stockNum);
        if (updated == 1) {
            System.out.println("Inventory updated for " + medicineName);
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    }*/

    // Method to update stock and low stock alert for an existing medicine
/*    public void updateInventoryStockWithAlert(String medicineName, int stockNum, int lowStockAlert) {
        int updated = inventory.setInventory(medicineName, stockNum, lowStockAlert);
        if (updated == 1) {
            System.out.println("Inventory and low stock alert updated for " + medicineName);
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    }*/

/*    // Method to add a new medicine to the inventory
    public void addNewMedicine(String medicineName, int initialStock, int lowStockAlert) {
        inventory.addInventory(medicineName, initialStock, lowStockAlert);
        System.out.println("New medicine added to the inventory: " + medicineName);
    }*/

  /*  // Method to remove a medicine from the inventory
    public void removeMedicine(String medicineName) {
        String updated = Inventory.removeMedicine(medicineName);
        if (updated == 1) {
            System.out.println(medicineName + " removed from the inventory.");
        } else {
            System.out.println("Medicine not found in the inventory.");
        }
    }
}*/
