import java.util.Scanner;
import authentication.LoginSystem;
import users.User;
import users.Patient; // Include your user classes
// import users.Doctor; // Uncomment if you have this user class

public class HospitalManagementSystem {
    private static LoginSystem loginSystem = new LoginSystem();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        // Login loop
        while (currentUser == null) {
            System.out.println("Welcome to the Hospital Management System");
            System.out.print("Enter user ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // Currently added this patient into the set for testing: Patient("patient1",
            // "John Doe", "notpassword")
            currentUser = loginSystem.login(userId, password);
        }

        // After login, present options based on role
        boolean running = true;
        while (running) {
            String role = currentUser.getRole();
            if (role.equals("Patient")) {
                runPatientMenu(scanner, (Patient) currentUser);
            } else if (role.equals("Doctor")) {
                // runDoctorMenu(scanner, (Doctor) currentUser);
            } else {
                System.out.println("Invalid role. Logging out.");
                running = false;
            }
        }

        scanner.close();
        System.out.println("Thank you for using the Hospital Management System.");
    }

    // Patient menu options
    private static void runPatientMenu(Scanner scanner, Patient patient) {
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
                    // Schedule appointment logic
                    break;
                case 3:
                    patient.viewMedicalRecord(); // Ensure you have this method
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

    // // Doctor menu options (implementation commented out for now)
    // private static void runDoctorMenu(Scanner scanner, Doctor doctor) {
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
    // // View patient records logic
    // break;
    // case 3:
    // System.out.println("Updating patient records...");
    // // Update patient records logic
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
