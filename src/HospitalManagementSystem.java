import java.util.Scanner;

import authentication.AuthenticationManager;
import medicalrecords.MedicalRecordManager;
import users.User;
import users.Patient;
import menus.PatientMenu;
import database.HMSDatabase;

public class HospitalManagementSystem {
    private static AuthenticationManager loginSystem = new AuthenticationManager();
    private static MedicalRecordManager medicalRecordManager = new MedicalRecordManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Initialize the database and load data
            System.out.println("Loading database...");
            HMSDatabase.getInstance().initializeDatabase();
            ; // Access singleton instance
            System.out.println("Database loaded successfully.");

            User currentUser = loginSystem.handleLogin();

            if (currentUser != null) {
                handleUserRole(scanner, currentUser);
            }

            // Save any changes to the database before exiting
            // hmsDatabase.savePatients();
            // hmsDatabase.saveMedicalRecords();

        } catch (Exception e) {
            System.out.println("Error loading the database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }

        System.out.println("Thank you for using the Hospital Management System.");
    }

    // Handles the actions based on the user's role
    private static void handleUserRole(Scanner scanner, User currentUser) {
        String role = currentUser.getRole();

        if (role.equals("Patient")) {
            Patient patient = (Patient) currentUser;
            PatientMenu patientMenu = new PatientMenu(patient, medicalRecordManager);
            patientMenu.displayMenu();
        } else if (role.equals("Doctor")) {
            // Implement the doctor menu
            // runDoctorMenu(scanner, (Doctor) currentUser);
        } else {
            System.out.println("Invalid role. Logging out.");
        }
    }
}
