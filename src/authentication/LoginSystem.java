package authentication;

import users.User;
import users.Patient;

import java.util.HashMap;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

public class LoginSystem {
    private Map<String, User> users = new HashMap<>();
    private Scanner scanner;

    public LoginSystem() {
        // Sample users for testing
        // TODO: Ensure unique IDs
        String yx_id = "P69";
        users.put(yx_id, new Patient(yx_id, "Patient YX", "yxpass", "123456789", "patientyx@example.com", "AB+",
                LocalDate.of(2000, 1, 1), "Male", null));
        this.scanner = new Scanner(System.in);
    }

    public User login(String userId, String password) {
        User user = users.get(userId);
        if (user != null && user.validatePassword(password)) {
            System.out.println("Login successful for user: " + userId);
            return user;
        }
        System.out.println("Login failed for user: " + userId);
        return null;
    }

    // New method to handle login interaction
    public User handleLogin() {
        User currentUser = null;
        while (currentUser == null) {
            System.out.println("Welcome to the Hospital Management System");
            System.out.print("Enter user ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            currentUser = login(userId, password);
            if (currentUser == null) {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
        return currentUser;
    }
}
