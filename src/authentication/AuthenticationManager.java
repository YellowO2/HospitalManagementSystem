package authentication;

import users.User;
import users.Patient;
import java.util.Scanner;
import database.HMSDatabase;

public class AuthenticationManager {
    private Scanner scanner;

    // TODO: Consider passing scanner from main
    public AuthenticationManager() {
        this.scanner = new Scanner(System.in);
    }

    // Method to check if user credentials are valid
    public User login(String userId, String password) {
        HMSDatabase hmsDatabase = HMSDatabase.getInstance();
        User user = hmsDatabase.getUserById(userId);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful for user: " + userId);
            return user; // Return the authenticated patient
        }

        System.out.println("Login failed for user: " + userId);
        return null; // Return null if no match found
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
        return currentUser; // Return the authenticated user
    }
}
