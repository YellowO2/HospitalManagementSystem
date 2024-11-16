package menus;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import appointments.AppointmentManager;
import appointments.DoctorUnavailableSlots;
import database.DoctorUnavailabilityDB;
import medicalrecords.Diagnosis;
import medicalrecords.MedicalRecordManager;
import medicalrecords.Prescription;
import medicalrecords.Treatment;
import users.Doctor;
import users.Patient;

public class DoctorMenu {
    private Doctor doctor;
    private Scanner scanner;
    private MedicalRecordManager medicalRecordManager;
    private AppointmentManager appointmentManager;
    private DoctorUnavailabilityDB doctorUnavailabilityDB;

    // Constructor
    public DoctorMenu(Doctor doctor, MedicalRecordManager medicalRecordManager, AppointmentManager appointmentManager, DoctorUnavailabilityDB doctorUnavailabilityDB) {
        this.doctor = doctor;
        this.scanner = new Scanner(System.in);
        this.medicalRecordManager = medicalRecordManager;
        this.appointmentManager = appointmentManager;
        this.doctorUnavailabilityDB = doctorUnavailabilityDB;
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
            System.out.println("8. Change Password");
            System.out.println("9. Logout");
            System.out.print("Enter the number corresponding to your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();     // Consume newline
            System.out.println();   // Add a line break for spacing

            switch (choice) {
                case 1:
                    viewPatientMedicalRecords();
                    break;
                case 2:
                    updatePatientMedicalRecords();
                    break;
                case 3:
                    viewPersonalSchedule(null);
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
                    changePassword();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 9);
    }

    // TODO: Doctor can only update his own list of patients. To be implemented with database
    private void viewPatientMedicalRecords() {
        String patientId;
        String medicalHistory;

        System.out.print("Enter the patient ID to view their medical record: ");
        patientId = scanner.nextLine();

        medicalHistory = medicalRecordManager.getMedicalHistory(patientId);

        if (medicalHistory == null){
            System.out.println("No medical record found for " + patientId + ":");
        }

        else {
            System.out.println("Viewing medical record for " + patientId + ":");
            System.out.println(medicalHistory);
        }
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

    private static boolean isValidTime(String timeStr){
        try {
            // Parse the time using the standard "HH:mm" format
            LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
            return true; // Input is valid
        } catch (DateTimeParseException e) {
            return false; // Input is invalid
        }
    }

    private LocalDate getDayOfChoice() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int choice = -1;
        LocalDate today = LocalDate.now();
        LocalDate selectedDate;
        DayOfWeek selectedDay;

        while(true){
            for (DayOfWeek day : DayOfWeek.values()) {
                LocalDate date = today.with(day);

                if (date.isBefore(today)){
                    date = date.plusWeeks(1);
                }
                System.out.printf("%d. %s (%s)%n", day.getValue(), day.name().substring(0, 1).toUpperCase() + day.name().substring(1).toLowerCase(), date.format(formatter));
            }
            System.out.print("Enter the day (e.g., 1 for Monday, 2 for Tuesday): ");
            choice = scanner.nextInt();

            if (choice >= 1 || choice <= 7){
                selectedDay = DayOfWeek.of(choice);
                selectedDate = today.with(selectedDay);

                if (selectedDate.isBefore(today)){
                    selectedDate = selectedDate.plusWeeks(1);
                }
                return selectedDate;
            } else {
                System.out.println("Invalid choice. Please enter a valid day between 1 and 7.");
            }
        }
    }

    // TODO: Doctor's schedule only show the day itself (for now...)
    private void viewPersonalSchedule(LocalDate selectedDate) {
        List<String> scheduleList;
        String currentTime, previousTime;

        if (selectedDate == null){
            System.out.println("Viewing personal schedule...");
            System.out.println("Select the day to view schedule:");
            selectedDate = getDayOfChoice();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Viewing personal schedule for: " + selectedDate.format(formatter));
    
        scheduleList = appointmentManager.getAvailableSlots(doctor.getId(), selectedDate);

        if (scheduleList == null){
            System.out.println("How did this happen?");
        }
        // Assume that the Doctor_Unavailability.csv is completely populated for that day
        else if (scheduleList.isEmpty()){ 
            System.out.println("Today is your day off");
        }
        else {
            previousTime = scheduleList.get(0);
            currentTime = LocalTime.parse(previousTime, DateTimeFormatter.ofPattern("HH:mm")).plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm"));
            System.out.println(previousTime + " - " + currentTime);
            
            if (scheduleList.size() > 1){
                for (int i = 1; i < scheduleList.size() - 1; i++){
                    previousTime = scheduleList.get(i);
                    currentTime = scheduleList.get(i+1);
                    System.out.println(previousTime + " - " + currentTime);
                }
            }

        }

    }

    private void setAvailabilityForAppointments() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalDate selectedDate;
        LocalTime roundedStartTime, roundedEndTime, currenTime, nextTime;
        String startTimeStr, endTimeStr;

        System.out.println("Select the day to set availability:");
        selectedDate = getDayOfChoice();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Setting availability for: " + selectedDate.format(formatter));

        viewPersonalSchedule(selectedDate);
        scanner.nextLine(); // Consume newline

        while(true){
            System.out.println("Enter the time range you are unavailability on this day (e.g., 09:00 - 12:00):");
            String timeRange = scanner.nextLine().trim();

            String[] times = timeRange.split(" - ");
            if (times.length != 2){
                System.out.println("Invalid input format. Please enter the time range in the format HH:MM - HH:MM.");
                continue;
            }

            startTimeStr = times[0].trim();
            endTimeStr = times[1].trim();

            if (!isValidTime(startTimeStr) || !isValidTime(endTimeStr)){
                System.out.println("Invalid time format. Please use the format HH:MM (e.g., 09:00).");
                continue;
            }

            if (Integer.parseInt(startTimeStr.replace(":", "")) >= Integer.parseInt(endTimeStr.replace(":", ""))) {
                System.out.println("Start time must be earlier than end time. Please enter a valid range.");
                continue;
            }   
            break;      
        }

        roundedStartTime = LocalTime.parse(startTimeStr).truncatedTo(ChronoUnit.HOURS);
        if (LocalTime.parse(startTimeStr).getMinute() >= 1) {
            roundedStartTime = roundedStartTime.plusHours(1);
        }

        roundedEndTime = LocalTime.parse(endTimeStr).truncatedTo(ChronoUnit.HOURS);
        if (LocalTime.parse(endTimeStr).getMinute() >= 1) {
            roundedEndTime = roundedEndTime.plusHours(1);
        } 

        currenTime = roundedStartTime;
        while (currenTime.isBefore(roundedEndTime)){
            nextTime = currenTime.plusHours(1);
            DoctorUnavailableSlots doctorUnavailableSlots = new DoctorUnavailableSlots(doctor.getId(), selectedDate, currenTime);
            doctorUnavailabilityDB.create(doctorUnavailableSlots);

            currenTime = nextTime;
        }

        System.out.println("Unavailability for " + roundedStartTime + " - " + roundedEndTime + " updated successfully.");
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

    private void changePassword() {
        System.out.println("Changing password...");
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();
        boolean success = doctor.changePassword(newPassword);
        if (success) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Error: Failed to change password.");
        }
    }
}
