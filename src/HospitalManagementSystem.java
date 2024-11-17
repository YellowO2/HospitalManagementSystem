import java.io.IOException;
import java.util.Scanner;
import managers.AppointmentManager;
import managers.AppointmentOutcomeManager;
import managers.AuthenticationManager;
import managers.DatabaseManager;
import managers.InventoryManager;
import managers.MedicalRecordManager;
import menus.AdministratorMenu;
import menus.DoctorMenu;
import menus.PatientMenu;
import menus.PharmacistMenu;
import users.Administrator;
import users.Doctor;
import users.Patient;
import users.Pharmacist;
import users.User;

/**
 * The HospitalManagementSystem class is the main entry point for the
 * hospital management application. It initializes the system, manages
 * user authentication, and navigates users to their respective menus based
 * on their roles.
 */
public class HospitalManagementSystem {

    private static DatabaseManager databaseManager = new DatabaseManager();
    private static AuthenticationManager loginSystem = new AuthenticationManager(databaseManager.getUserDB());
    private static MedicalRecordManager medicalRecordManager = new MedicalRecordManager(
            databaseManager.getMedicalRecordDB());
    private static AppointmentManager appointmentManager = new AppointmentManager(
            databaseManager.getdoctorAvailabilityDB(), databaseManager.getAppointmentDB(), databaseManager.getUserDB());
    private static AppointmentOutcomeManager appointmentOutcomeManager = new AppointmentOutcomeManager(
            databaseManager.getAppointmentOutcomeRecordDB());
    private static InventoryManager inventory = new InventoryManager(databaseManager.getMedicineDB(),
            databaseManager.getReplenishmentDB());
    private static Scanner scanner = new Scanner(System.in);

    /**
     * The main method initializes the system, handles user login, and navigates the user
     * to their respective menu based on their role.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Initialize the database
            System.out.println("Loading database...");
            databaseManager.initialize();

            // Handle user login
            User currentUser = loginSystem.handleLogin();

            if (currentUser != null) {
                handleUserRole(scanner, currentUser);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the scanner
            scanner.close();
        }

        // Save changes to the database before exiting
        try {
            databaseManager.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Thank you for using the Hospital Management System.");
    }

    /**
     * Directs the user to the appropriate menu based on their role in the system.
     *
     * @param scanner     the Scanner object for reading user input
     * @param currentUser the user currently logged in
     */
    private static void handleUserRole(Scanner scanner, User currentUser) {
        String role = currentUser.getRole();

        if (role.equals("Patient")) {
            Patient patient = (Patient) currentUser;
            PatientMenu patientMenu = new PatientMenu(patient, medicalRecordManager, appointmentManager,
                    appointmentOutcomeManager, databaseManager.getUserDB());
            patientMenu.displayMenu();
        } else if (role.equals("Doctor")) {
            Doctor doctor = (Doctor) currentUser;
            DoctorMenu doctorMenu = new menus.DoctorMenu(doctor, appointmentManager, appointmentOutcomeManager, medicalRecordManager,
                    databaseManager.getdoctorAvailabilityDB(), databaseManager.getUserDB());
            doctorMenu.displayMenu();
        } else if (role.equals("Pharmacist")) {
            Pharmacist pharmacist = (Pharmacist) currentUser;
            PharmacistMenu pharmacistMenu = new PharmacistMenu(pharmacist, appointmentOutcomeManager, inventory);
            pharmacistMenu.displayMenu();
        } else if (role.equals("Administrator")) {
            Administrator administrator = (Administrator) currentUser;
            AdministratorMenu administratorMenu = new AdministratorMenu(administrator, databaseManager.getUserDB(),
                    inventory, appointmentManager);
            administratorMenu.displayMenu();
        } else {
            System.out.println("Invalid role. Logging out.");
        }
    }
}