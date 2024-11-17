/**
 * Manages the operations related to medical records, including creation, 
 * updating, deletion, and retrieval of medical history.
 */
package managers;

import database.MedicalRecordDB;
import medicalrecords.Diagnosis;
import medicalrecords.MedicalRecord;
import medicalrecords.Prescription;
import medicalrecords.Treatment;

public class MedicalRecordManager {
    // Reference to the MedicalRecordDB
    private MedicalRecordDB database;

    /**
     * Constructor to initialize the MedicalRecordManager with a database instance.
     *
     * @param database the MedicalRecordDB instance.
     */
    public MedicalRecordManager(MedicalRecordDB database) {
        this.database = database;
    }

    /**
     * Adds a new medical record for a patient.
     *
     * @param patientId     the ID of the patient.
     * @param medicalRecord the medical record to be added.
     * @return true if the record was successfully added, false otherwise.
     */
    public boolean addMedicalRecord(String patientId, MedicalRecord medicalRecord) {
        if (medicalRecord != null && patientId != null && !patientId.isEmpty()) {
            return database.create(medicalRecord);
        }
        return false;
    }

    /**
     * Updates an existing medical record with new diagnosis, prescription,
     * or treatment details.
     *
     * @param patientId    the ID of the patient.
     * @param diagnosis    the new diagnosis to be added.
     * @param prescription the new prescription to be added.
     * @param treatment    the new treatment to be added.
     * @return true if the record was successfully updated, false otherwise.
     */
    public boolean updateMedicalRecord(String patientId, Diagnosis diagnosis, Prescription prescription,
            Treatment treatment) {
        MedicalRecord record = database.getById(patientId);
        if (record != null) {
            if (diagnosis != null) {
                record.addDiagnosis(diagnosis);
            }
            if (prescription != null) {
                record.addPrescription(prescription);
            }
            if (treatment != null) {
                record.addTreatment(treatment);
            }
            return database.update(record);
        }
        return false;
    }

    /**
     * Deletes a medical record of a patient.
     *
     * @param patientId the ID of the patient whose record is to be deleted.
     * @return true if the record was successfully deleted, false otherwise.
     */
    public boolean deleteMedicalRecord(String patientId) {
        return database.delete(patientId);
    }

    /**
     * Retrieves the medical history of a patient as a formatted string.
     *
     * @param patientId the ID of the patient.
     * @return a string describing the medical history, or null if no record is
     *         found.
     */
    public String getMedicalHistory(String patientId) {
        MedicalRecord record = database.getById(patientId);
        return (record != null) ? record.getMedicalRecordDescription() : null;
    }

    /**
     * Updates the contact information for a patient in their medical record.
     *
     * @param patientId       the ID of the patient.
     * @param newPhoneNumber  the new phone number to update.
     * @param newEmailAddress the new email address to update.
     * @return true if the contact information was successfully updated, false
     *         otherwise.
     */
    public boolean updateContactInfo(String patientId, String newPhoneNumber, String newEmailAddress) {
        MedicalRecord record = database.getById(patientId);
        if (record != null) {
            record.updateContactInfo(newPhoneNumber, newEmailAddress);
            return database.update(record);
        }
        return false;
    }
}
