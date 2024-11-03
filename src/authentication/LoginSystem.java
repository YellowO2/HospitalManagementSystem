package authentication;

import users.User;
import users.Patient;

import java.util.HashMap;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

import medicalrecords.Diagnosis;
import medicalrecords.Prescription;
import medicalrecords.MedicalRecord;
import medicalrecords.Treatment;

public class LoginSystem {
    private Map<String, User> users = new HashMap<>();
    private Scanner scanner;

    public LoginSystem() {
        // Sample users for testing
        // TODO: Ensure unique IDs
        String yx_id = "P69";
        Patient yx = new Patient(yx_id, "Patient YX", "yxpass", "123456789", "patientyx@example.com", "AB+",
                LocalDate.of(2000, 1, 1), "Male");
        // yx.addDiagnosis(new Diagnosis("Big balls disease", "Moderate",
        // LocalDate.of(2022, 1, 1), yx_id));
        // yx.addPrescription(new Prescription("Pill A", "Dose", "After food", "Once a
        // day", 1, 5));
        // yx.addTreatment(new Treatment("Treatment A", LocalDate.of(2022, 1, 1), "Dr.
        // Smith", "Details A"));
        users.put(yx_id, yx);

        this.scanner = new Scanner(System.in);
    }

    public User login(String userId, String password) {
        User user = users.get(userId);

        if (user != null && user.getPassword().equals(password)) {
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
