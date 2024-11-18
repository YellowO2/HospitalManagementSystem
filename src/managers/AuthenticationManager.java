/**
 * Manages user authentication for the Hospital Management System (HMS).
 * Provides methods to validate user credentials and handle login interactions.
 */
package managers;

import users.User;
import java.util.Scanner;
import database.UserDB;

public class AuthenticationManager {
    private Scanner scanner;
    private UserDB userDB;

    /**
     * Constructor to initialize the AuthenticationManager.
     *
     * @param userDB the database instance for storing and retrieving user data.
     */
    public AuthenticationManager(UserDB userDB) {
        this.scanner = new Scanner(System.in);
        this.userDB = userDB;
    }

    /**
     * Validates the user credentials by checking the User ID and password.
     *
     * @param userId   the User ID entered by the user.
     * @param password the password entered by the user.
     * @return the authenticated user if credentials are valid; null otherwise.
     */
    public User login(String userId, String password) {
        User user = userDB.getById(userId); // Retrieve the user by ID using UserDB

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful for user: " + userId);
            return user; // Return the authenticated user
        }

        System.out.println("Login failed for user: " + userId);
        return null; // Return null if no match found
    }

    /**
     * Handles the login interaction with the user.
     * Continuously prompts for credentials until successful authentication.
     *
     * @return the authenticated user after successful login.
     */
    public User handleLogin() {
        User currentUser = null;
        while (currentUser == null) {
            System.out.println( "               ██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗    ████████╗ ██████╗     ████████╗██╗  ██╗███████╗\r\n" + //
                                "               ██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝    ╚══██╔══╝██╔═══██╗    ╚══██╔══╝██║  ██║██╔════╝\r\n" + //
                                "               ██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗         ██║   ██║   ██║       ██║   ███████║█████╗  \r\n" + //
                                "               ██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝         ██║   ██║   ██║       ██║   ██╔══██║██╔══╝  \r\n" + //
                                "               ╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗       ██║   ╚██████╔╝       ██║   ██║  ██║███████╗\r\n" + //
                                "                ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝       ╚═╝    ╚═════╝        ╚═╝   ╚═╝  ╚═╝╚══════╝\r\n" + //
                                "                                                                                                                 ");
            System.out.println( "                                           ██╗  ██╗ ██████╗ ███████╗██████╗ ██╗████████╗ █████╗ ██╗     \r\n" + //
                                "                                           ██║  ██║██╔═══██╗██╔════╝██╔══██╗██║╚══██╔══╝██╔══██╗██║     \r\n" + //
                                "                                           ███████║██║   ██║███████╗██████╔╝██║   ██║   ███████║██║     \r\n" + //
                                "                                           ██╔══██║██║   ██║╚════██║██╔═══╝ ██║   ██║   ██╔══██║██║     \r\n" + //
                                "                                           ██║  ██║╚██████╔╝███████║██║     ██║   ██║   ██║  ██║███████╗\r\n" + //
                                "                                           ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝     ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝\r\n" + //
                                "                                                             ");
            System.out.println( "                               ███╗   ███╗ █████╗ ███╗   ██╗ █████╗  ██████╗ ███████╗███╗   ███╗███████╗███╗   ██╗████████╗\r\n" + //
                                "                               ████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝ ██╔════╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝\r\n" + //
                                "                               ██╔████╔██║███████║██╔██╗ ██║███████║██║  ███╗█████╗  ██╔████╔██║█████╗  ██╔██╗ ██║   ██║   \r\n" + //
                                "                               ██║╚██╔╝██║██╔══██║██║╚██╗██║██╔══██║██║   ██║██╔══╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║   \r\n" + //
                                "                               ██║ ╚═╝ ██║██║  ██║██║ ╚████║██║  ██║╚██████╔╝███████╗██║ ╚═╝ ██║███████╗██║ ╚████║   ██║   \r\n" + //
                                "                               ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝   \r\n" + //
                                "                                                                                            ");
            System.out.println("                                            ███████╗██╗   ██╗███████╗████████╗███████╗███╗   ███╗\r\n" + //
                               "                                            ██╔════╝╚██╗ ██╔╝██╔════╝╚══██╔══╝██╔════╝████╗ ████║\r\n" + //
                               "                                            ███████╗ ╚████╔╝ ███████╗   ██║   █████╗  ██╔████╔██║\r\n" + //
                               "                                            ╚════██║  ╚██╔╝  ╚════██║   ██║   ██╔══╝  ██║╚██╔╝██║\r\n" + //
                               "                                            ███████║   ██║   ███████║   ██║   ███████╗██║ ╚═╝ ██║\r\n" + //
                               "                                            ╚══════╝   ╚═╝   ╚══════╝   ╚═╝   ╚══════╝╚═╝     ╚═╝\r\n" + //
                            "                                                     ");
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
