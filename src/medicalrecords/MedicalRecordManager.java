package medicalrecords;

import database.MedicalRecordDB;

public class MedicalRecordManager {
    // Reference to the MedicalRecordDB
    private MedicalRecordDB database;

    // Constructor
    public MedicalRecordManager(MedicalRecordDB database) {
        this.database = database; // Create an instance directly with the filename
    }

    // Add a new medical record for a patient
    public boolean addMedicalRecord(String patientId, MedicalRecord medicalRecord) {
        if (medicalRecord != null && patientId != null && !patientId.isEmpty()) {
            // Add to database
            return database.create(medicalRecord); // Use the create method from MedicalRecordDB
        } else {
            return false; // Invalid input
        }
    }

    // Update an existing medical record (for use by doctors)
    public boolean updateMedicalRecord(String patientId, Diagnosis diagnosis, Prescription prescription,
            Treatment treatment) {
        MedicalRecord record = database.getById(patientId); // Retrieve the medical record using patient ID
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
            return database.update(record); // Update the record in the database
        }
        return false; // Record not found
    }

    // Delete a medical record (for potential administrative actions)
    public boolean deleteMedicalRecord(String patientId) {
        return database.delete(patientId); // Use the delete method to remove the record
    }

    // View past diagnoses and treatments (for patients)
    public String getMedicalHistory(String patientId) {
        MedicalRecord record = database.getById(patientId); // Retrieve the medical record using patient ID
        return (record != null) ? record.getMedicalRecordDescription() : null; // Return the description if found

    }

    // Update non-medical information such as contact info (for patients)
    public boolean updateContactInfo(String patientId, String newPhoneNumber, String newEmailAddress) {
        MedicalRecord record = database.getById(patientId); // Retrieve the medical record using patient ID
        if (record != null) {
            record.updateContactInfo(newPhoneNumber, newEmailAddress);
            return database.update(record); // Update the medical record in the database
        }
        return false; // Record not found
    }
}
