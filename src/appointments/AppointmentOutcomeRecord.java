package appointments;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import medicalrecords.Prescription;

public class AppointmentOutcomeRecord {
    private String appointmentId; // Links to the appointment
    private LocalDate appointmentDate;
    private String serviceProvided; // Type of service (e.g., consultation, X-ray)
    private List<Prescription> prescriptions; // Parsed prescriptions list
    private String prescribedStatus; // Status of the prescription (e.g., "Pending", "Fulfilled")
    private String consultationNotes; // Doctor's notes

    // Constructor
    public AppointmentOutcomeRecord(
            String appointmentId,
            LocalDate appointmentDate,
            String serviceProvided,
            String prescriptionString,
            String prescribedStatus,
            String consultationNotes) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.serviceProvided = serviceProvided;
        this.prescriptions = parsePrescriptions(prescriptionString);
        this.prescribedStatus = prescribedStatus;
        this.consultationNotes = consultationNotes;
    }

    private List<Prescription> parsePrescriptions(String prescriptionString) {
        List<Prescription> prescriptionsList = new ArrayList<>();
        String[] prescriptionsArray = prescriptionString.split(";");

        for (String presc : prescriptionsArray) {
            try {
                Prescription prescription = Prescription.fromCSV(presc);
                prescriptionsList.add(prescription);
            } catch (IllegalArgumentException e) {
                System.err.println("Error parsing prescription: " + e.getMessage());
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

    public String getPrescribedStatus() {
        return prescribedStatus;
    }

    public void setPrescribedStatus(String prescribedStatus) {
        this.prescribedStatus = prescribedStatus;
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

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public String getMedications() {
        String medications = "";
        for (Prescription prescription : prescriptions) {
            medications += prescription.getMedicationName() + ", ";
        }
        medications = medications.substring(0, medications.length() - 2);
        return medications;
    }

    // public void setMedications(String medications) {
    // this.medications = medications;
    // }

    @Override
    public String toString() {
        return appointmentId + "," +
                appointmentDate + "," +
                serviceProvided + "," +
                prescriptions.toString() + "," +
                prescribedStatus + "," +
                consultationNotes;
    }
}