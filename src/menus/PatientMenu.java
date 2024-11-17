package menus;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import managers.AppointmentManager;
import managers.AppointmentOutcomeManager;
import managers.MedicalRecordManager;
import users.Patient;

public class PatientMenu {
    private Patient patient;
    private Scanner scanner;
    private MedicalRecordManager medicalRecordManager;
    private AppointmentManager appointmentManager;
    private AppointmentOutcomeManager appointmentOutcomeManager;

    public PatientMenu(Patient patient, MedicalRecordManager medicalRecordManager,
            AppointmentManager appointmentManager, AppointmentOutcomeManager appointmentOutcomeManager) {
        this.patient = patient;
        this.scanner = new Scanner(System.in);
        this.medicalRecordManager = medicalRecordManager;
        this.appointmentManager = appointmentManager;
        this.appointmentOutcomeManager = appointmentOutcomeManager;
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n===== Patient Menu =====");
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
                    appointmentOutcomeManager.viewPatientOutcomeRecords(patient.getId());
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

    private int selectDaySlot() {
        System.out.print("Please select a day (1 to 7): ");
        return getValidMenuChoice(1, 7) - 1;
    }

    private int selectTimeSlot(List<LocalTime> availableSlots) {
        System.out.println("Available Time Slots:");
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.println((i + 1) + ". " + availableSlots.get(i));
        }
        System.out.print("Please select a time slot (1 to " + availableSlots.size() + "): ");
        return getValidMenuChoice(1, availableSlots.size()) - 1;
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

        if (choice == 1) {
            System.out.print("Enter new email: ");
            String newEmail = scanner.nextLine().trim();
            medicalRecordManager.updateContactInfo(patient.getId(), patient.getPhoneNumber(), newEmail);
            patient.setEmailAddress(newEmail);
        } else if (choice == 2) {
            System.out.print("Enter new phone number: ");
            String newPhoneNo = scanner.nextLine().trim();
            medicalRecordManager.updateContactInfo(patient.getId(), newPhoneNo, patient.getEmailAddress());
            patient.setPhoneNumber(newPhoneNo);
        }

        System.out.println("Personal information updated successfully.");
    }

    private void viewAvailableAppointmentSlots() {
        boolean returnToMenu = false;

        while (!returnToMenu) {
            System.out.println("Viewing available appointment slots...");

            String doctorId = selectDoctor();
            appointmentManager.showAvailableSlots(doctorId, LocalDate.now());

            System.out.println("Return to menu? (Y/N)");
            String choice = scanner.nextLine().trim().toUpperCase();
            returnToMenu = choice.equals("Y");
        }
    }

    private String selectDoctor() {
        System.out.println("\nSelect a doctor...");

        List<String> doctors = appointmentManager.getAllAvailableDoctors();
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". " + doctors.get(i));
        }
        System.out.print("Please enter the number corresponding to the doctor: ");
        int selectedDoctorIndex = getValidMenuChoice(1, doctors.size()) - 1;

        return doctors.get(selectedDoctorIndex).split(" - ")[1].trim();
    }

    private void selectAndScheduleDoctorSlot(String doctorId, LocalDate date) {
        appointmentManager.showAvailableSlots(doctorId, date);

        System.out.println("\nSelect an available slot ... ");

        int selectedDayIndex = selectDaySlot();
        LocalDate selectedDay = date.plusDays(selectedDayIndex);
        List<LocalTime> availableSlots = appointmentManager.getAvailableSlotsForDoctor(doctorId, selectedDay);

        if (availableSlots.isEmpty()) {
            System.out.println("No available slots for the selected doctor on this date.");
            return;
        }

        int selectedTimeSlotIndex = selectTimeSlot(availableSlots);

        System.out.println("Selected Day Index: " + selectedDayIndex);
        System.out.println("Selected Time Slot Index: " + selectedTimeSlotIndex);

        boolean success = appointmentManager.scheduleAppointment(patient.getId(), doctorId, selectedDay,
                selectedTimeSlotIndex);
        if (success) {
            System.out.println("Appointment scheduled successfully.");
        } else {
            System.out.println("Failed to schedule the appointment.");
        }
    }

    private void scheduleAppointment() {
        System.out.println("\nScheduling an appointment...");

        String doctorId = selectDoctor();

        selectAndScheduleDoctorSlot(doctorId, LocalDate.now());
    }

    private void rescheduleAppointment() {
        System.out.println("Rescheduling an appointment...");
        System.out.print("Enter old Appointment ID: ");
        String appointmentId = scanner.nextLine().trim();

        String newDoctorID = selectDoctor();

        appointmentManager.showAvailableSlots(newDoctorID, LocalDate.now());
        System.out.println("==== Select a new appointment ====");

        int selectedDayIndex = selectDaySlot();
        LocalDate newDate = LocalDate.now().plusDays(selectedDayIndex);
        // System.out.println("DEBUG: THIS PRINTED");
        int selectedTimeSlotIndex = selectTimeSlot(appointmentManager.getAvailableSlotsForDoctor(newDoctorID, newDate));

        appointmentManager.rescheduleAppointment(newDoctorID, appointmentId, newDate, selectedTimeSlotIndex);
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
                String[] details = appointment.split(",");
                String appointmentId = details[0].trim();
                String doctorId = details[1].trim();
                String patientId = details[2].trim();
                String date = details[3].trim();
                String time = details[4].trim();
                String status = details[5].trim();

                System.out.println("\nAppointment ID: " + appointmentId);
                System.out.println("Doctor: " + appointmentManager.getDoctorById(doctorId).getName());
                System.out.println("Patient ID: " + patientId);
                System.out.println("Date: " + date);
                System.out.println("Time: " + time);
                System.out.println("Status: " + status);
                System.out.println("------------------------");
            }
        }
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
