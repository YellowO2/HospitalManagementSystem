package database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import medicalrecords.MedicalRecord;

/**
 * A class that manages medical records in the database.
 * This class allows creating, updating, deleting, retrieving, saving, and
 * loading medical records from a CSV file.
 * 
 * @see MedicalRecord
 */
public class MedicalRecordDB extends Database<MedicalRecord> {
    private List<MedicalRecord> medicalRecords; // List to store medical records
    private static final String filename = "csv_data/Medical_Record.csv"; // File path for saving/loading data
    private static final String header = "PatientID,Name,DateOfBirth,Gender,BloodType,PhoneNumber,EmailAddress,Diagnoses,Treatments,Prescriptions"; // CSV
                                                                                                                                                    // header

    /**
     * Constructor for initializing the MedicalRecordDB with the specified CSV file
     * path.
     */
    public MedicalRecordDB() {
        super(filename); // Pass the filename to the parent class
        this.medicalRecords = new ArrayList<>();
    }

    /**
     * Creates a new medical record and adds it to the database.
     * Automatically saves the data after creation.
     *
     * @param medicalRecord the MedicalRecord object to be added
     * @return true if the medical record was successfully created and saved, false
     *         otherwise
     */
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

    /**
     * Retrieves a medical record by patient ID.
     *
     * @param patientId the ID of the patient
     * @return the MedicalRecord object if found, or null if not found
     */
    @Override
    public MedicalRecord getById(String patientId) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getPatientId().equals(patientId)) {
                return record; // Return the matching record
            }
        }
        return null; // Return null if not found
    }

    /**
     * Retrieves all medical records in the database.
     *
     * @return a list of all MedicalRecord objects
     */
    @Override
    public List<MedicalRecord> getAll() {
        return medicalRecords;
    }

    /**
     * Updates an existing medical record in the database.
     * Automatically saves the data after updating.
     *
     * @param medicalRecord the updated MedicalRecord object
     * @return true if the medical record was successfully updated and saved, false
     *         otherwise
     */
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

    /**
     * Deletes a medical record by patient ID.
     * Automatically saves the data after deletion.
     *
     * @param patientId the ID of the patient whose record is to be deleted
     * @return true if the medical record was successfully deleted and saved, false
     *         otherwise
     */
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

    /**
     * Saves all medical records to a CSV file.
     *
     * @return true if the data was successfully saved
     * @throws IOException if an I/O error occurs during saving
     */
    @Override
    public boolean save() throws IOException {
        saveData(filename, medicalRecords, header);
        return true;
    }

    /**
     * Loads medical records from a CSV file into the database.
     *
     * @return true if the data was successfully loaded
     * @throws IOException if an I/O error occurs during loading
     */
    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(filename); // Read the CSV file
        for (String line : lines) {
            String[] tokens = splitLine(line); // Split line into tokens

            if (tokens.length >= 10) { // Ensure there are enough tokens
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
