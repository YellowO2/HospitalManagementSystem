package medicalrecords;

import appointments.AppointmentOutcomeRecord;
import java.util.List;

public class PrescriptionManager {

    private MedicalRecord medicalRecord;

    public PrescriptionManager(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    // Method to check if a prescription already exists in the patient's medical
    // histories
    public boolean prescriptionExists(Prescription newPrescription) {
        return medicalRecord.getPrescriptions().stream()
                .anyMatch(prescription -> prescription.getMedicationName().equals(newPrescription.getMedicationName())
                        && prescription.getDosage().equals(newPrescription.getDosage())
                        && prescription.getFrequency().equals(newPrescription.getFrequency()));
    }

    // Method to add a prescription to both the medical history and the appointment
    // outcome record
    public void addPrescription(Prescription newPrescription, AppointmentOutcomeRecord appointmentOutcomeRecord) {
        if (!prescriptionExists(newPrescription)) {
            medicalRecord.addPrescription(newPrescription); // Add to medical history if not already present
        }
        List<Prescription> appointmentPrescriptions = appointmentOutcomeRecord.getPrescriptions();
        appointmentPrescriptions.add(newPrescription); // Always add to the appointment outcome record
        appointmentOutcomeRecord.setPrescriptions(appointmentPrescriptions);
    }
}
