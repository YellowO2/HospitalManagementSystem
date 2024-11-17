package appointments;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import medicalrecords.Prescription;

/**
 * Represents the outcome of an appointment, including details such as services
 * provided,
 * prescriptions, consultation notes, and the prescription status.
 */
public class AppointmentOutcomeRecord {
    private String appointmentId; // ID of the related appointment
    private LocalDate appointmentDate; // Date of the appointment
    private String patientId; // ID of the patient
    private String serviceProvided; // Type of service provided during the appointment
    private List<Prescription> prescriptions; // List of prescriptions provided
    private String prescribedStatus; // Status of the prescriptions (e.g., "Pending", "Fulfilled")
    private String consultationNotes; // Notes provided by the doctor

    /**
     * Constructs an AppointmentOutcomeRecord with the specified details.
     *
     * @param appointmentId      the ID of the related appointment
     * @param patientId          the ID of the patient
     * @param appointmentDate    the date of the appointment
     * @param serviceProvided    the type of service provided during the appointment
     * @param prescriptionString a semicolon-separated string of prescriptions
     * @param prescribedStatus   the status of the prescriptions
     * @param consultationNotes  the doctor's notes
     */
    public AppointmentOutcomeRecord(
            String appointmentId,
            String patientId,
            LocalDate appointmentDate,
            String serviceProvided,
            String prescriptionString,
            String prescribedStatus,
            String consultationNotes) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
        this.serviceProvided = serviceProvided;
        this.prescriptions = parsePrescriptions(prescriptionString);
        this.prescribedStatus = prescribedStatus;
        this.consultationNotes = consultationNotes;
    }

    /**
     * Parses a semicolon-separated string of prescriptions into a list of
     * Prescription objects.
     *
     * @param prescriptionString the semicolon-separated string of prescriptions
     * @return a list of Prescription objects
     */
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

    /**
     * Gets the ID of the related appointment.
     *
     * @return the appointment ID
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the ID of the related appointment.
     *
     * @param appointmentId the new appointment ID
     */
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the ID of the patient.
     *
     * @return the patient ID
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Gets the date of the appointment.
     *
     * @return the appointment date
     */
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Sets the date of the appointment.
     *
     * @param appointmentDate the new appointment date
     */
    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Gets the type of service provided during the appointment.
     *
     * @return the type of service provided
     */
    public String getServiceProvided() {
        return serviceProvided;
    }

    /**
     * Sets the type of service provided during the appointment.
     *
     * @param serviceProvided the new type of service
     */
    public void setServiceProvided(String serviceProvided) {
        this.serviceProvided = serviceProvided;
    }

    /**
     * Gets the status of the prescriptions.
     *
     * @return the prescribed status
     */
    public String getPrescribedStatus() {
        return prescribedStatus;
    }

    /**
     * Sets the status of the prescriptions.
     *
     * @param prescribedStatus the new prescribed status
     */
    public void setPrescribedStatus(String prescribedStatus) {
        this.prescribedStatus = prescribedStatus;
    }

    /**
     * Gets the doctor's consultation notes.
     *
     * @return the consultation notes
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Sets the doctor's consultation notes.
     *
     * @param consultationNotes the new consultation notes
     */
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    /**
     * Gets a comma-separated string of all prescribed medications.
     *
     * @return a comma-separated string of medication names
     */
    public String getMedications() {
        String medications = "";
        for (Prescription prescription : prescriptions) {
            medications += prescription.getMedicationName() + ", ";
        }
        medications = medications.substring(0, medications.length() - 2);
        return medications;
    }

    /**
     * Returns a string representation of the appointment outcome record in CSV
     * format.
     *
     * @return a CSV-formatted string representing the outcome record
     */
    @Override
    public String toString() {
        String prescriptionsString = "";
        for (Prescription prescription : prescriptions) {
            prescriptionsString += prescription.toString() + ";";
        }
        prescriptionsString = prescriptionsString.substring(0, prescriptionsString.length() - 1);

        return appointmentId + "," +
                patientId + "," +
                appointmentDate + "," +
                serviceProvided + "," +
                prescriptionsString + "," +
                prescribedStatus + "," +
                consultationNotes;
    }
}
