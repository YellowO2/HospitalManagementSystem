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
        return doctorId != null ? doctorId.trim().toLowerCase() : ""; // Normalize by trimming and making lowercase
    }

    // TODO: Might have to change the logic of loadDoctorsIntoMap
    // Right now, loadDoctorsIntoMap is making use of getAllAvailableDoctors to lazy
    // load...
    private void loadDoctorsIntoMap() {
        if (doctorMap.isEmpty()) {
            System.out.println("Loading doctors into map...");
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

    // View available appointment slots for a specified doctor
    public List<String> viewAvailableSlots(String doctorId) {
        List<String> availableSlots = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // Validate doctor ID
        if (!isValidDoctorId(doctorId)) {
            return null;
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

        // Retrieve unavailable slots and appointments for the doctor
        List<DoctorUnavailableSlots> unavailableSlots = availabilityDB.getDoctorUnavailability(doctorId, today);
        System.out.println("Unavailable slots size: " + unavailableSlots.size());
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
            if (appointment.getAppointmentDate().equals(today)) {
                bookedTimes.add(appointment.getAppointmentTime());
            }
        }

        // Check all possible slots for availability
        for (LocalTime slot : allPossibleSlots) {
            if (!unavailableTimes.contains(slot) && !bookedTimes.contains(slot)) {
                availableSlots.add(slot.toString());
            }
        }

        if (availableSlots.isEmpty()) {
            System.out.println("Doctor ID: " + doctorId + " has no available slots.");
        }

        return availableSlots;
    }

    // Schedule a new appointment
    public boolean scheduleAppointment(String patientId, String doctorId, String date, String timeSlot) {
        LocalDate appointmentDate = LocalDate.parse(date);
        LocalTime appointmentTime = LocalTime.parse(timeSlot);

        if (patientId == null || doctorId == null || date == null || timeSlot == null) {
            System.out.println("Invalid input. Please ensure all fields are filled.");
            return false;
        }

        // Check if the selected time is within working hours (9 AM to 5 PM)
        LocalTime startOfWork = LocalTime.of(9, 0);
        LocalTime endOfWork = LocalTime.of(17, 0);
        if (appointmentTime.isBefore(startOfWork) || appointmentTime.isAfter(endOfWork)) {
            System.out.println("The selected time is outside of working hours (9 AM - 5 PM).");
            return false;
        }

        // Retrieve unavailable slots for the doctor from DoctorUnavailabilityDB
        List<DoctorUnavailableSlots> unavailableSlots = availabilityDB.getDoctorUnavailability(doctorId,
                appointmentDate);

        // Check if the selected time slot is unavailable
        for (DoctorUnavailableSlots unavailable : unavailableSlots) {
            if (unavailable.getTime().equals(appointmentTime)) {
                System.out.println("The selected time slot is unavailable.");
                return false;
            }
        }

        // Retrieve all appointments for the doctor
        List<Appointment> doctorAppointments = appointmentDB.getDoctorAppointments(doctorId);

        // Check if the selected time slot is already booked
        for (Appointment appointment : doctorAppointments) {
            if (appointment.getAppointmentDate().equals(appointmentDate)
                    && appointment.getAppointmentTime().equals(appointmentTime)) {
                System.out.println("The selected time slot is already booked.");
                return false;
            }
        }

        String appointmentId = UUID.randomUUID().toString(); // Generate a unique appointment ID

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
