package database;

import appointments.AppointmentOutcomeRecord;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRecordDB extends Database<AppointmentOutcomeRecord> {
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords;
    private static final String filename = "csv_data/Appointment_Outcome_Record.csv";
    private static final String header = "AppointmentID,Date,Service Type,Medications,Prescribed,Consultation Notes";

    public AppointmentOutcomeRecordDB() {
        super(filename);
        this.appointmentOutcomeRecords = new ArrayList<>();
    }

    @Override
    public boolean create(AppointmentOutcomeRecord record) {
        if (record != null) {
            appointmentOutcomeRecords.add(record);
            return true;
        }
        return false;
    }

    @Override
    public AppointmentOutcomeRecord getById(String appointmentId) {
        for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
            if (record.getAppointmentId().equals(appointmentId)) {
                return record;
            }
        }
        return null;
    }

    @Override
    public boolean update(AppointmentOutcomeRecord updatedRecord) {
        AppointmentOutcomeRecord existingRecord = getById(updatedRecord.getAppointmentId());
        if (existingRecord != null) {
            appointmentOutcomeRecords.remove(existingRecord);
            appointmentOutcomeRecords.add(updatedRecord);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String appointmentId) {
        AppointmentOutcomeRecord existingRecord = getById(appointmentId);
        if (existingRecord != null) {
            appointmentOutcomeRecords.remove(existingRecord);
            return true;
        }
        return false;
    }

    @Override
    public boolean save() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(header);
            writer.newLine();
            for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
                writer.write(record.toString());
                writer.newLine();
            }
        }
        return true;
    }

    @Override
    public boolean load() throws IOException {
        appointmentOutcomeRecords.clear();
        List<String> lines = readFile(filename);
        for (String line : lines) {
            try {
                AppointmentOutcomeRecord record = AppointmentOutcomeRecord.fromCSV(line);
                appointmentOutcomeRecords.add(record);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid line in CSV: " + line + " - " + e.getMessage());
            }
        }
        return true;
    }

    @Override
    public List<AppointmentOutcomeRecord> getAll() {
        return appointmentOutcomeRecords;
    }
}