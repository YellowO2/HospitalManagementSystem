package appointments;

import java.util.List;
import medicalrecords.Prescription;

public class AppointmentOutcome {
    private String appointmentId; // Links to the appointment
    private String serviceProvided; // Type of service (e.g., consultation, X-ray)
    private String consultationNotes; // Doctor's notes
    private List<Prescription> prescriptions; // Prescribed medications

    // Constructor
    public AppointmentOutcome(String appointmentId, String serviceProvided, String consultationNotes,
            List<Prescription> prescriptions) {
        this.appointmentId = appointmentId;
        this.serviceProvided = serviceProvided;
        this.consultationNotes = consultationNotes;
        this.prescriptions = prescriptions;
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
}
