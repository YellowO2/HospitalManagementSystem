import appointments.AppointmentManager;
import appointments.AppointmentOutcomeManager;
import authentication.AuthenticationManager;
import database.DatabaseManager;
import inventory.Inventory;
import java.io.IOException;
import java.util.Scanner;
import medicalrecords.MedicalRecordManager;
import menus.AdministratorMenu;
import menus.DoctorMenu;
import menus.PatientMenu;
import menus.PharmacistMenu;
import users.Administrator;
import users.Doctor;
import users.Patient;
import users.Pharmacist;
import users.User;

public class HospitalManagementSystem {

    private static DatabaseManager databaseManager = new DatabaseManager();
    private static AuthenticationManager loginSystem = new AuthenticationManager(databaseManager.getUserDB());
    private static MedicalRecordManager medicalRecordManager = new MedicalRecordManager(
            databaseManager.getMedicalRecordDB());
    private static AppointmentManager appointmentManager = new AppointmentManager(
            databaseManager.getdoctorAvailabilityDB(), databaseManager.getAppointmentDB(), databaseManager.getUserDB());
    private static AppointmentOutcomeManager appointmentOutcomeManager = new AppointmentOutcomeManager(
            databaseManager.getAppointmentOutcomeRecordDB());
    private static Inventory inventory = new Inventory(databaseManager.getMedicineDB(),
            databaseManager.getReplenishmentDB());
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

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
            PatientMenu patientMenu = new PatientMenu(patient, medicalRecordManager, appointmentManager,
                    appointmentOutcomeManager);
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
            AdministratorMenu administratorMenu = new AdministratorMenu(administrator, databaseManager.getUserDB(), inventory, appointmentManager);
            administratorMenu.displayMenu();
        } else {
            System.out.println("Invalid role. Logging out.");
        }
    }
}
