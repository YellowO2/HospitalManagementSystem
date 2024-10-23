package users;

public abstract class User {
    private String id;
    private String name;
    private String role; // "Patient", "Doctor", etc.

    public User(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
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

    public abstract void viewProfile(); // To be implemented by child classes
}
