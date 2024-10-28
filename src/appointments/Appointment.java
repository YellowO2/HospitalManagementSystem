package appointments;

import java.time.LocalDateTime;
import java.util.List;

import medicalrecords.Prescription;

public class Appointment {
    private String appointmentId;
    private String patientId; // Links to the patient's ID
    private String doctorId; // Doctor assigned to this appointment
    private LocalDateTime appointmentDate; // Date and time of the appointment
    private String status; // confirmed, canceled, completed, etc.

    // Outcome information
    private String serviceProvided; // Type of service (e.g., consultation, X-ray)
    private String consultationNotes; // Doctor's notes
    private List<Prescription> prescriptions; // List of prescribed medications

    // Constructor
    public Appointment(String appointmentId, String patientId, String doctorId, LocalDateTime appointmentDate,
            String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }

    // Getters and setters for appointment outcome
    public void setServiceProvided(String serviceProvided) {
        this.serviceProvided = serviceProvided;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public String getServiceProvided() {
        return serviceProvided;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    // Other methods for appointment management
}
