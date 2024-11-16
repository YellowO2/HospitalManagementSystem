package database;

import appointments.AppointmentOutcomeRecord;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRecordDB extends Database<AppointmentOutcomeRecord> {
    private List<AppointmentOutcomeRecord> outcomeRecords;
    private static final String filename = "csv_data/Appointment_Outcome_Record.csv";
    private static final String header = "AppointmentID,PatientId,Date,Service Type,Prescriptions,Prescribed,Consultation Notes";

    public AppointmentOutcomeRecordDB() {
        super(filename);
        this.outcomeRecords = new ArrayList<>();
    }

    // Create a new outcome record
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

    // Get a record by appointment ID
    @Override
    public AppointmentOutcomeRecord getById(String appointmentId) {
        for (AppointmentOutcomeRecord record : outcomeRecords) {
            if (record.getAppointmentId().equals(appointmentId)) {
                return record;
            }
        }
        return null;
    }

    @Override
    public List<AppointmentOutcomeRecord> getAll() {
        return outcomeRecords;
    }

    // Update an existing outcome record
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
        return false;
    }

    // Delete an outcome record by appointment ID
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
        return false;
    }

    // Save outcome records to CSV
    @Override
    public boolean save() throws IOException {
        saveData(filename, outcomeRecords, header);
        return true;
    }

    // Load outcome records from CSV
    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(filename);
        for (String line : lines) {
            String[] tokens = splitLine(line);

            if (tokens.length >= 6) {
                // Assuming the format matches the CSV structure
                AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(
                        tokens[0], // appointmentId
                        tokens[1], // patientId
                        LocalDate.parse(tokens[2]), // date
                        tokens[3], // type of service
                        tokens[4], // prescriptions (convert from string)
                        tokens[5], // prescribed
                        tokens[6] // consultation notes
                );
                // System.out.println("DEBUG record: " + record.getAppointmentId() + " " +
                // record.getPatientId());
                outcomeRecords.add(record);
            } else {
                System.out.println("Invalid line in " + filename + ": " + line);
            }
        }
        return true;
    }

    // Get records by patient ID
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
