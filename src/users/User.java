package users;

public abstract class User {
    private String id;
    private String name;
    private String role; // "Patient", "Doctor", "Pharmacist", "Administrator"
    private String password; // Store user password

    public User(String id, String name, String role, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
    }

    // Constructor with default password
    public User(String id, String name, String role) {
        this(id, name, role, "password"); // Default password
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public boolean changePassword(String newPassword) {
        this.password = newPassword;
        return true; // You might add more logic here for validation
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public abstract void viewProfile(); // To be implemented by child classes
}
