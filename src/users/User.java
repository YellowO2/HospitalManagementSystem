package users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a user in the hospital system. This is an abstract class
 * that contains common properties and methods shared by different types of
 * users (e.g., Patient, Doctor).
 */
public abstract class User {
    private String id; // Unique hospital ID
    private String name; // Full name
    private String role; // User role (Patient, Doctor, etc.)
    private String password; // Store user password
    private String phoneNumber; // Contact number
    private String emailAddress; // Email address
    private LocalDate dateOfBirth; // Date of birth
    private String gender; // Gender

    /**
     * Constructs a new User with the specified details.
     * 
     * @param id           the unique identifier for the user
     * @param name         the full name of the user
     * @param role         the role of the user (e.g., Patient, Doctor)
     * @param password     the password for the user
     * @param phoneNumber  the contact phone number
     * @param emailAddress the email address
     * @param dateOfBirth  the date of birth in string format (yyyy-MM-dd)
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
            return false; // Password must be non-empty
        }
        this.password = newPassword;
        return true;
    }

    /**
     * Returns a string representation of the User object in CSV format.
     * 
     * @return a CSV string with user details
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                getId(),
                getName(),
                getDateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE), // Format date as yyyy-MM-dd
                getGender(),
                getPhoneNumber(),
                getEmailAddress(),
                getPassword(),
                getRole());
    }
}
