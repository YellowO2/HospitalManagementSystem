package managers;

import database.AppointmentDB;
import database.DoctorUnavailabilityDB;
import database.UserDB;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import appointments.Appointment;
import appointments.DoctorUnavailableSlots;
import users.Doctor;

public class AppointmentManager {
    private AppointmentDB appointmentDB;
    private DoctorUnavailabilityDB availabilityDB;
    private UserDB userDB;

    /**
     * Constructor for the AppointmentManager.
     *
     * @param availabilityDB the DoctorUnavailabilityDB instance
     * @param appointmentDB  the AppointmentDB instance
     * @param userDB         the UserDB instance
     */
    public AppointmentManager(DoctorUnavailabilityDB availabilityDB, AppointmentDB appointmentDB, UserDB userDB) {
        this.appointmentDB = appointmentDB;
        this.availabilityDB = availabilityDB;
        this.userDB = userDB;
    }

    /**
     * Retrieves a list of all available doctors with their IDs.
     *
     * @return a list of formatted strings with doctor names and IDs
     */
    public List<String> getAllAvailableDoctors() {
        List<Doctor> doctorList = userDB.getAllDoctors();
        List<String> doctorListFormatted = new ArrayList<>();
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor doctor = doctorList.get(i);
            doctorListFormatted.add(doctor.getName() + " - " + doctor.getId());
        }
        return doctorListFormatted;
    }

    /**
     * Displays available appointment slots for a given doctor starting from a
     * specified date.
     *
     * @param doctorId  the ID of the doctor
     * @param startDate the starting date to view available slots
     */
    public void showAvailableSlots(String doctorId, LocalDate startDate) {
        System.out.println("Viewing available appointment slots for Doctor " + userDB.getById(doctorId).getName());
        System.out.println("===================================================");

        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            System.out.print((i + 1) + ". Date: " + currentDate + "  ");

            List<LocalTime> availableSlots = getAvailableSlotsForDoctor(doctorId, currentDate);

            if (availableSlots.isEmpty()) {
                System.out.print("No available slots.");
            } else {
                int slotNumber = 1;
                for (LocalTime slot : availableSlots) {
                    System.out.printf("%d. %s  ", slotNumber++, slot);
                }
            }
            System.out.println();
            System.out.println("---------------------------------------------------");
        }

        System.out.println("===================================================");
    }

    /**
     * Retrieves a doctor's personal schedule for a specific date.
     *
     * @param doctorId the ID of the doctor
     * @param date     the date to get the schedule for
     * @return a list of available slots for the doctor on the given date
     */
    public List<String> getPersonalSchedule(String doctorId, LocalDate date) {
        LocalTime startOfWork = LocalTime.of(9, 0);
        LocalTime endOfWork = LocalTime.of(17, 0);

        List<LocalTime> allPossibleSlots = new ArrayList<>();
        for (LocalTime time = startOfWork; !time.isAfter(endOfWork); time = time.plusHours(1)) {
            allPossibleSlots.add(time);
        }

        Set<LocalTime> unavailableTimes = getUnavailableTimes(doctorId, date);

        List<String> availableSlots = new ArrayList<>();
        for (LocalTime slot : allPossibleSlots) {
            if (!unavailableTimes.contains(slot)) {
                availableSlots.add(slot.toString());
            }
        }
        return availableSlots;
    }

    /**
     * Retrieves all appointments for a specific patient.
     *
     * @param patientId the ID of the patient
     * @return a list of formatted strings representing the patient's appointments
     */
    public List<String> getPatientAppointments(String patientId) {
        List<Appointment> patientAppointments = appointmentDB.getPatientAppointments(patientId);
        List<String> patientAppointmentsFormatted = new ArrayList<>();
        for (int i = 0; i < patientAppointments.size(); i++) {
            Appointment appointment = patientAppointments.get(i);
            patientAppointmentsFormatted.add(appointment.toString());
        }
        return patientAppointmentsFormatted;
    }

    /**
     * Retrieves all appointments for a specific doctor, filtered by status.
     *
     * @param doctorId     the ID of the doctor
     * @param statusFilter the status filter for the appointments ("All", "Pending",
     *                     "Accepted")
     * @return a list of formatted strings representing the doctor's appointments
     */
    public List<String> getDoctorAppointments(String doctorId, String statusFilter) {
        List<Appointment> doctorAppointments = appointmentDB.getDoctorAppointments(doctorId);
        List<String> doctorAppointmentsFormatted = new ArrayList<>();

        for (Appointment appointment : doctorAppointments) {
            if (statusFilter.equalsIgnoreCase("All") ||
                    (statusFilter.equalsIgnoreCase("Pending") && appointment.getStatus().equalsIgnoreCase("Pending")) ||
                    (statusFilter.equalsIgnoreCase("Confirm")
                            && appointment.getStatus().equalsIgnoreCase("Confirm"))) {
                doctorAppointmentsFormatted.add(appointment.toString());
            }
        }
        return doctorAppointmentsFormatted;
    }

    /**
     * Retrieves the unavailable times for a doctor on a specific date.
     *
     * @param doctorId the ID of the doctor
     * @param date     the date to check for unavailable times
     * @return a set of unavailable times for the doctor on the given date
     */
    private Set<LocalTime> getUnavailableTimes(String doctorId, LocalDate date) {
        List<DoctorUnavailableSlots> unavailableSlots = availabilityDB.getDoctorUnavailability(doctorId, date);
        Set<LocalTime> unavailableTimes = new HashSet<>();
        for (DoctorUnavailableSlots unavailable : unavailableSlots) {
            unavailableTimes.add(unavailable.getTime());
        }
        return unavailableTimes;
    }

    /**
     * Retrieves the booked times for a doctor on a specific date.
     *
     * @param doctorId the ID of the doctor
     * @param date     the date to check for booked times
     * @return a set of booked times for the doctor on the given date
     */
    private Set<LocalTime> getBookedTimes(String doctorId, LocalDate date) {
        List<Appointment> doctorAppointments = appointmentDB.getDoctorAppointments(doctorId);
        Set<LocalTime> bookedTimes = new HashSet<>();
        for (Appointment appointment : doctorAppointments) {
            if (appointment.getAppointmentDate().equals(date)
                    && !appointment.getStatus().equalsIgnoreCase("Cancelled")) {
                bookedTimes.add(appointment.getAppointmentTime());
            }
        }
        return bookedTimes;
    }

    /**
     * Retrieves available appointment slots for a doctor on a specific date.
     *
     * @param doctorId the ID of the doctor
     * @param date     the date to check for available slots
     * @return a list of available appointment slots for the doctor
     */
    public List<LocalTime> getAvailableSlotsForDoctor(String doctorId, LocalDate date) {
        LocalTime startOfWork = LocalTime.of(9, 0);
        LocalTime lastAppointmentTime = LocalTime.of(16, 0);

        List<LocalTime> allPossibleSlots = new ArrayList<>();
        for (LocalTime time = startOfWork; !time.isAfter(lastAppointmentTime); time = time.plusHours(1)) {
            allPossibleSlots.add(time);
        }

        Set<LocalTime> unavailableTimes = getUnavailableTimes(doctorId, date);
        Set<LocalTime> bookedTimes = getBookedTimes(doctorId, date);

        List<LocalTime> availableSlots = new ArrayList<>();
        for (LocalTime slot : allPossibleSlots) {
            if (!unavailableTimes.contains(slot) && !bookedTimes.contains(slot)) {
                availableSlots.add(slot);
            }
        }

        return availableSlots;
    }

    /**
     * Schedules an appointment for a patient with a doctor.
     *
     * @param patientId the ID of the patient
     * @param doctorId  the ID of the doctor
     * @param date      the date of the appointment
     * @param slotIndex the index of the selected slot
     * @return true if the appointment is successfully scheduled, false otherwise
     */
    public boolean scheduleAppointment(String patientId, String doctorId, LocalDate date, int slotIndex) {
        List<LocalTime> availableSlots = getAvailableSlotsForDoctor(doctorId, date);
        if (!validateSlotSelection(slotIndex, availableSlots)) {
            return false;
        }

        LocalTime appointmentTime = availableSlots.get(slotIndex);
        String appointmentId = UUID.randomUUID().toString();
        Appointment appointment = new Appointment(appointmentId, doctorId, patientId, date, appointmentTime, "Pending");
        if (appointmentDB.create(appointment)) {
            System.out.println("Appointment scheduled to " + date + " at " + appointmentTime + ".");
            return true;
        } else {
            return false;
        }
    }

    // Helper method to validate slot selection
    private boolean validateSlotSelection(int slotIndex, List<LocalTime> availableSlots) {
        if (slotIndex < 0 || slotIndex >= availableSlots.size()) {
            System.out.println("Invalid slot selection. Please choose a valid slot index.");
            return false;
        }
        return true;
    }

    // Reschedule an appointment
    public void rescheduleAppointment(String doctorId, String originalAppointmentId, LocalDate newDate,
            int newSlotIndex) {
        // Retrieve the original appointment
        Appointment originalAppointment = appointmentDB.getById(originalAppointmentId);
        if (originalAppointment == null) {
            System.out.println("Error: Appointment with ID " + originalAppointmentId + " not found.");
            return;
        }

        String patientId = originalAppointment.getPatientId();

        // Attempt to schedule the new appointment first
        boolean isNewAppointmentScheduled = scheduleAppointment(patientId, doctorId, newDate, newSlotIndex);
        if (!isNewAppointmentScheduled) {
            System.out.println("Error: Failed to schedule the new appointment. Rescheduling aborted.");
            return;
        }

        // If new appointment is successfully scheduled, cancel the old appointment
        boolean isOldAppointmentCancelled = cancelAppointment(originalAppointmentId);
        if (isOldAppointmentCancelled) {
            System.out.println("Success: Appointment rescheduled successfully.");
        } else {
            System.out.println("Warning: Failed to cancel the original appointment. Please check manually.");
        }
    }

    public boolean cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentDB.getById(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return false;
        }

        // Remove the appointment from the database
        return appointmentDB.delete(appointment.getAppointmentId());
    }

    // TODO: Change boolean to void upon validation of method
    public boolean updateAppointmentStatus(String appointmentId, String status) {
        Appointment appointment = appointmentDB.getById(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return false;
        }

        appointment.setStatus(status);
        return appointmentDB.update(appointment); // Test and make sure that this is working
        // return true;
    }

    public boolean isValidDoctorId(String doctorId) {
        List<Doctor> doctorList = userDB.getAllDoctors();

        return doctorList.stream().anyMatch(doctor -> doctor.getId().equals(doctorId));
    }

    public boolean isValidAppointmentId(String appointmentId) {
        Appointment appointment = appointmentDB.getById(appointmentId);
        if (appointment != null) {
            return true;
        }
        return false;
    }

    public List<String> viewAllAppointments() {
        List<Appointment> allAppointments = appointmentDB.getAll();
        List<String> allAppointmentsFormatted = new ArrayList<>();

        for (Appointment appointment : allAppointments) {
            allAppointmentsFormatted.add(appointment.toString());
        }

        return allAppointmentsFormatted;
    }
}
