package users;

import database.UserDB;
import inventory.Inventory;

/**
 * The Administrator class extends the User class and represents an administrator
 * with additional access to manage the inventory and user database.
 */
public class Administrator extends User {
    private Inventory inventory;
    private UserDB userDB;

    /**
     * Constructs an Administrator object with the specified details.
     *
     * @param id           the unique identifier for the administrator
     * @param name         the name of the administrator
     * @param dateOfBirth  the date of birth of the administrator
     * @param gender       the gender of the administrator
     * @param phoneNumber  the phone number of the administrator
     * @param emailAddress the email address of the administrator
     * @param password     the password for the administrator's account
     */
    public Administrator(String id, String name, String dateOfBirth, String gender, String phoneNumber,
                         String emailAddress, String password) {
        super(id, name, "Administrator", password, phoneNumber, emailAddress, dateOfBirth, gender);
    }

    /**
     * Gets the inventory associated with the administrator.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the user database associated with the administrator.
     *
     * @return the user database
     */
    public UserDB getUserDB() {
        return userDB;
    }

}
