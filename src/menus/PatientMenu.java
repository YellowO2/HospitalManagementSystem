/*
 * The menu class should be responsible for displaying options and handling user input. 
 * This class can call methods from the Patient class to get data and display it.
 */

package menus;

import java.util.Scanner;
import users.Patient;

public class PatientMenu {
    private Patient patient; // The currently logged-in patient
    private Scanner scanner;

    // Constructor
    public PatientMenu(Patient patient) {
        this.patient = patient;
        this.scanner = new Scanner(System.in);
    }

    // Method to display the menu and handle user input
    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== Patient Menu ===");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println(); // Add a line break for spacing

            switch (choice) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    updatePersonalInformation();
                    break;
                case 3:
                    viewAvailableAppointmentSlots();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    rescheduleAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    viewScheduledAppointments();
                    break;
                case 8:
                    viewPastAppointmentOutcomeRecords();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println(); // Add a line break after the action is completed

        } while (choice != 9);
    }

    // Placeholder methods for each menu option
    private void viewMedicalRecord() {
        System.out.println("Viewing medical record for " + patient.getName());
        System.out.println(patient.getMedicalRecord().getMedicalRecordDescription());

    }

    private void updatePersonalInformation() {
        System.out.println("Do you want to update your email or phone number?");
        System.out.println("1. Email");
        System.out.println("2. Phone Number");

        int choice = -1;

        // Validate input for choice
        while (choice < 1 || choice > 2) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice < 1 || choice > 2) {
                    System.out.println("Please enter 1 for Email or 2 for Phone Number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number (1 or 2):");
                scanner.next(); // Consume invalid input
            }
        }

        String newValue = null;
        if (choice == 1) {
            System.out.print("Enter new email: ");
            newValue = scanner.nextLine().trim(); // Get and trim input
            patient.updatePersonalInformation(newValue, null); // Update email
        } else if (choice == 2) {
            System.out.print("Enter new phone number: ");
            newValue = scanner.nextLine().trim(); // Get and trim input
            patient.updatePersonalInformation(null, newValue); // Update phone number
        }

        System.out.println("Personal information updated successfully.");
    }

    private void viewAvailableAppointmentSlots() {
        System.out.println("Viewing available appointment slots...");
        // Implement logic to display available slots
    }

    private void scheduleAppointment() {
        System.out.println("Scheduling an appointment...");
        // Implement logic to schedule an appointment
    }

    private void rescheduleAppointment() {
        System.out.println("Rescheduling an appointment...");
        // Implement logic to reschedule an appointment
    }

    private void cancelAppointment() {
        System.out.println("Canceling an appointment...");
        // Implement logic to cancel an appointment
    }

    private void viewScheduledAppointments() {
        System.out.println("Viewing scheduled appointments...");
        // Implement logic to display scheduled appointments
    }

    private void viewPastAppointmentOutcomeRecords() {
        System.out.println("Viewing past appointment outcome records...");
        // Implement logic to display past appointment outcomes
    }
}
