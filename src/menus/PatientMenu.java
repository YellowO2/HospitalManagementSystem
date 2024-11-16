package menus;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

import appointments.AppointmentManager;
import medicalrecords.MedicalRecordManager;
import users.Patient;

public class PatientMenu {
    private Patient patient;
    private Scanner scanner;
    private MedicalRecordManager medicalRecordManager;
    private AppointmentManager appointmentManager;

    public PatientMenu(Patient patient, MedicalRecordManager medicalRecordManager,
            AppointmentManager appointmentManager) {
        this.patient = patient;
        this.scanner = new Scanner(System.in);
        this.medicalRecordManager = medicalRecordManager;
        this.appointmentManager = appointmentManager;
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== Patient Menu ===");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Change Password");
            System.out.println("10. Logout");
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
                    changePassword();
                    break;
                case 10:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 10);
    }

    private int getValidMenuChoice(int min, int max) {
        int choice;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } else {
                scanner.next(); // Consume invalid input
            }
            System.out.print("Invalid choice. Please enter a number between " + min + " and " + max + ": ");
        }
    }

    private void viewMedicalRecord() {
        System.out.println("Viewing medical record for " + patient.getName());
        System.out.println(medicalRecordManager.getMedicalHistory(patient.getId()));
    }

    private void updatePersonalInformation() {
        System.out.println("Do you want to update your email or phone number?");
        System.out.println("1. Email");
        System.out.println("2. Phone Number");

        int choice = getValidMenuChoice(1, 2);

        String newValue = null;
        if (choice == 1) {
            System.out.print("Enter new email: ");
            newValue = scanner.nextLine().trim();

            // ToFix: This is updating Medical_Record csv, it also needs to update User_List
            medicalRecordManager.updateContactInfo(patient.getId(), null, newValue);
        } else if (choice == 2) {
            System.out.print("Enter new phone number: ");
            newValue = scanner.nextLine().trim();
            medicalRecordManager.updateContactInfo(patient.getId(), newValue, null);
        }

        System.out.println("Personal information updated successfully.");
    }

    private void viewAvailableAppointmentSlots() {
        boolean returnToMenu = false;
        String input = "";

        while (!returnToMenu) {
            System.out.println("Viewing available appointment slots...");

            // List of doctors
            String doctorId = selectDoctor();

            // Display available slots for the selected doctor
            List<String> availableSlots = appointmentManager.getAvailableSlots(doctorId, LocalDate.now());
            printSlotsWithNumber(availableSlots);

            // Ask if the user wants to select a slot or return to the menu
            System.out.print(
                    "Would you like to view appointments for another doctor or return to the menu? (Enter 'another' or 'back'):");
            input = scanner.nextLine().trim();

            if (input.equals("back")) {
                returnToMenu = true;
                System.out.println("\nReturning to the Patient Menu...");
            } else if (input.equals("another")) {
                // If they want to see another doctor's slots, continue the loop
                System.out.println("Choosing another doctor...");
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private String selectDoctor() {
        System.out.println("\nSelect a doctor...");

        // Ask the user to select a doctor
        List<String> doctors = appointmentManager.getAllAvailableDoctors();
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". " + doctors.get(i));
        }
        System.out.print("Please enter the number corresponding to the doctor: ");
        int selectedDoctorIndex = getValidMenuChoice(1, doctors.size()) - 1;

        // Extract doctorId from the selected doctor
        String doctorId = doctors.get(selectedDoctorIndex).split(" - ")[1].trim();
        return doctorId;
    }

    private int selectDoctorSlot(String doctorId, LocalDate date) {
        System.out.println("\nSelect an available time slot for doctor " + doctorId + "...");

        // Ask the user to select a time slot
        List<String> availableSlots = appointmentManager.getAvailableSlots(doctorId, date);
        printSlotsWithNumber(availableSlots);

        System.out.print("Please select a slot (1 to " + availableSlots.size() + "): ");
        int selectedSlotIndex = getValidMenuChoice(1, availableSlots.size()) - 1;

        // Return the selected slot
        return selectedSlotIndex;
    }

    // Method to schedule an appointment (asks for doctor ID and slot)
    private void scheduleAppointment() {
        String input = "";
        String doctorId = "";

        System.out.println("\nScheduling an appointment...");

        while(true){
            // Ask the user to select a doctor ID (prompt for doctor selection if needed)
            System.out.print("Please enter the doctor ID: ");
            input = scanner.nextLine().trim();

            if (appointmentManager.isValidDoctorId(input)){
                doctorId = input;
                break;
            }

            else {
                System.out.println("Invalid input. Please enter a valid doctor ID.");
            }
        }

        // TODO: Maybe replace current date to by dynamic
        int selectedSlotIndex = selectDoctorSlot(doctorId, LocalDate.now());
        System.out.println("DEBUG newSlot: " + selectedSlotIndex);
        try {
            if (appointmentManager.scheduleAppointment(patient.getId(), doctorId,
                    LocalDate.now().toString(), selectedSlotIndex)) {
                System.out.println("Appointment scheduled successfully.");
            } else {
                System.out.println("Failed to schedule the appointment. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private void printSlotsWithNumber(List<String> availableSlots) {
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots.");
        } else {
            // -1 to not include last time, which is the ending time
            for (int i = 0; i < availableSlots.size() - 1; i++) {
                String currentTime = availableSlots.get(i);
                String nextTime = i + 1 < availableSlots.size() ? availableSlots.get(i + 1) : null;

                System.out.println((i + 1) + ". " + currentTime + " - " + (nextTime != null ? nextTime : "N/A"));
            }
        }
        System.out.println();
    }

    private void rescheduleAppointment() {
        System.out.println("Rescheduling an appointment...");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.nextLine().trim();
        String newDoctorID = selectDoctor();

        System.out.print("Enter new date to reschedule to (YYYY-MM-DD): ");
        String newDate = scanner.nextLine().trim();

        int newSlot = selectDoctorSlot(newDoctorID, LocalDate.parse(newDate));
        System.out.println("DEBUG newSlot: " + newSlot);
        boolean success = appointmentManager.rescheduleAppointment(appointmentId, newDate, newSlot);

        if (success) {
            System.out.println("Appointment rescheduled successfully.");
        } else {
            System.out.println("Failed to reschedule the appointment. Please try again.");
        }
    }

    private void cancelAppointment() {
        System.out.println("Canceling an appointment...");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.nextLine().trim();

        boolean success = appointmentManager.cancelAppointment(appointmentId);

        if (success) {
            System.out.println("Appointment canceled successfully.");
        } else {
            System.out.println("Failed to cancel the appointment. Please try again.");
        }
    }

    private void viewScheduledAppointments() {
        System.out.println("Viewing scheduled appointments...");

        List<String> appointments = appointmentManager.getPatientAppointments(patient.getId());

        if (appointments.isEmpty()) {
            System.out.println("No scheduled appointments found.");
        } else {
            for (String appointment : appointments) {
                // Split the appointment details by comma
                String[] details = appointment.split(",");

                // Assuming the format is: appointmentId, doctorId, patientId, date, time,
                // status
                String appointmentId = details[0].trim();
                String doctorId = details[1].trim();
                String patientId = details[2].trim();
                String date = details[3].trim();
                String time = details[4].trim();
                String status = details[5].trim();

                // Print the appointment details in a nicer format
                System.out.println("\nAppointment ID: " + appointmentId);

                // ToFix: Test Case wants doctor name
                // System.out.println("Doctor ID: " + doctorId);

                System.out.println("Patient ID: " + patientId);
                System.out.println("Date: " + date);
                System.out.println("Time: " + time);
                System.out.println("Status: " + status);
                System.out.println("------------------------");
            }
        }
    }

    private void viewPastAppointmentOutcomeRecords() {
        System.out.println("Viewing past appointment outcome records...");
        // Implement logic to display past appointment outcomes
    }

    private void changePassword() {
        System.out.println("Changing password...");
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();
        boolean success = patient.changePassword(newPassword);
        if (success) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Error: Failed to change password.");
        }
    }
}
