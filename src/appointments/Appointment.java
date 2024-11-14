package appointments;

import java.time.LocalDate;
import java.time.LocalTime;
// import java.time.LocalDateTime;
// import java.util.List;
// import medicalrecords.Prescription;

public class Appointment {
    private String appointmentId;
    private String doctorId;
    private String patientId; // Links to the patient's ID
    // private String doctorId; // Doctor assigned to this appointment
    private LocalDate appointmentDate; // Date and time of the appointment
    private String timeSlot;
    private String status; // confirmed, canceled, completed, etc.

    // Outcome record
    private AppointmentOutcomeRecord outcomeRecord;

    // Constructor
    public Appointment(String appointmentId, String doctorId, String patientId, LocalDate appointmentDate, String timeSlot,
            String status) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
        this.timeSlot = timeSlot;
        this.status = status;
        // this.outcomeRecord = null; // Outcome record is initially null
    }

    // Method to complete the appointment and create an outcome record
    // public void completeAppointment(String serviceProvided, String consultationNotes,
    //         List<Prescription> prescriptions) {
    //     this.outcomeRecord = new AppointmentOutcomeRecord(appointmentId, LocalDate.now(), serviceProvided,
    //             consultationNotes, prescriptions);
    //     this.outcomeRecord.setConsultationNotes(consultationNotes);
    //     this.status = "completed"; // Update the appointment status
    // }

    // Getters for appointment details
    public String getAppointmentId() {
        return appointmentId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public String getTimeSlot(){
        return timeSlot;
    }

    public String getStatus() {
        return status;
    }

    public AppointmentOutcomeRecord getOutcomeRecord() {
        return outcomeRecord; // Return the outcome record if it exists
    }

     // Setters
    public void setAppointmentDate(LocalDate appointmenDate) {
        this.appointmentDate = appointmenDate;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

     public void setStatus(String status){
        this.status = status;
    }
}
