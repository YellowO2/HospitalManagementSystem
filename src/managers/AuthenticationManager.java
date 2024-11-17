package managers;

import users.User;
import java.util.Scanner;

import database.UserDB;

public class AuthenticationManager {
    private Scanner scanner;
    private UserDB userDB;

    // Constructor, scanner can be passed from the main method
    public AuthenticationManager(UserDB userDB) {
        this.scanner = new Scanner(System.in);
        this.userDB = userDB;
    }

    // Method to check if user credentials are valid
    public User login(String userId, String password) {
        User user = userDB.getById(userId); // Retrieve the user by ID using UserDB

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful for user: " + userId);
            return user; // Return the authenticated user
        }
        System.out.println("user:" + user);

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
