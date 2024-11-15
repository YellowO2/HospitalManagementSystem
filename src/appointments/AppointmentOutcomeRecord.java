package appointments;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import medicalrecords.Prescription;

public class AppointmentOutcomeRecord {
    private String appointmentId; // Links to the appointment
    private LocalDate appointmentDate;
    private String serviceProvided; // Type of service (e.g., consultation, X-ray)
    private List<Prescription> prescriptions;
    private String medicationStatus;
    private String consultationNotes; // Doctor's notes

    // Constructor
    public AppointmentOutcomeRecord(
            String appointmentId,
            LocalDate appointmentDate,
            String serviceProvided,
            String prescriptionString,
            String medicationStatus,
            String consultationNotes) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.serviceProvided = serviceProvided;
        this.prescriptions = parsePrescriptions(prescriptionString);
        this.medicationStatus = medicationStatus;
        this.consultationNotes = consultationNotes;
    }

    private List<Prescription> parsePrescriptions(String prescriptionString) {
        List<Prescription> prescriptionsList = new ArrayList<>();

        // Split the prescriptions based on a semicolon delimiter
        String[] prescriptionsArray = prescriptionString.split(";");

        for (String presc : prescriptionsArray) {
            try {
                // Use the static factory method fromCSV to create a Prescription
                Prescription prescription = Prescription.fromCSV(presc);
                prescriptionsList.add(prescription);
            } catch (IllegalArgumentException e) {
                // FOR DEBUGGING
                System.out.println("Error parsing prescription: " + e.getMessage());
            }
        }

        return prescriptionsList;
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
