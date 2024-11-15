package menus;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import appointments.AppointmentManager;
import database.DoctorUnavailabilityDB;
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
            System.out.println("9. Logout");
            System.out.print("Enter the number corresponding to your choice: ");

            choice = getValidMenuChoice(1, 9);

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

        } while (choice != 9);
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
        List<String> receivedList;

        while (!returnToMenu) {
            System.out.println("Viewing available appointment slots...");

            // List of doctors
            System.out.println("List of Doctors:");
            receivedList = appointmentManager.getAllAvailableDoctors();

            // Print doctors
            for (int i = 0; i < receivedList.size(); i++) {
                System.out.println((i + 1) + ". " + receivedList.get(i));
            }

            // Get the selected doctor index
            System.out.print("Enter the number corresponding to the doctor you want to view: ");
            int selectedDoctorIndex = getValidMenuChoice(1, receivedList.size()) - 1;
            String doctorId = receivedList.get(selectedDoctorIndex).split(" - ")[1].trim();

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

    // Method to schedule an appointment (asks for doctor ID and slot)
    private void scheduleAppointment() {
        String input = "";

        System.out.println("\nScheduling an appointment...");

        // Ask the user to select a doctor ID (prompt for doctor selection if needed)
        System.out.print("Please enter the doctor ID: ");
        input = scanner.nextLine().trim();
        String doctorId = input; // You could also validate the doctorId here based on available doctors

        // Ask the user to select a slot
        List<String> availableSlots = appointmentManager.getAvailableSlots(doctorId, LocalDate.now());
        printSlotsWithNumber(availableSlots);
        System.out.print("Please select a slot (1 to " + (availableSlots.size() - 1) + "): ");
        int selectedSlotIndex = getValidMenuChoice(1, availableSlots.size());

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
        System.out.print("Enter new Appointment Date (YYYY-MM-DD): ");
        String newDate = scanner.nextLine().trim();
        System.out.print("Enter new Appointment Time (HH:MM): ");
        String newTime = scanner.nextLine().trim();

        // boolean success = appointmentManager.rescheduleAppointment(appointmentId,
        // newDate, newTime);

        // if (success) {
        // System.out.println("Appointment rescheduled successfully.");
        // } else {
        // System.out.println("Failed to reschedule the appointment. Please try
        // again.");
        // }
    }

    private void cancelAppointment() {
        // System.out.println("Canceling an appointment...");
        // System.out.print("Enter Appointment ID: ");
        // String appointmentId = scanner.nextLine().trim();

        // boolean success = appointmentManager.cancelAppointment(appointmentId);

        // if (success) {
        // System.out.println("Appointment canceled successfully.");
        // } else {
        // System.out.println("Failed to cancel the appointment. Please try again.");
        // }
    }

    private void viewScheduledAppointments() {
        System.out.println("Viewing scheduled appointments...");
        // // Implement logic to display scheduled appointments
        // System.out.println(appointmentManager.getPatientAppointments(patient.getId()));
    }

    private void viewPastAppointmentOutcomeRecords() {
        System.out.println("Viewing past appointment outcome records...");
        // Implement logic to display past appointment outcomes
    }
}
