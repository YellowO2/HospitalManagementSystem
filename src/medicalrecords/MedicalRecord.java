package medicalrecords;

import java.util.ArrayList;
import java.util.List;

/**
 * The MedicalRecord class represents a comprehensive record of a patient's
 * medical history. It includes patient information, diagnoses, treatments, and
 * prescriptions. The class provides methods for parsing input strings into
 * medical components and viewing the full medical record.
 */
public class MedicalRecord {

    private String patientId; // Unique ID of the patient
    private String name; // Name of the patient
    private String dateOfBirth; // Date of birth of the patient
    private String gender; // Gender of the patient
    private String bloodType; // Blood type of the patient
    private String phoneNumber; // Contact number of the patient
    private String emailAddress; // Email address of the patient
    private List<Diagnosis> diagnoses; // List of diagnoses for the patient
    private List<Treatment> treatments; // List of treatments for the patient
    private List<Prescription> prescriptions; // List of prescriptions for the patient

    /**
     * Constructs a MedicalRecord object with the specified patient information
     * and medical history.
     *
     * @param patientId the ID of the patient
     * @param name the name of the patient
     * @param dateOfBirth the date of birth of the patient
     * @param gender the gender of the patient
     * @param bloodType the blood type of the patient
     * @param phoneNumber the contact number of the patient
     * @param emailAddress the email address of the patient
     * @param diagnosisString a string containing diagnoses in CSV format
     * @param treatmentString a string containing treatments in CSV format
     * @param prescriptionString a string containing prescriptions in CSV format
     */
    public MedicalRecord(String patientId, String name, String dateOfBirth, String gender, String bloodType,
            String phoneNumber, String emailAddress, String diagnosisString,
            String treatmentString, String prescriptionString) {
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.diagnoses = parseDiagnoses(diagnosisString);
        this.treatments = parseTreatments(treatmentString);
        this.prescriptions = parsePrescriptions(prescriptionString);
    }

    /**
     * Parses a CSV-formatted string of diagnoses and returns a list of
     * Diagnosis objects.
     *
     * @param diagnosisString the string containing diagnoses separated by
     * semicolons
     * @return a list of Diagnosis objects
     */
    private List<Diagnosis> parseDiagnoses(String diagnosisString) {
        List<Diagnosis> diagnosesList = new ArrayList<>();
        String[] diagnosesArray = diagnosisString.split(";");
        for (String diag : diagnosesArray) {
            Diagnosis diagnosis = Diagnosis.fromCSV(diag);
            diagnosesList.add(diagnosis);
        }
        return diagnosesList;
    }

    /**
     * Parses a CSV-formatted string of treatments and returns a list of
     * Treatment objects.
     *
     * @param treatmentString the string containing treatments separated by
     * semicolons
     * @return a list of Treatment objects
     */
    private List<Treatment> parseTreatments(String treatmentString) {
        List<Treatment> treatmentsList = new ArrayList<>();
        String[] treatmentsArray = treatmentString.split(";");
        for (String treat : treatmentsArray) {
            Treatment treatment = Treatment.fromCSV(treat);
            treatmentsList.add(treatment);
        }
        return treatmentsList;
    }

    /**
     * Parses a CSV-formatted string of prescriptions and returns a list of
     * Prescription objects.
     *
     * @param prescriptionString the string containing prescriptions separated
     * by semicolons
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
                System.out.println("Error parsing prescription: " + e.getMessage());
            }
        }

        return prescriptionsList;
    }

    /**
     * Returns the entire medical record as a formatted string.
     *
     * @return a formatted string representation of the medical record
     */
    public String getMedicalRecordDescription() {
        StringBuilder record = new StringBuilder();
        record.append("Patient ID: ").append(patientId).append("\n")
                .append("Name: ").append(name).append("\n")
                .append("Date of Birth: ").append(dateOfBirth).append("\n")
                .append("Gender: ").append(gender).append("\n")
                .append("Blood Type: ").append(bloodType).append("\n")
                .append("Phone Number: ").append(phoneNumber).append("\n")
                .append("Email Address: ").append(emailAddress).append("\n\n");

        // Add diagnoses section
        record.append("-----Diagnoses-----\n");
        if (diagnoses.isEmpty()) {
            record.append("No diagnoses available.\n");
        } else {
            for (Diagnosis diagnosis : diagnoses) {
                record.append(diagnosis.getDiagnosisDetails()).append("\n\n");
            }
        }

        // Add treatments section
        record.append("-----Treatments-----\n");
        if (treatments.isEmpty()) {
            record.append("No treatments available.\n");
        } else {
            for (Treatment treatment : treatments) {
                record.append(treatment.getTreatment()).append("\n\n");
            }
        }

        // Add prescriptions section
        record.append("-----Prescriptions-----\n");
        if (prescriptions.isEmpty()) {
            record.append("No prescriptions available.\n");
        } else {
            for (Prescription prescription : prescriptions) {
                record.append(prescription.getDescriptionDetails()).append("\n\n");
            }
        }

        return record.toString();
    }

    /**
     * Adds a diagnosis to the list of diagnoses.
     *
     * @param diagnosis the Diagnosis object to add
     */
    public void addDiagnosis(Diagnosis diagnosis) {
        if (diagnosis != null) {
            diagnoses.add(diagnosis);
        }
    }

    /**
     * Removes a diagnosis from the list of diagnoses.
     *
     * @param diagnosis the Diagnosis object to remove
     */
    public void removeDiagnosis(Diagnosis diagnosis) {
        diagnoses.remove(diagnosis);
    }

    /**
     * Adds a treatment to the list of treatments.
     *
     * @param treatment the Treatment object to add
     */
    public void addTreatment(Treatment treatment) {
        if (treatment != null) {
            treatments.add(treatment);
        }
    }

    /**
     * Removes a treatment from the list of treatments.
     *
     * @param treatment the Treatment object to remove
     */
    public void removeTreatment(Treatment treatment) {
        treatments.remove(treatment);
    }

    /**
     * Adds a prescription to the list of prescriptions.
     *
     * @param prescription the Prescription object to add
     */
    public void addPrescription(Prescription prescription) {
        if (prescription != null) {
            prescriptions.add(prescription);
        }
    }

    /**
     * Removes a prescription from the list of prescriptions.
     *
     * @param prescription the Prescription object to remove
     */
    public void removePrescription(Prescription prescription) {
        prescriptions.remove(prescription);
    }

    /**
     * Updates the patient's contact information, including phone number and
     * email address.
     *
     * @param newPhoneNumber the new phone number to set
     * @param newEmailAddress the new email address to set
     */
    public void updateContactInfo(String newPhoneNumber, String newEmailAddress) {
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            this.phoneNumber = newPhoneNumber;
        }
        if (newEmailAddress != null && !newEmailAddress.isEmpty()) {
            this.emailAddress = newEmailAddress;
        }
    }

    /**
     * Gets the patient ID.
     *
     * @return the patient ID
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Gets the patient's name.
     *
     * @return the patient's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the patient's blood type.
     *
     * @return the patient's blood type
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Gets the patient's date of birth.
     *
     * @return the date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets the patient's gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the patient's phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the patient's email address.
     *
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Gets the list of prescriptions for the patient.
     *
     * @return the list of prescriptions
     */
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    /**
     * Returns a CSV-formatted string representation of the medical record.
     *
     * @return a string representing the medical record in CSV format
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Append basic information
        sb.append(patientId).append(",")
                .append(name).append(",")
                .append(dateOfBirth).append(",")
                .append(gender).append(",")
                .append(bloodType).append(",")
                .append(phoneNumber).append(",")
                .append(emailAddress).append(",");

        // Append diagnoses
        if (!diagnoses.isEmpty()) {
            for (int i = 0; i < diagnoses.size(); i++) {
                sb.append(diagnoses.get(i).toString());
                if (i < diagnoses.size() - 1) {
                    sb.append(";"); // Use semicolon as a delimiter between diagnoses
                }
            }
        }
        sb.append(",");

        // Append treatments
        if (!treatments.isEmpty()) {
            for (int i = 0; i < treatments.size(); i++) {
                sb.append(treatments.get(i).toString());
                if (i < treatments.size() - 1) {
                    sb.append(";"); // Use semicolon as a delimiter between treatments
                }
            }
        }
        sb.append(",");

        // Append prescriptions
        if (!prescriptions.isEmpty()) {
            for (int i = 0; i < prescriptions.size(); i++) {
                sb.append(prescriptions.get(i).toString());
                if (i < prescriptions.size() - 1) {
                    sb.append(";"); // Use semicolon as a delimiter between prescriptions
                }
            }
        }

        return sb.toString();
    }

}
