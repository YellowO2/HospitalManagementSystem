import java.io.IOException;
import java.util.Scanner;

import authentication.AuthenticationManager;
import appointments.AppointmentManager;
import medicalrecords.MedicalRecordManager;
import users.User;
import users.Patient;
import menus.PatientMenu;
import database.DatabaseManager;

public class HospitalManagementSystem {

    private static DatabaseManager databaseManager = new DatabaseManager();
    private static AuthenticationManager loginSystem = new AuthenticationManager(databaseManager.getUserDB());
    private static MedicalRecordManager medicalRecordManager = new MedicalRecordManager(databaseManager.getMedicalRecordDB());
    private static AppointmentManager appointmentManager = new AppointmentManager(databaseManager.getdoctorAvailabilityDB(), databaseManager.getAppointmentDB());

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Initialize the database
            System.out.println("Loading database...");
            databaseManager.initialize();

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

        try {
            databaseManager.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Thank you for using the Hospital Management System.");
    }

    // Handles the actions based on the user's role
    private static void handleUserRole(Scanner scanner, User currentUser) {
        String role = currentUser.getRole();

        if (role.equals("Patient")) {
            Patient patient = (Patient) currentUser;
            PatientMenu patientMenu = new PatientMenu(patient, medicalRecordManager, appointmentManager);
            patientMenu.displayMenu();
        } else if (role.equals("Doctor")) {
            // Implement the doctor menu
            // runDoctorMenu(scanner, (Doctor) currentUser);
        } else {
            System.out.println("Invalid role. Logging out.");
        }
    }
}
