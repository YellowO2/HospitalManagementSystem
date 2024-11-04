// To Do: Uncomment after merging with git

package users;

import appointments.Appointment;
import medicalrecords.Diagnosis;
import medicalrecords.MedicalRecord;
import medicalrecords.MedicalRecordManager;
import medicalrecords.Prescription;
import medicalrecords.Treatment;
// import medicalrecords.MedicalRecordManger; 

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Doctor extends User {
    private List<Appointment> appointmentSchedule;
    // private MedicalRecordManager medicalRecordManager;

    // ID,Name,Date of Birth,Gender,Phone Number,Email Address,Password,Role
    public Doctor(String id, String name, String dateOfBirth, String gender, String phoneNumber,
            String emailAddress, String password) {
        super(id, name, "Doctor", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.appointmentSchedule = new ArrayList<>();
        // this.medicalRecordManager = medicalRecordManager;
    }

    public boolean updatePatientMedicalRecords(String patientId){
        Scanner scanner = new Scanner(System.in);

        /* Diagnosis */
        String severity;
        LocalDate diagnosisDate = LocalDate.now();
        String doctorName = this.getName();

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
        System.out.print("Do you want to add a treatment? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter treatment details: ");
            String treatmentName = scanner.nextLine();

            System.out.print("Enter treatment details: ");
            String treatmentDetails = scanner.nextLine();

            // Create the Treatment object, surely there will not be any error using diagnosisDate right?
            treatment = new Treatment(treatmentName, diagnosisDate, doctorName, treatmentDetails);
        }

        // Update the medical record
        // return medicalRecordManager.updateMedicalRecord(patientId, diagnosis, prescription, treatment);
        return false; // Placeholder return
    }

    // TODO: These methods needs to be moved into Medical Record Manager
    // public String viewPatientMedicalRecord(Patient patient){
    // return patient.getMedicalRecord().getMedicalRecordDescription();
    // }

    // public void addDiagnosis(Patient patient, Diagnosis diagnosis) { //"Roland" -
    // do we put in all the scanners for diagnosis in this function?
    // patient.getMedicalRecord().addDiagnosis(diagnosis); // Ensure method access
    // control
    // }

    // public void addPrescription(Patient patient, Prescription prescription) {
    // patient.getMedicalRecord().addPrescription(prescription); // Ensure method
    // access control
    // }

    // public void addTreatment(Patient patient, Treatment treatment) {
    // patient.getMedicalRecord().addTreatment(treatment); // Ensure method access
    // control
    // }

    // Work In Progress
    // public void acceptAppointment(Appointment appointment){
    // appointment.setStatus("Accepted");
    // }

    // public void declineAppointment(Appointment appointment) {
    // // Logic to decline the appointment and update the status
    // appointment.setStatus("Declined");
    // }

    // public List<Appointment> getUpcomingAppointments() {
    // return upcomingAppointments;
    // }
}
