package users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    // Setters
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Changes the password of the user.
     *
     * @param newPassword the new password to set
     * @return true if the password was changed successfully, false if the password is null or empty
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
