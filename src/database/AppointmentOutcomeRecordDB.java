package database;

import appointments.AppointmentOutcomeRecord;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A database class to manage AppointmentOutcomeRecord objects.
 * Handles CRUD operations and provides methods to interact with appointment
 * outcome records,
 * including loading and saving to a CSV file.
 */
public class AppointmentOutcomeRecordDB extends Database<AppointmentOutcomeRecord> {
    private List<AppointmentOutcomeRecord> outcomeRecords; // List of outcome records
    private static final String filename = "csv_data/Appointment_Outcome_Record.csv"; // Filepath for CSV file
    private static final String header = "AppointmentID,PatientId,Date,Service Type,Prescriptions,Prescribed,Consultation Notes"; // CSV
                                                                                                                                  // file
                                                                                                                                  // header

    /**
     * Constructs an AppointmentOutcomeRecordDB instance and initializes the list of
     * outcome records.
     */
    public AppointmentOutcomeRecordDB() {
        super(filename); // Pass the filename to the parent class
        this.outcomeRecords = new ArrayList<>();
    }

    /**
     * Creates a new appointment outcome record and saves the changes to the file.
     *
     * @param record the AppointmentOutcomeRecord object to be created
     * @return true if the record was successfully added, false otherwise
     */
    @Override
    public boolean create(AppointmentOutcomeRecord record) {
        if (record != null) {
            outcomeRecords.add(record);
            try {
                save(); // Automatically save after creation
            } catch (IOException e) {
                System.err.println("Error saving data after creating appointment outcome record: " + e.getMessage());
            }
            return true;
        }
        return false;
    }

    /**
     * Retrieves an appointment outcome record by its appointment ID.
     *
     * @param appointmentId the unique ID of the appointment
     * @return the AppointmentOutcomeRecord object if found, or null otherwise
     */
    @Override
    public AppointmentOutcomeRecord getById(String appointmentId) {
        for (AppointmentOutcomeRecord record : outcomeRecords) {
            if (record.getAppointmentId().equals(appointmentId)) {
                return record;
            }
        }
        return null; // Record not found
    }

    /**
     * Retrieves all appointment outcome records stored in the database.
     *
     * @return a list of all AppointmentOutcomeRecord objects
     */
    @Override
    public List<AppointmentOutcomeRecord> getAll() {
        return outcomeRecords;
    }

    /**
     * Updates an existing appointment outcome record and saves the changes to the
     * file.
     *
     * @param updatedRecord the updated AppointmentOutcomeRecord object
     * @return true if the record was successfully updated, false otherwise
     */
    @Override
    public boolean update(AppointmentOutcomeRecord updatedRecord) {
        AppointmentOutcomeRecord existingRecord = getById(updatedRecord.getAppointmentId());
        if (existingRecord != null) {
            outcomeRecords.remove(existingRecord);
            outcomeRecords.add(updatedRecord);
            try {
                save(); // Automatically save after update
            } catch (IOException e) {
                System.err.println("Error saving data after updating appointment outcome record: " + e.getMessage());
            }
            return true;
        }
        return false; // Record not found
    }

    /**
     * Deletes an appointment outcome record by its appointment ID and saves the
     * changes to the file.
     *
     * @param appointmentId the unique ID of the record to delete
     * @return true if the record was successfully deleted, false otherwise
     */
    @Override
    public boolean delete(String appointmentId) {
        AppointmentOutcomeRecord record = getById(appointmentId);
        if (record != null) {
            outcomeRecords.remove(record);
            try {
                save(); // Automatically save after deletion
            } catch (IOException e) {
                System.err.println("Error saving data after deleting appointment outcome record: " + e.getMessage());
            }
            return true;
        }
        return false; // Record not found
    }

    /**
     * Saves all appointment outcome records to the CSV file.
     *
     * @return true if the data was successfully saved
     * @throws IOException if there is an error saving the data
     */
    @Override
    public boolean save() throws IOException {
        saveData(filename, outcomeRecords, header);
        return true;
    }

    /**
     * Loads appointment outcome records from the CSV file into the database.
     *
     * @return true if the data was successfully loaded
     * @throws IOException if there is an error reading the file
     */
    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(filename); // Read the CSV file
        for (String line : lines) {
            String[] tokens = splitLine(line); // Split line into tokens

            if (tokens.length >= 6) { // Ensure there are enough tokens in the line
                AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(
                        tokens[0], // appointmentId
                        tokens[1], // patientId
                        LocalDate.parse(tokens[2]), // date
                        tokens[3], // type of service
                        tokens[4], // prescriptions (convert from string)
                        tokens[5], // prescribed
                        tokens[6] // consultation notes
                );
                outcomeRecords.add(record);
            } else {
                System.out.println("Invalid line in " + filename + ": " + line);
            }
        }
        return true;
    }

    /**
     * Retrieves all appointment outcome records for a specific patient.
     *
     * @param patientId the unique ID of the patient
     * @return a list of AppointmentOutcomeRecord objects for the specified patient
     */
    public List<AppointmentOutcomeRecord> getByPatientId(String patientId) {
        List<AppointmentOutcomeRecord> records = new ArrayList<>();
        for (AppointmentOutcomeRecord record : outcomeRecords) {
            if (record.getPatientId().equals(patientId)) {
                records.add(record);
            }
        }
        return records;
    }
}
