package appointments;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

import medicalrecords.Prescription;

public class Appointment {
    private String appointmentId;
    private String patientId; // Links to the patient's ID
    private String doctorId; // Doctor assigned to this appointment
    private LocalDateTime appointmentDate; // Date and time of the appointment
    private String status; // confirmed, canceled, completed, etc.

    // Outcome record
    private AppointmentOutcomeRecord outcomeRecord;

    // Constructor
    public Appointment(String appointmentId, String patientId, String doctorId, LocalDateTime appointmentDate,
            String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.outcomeRecord = null; // Outcome record is initially null
    }

    // Method to complete the appointment and create an outcome record
    public void completeAppointment(String serviceProvided, String consultationNotes,
            List<Prescription> prescriptions) {
        this.outcomeRecord = new AppointmentOutcomeRecord(appointmentId, LocalDate.now(), serviceProvided,
                consultationNotes, prescriptions);
        this.outcomeRecord.setConsultationNotes(consultationNotes);
        this.status = "completed"; // Update the appointment status
    }

    // Getters for appointment details
    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public AppointmentOutcomeRecord getOutcomeRecord() {
        return outcomeRecord; // Return the outcome record if it exists
    }

    // Other methods for appointment management can be added here
}
