/* 
 * The Patient class represents a patient who has access to a medical record and can schedule appointments.
 * It should contain methods that manage and manipulate patient data, 
 * but not handle interaction with the user as that is done by the menu.
 */
package users;

import medicalrecords.Diagnosis;
import medicalrecords.MedicalRecord;
import medicalrecords.Prescription;
import medicalrecords.Treatment;

import java.time.LocalDate;

public class Patient extends User {
    private MedicalRecord medicalRecord; // Patient's medical record

    // Constructor
    public Patient(String id, String name, String password, String phoneNumber, String emailAddress, String bloodType,
            LocalDate dateOfBirth, String gender, MedicalRecord medicalRecord) {
        super(id, name, "Patient", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.medicalRecord = (medicalRecord != null) ? medicalRecord
                : new MedicalRecord(id, name, password, gender, bloodType, phoneNumber, emailAddress);
    }

    // Add a diagnosis to the patient's medical record
    public void addDiagnosis(Diagnosis diagnosis, Doctor doctor) {
        if (doctor != null){
            medicalRecord.addDiagnosis(diagnosis);
        } else {
            throw new SecurityException("Only doctors can add diagnoses.");
        }

    }

    // Add a prescription to the patient's medical record
    public void addPrescription(Prescription prescription, Doctor doctor) {
        if (doctor != null){
            medicalRecord.addPrescription(prescription);
        } else {
            throw new SecurityException("Only doctors can add diagnoses.");
        }
    }

    // Add a treatment to the patient's medical record
    public void addTreatment(Treatment treatment, Doctor doctor) {
        if (doctor != null){
            medicalRecord.addTreatment(treatment);
        } else {
            throw new SecurityException("Only doctors can add diagnoses.");
        }
    }

    // Accessor for medical record
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }
}
