package appointments;

import java.util.ArrayList;
import java.util.Comparator;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import database.AppointmentDB;
import database.DoctorUnavailabilityDB;
import database.UserDB;
import appointments.Appointment;
import users.Doctor;

public class AppointmentManager {
    private AppointmentDB appointmentDB; // Reference to the AppointmentDB
    private DoctorUnavailabilityDB availabilityDB;
    private UserDB userDB; // Reference to UserDB
    private Map<String, Doctor> doctorMap; // Making use of Map for faster search time

    // Constructor
    public AppointmentManager(DoctorUnavailabilityDB availabilityDB, AppointmentDB appointmentDB, UserDB userDB) {
        this.appointmentDB = appointmentDB;
        this.availabilityDB = availabilityDB;
        this.userDB = userDB;
        this.doctorMap = new TreeMap<>(String::compareTo);
    }

    // Centralized normalization of doctor ID
    private String normalizeDoctorId(String doctorId) {
        return doctorId != null ? doctorId.trim().toUpperCase() : ""; // Normalize by trimming and making lowercase
    }

    // TODO: Might have to change the logic of loadDoctorsIntoMap
    // Right now, loadDoctorsIntoMap is making use of isValidDoctorId to population
    // the Map...
    private void loadDoctorsIntoMap() {
        if (doctorMap.isEmpty()) {
            // System.out.println("Loading doctors into map...");
            List<Doctor> allDoctors = userDB.getAllDoctors();
            for (Doctor doctor : allDoctors) {
                String normalizedId = normalizeDoctorId(doctor.getId());
                doctorMap.put(normalizedId, doctor);
                // System.out.println("Loaded Doctor ID: " + doctor.normalizedId());
            }
        }
    }

    // Validate if doctorId exists using the map
    private boolean isValidDoctorId(String doctorId) {
        loadDoctorsIntoMap();

        doctorId = normalizeDoctorId(doctorId);
        return doctorMap.containsKey(doctorId); // Check if doctorId exists in the map
    }

    // Method to show all doctors with their IDs
    public List<String> getAllAvailableDoctors() {
        // List<Doctor> allDoctors = userDB.getAllDocters();
        loadDoctorsIntoMap();

        List<String> doctorList = new ArrayList<>();

        for (Doctor doctor : doctorMap.values()) {
            doctorList.add(doctor.getName() + " , " + doctor.getId());
        }
        return doctorList;
    }

    // Schedule a new appointment
    public boolean scheduleAppointment(String patientId, String doctorId, String date, int slotIndex) {
        LocalDate appointmentDate = LocalDate.parse(date);

        if (patientId == null || doctorId == null || date == null || slotIndex < 1) {
            System.out.println("Invalid input. Please ensure all fields are filled correctly.");
            return false;
        }

        // Get available slots from the getAvailableSlots method
        List<String> availableSlots = getAvailableSlots(doctorId, appointmentDate);

        if (availableSlots == null || availableSlots.isEmpty()) {
            System.out.println("No available slots to book.");
            return false;
        }

        // Ensure the selected index is within the valid range
        if (slotIndex > availableSlots.size()) {
            System.out.println("Invalid slot selection.");
            return false;
        }

        // Convert the selected slot (based on index) back to LocalTime
        LocalTime appointmentTime = LocalTime.parse(availableSlots.get(slotIndex - 1));

        String appointmentId = UUID.randomUUID().toString(); // Generate a unique appointment ID

        // Create the appointment
        Appointment appointment = new Appointment(appointmentId, doctorId, patientId, appointmentDate, appointmentTime,
                "Pending");

        // Save the appointment in the database
        boolean isAdded = appointmentDB.create(appointment);

        if (isAdded) {
            System.out.println("Appointment scheduled successfully for " + patientId + " with Doctor " + doctorId
                    + " on " + date + " at " + availableSlots.get(slotIndex - 1));
            return true;
        } else {
            System.out.println("Failed to create the appointment.");
            return false;
        }
    }

    public List<String> getAvailableSlots(String doctorId, LocalDate date) {
        List<String> availableSlots = new ArrayList<>();

        // Validate doctor ID
        if (!isValidDoctorId(doctorId)) {
            return null; // Invalid doctor ID
        }

        // Define working hours (9 AM to 5 PM)
        LocalTime startOfWork = LocalTime.of(9, 0);
        LocalTime endOfWork = LocalTime.of(17, 0);

        // Create a list of all potential time slots for a day (9 AM to 5 PM, every
        // hour)
        List<LocalTime> allPossibleSlots = new ArrayList<>();
        for (LocalTime time = startOfWork; !time.isAfter(endOfWork); time = time.plusHours(1)) {
            allPossibleSlots.add(time);
        }

        // Retrieve unavailable slots for the doctor (from DoctorUnavailabilityDB)
        List<DoctorUnavailableSlots> unavailableSlots = availabilityDB.getDoctorUnavailability(doctorId, date);

        // Retrieve all appointments for the doctor on this date
        List<Appointment> doctorAppointments = appointmentDB.getDoctorAppointments(doctorId);

        // Use sets for fast lookup of unavailable and booked slots
        Set<LocalTime> unavailableTimes = new HashSet<>();
        Set<LocalTime> bookedTimes = new HashSet<>();

        // Add all unavailable times to the set
        for (DoctorUnavailableSlots unavailable : unavailableSlots) {
            System.out.println("Unavailable time: " + unavailable.getTime());
            unavailableTimes.add(unavailable.getTime());
        }

        // Add all booked appointment times to the set
        for (Appointment appointment : doctorAppointments) {
            if (appointment.getAppointmentDate().equals(date)) {
                bookedTimes.add(appointment.getAppointmentTime());
            }
        }

        // Check all possible slots for availability
        for (LocalTime slot : allPossibleSlots) {
            boolean isUnavailable = unavailableSlots.stream()
                    .anyMatch(unavailable -> unavailable.getTime().equals(slot))
                    || doctorAppointments.stream()
                            .anyMatch(appointment -> appointment.getAppointmentDate().equals(date)
                                    && appointment.getAppointmentTime().equals(slot));

            // If the slot is available, add it to the availableSlots list
            if (!isUnavailable) {
                availableSlots.add(slot.toString());
            }
        }

        return availableSlots;
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

        // Check if the selected time is within working hours (9 AM to 5 PM)
        LocalTime startOfWork = LocalTime.of(9, 0);
        LocalTime endOfWork = LocalTime.of(17, 0);
        if (appointmentTime.isBefore(startOfWork) || appointmentTime.isAfter(endOfWork)) {
            System.out.println("The selected time is outside of working hours (9 AM - 5 PM).");
            return false;
        }

        // Retrieve unavailable slots for the doctor on the new date from
        // DoctorUnavailabilityDB
        List<DoctorUnavailableSlots> unavailableSlots = availabilityDB.getDoctorUnavailability(doctorId,
                appointmentDate);

        // Check if the selected time slot is unavailable
        for (DoctorUnavailableSlots unavailable : unavailableSlots) {
            if (unavailable.getTime().equals(appointmentTime)) {
                System.out.println("The selected time slot is unavailable.");
                return false;
            }
        }

        // Retrieve all appointments for the doctor on the new date
        List<Appointment> doctorAppointments = appointmentDB.getDoctorAppointments(doctorId);

        // Check if the new time slot is already booked
        for (Appointment appointment : doctorAppointments) {
            if (appointment.getAppointmentDate().equals(appointmentDate)
                    && appointment.getAppointmentTime().equals(appointmentTime)) {
                System.out.println("No available slots for the new date and time.");
                return false;
            }
        }

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
    }

    public List<String> getPatientAppointments(String patientId) {
        List<String> patientAppointments = new ArrayList<>();

        List<Appointment> appointments = appointmentDB.getPatientAppointments(patientId);

        for (Appointment appointment : appointments) {
            patientAppointments.add(appointment.toString());
        }

        return patientAppointments;
    }

}
