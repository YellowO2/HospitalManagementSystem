package menus;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import appointments.DoctorUnavailableSlots;
import database.DoctorUnavailabilityDB;
import managers.AppointmentManager;
import managers.AppointmentOutcomeManager;
import managers.MedicalRecordManager;
import database.UserDB;
import medicalrecords.Diagnosis;
import medicalrecords.MedicalRecord;
import medicalrecords.Prescription;
import medicalrecords.Treatment;
import menus.utils.ValidationUtils;
import users.Doctor;

/**
 * The DoctorMenu class represents the menu interface for a doctor, allowing them to
 * interact with patient medical records, manage their schedule, set availability, and
 * record appointment outcome record.
 */
public class DoctorMenu {
    private Doctor doctor;
    private Scanner scanner;
    private AppointmentManager appointmentManager;
    private AppointmentOutcomeManager appointmentOutcomeManager;
    private DoctorUnavailabilityDB doctorUnavailabilityDB;
    private MedicalRecordManager medicalRecordManager;
    private UserDB userDB;

    /**
     * Constructs a new DoctorMenu with the specified dependencies.
     * 
     * @param doctor                The doctor associated with this menu.
     * @param appointmentManager    The AppointmentManager to handle appointments.
     * @param appointmentOutcomeManager The AppointmentOutcomeManager for handling appointment outcomes.
     * @param medicalRecordManager The MedicalRecordManager to manage patient medical records.
     * @param doctorUnavailabilityDB The DoctorUnavailabilityDB to manage doctor unavailability slots.
     * @param userDB                The UserDB for handling user information.
     */
    public DoctorMenu(Doctor doctor, AppointmentManager appointmentManager, AppointmentOutcomeManager appointmentOutcomeManager,
        MedicalRecordManager medicalRecordManager, DoctorUnavailabilityDB doctorUnavailabilityDB, UserDB userDB) {
        this.doctor = doctor;
        this.scanner = new Scanner(System.in);
        this.appointmentManager = appointmentManager;
        this.appointmentOutcomeManager = appointmentOutcomeManager;
        this.medicalRecordManager = medicalRecordManager;
        this.doctorUnavailabilityDB = doctorUnavailabilityDB;
        this.userDB = userDB;
    }

    /**
     * Displays the doctor menu and handles user input for different actions.
     */
    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Confirm or Cancel Appointment Requests");
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

    /**
     * Allows the doctor to view a patient's medical record by entering the patient's ID
     */
    private void viewPatientMedicalRecords() {
        String patientId, medicalHistory, patientIdFromAppointment;
        List<String> patientList, patientIds;
        String[] patienttDetails;

        System.out.println("Patients under your care:");
        patientList = appointmentManager.getDoctorAppointments(doctor.getId(), "Confirmed");

        if (patientList.isEmpty() || patientList == null){
            System.out.println("You have no patients under your care.\nExiting to Doctor Menu...");
            return;
        }

        // Display the list of patients the doctor is seeing based on the appointment list
        patientIds = new ArrayList<>();
        for (String patient : patientList) {
            patienttDetails = patient.split(",");
            patientIdFromAppointment = patienttDetails[2];

            if (!patientIds.contains(patientIdFromAppointment)) {
                patientIds.add(patientIdFromAppointment);
                System.out.println("Patient Name: " + userDB.getById(patientIdFromAppointment).getName() + "\t\t[" + patientIdFromAppointment + "]");
            }
        }

        while(true){
            System.out.print("Enter the patient ID to view their medical record or type 'back' to return to the menu: ");
            patientId = scanner.nextLine().trim();

            if (patientId.equalsIgnoreCase("back")) {
                System.out.println("Returning to the Doctor Menu...");
                return;
            }

            if (!patientId.contains(patientId)) {
                System.out.println("Invalid patient ID or the patient is not under your care.");
                continue;
            }

            medicalHistory = medicalRecordManager.getMedicalHistory(patientId);

            if (medicalHistory == null) {
                System.out.println("No medical record found for " + patientId + ".");
            }

            else {
                System.out.println("\nViewing medical record for " + patientId + ":");
                System.out.println(medicalHistory);
            }
        }
    }

    /**
     * Allows the doctor to update a patient's medical record with a diagnosis, prescription,
     * and treatment. The user will be prompted for relevant information.
     */
    private void updatePatientMedicalRecords() {
        String patientId;
        boolean updated = false;

        System.out.print("Enter the patient ID to update their medical record: ");
        patientId = scanner.nextLine();

        /* Diagnosis */
        String severity;
        LocalDate diagnosisDate = LocalDate.now();
        String doctorName = doctor.getName();

        // Obtain diagnosis information
        System.out.print("Enter the diagnosis name: ");
        String diagnosisName = scanner.nextLine();

        while (true) {
            System.out.print("Enter the severity (Mild, Moderate, Severe): ");
            severity = scanner.nextLine();

            if (severity.equalsIgnoreCase("Mild") || severity.equalsIgnoreCase("Moderate")
                    || severity.equalsIgnoreCase("Severe")) {
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
                    if (amount > 0)
                        break; // Valid input, exit the loop
                    System.out.println("The quantity must be positive. Please enter a valid number.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value for the amount.");
                }
            }
            // Create the Prescription object
            prescription = new Prescription(medicationName, dosage, instructions, frequency, amount, status);
        }
        else {
            prescription = new Prescription("Nil", "Nil", "Nil", "Nil", 0, 0);
        }

        /* Treatment */
        Treatment treatment = null;

        // Obtain treatment information (Optional, skipped if input is No)
        System.out.print("Do you want to add a treatment? (Yes/No): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter treatment name: ");
            String treatmentName = scanner.nextLine();

            System.out.print("Enter treatment details: ");
            String treatmentDetails = scanner.nextLine();

            // Create the Treatment object, surely there will not be any error using
            // diagnosisDate right?
            treatment = new Treatment(treatmentName, diagnosisDate, doctorName, treatmentDetails);
        }
        else {
            treatment = new Treatment("Nil", diagnosisDate, doctorName, "Nil");
        }

        if (medicalRecordManager.getMedicalHistory(patientId) != null){
            updated = medicalRecordManager.updateMedicalRecord(patientId, diagnosis, prescription, treatment);
        } else {
            String dateOfBirth = userDB.getById(patientId).getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            MedicalRecord medicalRecord = new MedicalRecord(
                patientId, userDB.getById(patientId).getName(), dateOfBirth, userDB.getById(patientId).getGender(), "A+", userDB.getById(patientId).getPhoneNumber(), 
                userDB.getById(patientId).getEmailAddress(), diagnosis != null ? diagnosis.toString() : "Nil", treatment.toString(), prescription != null ? prescription.toString() : "Nil");
            updated = medicalRecordManager.addMedicalRecord(patientId, medicalRecord);
        }


        if (updated) {
            System.out.println("Medical record updated successfully.");
        } else {
            System.out.println("Failed to update medical record. Please check if the patient ID is correct.");
        }
    }

    /**
     * Validates the time format entered by the user.
     * 
     * @param timeStr The time string to validate.
     * @return true if the time string is in a valid format, false otherwise.
     */
    private static boolean isValidTime(String timeStr) {
        try {
            // Parse the time using the standard "HH:mm" format
            LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
            return true; // Input is valid
        } catch (DateTimeParseException e) {
            return false; // Input is invalid
        }
    }

    /**
     * Helper method to get a date from the user based on a chosen day of the week.
     * 
     * @return The LocalDate corresponding to the chosen day of the week.
     */
    private LocalDate getDayOfChoice() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int choice = -1;
        LocalDate today = LocalDate.now();
        LocalDate selectedDate;
        DayOfWeek selectedDay;

        while (true) {
            for (DayOfWeek day : DayOfWeek.values()) {
                LocalDate date = today.with(day);

                if (date.isBefore(today)) {
                    date = date.plusWeeks(1);
                }
                System.out.printf("%d. %s (%s)%n", day.getValue(),
                        day.name().substring(0, 1).toUpperCase() + day.name().substring(1).toLowerCase(),
                        date.format(formatter));
            }
            System.out.print("Enter the day (e.g., 1 for Monday, 2 for Tuesday): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();

            if (choice >= 1 || choice <= 7) {
                selectedDay = DayOfWeek.of(choice);
                selectedDate = today.with(selectedDay);

                if (selectedDate.isBefore(today)) {
                    selectedDate = selectedDate.plusWeeks(1);
                }
                return selectedDate;
            } else {
                System.out.println("Invalid choice. Please enter a valid day between 1 and 7.");
            }
        }
    }

    /**
     * Displays the doctor's personal schedule for a selected date.
     * 
     * @param selectedDate The date for which to display the schedule. If null, the user will be prompted to choose.
     */
    private void viewPersonalSchedule(LocalDate selectedDate) {
        List<String> scheduleList, appointmentList, filteredAppointments = new ArrayList<>();;
        String appointmentDetails, currentTime, previousTime;

        if (selectedDate == null) {
            System.out.println("Viewing personal schedule...");
            System.out.println("Select the day to view schedule:");
            selectedDate = getDayOfChoice();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Viewing personal schedule for: " + selectedDate.format(formatter));

        scheduleList = appointmentManager.getPersonalSchedule(doctor.getId(), selectedDate);
        appointmentList = appointmentManager.getDoctorAppointments(doctor.getId(), "Confirmed");

        for (String appointment : appointmentList) {
            String[] parts = appointment.split(",");
            String appointmentDateString = parts[3].trim(); // Date of the appointment
            try {
                LocalDate appointmentDate = LocalDate.parse(appointmentDateString); // Convert to LocalDate
                if (appointmentDate.equals(selectedDate)) {
                    filteredAppointments.add(appointment); // Add to the filtered list if the dates match
                }
            } catch (Exception e) {
                System.out.println("Error parsing date: " + appointmentDateString);
            }
        }

        Map<String, String> appointmentMap = new HashMap<>();
        for (String appointment : filteredAppointments) {
            String[] parts = appointment.split(",");
            String appointmentTime = parts[4];  // Time at index 4
            String patientId = parts[2];        // Patient id at index 2
            String patientName = userDB.getById(patientId).getName();
            appointmentMap.put(appointmentTime, "Appointment with " + patientName);
        }

        if (scheduleList == null) {
            System.out.println("Your schedule list has not been created.");
        }

        else if (scheduleList.isEmpty()) {
            System.out.println("It is your day off.");
        } else {
            previousTime = scheduleList.get(0);
            currentTime = LocalTime.parse(previousTime, DateTimeFormatter.ofPattern("HH:mm")).plusHours(1)
                    .format(DateTimeFormatter.ofPattern("HH:mm"));
            appointmentDetails = appointmentMap.getOrDefault(previousTime, "");
            System.out.printf("%s - %s\t%s%n", previousTime, currentTime, appointmentDetails);

            if (scheduleList.size() > 1) {
                for (int i = 1; i < scheduleList.size() - 1; i++) {
                    previousTime = scheduleList.get(i);
                    currentTime = scheduleList.get(i + 1);
                    appointmentDetails = appointmentMap.getOrDefault(previousTime, "");
                    System.out.printf("%s - %s\t%s%n", previousTime, currentTime, appointmentDetails);
                }
            }
        }
    }

    /**
     * Allows the doctor to set their availability for appointments by selecting a time range on a specific date.
     */
    private void setAvailabilityForAppointments() {
        boolean validTimeRange = false;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalDate selectedDate;
        LocalTime roundedStartTime, roundedEndTime, currenTime, nextTime, unavailabilityStart, unavailabilityEnd;
        String startTimeStr, endTimeStr;
        List<String> appointments, selectedDayOfAppointment;

        roundedStartTime = null;
        roundedEndTime = null;

        System.out.println("Select the day to set availability:");
        selectedDate = getDayOfChoice();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Setting availability for: " + selectedDate.format(formatter));

        viewPersonalSchedule(selectedDate);
        scanner.nextLine();// Consume newline

        appointments = appointmentManager.getDoctorAppointments(doctor.getId(), "Confirmed");

        selectedDayOfAppointment = appointments.stream().filter(appointment -> LocalDate.parse(appointment.split(",")[3]).equals(selectedDate))
                                                        .collect(Collectors.toList());
 
        while (!validTimeRange) {
            System.out.println("Enter the time range you are unavailability on this day (e.g., 09:00 - 12:00), or type 'back' to return:");
            String timeRange = scanner.nextLine().trim();

            if (timeRange.equalsIgnoreCase("back")){
                System.out.println("\nReturning to the Doctor Menu...");
                return; // Exit the method to go back to the previous menu
            }

            String[] times = timeRange.split(" - ");
            if (times.length != 2) {
                System.out.println("Invalid input format. Please enter the time range in the format HH:MM - HH:MM.");
                continue;
            }

            startTimeStr = times[0].trim();
            endTimeStr = times[1].trim();

            if (!isValidTime(startTimeStr) || !isValidTime(endTimeStr)) {
                System.out.println("Invalid time format. Please use the format HH:MM (e.g., 09:00).");
                continue;
            }

            if (Integer.parseInt(startTimeStr.replace(":", "")) >= Integer.parseInt(endTimeStr.replace(":", ""))) {
                System.out.println("Start time must be earlier than end time. Please enter a valid range.");
                continue;
            }

            roundedStartTime = LocalTime.parse(startTimeStr).truncatedTo(ChronoUnit.HOURS);
            if (LocalTime.parse(startTimeStr).getMinute() >= 1) {
                roundedStartTime = roundedStartTime.plusHours(1);
            }

            roundedEndTime = LocalTime.parse(endTimeStr).truncatedTo(ChronoUnit.HOURS);
            if (LocalTime.parse(endTimeStr).getMinute() >= 1) {
                roundedEndTime = roundedEndTime.plusHours(1);
            }

            validTimeRange = true;
            for (String appointment : selectedDayOfAppointment){
                String[] appointmentDetails = appointment.split(",");
                LocalTime appointmentStartTime = LocalTime.parse(appointmentDetails[4]);
                LocalTime appointmentEndTime = appointmentStartTime.plusHours(1);

                if (roundedStartTime.equals(appointmentStartTime) && roundedEndTime.equals(appointmentEndTime) ||
                    roundedStartTime.isBefore(appointmentStartTime) && roundedEndTime.isBefore(appointmentEndTime) ||
                    roundedStartTime.isAfter(appointmentStartTime) && roundedEndTime.isBefore(appointmentEndTime) || 
                    roundedStartTime.equals(appointmentStartTime) && roundedEndTime.isAfter(appointmentEndTime)){
                        System.out.println("Warning: The unavailability period overlaps with an exisiting appointment from " + appointmentStartTime + " to " + appointmentEndTime + ". Please choose another time range.");
                        validTimeRange = false;
                        break;
                }
            }
        }

        currenTime = roundedStartTime;
        while (currenTime.isBefore(roundedEndTime)) {
            nextTime = currenTime.plusHours(1);
            DoctorUnavailableSlots doctorUnavailableSlots = new DoctorUnavailableSlots(doctor.getId(), selectedDate,
                    currenTime);
            doctorUnavailabilityDB.create(doctorUnavailableSlots);

            currenTime = nextTime;
        }

        System.out.println("Unavailability for " + roundedStartTime + " - " + roundedEndTime + " updated successfully.");
    }

    /**
     * Helper method used by other methods to display appointment details in a readable format.
     * 
     * @param appointments List of appointment strings to display.
     */    private void displayAppointments(List<String> appointments) {
        for (String appointment : appointments) {
            String[] details = appointment.split(",");
            String appointmentId = details[0].trim();
            String doctorId = details[1].trim();
            String patientId = details[2].trim();
            String appointmentDate = details[3].trim();
            String appointmentTime = details[4].trim();
            String status = details[5].trim();

            // Print the appointment details in a nicer format
            System.out.println("\nAppointment ID: " + appointmentId);
            System.out.println("Doctor ID: " + doctorId);
            System.out.println("Patient ID: " + patientId);
            System.out.println("Date: " + appointmentDate);
            System.out.println("Time: " + appointmentTime);
            System.out.println("Status: " + status);
            System.out.println("------------------------");
        }
    }

    /**
     * Allows the doctor to confirm or cancel appointment requests.
     * Displays a list of pending appointments, prompts the doctor to choose an appointment,
     * and allows them to either confirm or cancel the appointment.
     */
    private void acceptOrDeclineAppointmentRequests() {
        boolean yourAppointments = false;
        List<String> appointments;
        String[] parts;
        String appointmentId;

        System.out.println("Confirming or cancelling appointment requests...");

        while (true) {
            appointments = appointmentManager.getDoctorAppointments(doctor.getId(), "Pending");

            if (appointments.isEmpty()) {
                System.out.println("No scheduled appointments found.");
                return;
            } else {
                displayAppointments(appointments);
            }

            System.out.print("Choose an appointment to confirm or cancel (enter the appointment number or type 'back' to return to the menu): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("back")) {
                System.out.println("\nReturning to the Doctor Menu...");
                break;
            }

            for (String appointment : appointments){
                parts = appointment.split(",");
                appointmentId = parts[0];

                if (appointmentId.equals(input)){
                    yourAppointments = true;
                    break;
                }
            }

            if (!appointmentManager.isValidAppointmentId(input)) {
                System.out.println("Invalid Appointment ID. Please enter a valid Appointment ID.");
                continue;
            }

            if(!yourAppointments){
                System.out.println("This appointment is not associated with your current patient list. Please choose a valid appointment under your care.");
                continue;
            }

            System.out.println("\nAppointment ID: " + input + " selected.");
            System.out.println("\nDo you wish to:\n1.Confirm this appointment\n2.Cancel this appointment.\n3.Return to list of Pending appointment");

            while (true) {
                System.out.print("\nEnter your choice (1, 2 or 3): ");
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        if (appointmentManager.updateAppointmentStatus(input, "Confirmed")) {
                            System.out.println("You have confirmed the appointment.");
                        } else {
                            System.out.println("There was an error confirming the appointment.");
                        }
                        // return;
                        break;
                    case "2":
                        if (appointmentManager.updateAppointmentStatus(input, "Cancelled")) {
                            System.out.println("You have cancelled the appointment.");
                        } else {
                            System.out.println("There was an error cancelling the appointment.");
                        }
                        // return;
                        break;
                    case "3":
                        System.out.println("Returning to the list...");
                        //return;
                        break;
                    default:
                        System.out.println("Invalid input. Please choose 1, 2 or 3.");
                        // break;
                        continue;
                }
                break;
            }
        }
    }

    /**
     * Displays a list of upcoming confirmed appointments for the doctor.
     */
    private void viewUpcomingAppointments() {
        System.out.println("Viewing upcoming appointments...");

        List<String> appointments = appointmentManager.getDoctorAppointments(doctor.getId(), "Confirmed");

        if (appointments.isEmpty()) {
            System.out.println("No scheduled appointments found.");
        } else {
            displayAppointments(appointments);
        }
    }

    /**
     * Allows the doctor to record the outcome of today's appointments.
     * Displays a list of appointments for the day, and prompts the doctor to record details
     * including the service provided, prescriptions, and consultation notes.
     */
    private void recordAppointmentOutcome() {
        LocalDate todaysDate = LocalDate.now();
        String input, prescription, patientId = null;

        System.out.println("Recording the outcome of today's appointments...");

        List<String> appointments = appointmentManager.getDoctorAppointments(doctor.getId(), "Confirmed");

        List<String> todaysAppointment = appointments.stream()
                .filter(appointment -> LocalDate.parse(appointment.split(",")[3]).equals(todaysDate))
                .collect(Collectors.toList());

        if (todaysAppointment.isEmpty()) {
            System.out.println("You have no appointments to record for today.");
            return;
        }

        for (int i = 0; i < todaysAppointment.size(); i++) {
            String appointment = todaysAppointment.get(i);
            String[] parts = appointment.split(",");
            String appointmentId = parts[0];
            patientId = parts[2];
            String appointmentTime = parts[4];

            System.out.println(
                    "Appointment ID: " + appointmentId + ", Patient ID: " + patientId + ", Time: " + appointmentTime);
        }

        while (true) {
            System.out.print(
                    "\nChoose an appointment to record the outcome (enter the appointment number or type 'back' to return to the menu): ");
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("back")) {
                System.out.println("\nReturning to the Doctor Menu...");
                return;
            }

            if (!appointmentManager.isValidAppointmentId(input)) {
                System.out.println("Invalid Appointment ID. Please enter a valid Appointment ID.");
                // return;
            } else {
                break;
            }
        }

        System.out.println("Recording appointment outcome...");

        // Prompt for the service provided
        System.out.print("Enter the Service provided: ");
        String serviceProvided = scanner.nextLine().trim();

        // Prompt for the prescription
        while (true) {
            System.out.print("Enter prescription details in the following format:\n" +
                 "<Medication Name>|<Dosage>|<Dosage Frequency>|<Amount>|<Instructions>|<Status>\n" +
                 "Example: Paracetamol|250mg|1 time per day|15|Take after meals|1\n" +
                 "Separate each parameter with '|' and multiple prescriptions with ';' if needed:\n");
            prescription = scanner.nextLine().trim();

            String[] prescriptionDetails = prescription.split("\\|");

            // Check if the input has exactly 6 parameters
            if (prescriptionDetails.length == 6) {
                break;
            } else {
                System.out.println("Invalid input. Please enter exactly 6 parameters separated by '|'.");
            }
        }

        // Prompt for consultation notes
        System.out.print("Enter the consultation notes: ");
        String consultationNotes = scanner.nextLine().trim();

        // Call the recordOutcomeRecord method to store the outcome
        boolean success = appointmentOutcomeManager.recordAppointmentOutcome(input, patientId, todaysDate,
                serviceProvided, prescription, "Pending", consultationNotes);

        // Provide feedback to the doctor
        if (success) {
            System.out.println("Appointment outcome recorded successfully.");
        } else {
            System.out.println("Failed to record appointment outcome. Please try again.");
        }
    }

    /**
     * Changes the pharmacist's password.
     */
    private void changePassword() {
        System.out.println("Changing password...");
        String newPassword = ValidationUtils.getValidPassword(scanner);

        doctor.changePassword(newPassword);
        boolean success = userDB.update(doctor);
        if (success) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Error: Failed to change password.");
        }
    }
}
