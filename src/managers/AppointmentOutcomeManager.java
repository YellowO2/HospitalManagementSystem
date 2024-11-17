/**
 * Manages operations related to appointment outcome records, including creating,
 * retrieving, updating, and viewing records.
 */
package managers;

import database.AppointmentOutcomeRecordDB;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import appointments.AppointmentOutcomeRecord;

public class AppointmentOutcomeManager {
    private AppointmentOutcomeRecordDB appointmentOutcomeRecordDB;

    /**
     * Constructor to initialize the AppointmentOutcomeManager with a database
     * instance.
     *
     * @param appointmentOutcomeRecordDB the database instance for appointment
     *                                   outcome records.
     */
    public AppointmentOutcomeManager(AppointmentOutcomeRecordDB appointmentOutcomeRecordDB) {
        this.appointmentOutcomeRecordDB = appointmentOutcomeRecordDB;
    }

    /**
     * Creates a new appointment outcome record.
     *
     * @param patientId         the ID of the patient.
     * @param appointmentDate   the date of the appointment.
     * @param serviceProvided   the service provided during the appointment.
     * @param prescribedStatus  the status of the prescription (e.g., Pending,
     *                          Fulfilled).
     * @param consultationNotes notes from the consultation.
     * @return true if the record is successfully created, false otherwise.
     */
    public boolean createOutcomeRecord(String patientId, LocalDate appointmentDate,
            String serviceProvided, String prescribedStatus, String consultationNotes) {
        String appointmentId = UUID.randomUUID().toString();
        AppointmentOutcomeRecord newRecord = new AppointmentOutcomeRecord(
                appointmentId, patientId, appointmentDate, serviceProvided, "", prescribedStatus, consultationNotes);

        return appointmentOutcomeRecordDB.create(newRecord);
    }

    /**
     * Retrieves an appointment outcome record by its ID.
     *
     * @param appointmentId the ID of the appointment.
     * @return the appointment outcome record, or null if not found.
     */
    public AppointmentOutcomeRecord getOutcomeRecord(String appointmentId) {
        return appointmentOutcomeRecordDB.getById(appointmentId);
    }

    /**
     * Retrieves all appointment outcome records.
     *
     * @return a list of all appointment outcome records.
     */
    public List<AppointmentOutcomeRecord> getAllOutcomeRecords() {
        return appointmentOutcomeRecordDB.getAll();
    }

    /**
     * Displays appointment outcome records for a specific patient.
     *
     * @param patientId the ID of the patient.
     */
    public void viewPatientOutcomeRecords(String patientId) {
        List<AppointmentOutcomeRecord> records = appointmentOutcomeRecordDB.getByPatientId(patientId);
        if (records.isEmpty()) {
            System.out.println("No appointment outcome records found for patient " + patientId);
        } else {
            System.out.println("Viewing past appointment outcome records...");
            System.out.println("========================================");
            System.out.println("Appointment outcome records for patient " + patientId + ":");
            for (AppointmentOutcomeRecord record : records) {
                System.out.println("----------------------------------------");
                System.out.println("Appointment ID: " + record.getAppointmentId());
                System.out.println("Date: " + record.getAppointmentDate());
                System.out.println("Service Provided: " + record.getServiceProvided());
                System.out.println("Prescribed Medications: " + formatMedications(record.getMedications()));
                System.out.println("Prescription Status: " + record.getPrescribedStatus());
                System.out.println("Consultation Notes: " + record.getConsultationNotes());
            }
            System.out.println("========================================");
        }
    }

    /**
     * Formats prescribed medications into a user-friendly string.
     *
     * @param prescribedMedications the medications in a pipe-separated format.
     * @return the formatted string of medications.
     */
    private String formatMedications(String prescribedMedications) {
        if (prescribedMedications == null || prescribedMedications.isEmpty()) {
            return "None";
        }
        StringBuilder formatted = new StringBuilder();
        String[] medications = prescribedMedications.split("\\|");
        for (String med : medications) {
            formatted.append("- ").append(med).append("\n");
        }
        return formatted.toString().trim();
    }

    /**
     * Updates an existing appointment outcome record.
     *
     * @param updatedRecord the updated appointment outcome record.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updateOutcomeRecord(AppointmentOutcomeRecord updatedRecord) {
        return appointmentOutcomeRecordDB.update(updatedRecord);
    }

    /**
     * Creates and records an appointment outcome for a specific session.
     *
     * @param appointmentId     the ID of the appointment.
     * @param patientId         the ID of the patient.
     * @param appointmentDate   the date of the appointment.
     * @param serviceProvided   the service provided during the appointment.
     * @param prescription      the prescribed medications.
     * @param prescribedStatus  the status of the prescription.
     * @param consultationNotes notes from the consultation.
     * @return true if the record is successfully created, false otherwise.
     */
    public boolean recordAppointmentOutcome(String appointmentId, String patientId, LocalDate appointmentDate,
            String serviceProvided, String prescription, String prescribedStatus,
            String consultationNotes) {
        AppointmentOutcomeRecord newRecord = new AppointmentOutcomeRecord(
                appointmentId,
                patientId,
                appointmentDate,
                serviceProvided,
                prescription,
                prescribedStatus,
                consultationNotes);
        return appointmentOutcomeRecordDB.create(newRecord);
    }

    /**
     * Updates the prescription status of an appointment outcome record.
     *
     * @param appointmentId the ID of the appointment.
     * @param newStatus     the new prescription status.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updatePrescriptionStatus(String appointmentId, String newStatus) {
        AppointmentOutcomeRecord record = appointmentOutcomeRecordDB.getById(appointmentId);
        if (record != null) {
            record.setPrescribedStatus(newStatus);
            return appointmentOutcomeRecordDB.update(record);
        }
        return false;
    }

    /**
     * Gets the database instance for appointment outcome records.
     *
     * @return the appointment outcome record database.
     */
    public AppointmentOutcomeRecordDB getAppointmentOutcomeRecordDB() {
        return this.appointmentOutcomeRecordDB;
    }
}
