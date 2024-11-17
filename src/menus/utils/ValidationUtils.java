package menus.utils;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The ValidationUtils class provides utility methods for validating user input,
 * such as phone numbers, email addresses, and passwords. It also includes methods
 * for prompting the user to enter valid input using a Scanner.
 * 
 * @author Your Name
 */
public class ValidationUtils {
    // Singapore phone number pattern (8 digits starting with 8 or 9)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[89]\\d{7}$");

    // Email pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    // Simpler password pattern (at least 6 chars)
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,}$");

    /**
     * Validates whether the given phone number matches the format for a Singapore phone number.
     *
     * @param phone the phone number to validate
     * @return true if the phone number is valid, false otherwise
     */
    public static boolean isValidSingaporePhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Validates whether the given email address matches a standard email format.
     *
     * @param email the email address to validate
     * @return true if the email address is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates whether the given password meets the required format (minimum 6 characters).
     *
     * @param password the password to validate
     * @return true if the password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * Prompts the user to enter a valid phone number and validates it.
     * The phone number must be 8 digits and start with either 8 or 9.
     *
     * @param scanner a Scanner object for reading user input
     * @return a valid phone number
     */
    public static String getValidPhoneNumber(Scanner scanner) {
        String phoneNumber;
        do {
            System.out.print("Enter phone number (8 digits starting with 8 or 9): ");
            phoneNumber = scanner.nextLine().trim();

            if (!isValidSingaporePhone(phoneNumber)) {
                System.out.println("Invalid phone number format. Please enter a valid Singapore phone number.");
            }
        } while (!isValidSingaporePhone(phoneNumber));

        return phoneNumber;
    }

    /**
     * Prompts the user to enter a valid email address and validates it.
     *
     * @param scanner a Scanner object for reading user input
     * @return a valid email address
     */
    public static String getValidEmail(Scanner scanner) {
        String email;
        do {
            System.out.print("Enter email address: ");
            email = scanner.nextLine().trim();

            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        } while (!isValidEmail(email));

        return email;
    }

    /**
     * Prompts the user to enter a valid password and validates it.
     * The password must be at least 6 characters long.
     *
     * @param scanner a Scanner object for reading user input
     * @return a valid password
     */
    public static String getValidPassword(Scanner scanner) {
        String password;
        do {
            System.out.println("Enter password (minimum 6 characters): ");
            password = scanner.nextLine().trim();

            if (!isValidPassword(password)) {
                System.out.println("Invalid password format. Password must be at least 6 characters long.");
            }
        } while (!isValidPassword(password));

        return password;
    }
}