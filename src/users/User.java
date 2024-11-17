package users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a user in the hospital system. This is an abstract class
 * that contains common properties and methods shared by different types of
 * users (e.g., Patient, Doctor).
 */
/**
 * The User class represents a generic user in the hospital system with basic properties
 * such as ID, name, role, password, contact information, date of birth, and gender.
 * This class is designed to be extended by more specific user types (e.g., Doctor, Patient).
 */
public abstract class User {
    private String id;
    private String name;
    private String role;
    private String password;
    private String phoneNumber;
    private String emailAddress;
    private LocalDate dateOfBirth;
    private String gender;

    /**
     * Constructs a User object with the specified details.
     *
     * @param id           the unique hospital ID for the user
     * @param name         the full name of the user
     * @param role         the role of the user (e.g., Patient, Doctor, etc.)
     * @param password     the password for the user's account
     * @param phoneNumber  the contact number of the user
     * @param emailAddress the email address of the user
     * @param dateOfBirth  the date of birth of the user in the format yyyy-MM-dd
     * @param gender       the gender of the user
     */
    public User(String id, String name, String role, String password, String phoneNumber, String emailAddress,
            String dateOfBirth, String gender) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.dateOfBirth = LocalDate.parse(dateOfBirth);
        this.gender = gender;
    }

    /**
     * Gets the unique ID of the user.
     * 
     * @return the ID of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the user.
     * 
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the role of the user.
     * 
     * @return the role of the user
     */
    public String getRole() {
        return role;
    }

    /**
     * Gets the contact phone number of the user.
     * 
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the email address of the user.
     * 
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Gets the date of birth of the user.
     * 
     * @return the date of birth as a LocalDate object
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets the gender of the user.
     * 
     * @return the gender of the user
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets a new phone number for the user.
     * 
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets a new email address for the user.
     * 
     * @param emailAddress the new email address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Sets a new password for the user.
     * 
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Changes the password of the user.
     * 
     * @param newPassword the new password
     * @return true if the password was successfully changed, false otherwise
     */
    public boolean changePassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            return false;
        }
        this.password = newPassword;
        return true;
    }

    /**
     * Returns a string representation of the user with their details in a CSV format.
     *
     * @return a formatted string representing the user's details
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                getId(),
                getName(),
                getDateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE),
                getGender(),
                getPhoneNumber(),
                getEmailAddress(),
                getPassword(),
                getRole());
    }
}
