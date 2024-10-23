
// HospitalManagementSystem.java
import java.util.Scanner;
import authentication.LoginSystem;
import users.Patient;
import users.Doctor;
import appointments.Appointment;

public class HospitalManagementSystem {
    private static LoginSystem loginSystem = new LoginSystem();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;
        String userId = "";
        String role = "";

        // Login loop
        while (!loggedIn) {
            System.out.println("Welcome to the Hospital Management System");
            System.out.print("Enter user ID: ");
            userId = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            loggedIn = loginSystem.login(userId, password);
            if (loggedIn) {
                role = loginSystem.getUserRole(userId);
                System.out.println("Login successful! Role: " + role);
            } else {
                System.out.println("Login failed. Please try again.");
            }
        }

        // After login, present options based on role
        boolean running = true;
        while (running) {
            if (role.equals("Patient")) {
                runPatientMenu(scanner, userId);
            } else if (role.equals("Doctor")) {
                runDoctorMenu(scanner, userId);
            } else {
                System.out.println("Invalid role. Logging out.");
                running = false;
            }
        }

        scanner.close();
        System.out.println("Thank you for using the Hospital Management System.");
    }

    // Patient menu options
    private static void runPatientMenu(Scanner scanner, String userId) {
        Patient patient = new Patient(userId, "PatientName"); // Simulate loading patient from database
        boolean patientRunning = true;

        while (patientRunning) {
            System.out.println("\nPatient Menu:");
            System.out.println("1. View Profile");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. View Medical Records");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    patient.viewProfile();
                    break;
                case 2:
                    System.out.println("Scheduling logic not implemented yet...");
                    // Schedule appointment logic (could interact with Appointment class)
                    break;
                case 3:
                    patient.viewMedicalRecord();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    patientRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Doctor menu options
    // private static void runDoctorMenu(Scanner scanner, String userId) {
    // Doctor doctor = new Doctor(userId, "DoctorName"); // Simulate loading doctor
    // from database
    // boolean doctorRunning = true;

    // while (doctorRunning) {
    // System.out.println("\nDoctor Menu:");
    // System.out.println("1. View Profile");
    // System.out.println("2. View Patient Records");
    // System.out.println("3. Update Patient Records");
    // System.out.println("4. Logout");
    // System.out.print("Enter your choice: ");

    // int choice = scanner.nextInt();
    // scanner.nextLine(); // Consume newline

    // switch (choice) {
    // case 1:
    // doctor.viewProfile();
    // break;
    // case 2:
    // System.out.println("Viewing patient records...");
    // // View patient records logic (could interact with MedicalRecord class)
    // break;
    // case 3:
    // System.out.println("Updating patient records...");
    // // Update patient records logic (could interact with MedicalRecord class)
    // break;
    // case 4:
    // System.out.println("Logging out...");
    // doctorRunning = false;
    // break;
    // default:
    // System.out.println("Invalid choice. Please try again.");
    // }
    // }
    // }
}
