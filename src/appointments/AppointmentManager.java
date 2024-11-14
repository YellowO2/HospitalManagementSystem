// TODO: See if it is possible to refactor the System.out.println to Menu
// Also thinking if the input should be checked by menu or AppointmentManager...
package appointments;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// import database.AppointmentDB;
import appointments.Appointment;
import users.Doctor;
import database.AppointmentDB;
import database.DoctorAvailabilityDB;
import database.UserDB;

public class AppointmentManager {
    // private AppointmentDB database; // Reference to the AppointmentDB
    private AppointmentDB appointmentDB;
    private DoctorAvailabilityDB doctorAvailabilityDB; // Reference to the DoctorAvailabilityDB
    private UserDB userDB;
    private Map<String, List<DoctorAvailability>> doctorAvailabilityMap;

    // Constructor
    public AppointmentManager(DoctorAvailabilityDB doctorAvailabilityDB, AppointmentDB appointmentDB, UserDB userDB) {
        this.doctorAvailabilityDB = doctorAvailabilityDB; // Initialize with the AppointmentDB instance
        this.appointmentDB = appointmentDB;
        this.userDB = userDB;
        this.doctorAvailabilityMap = new HashMap<>();

        List<DoctorAvailability> allAvailabilities = doctorAvailabilityDB.getAll(); // Fetch all doctor availabilities
        for (DoctorAvailability availability : allAvailabilities) {
            // For each availability, group them by doctorId
            doctorAvailabilityMap
                    .computeIfAbsent(availability.getDoctorId(), k -> new ArrayList<>()) // Create list if it doesn't
                                                                                         // exist
                    .add(availability); // Add the availability to the list of the corresponding doctor
        }
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

        // Retrieve the list of doctor availabilities
        List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityMap.get(doctorId);

        if (doctorAvailabilities == null) {
            System.out.println("Invalid Doctor ID.");
            return availableSlots;
        }

        // Loop through the availabilities and filter for the selected doctor
        for (DoctorAvailability availability : doctorAvailabilities) {
            if (availability.getDoctorId().equals(doctorId) && availability.getAvailabilityStatus()) {
                // Format the available slot info (timeSlot)
                availableSlots.add("Date: " + availability.getDate() + ", Time: " + availability.getTimeSlot());
            }
        }

        if (availableSlots.isEmpty()) {
            System.out.println("Doctor ID: " + doctorId + " has no available slots.");
        }

        return availableSlots;
    }

    // Schedule a new appointment
    // TODO: Error checking (make sure that parsing date will not lead to errors)
    public boolean scheduleAppointment(String patientId, String doctorId, String date, String timeSlot) {
        String appointmentId;
        LocalDate appointmentDate = LocalDate.parse(date);

        if (patientId == null || doctorId == null || date == null || timeSlot == null) {
            System.out.println("Invalid input. Please ensure all fields are filled.");
            return false;
        }

        // Retrieve the doctor's availability list for the specified doctor
        List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityMap.get(doctorId);

        if (doctorAvailabilities == null) {
            System.out.println("Doctor ID not found.");
            return false;
        }

        // Check if the selected time slot is available for the specified doctor
        boolean isSlotAvailable = false;
        for (DoctorAvailability availability : doctorAvailabilities) {
            if (availability.getDate().equals(appointmentDate) && availability.getTimeSlot().equals(timeSlot)
                    && availability.getAvailabilityStatus()) {
                // Mark the slot as unavailable (it's now booked)
                availability.setAvailabilityStatus(false);
                doctorAvailabilityDB.update(availability);
                isSlotAvailable = true;
                break;
            }
        }

        appointmentId = UUID.randomUUID().toString(); // Generate a unique ID

        // If the slot is available, create the appointment
        if (isSlotAvailable) {
            // Create the appointment object
            Appointment appointment = new Appointment(appointmentId, doctorId, patientId, appointmentDate, timeSlot,
                    "Pending");

            System.out.println("Appointment scheduled successfully for " + patientId + " with Doctor " + doctorId
                    + " on " + date + " at " + timeSlot);

            // Update the doctor availability list in the database (mark the slot as
            // unavailable)
            boolean isAdded = appointmentDB.create(appointment);

            if (isAdded) {
                try {
                    doctorAvailabilityDB.save();
                } catch (IOException e) {
                    System.out.println("An error occurred while saving the doctor availability: " + e.getMessage());
                    return false;
                }
                return true;
            } else {
                System.out.println("Failed to create the appointment.");
                return false;
            }
        }
        // If no available slot was found, print a message and return false
        System.out.println("No available slots for the given date and time.");
        return false;
    }

    // Reschedule an existing appointment
    public boolean rescheduleAppointment(String appointmentId, String newDate, String newTimeSlot) {
        LocalDate appointmentDate = LocalDate.parse(newDate);
        Appointment existingAppointment = appointmentDB.getById(appointmentId);

        if (existingAppointment == null) {
            System.out.println("Appointment not found.");
            return false;
        }

        String doctorId = existingAppointment.getDoctorId();
        String patientId = existingAppointment.getPatientId();

        // Retrieve the doctor's availability list for the specified doctor
        List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityMap.get(doctorId);

        if (doctorAvailabilities == null) {
            System.out.println("Doctor ID not found.");
            return false;
        }

        // for (DoctorAvailability availability : doctorAvailabilities) {
        // if (availability.getDate().equals(existingAppointment.getAppointmentDate())
        // &&
        // availability.getTimeSlot().equals(existingAppointment.getTimeSlot()) &&
        // !availability.getAvailabilityStatus()) {
        // // Mark the previous slot as available
        // availability.setAvailabilityStatus(true);
        // break;
        // }
        // }

        boolean isSlotAvailable = false;
        for (DoctorAvailability availability : doctorAvailabilities) {
            if (availability.getDate().equals(appointmentDate) &&
                    availability.getTimeSlot().equals(newTimeSlot) &&
                    availability.getAvailabilityStatus()) {
                // Mark the slot as unavailable (it's now booked)
                availability.setAvailabilityStatus(false);
                doctorAvailabilityDB.update(availability);
                isSlotAvailable = true;
                break;
            }
        }

        // if (isSlotAvailable) {
        // // Update the appointment object with the new date and time
        // existingAppointment.setAppointmentDate(appointmentDate);
        // existingAppointment.setTimeSlot(newTimeSlot);

        // // Save the updated appointment to the database
        // boolean isUpdated = appointmentDB.update(existingAppointment);

        // if (isUpdated) {
        // try {
        // // Save the updated doctor availability to the database
        // doctorAvailabilityDB.save(); // Persist all changes
        // System.out.println("Appointment rescheduled successfully.");
        // return true;
        // } catch (IOException e) {
        // System.out.println("An error occurred while saving doctor availability: " +
        // e.getMessage());
        // return false;
        // }
        // } else {
        // System.out.println("Failed to reschedule the appointment.");
        // return false;
        // }
        // }
        // else {
        // System.out.println("The new appointment slot is not available.");
        // return false;
        // }

        // TODO: Pls simplify logic above by extracting out components
        return false;
    }

    // Cancel an appointment
    // public boolean cancelAppointment(String appointmentId) {
    // Appointment existingAppointment = appointmentDB.getById(appointmentId);

    // if (existingAppointment == null) {
    // System.out.println("Appointment not found.");
    // return false;
    // }

    // String doctorId = existingAppointment.getDoctorId();
    // LocalDate appointmentDate = existingAppointment.getAppointmentDate();
    // String timeSlot = existingAppointment.getTimeSlot();

    // // Retrieve the doctor's availability list for the specified doctor
    // List<DoctorAvailability> doctorAvailabilities =
    // doctorAvailabilityMap.get(doctorId);

    // if (doctorAvailabilities == null) {
    // System.out.println("Doctor ID not found.");
    // return false;
    // }

    // // Mark the slot as available again (i.e., if it's booked, make it available)
    // for (DoctorAvailability availability : doctorAvailabilities) {
    // if (availability.getDate().equals(appointmentDate) &&
    // availability.getTimeSlot().equals(timeSlot) &&
    // !availability.getAvailabilityStatus()) {
    // // Mark the slot as available
    // availability.setAvailabilityStatus(true);
    // doctorAvailabilityDB.update(availability);
    // break;
    // }
    // }

    // // Remove the appointment from the database
    // boolean isDeleted = appointmentDB.delete(appointmentId);

    // if (isDeleted) {
    // try {
    // // Save the updated doctor availability to the database
    // doctorAvailabilityDB.save(); // Persist the changes
    // System.out.println("Appointment canceled successfully.");
    // return true;
    // } catch (IOException e) {
    // System.out.println("An error occurred while saving the doctor availability: "
    // + e.getMessage());
    // return false;
    // }
    // }

    // System.out.println("Failed to cancel the appointment.");
    // return false;
    // }
}