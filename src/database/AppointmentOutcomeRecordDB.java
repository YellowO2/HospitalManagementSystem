package database;

import appointments.AppointmentOutcomeRecord;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRecordDB extends Database<AppointmentOutcomeRecord> {
    private List<AppointmentOutcomeRecord> outcomeRecords;
    private static final String filename = "csv_data/Appointment_Outcome_Record.csv";
    private static final String header = "AppointmentID,Date,Service Type,Medications,Prescribed,Consultation Notes";

    public AppointmentOutcomeRecordDB() {
        super(filename);
        this.outcomeRecords = new ArrayList<>();
    }

    // Implement the CRUD methods

    @Override
    public boolean create(AppointmentOutcomeRecord record) {
        if (record != null) {
            outcomeRecords.add(record);
            return true;
        }
        return false;
    }

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

    @Override
    public boolean update(AppointmentOutcomeRecord updatedRecord) {
        AppointmentOutcomeRecord existingRecord = getById(updatedRecord.getAppointmentId());
        if (existingRecord != null) {
            outcomeRecords.remove(existingRecord);
            outcomeRecords.add(updatedRecord);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String appointmentId) {
        AppointmentOutcomeRecord record = getById(appointmentId);
        if (record != null) {
            outcomeRecords.remove(record);
            return true;
        }
        return false;
    }

    @Override
    public boolean save() throws IOException {
        saveData(filename, outcomeRecords, header);
        return true;
    }

    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(filename);
        for (String line : lines) {
            String[] tokens = splitLine(line);

            if (tokens.length >= 6) {
                // Assuming the format matches the CSV structure
                AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(
                        tokens[0], // appointmentId
                        LocalDate.parse(tokens[1]), // date
                        tokens[2], // type of service
                        tokens[3], // prescriptions (convert from string)
                        tokens[4], // medication status
                        tokens[5] // consultation notes
                );
                outcomeRecords.add(record);
            } else {
                System.out.println("Invalid line in " + filename + ": " + line);
            }
        }
        return true;
    }
}