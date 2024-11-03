package medicalrecords;

import java.util.List;
import java.util.ArrayList;

public class MedicalRecord {
    private String patientId;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String bloodType;
    private String phoneNumber;
    private String emailAddress;

    // List of diagnoses, treatments, and prescriptions
    private List<Diagnosis> diagnoses;
    private List<Treatment> treatments;
    private List<Prescription> prescriptions;

    // Constructor
    public MedicalRecord(String patientId, String name, String dateOfBirth, String gender, String bloodType,
            String phoneNumber, String emailAddress) {
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
    }

    // Method to view the entire medical record as a formatted string
    public String getMedicalRecordDescription() {
        StringBuilder record = new StringBuilder();
        record.append("Patient ID: ").append(patientId).append("\n")
                .append("Name: ").append(name).append("\n")
                .append("Date of Birth: ").append(dateOfBirth).append("\n")
                .append("Gender: ").append(gender).append("\n")
                .append("Blood Type: ").append(bloodType).append("\n")
                .append("Phone Number: ").append(phoneNumber).append("\n")
                .append("Email Address: ").append(emailAddress).append("\n\n");

        // Add diagnoses string
        record.append("Diagnoses:\n");
        if (diagnoses.isEmpty()) {
            record.append("No diagnoses available.\n");
        } else {
            for (Diagnosis diagnosis : diagnoses) {
                record.append(diagnosis.getDiagnosisDetails()).append("\n");
            }
        }

        // Add treatments string
        record.append("\nTreatments:\n");
        if (treatments.isEmpty()) {
            record.append("No treatments available.\n");
        } else {
            for (Treatment treatment : treatments) {
                record.append(treatment.getTreatment()).append("\n");
            }
        }

        // Add prescriptions string
        record.append("\nPrescriptions:\n");
        if (prescriptions.isEmpty()) {
            record.append("No prescriptions available.\n");
        } else {
            for (Prescription prescription : prescriptions) {
                record.append(prescription.getDescriptionDetails()).append("\n");
            }
        }

        return record.toString();
    }

    // Method to add a diagnosis
    public void addDiagnosis(Diagnosis diagnosis) {
        if (diagnosis != null) {
            diagnoses.add(diagnosis);
        }
    }

    // Method to remove a diagnosis
    public void removeDiagnosis(Diagnosis diagnosis) {
        diagnoses.remove(diagnosis);
    }

    // Method to add a treatment
    public void addTreatment(Treatment treatment) {
        if (treatment != null) {
            treatments.add(treatment);
        }
    }

    // Method to remove a treatment
    public void removeTreatment(Treatment treatment) {
        treatments.remove(treatment);
    }

    // Method to add a prescription
    public void addPrescription(Prescription prescription) {
        if (prescription != null) {
            prescriptions.add(prescription);
        }
    }

    // Method to remove a prescription
    public void removePrescription(Prescription prescription) {
        prescriptions.remove(prescription);
    }

    // // Method to update the status of a prescription
    // TODO: Should see if prescription has id
    // public void updatePrescriptionStatus(String prescriptionId, String newStatus)
    // {
    // for (Prescription prescription : prescriptions) {
    // if (prescription.getPrescriptionId().equals(prescriptionId)) {
    // prescription.setStatus(newStatus);
    // break;
    // }
    // }
    // }

    // Allow patients to update contact information
    public void updateContactInfo(String newPhoneNumber, String newEmailAddress) {
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            this.phoneNumber = newPhoneNumber;
        }
        if (newEmailAddress != null && !newEmailAddress.isEmpty()) {
            this.emailAddress = newEmailAddress;
        }
    }

    // Getters for non-modifiable fields
    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    // Getter for prescriptions
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    // Method to retrieve medical history (for patients to view)
    public String getMedicalHistory() {
        StringBuilder history = new StringBuilder();

        history.append("Medical History for ").append(name).append(" (Patient ID: ").append(patientId).append(")\n");

        // Add diagnoses
        history.append("Diagnoses:\n");
        if (diagnoses.isEmpty()) {
            history.append("No diagnoses available.\n");
        } else {
            for (Diagnosis diagnosis : diagnoses) {
                history.append(diagnosis.getDiagnosisDetails()).append("\n");
            }
        }

        // Add treatments
        history.append("\nTreatments:\n");
        if (treatments.isEmpty()) {
            history.append("No treatments available.\n");
        } else {
            for (Treatment treatment : treatments) {
                history.append(treatment.getTreatment()).append("\n");
            }
        }

        // Add prescriptions
        history.append("\nPrescriptions:\n");
        if (prescriptions.isEmpty()) {
            history.append("No prescriptions available.\n");
        } else {
            for (Prescription prescription : prescriptions) {
                history.append(prescription.getDescriptionDetails()).append("\n");
            }
        }

        return history.toString();
    }
}
