package appointments;

import java.time.LocalDate;

public class AppointmentOutcomeRecord {
    private String appointmentId; // Links to the appointment
    private LocalDate appointmentDate;
    private String serviceProvided; // Type of service (e.g., consultation, X-ray)
    private String medications; // Medications prescribed as a single string
    private String prescribedStatus; // Status of the prescription (e.g., "Pending", "Fulfilled")
    private String consultationNotes; // Doctor's notes

    // Constructor
    public AppointmentOutcomeRecord(String appointmentId, LocalDate appointmentDate, String serviceProvided,
                                    String medications, String prescribedStatus, String consultationNotes) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.serviceProvided = serviceProvided;
        this.medications = medications;
        this.prescribedStatus = prescribedStatus;
        this.consultationNotes = consultationNotes;
    }

    // Getters and setters
    public String getAppointmentId() {
        return appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public String getServiceProvided() {
        return serviceProvided;
    }

    public void setServiceProvided(String serviceProvided) {
        this.serviceProvided = serviceProvided;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getPrescribedStatus() {
        return prescribedStatus;
    }

    public void setPrescribedStatus(String prescribedStatus) {
        this.prescribedStatus = prescribedStatus;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    @Override
    public String toString() {
        return appointmentId + "," +
               appointmentDate + "," +
               serviceProvided + "," +
               medications + "," +
               prescribedStatus + "," +
               consultationNotes;
    }

    // Static method to parse a CSV line into an AppointmentOutcomeRecord object
    public static AppointmentOutcomeRecord fromCSV(String csvLine) throws IllegalArgumentException {
        String[] fields = csvLine.split(",");
        if (fields.length < 6) {
            throw new IllegalArgumentException("Invalid CSV format");
        }

        String appointmentId = fields[0];
        LocalDate appointmentDate = LocalDate.parse(fields[1]);
        String serviceProvided = fields[2];
        String medications = fields[3];
        String prescribedStatus = fields[4];
        String consultationNotes = fields[5];

        return new AppointmentOutcomeRecord(appointmentId, appointmentDate, serviceProvided, medications, prescribedStatus, consultationNotes);
    }
}