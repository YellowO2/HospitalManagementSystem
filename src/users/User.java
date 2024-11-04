package users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    // Setters (optional)
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

    // Password methods
    public boolean changePassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            return false; // Password must be non-empty
        }
        this.password = newPassword;
        return true;
    }

    public String toString() {
        // Assuming getDateOfBirth() returns a LocalDate
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
