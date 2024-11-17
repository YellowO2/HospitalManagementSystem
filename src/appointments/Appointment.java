package appointments;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an appointment in the system, including details about the doctor,
 * patient, date, time, status, and an optional outcome record.
 */
public class Appointment {

    private String appointmentId; // Unique ID for the appointment
    private String doctorId; // ID of the doctor assigned to the appointment
    private String patientId; // ID of the patient linked to the appointment
    private LocalDate appointmentDate; // Date of the appointment
    private LocalTime appointmentTime; // Time of the appointment
    private String status; // Status of the appointment (e.g., confirmed, canceled, completed)

    // Outcome record for completed appointments
    private AppointmentOutcomeRecord outcomeRecord;

    /**
     * Constructs an Appointment object with the specified details.
     *
     * @param appointmentId   the unique ID of the appointment
     * @param doctorId        the ID of the doctor assigned to the appointment
     * @param patientId       the ID of the patient linked to the appointment
     * @param appointmentDate the date of the appointment
     * @param appointmentTime the time of the appointment
     * @param status          the status of the appointment
     */
    public Appointment(String appointmentId, String doctorId, String patientId, LocalDate appointmentDate,
            LocalTime appointmentTime, String status) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        // Outcome record is initially null
    }

    /**
     * Gets the unique ID of the appointment.
     *
     * @return the appointment ID
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Gets the ID of the doctor assigned to the appointment.
     *
     * @return the doctor ID
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Gets the ID of the patient linked to the appointment.
     *
     * @return the patient ID
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Gets the date of the appointment.
     *
     * @return the appointment date
     */
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Sets the date of the appointment.
     *
     * @param appointmentDate the new date for the appointment
     */
    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Gets the time of the appointment.
     *
     * @return the appointment time
     */
    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * Sets the time of the appointment.
     *
     * @param appointmentTime the new time for the appointment
     */
    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * Gets the status of the appointment.
     *
     * @return the appointment status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the appointment.
     *
     * @param status the new status for the appointment (e.g., confirmed, canceled,
     *               completed)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the outcome record for the appointment, if it exists.
     *
     * @return the outcome record, or null if no record exists
     */
    public AppointmentOutcomeRecord getOutcomeRecord() {
        return outcomeRecord;
    }

    /**
     * Creates an Appointment object from a CSV-formatted string.
     * This method is intended to be overridden by subclasses.
     *
     * @param csvString the CSV-formatted string representing an appointment
     * @return the Appointment object
     * @throws UnsupportedOperationException if the method is not overridden
     */
    public static Appointment fromCSV(String csvString) {
        throw new UnsupportedOperationException("This method should be overridden by subclasses");
    }

    /**
     * Returns a string representation of the appointment in a CSV format.
     *
     * @return a CSV-formatted string representing the appointment
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s", appointmentId, doctorId, patientId,
                appointmentDate.toString(), appointmentTime.toString(), status);
    }
}
