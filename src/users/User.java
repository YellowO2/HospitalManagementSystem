package users;

import java.time.LocalDate;

public abstract class User {
    private String id; // Unique hospital ID
    private String name; // Full name
    private String role; // User role (Patient, Doctor, etc.)
    private String password; // Store user password
    private String phoneNumber; // Contact number
    private String emailAddress; // Email address
    private LocalDate dateOfBirth; // Date of birth
    private String gender; // Gender

    // Constructor with all properties
    public User(String id, String name, String role, String password, String phoneNumber, String emailAddress,
            LocalDate dateOfBirth, String gender) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
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

    // Setters (optional)
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // Password methods
    public boolean changePassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            return false; // Password must be non-empty
        }
        this.password = newPassword;
        return true;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}
