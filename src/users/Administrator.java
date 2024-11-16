package users;

import database.ReplenishmentDB;
import database.UserDB;
import inventory.Inventory;

public class Administrator extends User {
    private Inventory inventory;
    private UserDB userDB;
    private ReplenishmentDB replenishmentDB;

    public Administrator(String id, String name, String dateOfBirth, String gender, String phoneNumber,
                         String emailAddress, String password) {
        super(id, name, "Administrator", password, phoneNumber, emailAddress, dateOfBirth, gender);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public UserDB getUserDB() {
        return userDB;
    }

    public ReplenishmentDB getReplenishmentDB() {
        return replenishmentDB;
    }
    
    // Additional methods for managing inventory, users, and replenishment requests can be added here.
}
