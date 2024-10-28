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

    // Public method to view the entire medical record
    public void viewMedicalRecord() {
        System.out.println("Medical Record for Patient ID: " + patientId);
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Gender: " + gender);
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email Address: " + emailAddress);

        System.out.println("\nDiagnoses:");
        if (diagnoses.isEmpty()) {
            System.out.println("No diagnoses available.");
        } else {
            for (Diagnosis diagnosis : diagnoses) {
                diagnosis.viewDiagnosis();
            }
        }

        System.out.println("\nTreatments:");
        if (treatments.isEmpty()) {
            System.out.println("No treatments available.");
        } else {
            for (Treatment treatment : treatments) {
                treatment.viewTreatment();
            }
        }

        System.out.println("\nPrescriptions:");
        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions available.");
        } else {
            for (Prescription prescription : prescriptions) {
                System.out.println(prescription.getDescriptionDetails());

            }
        }
    }

    // Method to add a diagnosis (only for significant diagnoses)
    public void addDiagnosis(Diagnosis diagnosis) {
        diagnoses.add(diagnosis);
        System.out.println("Diagnosis added: " + diagnosis.getDiagnosisName());
    }

    // Method to add a treatment (only for significant treatments)
    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
        System.out.println("Treatment added: " + treatment.getTreatmentName());
    }

    // Method to add a prescription (only for necessary prescriptions)
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        System.out.println("Prescription added: " + prescription.getMedicationName());
    }

    // Allow patients to update contact information
    public void updateContactInfo(String newPhoneNumber, String newEmailAddress) {
        this.phoneNumber = newPhoneNumber;
        this.emailAddress = newEmailAddress;
        System.out.println("Contact information updated.");
    }

    // Getters for non-modifiable fields (e.g., blood type, date of birth)
    public String getBloodType() {
        return bloodType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    // Getter for prescriptions (only for viewing, not modifying by patients)
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }
}
