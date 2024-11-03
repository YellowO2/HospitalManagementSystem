package medicalrecords;

import java.util.HashMap;
import java.util.Map;

public class MedicalRecordManager {
    // Map to store medical records, with patient ID as the key
    private Map<String, MedicalRecord> medicalRecords;

    // Constructor
    public MedicalRecordManager() {
        this.medicalRecords = new HashMap<>();
    }

    // Add a new medical record for a patient
    public void addMedicalRecord(String patientId, MedicalRecord medicalRecord) {
        if (medicalRecord != null && patientId != null && !patientId.isEmpty()) {
            medicalRecords.put(patientId, medicalRecord);
        } else {
            throw new IllegalArgumentException("Invalid patient ID or medical record");
        }
    }

    // Retrieve a medical record by patient ID
    public MedicalRecord getMedicalRecord(String patientId) {
        if (medicalRecords.containsKey(patientId)) {
            return medicalRecords.get(patientId);
        } else {
            throw new IllegalArgumentException("Medical record for patient ID " + patientId + " does not exist");
        }
    }

    // Update an existing medical record (for use by doctors)
    public void updateMedicalRecord(String patientId, Diagnosis diagnosis, Prescription prescription,
            Treatment treatment) {
        MedicalRecord record = medicalRecords.get(patientId);
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
        } else {
            throw new IllegalArgumentException("Medical record for this patient ID does not exist");
        }
    }

    // Delete a medical record (for potential administrative actions)
    public void deleteMedicalRecord(String patientId) {
        if (medicalRecords.containsKey(patientId)) {
            medicalRecords.remove(patientId);
        } else {
            throw new IllegalArgumentException("Medical record for this patient ID does not exist");
        }
    }

    // Check if a medical record exists for a patient ID
    public boolean hasMedicalRecord(String patientId) {
        return medicalRecords.containsKey(patientId);
    }

    // View past diagnoses and treatments (for patients)
    public String viewMedicalHistory(String patientId) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record != null) {
            return record.getMedicalHistory();
        } else {
            throw new IllegalArgumentException("Medical record for patient ID " + patientId + " does not exist");
        }
    }

    // Update non-medical information such as contact info (for patients)
    public void updateContactInfo(String patientId, String newPhoneNumber, String newEmailAddress) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record != null) {
            record.updateContactInfo(newPhoneNumber, newEmailAddress);
        } else {
            throw new IllegalArgumentException("Medical record for patient ID " + patientId + " does not exist");
        }
    }

    // For pharmacists to update prescription statuses
    // TODO: Decide if medical record manager should be responsible for updating
    // prescription status
    // public void updatePrescriptionStatus(String patientId, String prescriptionId,
    // String newStatus) {
    // MedicalRecord record = medicalRecords.get(patientId);
    // if (record != null) {
    // record.updatePrescriptionStatus(prescriptionId, newStatus);
    // } else {
    // throw new IllegalArgumentException("Medical record for this patient ID does
    // not exist");
    // }
    // }
}
