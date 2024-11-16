package database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import medicalrecords.MedicalRecord;

public class MedicalRecordDB extends Database<MedicalRecord> {
    private List<MedicalRecord> medicalRecords;
    private static final String filename = "csv_data/Medical_Record.csv";
    private static final String header = "PatientID,Name,DateOfBirth,Gender,BloodType,PhoneNumber,EmailAddress,Diagnoses,Treatments,Prescriptions";

    public MedicalRecordDB() {
        super(filename);
        this.medicalRecords = new ArrayList<>();
    }

    // Create a new medical record
    @Override
    public boolean create(MedicalRecord medicalRecord) {
        if (medicalRecord != null) {
            medicalRecords.add(medicalRecord);
            try {
                save(); // Automatically save after creation
            } catch (IOException e) {
                System.err.println("Error saving data after creating medical record: " + e.getMessage());
            }
            return true;
        }
        return false; // Invalid record
    }

    // Get medical record by patient ID
    @Override
    public MedicalRecord getById(String patientId) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getPatientId().equals(patientId)) {
                return record; // Return the matching record
            }
        }
        return null; // Return null if not found
    }

    // Get all medical records
    @Override
    public List<MedicalRecord> getAll() {
        return medicalRecords;
    }

    // Update a medical record
    @Override
    public boolean update(MedicalRecord medicalRecord) {
        MedicalRecord existingRecord = getById(medicalRecord.getPatientId());
        if (existingRecord != null) {
            medicalRecords.remove(existingRecord);
            medicalRecords.add(medicalRecord);
            try {
                save(); // Automatically save after updating
            } catch (IOException e) {
                System.err.println("Error saving data after updating medical record: " + e.getMessage());
            }
            return true;
        }
        return false; // Record not found
    }

    // Delete a medical record by patient ID
    @Override
    public boolean delete(String patientId) {
        MedicalRecord existingRecord = getById(patientId);
        if (existingRecord != null) {
            medicalRecords.remove(existingRecord);
            try {
                save(); // Automatically save after deletion
            } catch (IOException e) {
                System.err.println("Error saving data after deleting medical record: " + e.getMessage());
            }
            return true;
        }
        return false; // Record not found
    }

    // Save medical records to CSV
    @Override
    public boolean save() throws IOException {
        saveData(filename, medicalRecords, header);
        return true;
    }

    // Load medical records from CSV
    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(filename);
        for (String line : lines) {
            String[] tokens = splitLine(line);

            if (tokens.length >= 10) {
                MedicalRecord record = new MedicalRecord(
                        tokens[0], // patientId
                        tokens[1], // name
                        tokens[2], // dateOfBirth
                        tokens[3], // gender
                        tokens[4], // bloodType
                        tokens[5], // phoneNumber
                        tokens[6], // emailAddress
                        tokens[7], // diagnoses
                        tokens[8], // treatments
                        tokens[9] // prescriptions
                );
                medicalRecords.add(record);
            } else {
                System.out.println("Invalid line in " + filename + ": " + line);
            }
        }
        return true;
    }
}
