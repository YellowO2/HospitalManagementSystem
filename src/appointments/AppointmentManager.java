package appointments;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import database.AppointmentDB;
import database.DoctorUnavailabilityDB;
import database.UserDB;
import users.Doctor;

public class AppointmentManager {
    private AppointmentDB appointmentDB;
    private DoctorUnavailabilityDB availabilityDB;
    private UserDB userDB;

    // Constructor
    public AppointmentManager(DoctorUnavailabilityDB availabilityDB, AppointmentDB appointmentDB, UserDB userDB) {
        this.appointmentDB = appointmentDB;
        this.availabilityDB = availabilityDB;
        this.userDB = userDB;
    }

    // Get all doctors with their number in the list
    public List<String> getAllAvailableDoctors() {
        List<Doctor> doctorList = userDB.getAllDoctors();
        List<String> doctorListFormatted = new ArrayList<>();
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor doctor = doctorList.get(i);
            doctorListFormatted.add(doctor.getName() + " - " + doctor.getId());
        }
        return doctorListFormatted;
    }

    // Get all available slots for a doctor on a given date
    public List<String> getAvailableSlots(String doctorId, LocalDate date) {
        // Define working hours
        LocalTime startOfWork = LocalTime.of(9, 0);
        LocalTime endOfWork = LocalTime.of(17, 0);

        // Create a list of all potential time slots
        List<LocalTime> allPossibleSlots = new ArrayList<>();
        for (LocalTime time = startOfWork; !time.isAfter(endOfWork); time = time.plusHours(1)) {
            allPossibleSlots.add(time);
        }

        // Retrieve unavailable slots and existing appointments
        Set<LocalTime> unavailableTimes = getUnavailableTimes(doctorId, date);
        Set<LocalTime> bookedTimes = getBookedTimes(doctorId, date);

        // Filter out unavailable and booked times
        List<String> availableSlots = new ArrayList<>();
        for (LocalTime slot : allPossibleSlots) {
            if (!unavailableTimes.contains(slot) && !bookedTimes.contains(slot)) {
                availableSlots.add(slot.toString());
            }
        }

        return availableSlots;
    }

    // Helper method to retrieve unavailable times
    private Set<LocalTime> getUnavailableTimes(String doctorId, LocalDate date) {
        List<DoctorUnavailableSlots> unavailableSlots = availabilityDB.getDoctorUnavailability(doctorId, date);
        Set<LocalTime> unavailableTimes = new HashSet<>();
        for (DoctorUnavailableSlots unavailable : unavailableSlots) {
            unavailableTimes.add(unavailable.getTime());
        }
        return unavailableTimes;
    }

    // Helper method to retrieve booked times
    private Set<LocalTime> getBookedTimes(String doctorId, LocalDate date) {
        List<Appointment> doctorAppointments = appointmentDB.getDoctorAppointments(doctorId);
        Set<LocalTime> bookedTimes = new HashSet<>();
        for (Appointment appointment : doctorAppointments) {
            if (appointment.getAppointmentDate().equals(date)) {
                bookedTimes.add(appointment.getAppointmentTime());
            }
        }
        return bookedTimes;
    }

    // Schedule appointment based on user-selected doctor index
    public boolean scheduleAppointment(String patientId, String doctorId, String date, int slotIndex) {

        LocalDate appointmentDate = LocalDate.parse(date);
        List<String> availableSlots = getAvailableSlots(doctorId, appointmentDate);

        if (!validateSlotSelection(slotIndex, availableSlots)) {
            return false;
        }

        LocalTime appointmentTime = LocalTime.parse(availableSlots.get(slotIndex - 1));
        return createAppointment(patientId, doctorId, appointmentDate, appointmentTime, "Pending");
    }

    // Helper method to validate slot selection
    private boolean validateSlotSelection(int slotIndex, List<String> availableSlots) {
        if (availableSlots == null || availableSlots.isEmpty()) {
            System.out.println("No available slots.");
            return false;
        }
        if (slotIndex < 1 || slotIndex > availableSlots.size()) {
            System.out.println("Invalid slot selection.");
            return false;
        }
        return true;
    }

    // Helper method to create a new appointment
    private boolean createAppointment(String patientId, String doctorId, LocalDate date, LocalTime time,
            String status) {
        String appointmentId = UUID.randomUUID().toString();
        Appointment appointment = new Appointment(appointmentId, doctorId, patientId, date, time, status);
        return appointmentDB.create(appointment);
    }

    // Reschedule an appointment
    public boolean rescheduleAppointment(String originalAppointmentId,
            String newDate, int slotIndex) {

        // Find the existing appointment to cancel it first
        Appointment originalAppointment = appointmentDB.getById(originalAppointmentId);
        if (originalAppointment == null) {
            System.out.println("Appointment not found.");
            return false;
        }

        // Cancel the original appointment
        if (!cancelAppointment(originalAppointmentId)) {
            System.out.println("Failed to cancel the original appointment.");
            return false;
        }

        LocalDate appointmentDate = LocalDate.parse(newDate);

        // Get available slots for the selected doctor on the new date
        List<String> availableSlots = getAvailableSlots(originalAppointment.getDoctorId(), appointmentDate);

        // Validate slot selection
        if (!validateSlotSelection(slotIndex, availableSlots)) {
            return false;
        }

        // Get the new appointment time
        LocalTime newAppointmentTime = LocalTime.parse(availableSlots.get(slotIndex - 1));

        // Create the new appointment
        return createAppointment(originalAppointment.getPatientId(), originalAppointment.getDoctorId(), appointmentDate,
                newAppointmentTime, "Pending");
    }

    // Cancel the existing appointment
    private boolean cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentDB.getById(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return false;
        }

        // Remove the appointment from the database
        return appointmentDB.delete(appointment.getAppointmentId());
    }
}
