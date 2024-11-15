package appointments;

import java.time.LocalDate;
import java.time.LocalTime;

public class DoctorUnavailableSlots {
    private String doctorId;
    private LocalDate date;
    private LocalTime time;

    // Constructor
    public DoctorUnavailableSlots(String doctorId, LocalDate date, LocalTime time) {
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
    }

    // Getters
    public String getDoctorId() {
        return doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

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

    @Override
    public String toString() {
        return String.format("%s,%s,%s", doctorId, date.toString(), time.toString());
    }

}
