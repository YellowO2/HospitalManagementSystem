package menus;

import java.time.LocalDate;
import java.util.Scanner;

import medicalrecords.Diagnosis;
import medicalrecords.MedicalRecordManager;
import medicalrecords.Prescription;
import medicalrecords.Treatment;
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

    // To Do: Doctor can only update his own list of patients. To be implemented with database
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

        /* Diagnosis */
        String severity;
        LocalDate diagnosisDate = LocalDate.now();
        String doctorName = doctor.getName();

        // Obtain diagnosis information
        System.out.print("Enter the diagnosis name: ");
        String diagnosisName = scanner.nextLine();

        while (true){
            System.out.print("Enter the severity (Mild, Moderate, Severe): ");
            severity = scanner.nextLine();

            if (severity.equalsIgnoreCase("Mild") || severity.equalsIgnoreCase("Moderate") || severity.equalsIgnoreCase("Severe")) {
                break; // Valid input, exit the loop
            } else {
                System.out.println("Invalid input. Please enter either 'Mild', 'Moderate', or 'Severe'.");
            }
        }

        // Create the Diagnosis object
        Diagnosis diagnosis = new Diagnosis(diagnosisName, severity, diagnosisDate, doctorName);

        /* Prescription */
        Prescription prescription = null;

        // Obtain prescription information (Optional, skipped if input is No)
        System.out.print("Do you want to add a prescription? (Yes/No): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter medication name: ");
            String medicationName = scanner.nextLine();
        
            System.out.print("Enter the dosage to be taken per administration: ");
            String dosage = scanner.nextLine();
        
            System.out.print("Enter instructions for patient: ");
            String instructions = scanner.nextLine();
        
            System.out.print("Enter how often the medication should be taken: ");
            String frequency = scanner.nextLine();
        
            System.out.print("Enter the total quantity to prescribe: ");
            int amount;

            int status = 0;
            
            while (true) {
                try {
                    amount = Integer.parseInt(scanner.nextLine());
                    if (amount > 0) break; // Valid input, exit the loop
                    System.out.println("The quantity must be positive. Please enter a valid number.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value for the amount.");
                }
            }
            // Create the Prescription object
            prescription = new Prescription(medicationName, dosage, instructions, frequency, amount, status);
        }

        /* Treatment */
        Treatment treatment = null;

        // Obtain treatment information (Optional, skipped if input is No)
        System.out.print("Do you want to add a treatment? (Yes/No): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter treatment details: ");
            String treatmentName = scanner.nextLine();

            System.out.print("Enter treatment details: ");
            String treatmentDetails = scanner.nextLine();

            // Create the Treatment object, surely there will not be any error using diagnosisDate right?
            treatment = new Treatment(treatmentName, diagnosisDate, doctorName, treatmentDetails);
        }

        boolean updated = medicalRecordManager.updateMedicalRecord(patientId, diagnosis, prescription, treatment);

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
