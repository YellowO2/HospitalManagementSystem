package appointments;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an unavailable slot for a doctor on a specific date and time.
 * This is used to manage and track the doctor's unavailable times.
 */
public class DoctorUnavailableSlots {
    private String doctorId; // Identifier for the doctor
    private LocalDate date; // The date of unavailability
    private LocalTime time; // The time of unavailability

    /**
     * Constructs a new DoctorUnavailableSlots object.
     *
     * @param doctorId the unique identifier of the doctor
     * @param date     the date of unavailability
     * @param time     the time of unavailability
     */
    public DoctorUnavailableSlots(String doctorId, LocalDate date, LocalTime time) {
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
    }

    /**
     * Gets the ID of the doctor.
     *
     * @return the doctor's ID
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Gets the date of unavailability.
     *
     * @return the unavailable date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the time of unavailability.
     *
     * @return the unavailable time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Parses a CSV string to create a DoctorUnavailableSlots object.
     *
     * @param csvString the CSV string in the format "doctorId|date|time"
     *                  where date is in ISO format (yyyy-MM-dd) and time is in ISO
     *                  format (HH:mm)
     * @return a new DoctorUnavailableSlots object
     * @throws IllegalArgumentException if the CSV string is not in the correct
     *                                  format
     */
    public static DoctorUnavailableSlots fromCSV(String csvString) {
        String[] parts = csvString.split("\\|");

        // Ensure the array has exactly 3 parts (doctorId, date, and time)
        if (parts.length == 3) {
            String doctorId = parts[0];
            LocalDate date = LocalDate.parse(parts[1]); // Assuming the date is in ISO format (yyyy-MM-dd)
            LocalTime time = LocalTime.parse(parts[2]); // Assuming the time is in ISO format (HH:mm)

            return new DoctorUnavailableSlots(doctorId, date, time);
        }

        throw new IllegalArgumentException("Invalid CSV format for DoctorUnavailableSlots: " + csvString);
    }

    /**
     * Converts the DoctorUnavailableSlots object to a string representation.
     * The format is "doctorId,date,time".
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%s", doctorId, date.toString(), time.toString());
    }
}
