package medicalrecords;

import database.HMSDatabase;

public class MedicalRecordManager {
    // Reference to the database
    private HMSDatabase database;

    // Constructor
    public MedicalRecordManager() {
        this.database = HMSDatabase.getInstance(); // Get the instance of the database
    }

    // Add a new medical record for a patient
    public boolean addMedicalRecord(String patientId, MedicalRecord medicalRecord) {
        if (medicalRecord != null && patientId != null && !patientId.isEmpty()) {
            // Add to database
            database.createMedicalRecord(medicalRecord);
            return true;
        } else {
            return false; // Invalid input
        }
    }

    // Update an existing medical record (for use by doctors)
    public boolean updateMedicalRecord(String patientId, Diagnosis diagnosis, Prescription prescription,
            Treatment treatment) {
        MedicalRecord record = database.getMedicalRecordByPatientId(patientId);
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
            // Update in database
            return database.updateMedicalRecord(record);
        }
        return false; // Record not found
    }

    // Delete a medical record (for potential administrative actions)
    public boolean deleteMedicalRecord(String patientId) {
        return database.deleteMedicalRecord(patientId);
    }

    // View past diagnoses and treatments (for patients)
    public String getMedicalHistory(String patientId) {
        MedicalRecord record = database.getMedicalRecordByPatientId(patientId);
        return (record != null) ? record.getMedicalRecordDescription() : null; // Return null if not found
    }

    // Update non-medical information such as contact info (for patients)
    // TODO: Consider if i should also update the contact info in patient class
    public boolean updateContactInfo(String patientId, String newPhoneNumber, String newEmailAddress) {
        MedicalRecord record = database.getMedicalRecordByPatientId(patientId);
        if (record != null) {
            record.updateContactInfo(newPhoneNumber, newEmailAddress);
            return database.updateMedicalRecord(record); // Asks database to update it
        }
        return false; // Record not found
    }
}
