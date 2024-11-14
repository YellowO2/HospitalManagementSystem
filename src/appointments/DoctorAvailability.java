package appointments;

import java.time.LocalDate;
// import java.time.LocalTime;

public class DoctorAvailability {
    private String appointmentId;
    private String doctorId;
    private LocalDate date;
    private String timeSlot;
    private boolean availabilityStatus;


    public DoctorAvailability(String appointmentId, String doctorId, LocalDate date, String timeSlot, boolean availabilityStatus) {
        this.doctorId = doctorId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.availabilityStatus = availabilityStatus;
    }

    // Getters
    public String getAppointmentId(){
        return appointmentId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public boolean getAvailabilityStatus() {
        return availabilityStatus;
    }

    // Setters
    public void setAppointmentId(String appointmentId){
        this.appointmentId = appointmentId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}