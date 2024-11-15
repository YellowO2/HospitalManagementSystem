package medicalrecords;

import appointments.AppointmentOutcomeRecord;

public class PrescriptionManager {

    private MedicalRecord medicalRecord;

    public PrescriptionManager(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    // Method to check if a prescription already exists in the patient's medical histories
    public boolean prescriptionExists(Prescription newPrescription) {
        return medicalRecord.getPrescriptions().stream()
                .anyMatch(prescription -> prescription.getMedicationName().equals(newPrescription.getMedicationName())
                && prescription.getDosage().equals(newPrescription.getDosage())
                && prescription.getFrequency().equals(newPrescription.getFrequency()));
    }

    // Method to add a prescription to both the medical history and the appointment outcome record
    public void addPrescription(Prescription newPrescription, AppointmentOutcomeRecord appointmentOutcomeRecord) {
        if (!prescriptionExists(newPrescription)) {
            medicalRecord.addPrescription(newPrescription); // Add to medical history if not already present
        }

        // Append the new prescription to the `medications` string field
        String newMedications = appointmentOutcomeRecord.getMedications() + "; " + newPrescription.toString();
        appointmentOutcomeRecord.setMedications(newMedications.trim());
    }
}
