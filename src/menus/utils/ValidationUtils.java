package menus.utils;

import java.util.regex.Pattern;
import java.util.Scanner;

public class ValidationUtils {
    // Singapore phone number pattern (8 digits starting with 8 or 9)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[89]\\d{7}$");

    // Email pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    // Simpler password pattern (at least 6 chars)
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,}$");

    public static boolean isValidSingaporePhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

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