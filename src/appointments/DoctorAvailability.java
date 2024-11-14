package appointments;

import java.time.LocalDate;
import java.time.LocalTime;

public class DoctorAvailability {
    private String doctorId;
    private LocalDate date;
    private LocalTime timeSlotStart;
    private LocalTime timeSlotEnd;
    private String availabilityStatus;


    public DoctorAvailability(String doctorId, LocalDate date, LocalTime timeSlotStart, LocalTime timeSlotEnd, String availabilityStatus) {
        this.doctorId = doctorId;
        this.date = date;
        this.timeSlotStart = timeSlotStart;
        this.timeSlotEnd = timeSlotEnd;
        this.availabilityStatus = availabilityStatus;
    }

    // Getters
    public String getDoctorId() {
        return doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTimeSlotStart() {
        return timeSlotStart;
    }

    public LocalTime getTimeSlotEnd() {
        return timeSlotEnd;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    // Setters
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTimeSlotStart(LocalTime timeSlotStart) {
        this.timeSlotStart = timeSlotStart;
    }

    public void setTimeSlotEnd(LocalTime timeSlotEnd) {
        this.timeSlotEnd = timeSlotEnd;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}