import java.util.Scanner;

// custom imports
import authentication.LoginSystem;
import medicalrecords.MedicalRecordManager;
import users.User;
import users.Patient;
import menus.PatientMenu;

public class HospitalManagementSystem {
    private static LoginSystem loginSystem = new LoginSystem();
    private static MedicalRecordManager medicalRecordManager = new MedicalRecordManager();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User currentUser = loginSystem.handleLogin();

        if (currentUser != null) {
            handleUserRole(scanner, currentUser);
        }

        scanner.close();
        System.out.println("Thank you for using the Hospital Management System.");
    }

    // Handles the actions based on the user's role
    private static void handleUserRole(Scanner scanner, User currentUser) {
        String role = currentUser.getRole();

        if (role.equals("Patient")) {
            PatientMenu patientMenu = new PatientMenu((Patient) currentUser, medicalRecordManager);
            patientMenu.displayMenu();
        } else if (role.equals("Doctor")) {
            // Uncomment and implement the doctor menu
            // runDoctorMenu(scanner, (Doctor) currentUser);
        } else {
            System.out.println("Invalid role. Logging out.");
        }
    }
}
