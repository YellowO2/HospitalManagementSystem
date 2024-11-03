package appointments;

import java.time.LocalDate;

import java.util.List;
import medicalrecords.Prescription;

public class AppointmentOutcomeRecord {
    private String appointmentId; // Links to the appointment
    private LocalDate appointmentDate;
    private String serviceProvided; // Type of service (e.g., consultation, X-ray)
    private String consultationNotes; // Doctor's notes
    private List<Prescription> prescriptions; // Prescribed medications

    // Constructor
    public AppointmentOutcomeRecord(String appointmentId, LocalDate appointmentDate, String serviceProvided,
            String consultationNotes,
            List<Prescription> prescriptions) {
        this.appointmentId = appointmentId;
        this.serviceProvided = serviceProvided;
        this.consultationNotes = consultationNotes;
        this.prescriptions = prescriptions;
        this.appointmentDate = appointmentDate;
    }

    // Getters and setters
    public String getAppointmentId() {
        return appointmentId;
    }

    public String getServiceProvided() {
        return serviceProvided;
    }

    public void setServiceProvided(String serviceProvided) {
        this.serviceProvided = serviceProvided;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
    
	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}
}
