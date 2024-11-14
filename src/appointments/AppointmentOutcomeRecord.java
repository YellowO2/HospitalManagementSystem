package appointments;

import java.time.LocalDate;
import java.util.List;
import medicalrecords.Prescription;

public class AppointmentOutcomeRecord {
    private String appointmentId; // Links to the appointment
    private LocalDate appointmentDate;
    private String serviceProvided; // Type of service (e.g., consultation, X-ray)
    private List<Prescription> prescriptions; // Prescribed medications
    private String medicationStatus;
    private String consultationNotes; // Doctor's notes
    // private List<Prescription> prescriptions; // Prescribed medications

    // Constructor
    public AppointmentOutcomeRecord(
            String appointmentId, 
            LocalDate appointmentDate, 
            String serviceProvided,
            List<Prescription> prescriptions, 
            String medicationStatus,
            String consultationNotes) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.serviceProvided = serviceProvided;
        this.prescriptions = prescriptions;
        this.medicationStatus = medicationStatus;
        this.consultationNotes = consultationNotes;
    }

    // Getters and setters
    public String getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getServiceProvided() {
        return serviceProvided;
    }

    public void setServiceProvided(String serviceProvided) {
        this.serviceProvided = serviceProvided;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public String getMedicationStatus() {
        return medicationStatus;
    }

    public void setMedicationStatus(String medicationStatus) {
        this.medicationStatus = medicationStatus;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }
}
