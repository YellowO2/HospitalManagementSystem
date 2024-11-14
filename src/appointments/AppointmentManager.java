package appointments;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import database.AppointmentDB;
import database.UserDB;
import appointments.Appointment;
import users.Doctor;
import users.Patient;
import medicalrecords.MedicalRecord;
import medicalrecords.Prescription;

public class AppointmentManager {
    private AppointmentDB appointmentDB; // Reference to the AppointmentDB
    private UserDB userDB; // Reference to UserDB

    // Constructor
    public AppointmentManager(AppointmentDB appointmentDB, UserDB userDB) {
        this.appointmentDB = appointmentDB;
        this.userDB = userDB;
    }

    // Method to show all doctors with their IDs
    public List<String> getAllAvailableDoctors() {
        List<Doctor> allDoctors = userDB.getAllDocters();
        List<String> doctorList = new ArrayList<>();

        for (Doctor doctor : allDoctors) {
            doctorList.add("Name: " + doctor.getName() + " - ID: " + doctor.getId() + "\n");
        }
        return doctorList;
    }

    // View available appointment slots for a specified doctor
    public List<String> viewAvailableSlots(String doctorId) {
        List<String> availableSlots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0); // Doctor's start time (example: 9:00 AM)
        LocalTime endTime = LocalTime.of(17, 0); // Doctor's end time (example: 5:00 PM)

        // Retrieve all appointments for the doctor
        List<Appointment> doctorAppointments = appointmentDB.getDoctorAppointments(doctorId);

        // Loop through time slots and check if they are booked
        for (LocalTime time = startTime; time.isBefore(endTime); time = time.plusHours(1)) {
            boolean isSlotBooked = false;

            for (Appointment appointment : doctorAppointments) {
                if (appointment.getAppointmentDate().equals(LocalDate.now())
                        && appointment.getAppointmentTime().equals(time)) {
                    isSlotBooked = true;
                    break;
                }
            }

            if (!isSlotBooked) {
                availableSlots.add("Time: " + time);
            }
        }

        if (availableSlots.isEmpty()) {
            System.out.println("Doctor ID: " + doctorId + " has no available slots.");
        }

        return availableSlots;
    }

    // Schedule a new appointment
    public boolean scheduleAppointment(String patientId, String doctorId, String date, String timeSlot) {
        String appointmentId;
        LocalDate appointmentDate = LocalDate.parse(date);
        LocalTime appointmentTime = LocalTime.parse(timeSlot);

        if (patientId == null || doctorId == null || date == null || timeSlot == null) {
            System.out.println("Invalid input. Please ensure all fields are filled.");
            return false;
        }

        // Retrieve all appointments for the doctor
        List<Appointment> doctorAppointments = appointmentDB.getDoctorAppointments(doctorId);

        // Check if the selected time slot is available
        boolean isSlotAvailable = true;
        for (Appointment appointment : doctorAppointments) {
            if (appointment.getAppointmentDate().equals(appointmentDate)
                    && appointment.getAppointmentTime().equals(appointmentTime)) {
                isSlotAvailable = false;
                break;
            }
        }

        if (!isSlotAvailable) {
            System.out.println("No available slots for the given date and time.");
            return false;
        }

        appointmentId = UUID.randomUUID().toString(); // Generate a unique appointment ID

        // Create the appointment
        Appointment appointment = new Appointment(appointmentId, doctorId, patientId, appointmentDate, appointmentTime,
                "Pending");

        // Save the appointment in the database
        boolean isAdded = appointmentDB.create(appointment);

        if (isAdded) {
            System.out.println("Appointment scheduled successfully for " + patientId + " with Doctor " + doctorId
                    + " on " + date + " at " + timeSlot);
            return true;
        } else {
            System.out.println("Failed to create the appointment.");
            return false;
        }
    }

    // Reschedule an existing appointment
    public boolean rescheduleAppointment(String appointmentId, String newDate, String newTimeSlot) {
        LocalDate appointmentDate = LocalDate.parse(newDate);
        LocalTime appointmentTime = LocalTime.parse(newTimeSlot);
        Appointment existingAppointment = appointmentDB.getById(appointmentId);

        if (existingAppointment == null) {
            System.out.println("Appointment not found.");
            return false;
        }

        String doctorId = existingAppointment.getDoctorId();
        String patientId = existingAppointment.getPatientId();

        // Retrieve all appointments for the doctor
        List<Appointment> doctorAppointments = appointmentDB.getDoctorAppointments(doctorId);

        // Check if the new time slot is available
        boolean isSlotAvailable = true;
        for (Appointment appointment : doctorAppointments) {
            if (appointment.getAppointmentDate().equals(appointmentDate)
                    && appointment.getAppointmentTime().equals(appointmentTime)) {
                isSlotAvailable = false;
                break;
            }
        }

        if (isSlotAvailable) {
            // Update the appointment
            existingAppointment.setAppointmentDate(appointmentDate);
            existingAppointment.setAppointmentTime(appointmentTime);
            existingAppointment.setStatus("Rescheduled");

            boolean isUpdated = appointmentDB.update(existingAppointment);

            if (isUpdated) {
                System.out.println("Appointment rescheduled successfully.");
                return true;
            } else {
                System.out.println("Failed to reschedule the appointment.");
                return false;
            }
        } else {
            System.out.println("The new appointment slot is not available.");
            return false;
        }
    }
}
