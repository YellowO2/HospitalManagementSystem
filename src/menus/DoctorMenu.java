package menus;

import java.util.Scanner;

import medicalrecords.MedicalRecordManager;
import users.Doctor;
import users.Patient;

public class DoctorMenu {
    private Doctor doctor;
    private MedicalRecordManager medicalRecordManager;
    private Scanner scanner;

    // Constructor
    public DoctorMenu(Doctor doctor, MedicalRecordManager medicalRecordManager) {
        this.medicalRecordManager = medicalRecordManager;
        this.doctor = doctor;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println(); // Add a line break for spacing

            switch (choice) {
                case 1:
                    viewPatientMedicalRecords();
                    break;
                case 2:
                    updatePatientMedicalRecords();
                    break;
                case 3:
                    viewPersonalSchedule();
                    break;
                case 4:
                    setAvailabilityForAppointments();
                    break;
                case 5:
                    acceptOrDeclineAppointmentRequests();
                    break;
                case 6:
                    viewUpcomingAppointments();
                    break;
                case 7:
                    recordAppointmentOutcome();
                    break;
                case 8:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println(); // Add a line break after the action is completed

        } while (choice != 8);
    }

    // To Do: Doctor can only update his own list of patients. To be implemented
    // with database
    private void viewPatientMedicalRecords() {
        String patientId;

        System.out.print("Enter the patient ID to view their medical record: ");
        patientId = scanner.nextLine();

        System.out.println("Viewing medical record for " + patientId + ":");
        System.out.println(medicalRecordManager.getMedicalHistory(patientId));
    }

    private void updatePatientMedicalRecords() {
        String patientId;

        System.out.print("Enter the patient ID to update their medical record: ");
        patientId = scanner.nextLine();

        boolean updated = doctor.updatePatientMedicalRecords(patientId);

        if (updated) {
            System.out.println("Medical record updated successfully.");
        } else {
            System.out.println("Failed to update medical record. Please check if the patient ID is correct.");
        }
    }

    private void viewPersonalSchedule() {
        System.out.println("Viewing personal schedule...");
    }

    private void setAvailabilityForAppointments() {
        System.out.println("Setting availability for appointments...");
    }

    private void acceptOrDeclineAppointmentRequests() {
        System.out.println("Accepting or declining appointment requests...");
    }

    private void viewUpcomingAppointments() {
        System.out.println("Viewing upcoming appointments...");
    }

    private void recordAppointmentOutcome() {
        System.out.println("Recording appointment outcome...");
    }
}
