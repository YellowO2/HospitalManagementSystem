package appointments;

import appointments.AppointmentOutcomeRecord;
import database.AppointmentOutcomeRecordDB;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AppointmentOutcomeManager {
    private AppointmentOutcomeRecordDB appointmentOutcomeRecordDB;

    public AppointmentOutcomeManager(AppointmentOutcomeRecordDB appointmentOutcomeRecordDB) {
        this.appointmentOutcomeRecordDB = appointmentOutcomeRecordDB;
    }

    // Create a new appointment outcome record, including patient ID
    public boolean createOutcomeRecord(String patientId, LocalDate appointmentDate,
            String serviceProvided, String prescribedStatus, String consultationNotes) {
        // generate appointment ID
        String appointmentId = UUID.randomUUID().toString();
        AppointmentOutcomeRecord newRecord = new AppointmentOutcomeRecord(
                appointmentId, patientId, appointmentDate, serviceProvided, "", prescribedStatus, consultationNotes);

        return appointmentOutcomeRecordDB.create(newRecord);
    }

    // Retrieve an appointment outcome record by ID
    public AppointmentOutcomeRecord getOutcomeRecord(String appointmentId) {
        return appointmentOutcomeRecordDB.getById(appointmentId);
    }

    // Retrieve all appointment outcome records
    public List<AppointmentOutcomeRecord> getAllOutcomeRecords() {
        return appointmentOutcomeRecordDB.getAll();
    }

    // Retrieve appointment outcome records by patient ID
    public void viewPatientOutcomeRecords(String patientId) {
        List<AppointmentOutcomeRecord> records = appointmentOutcomeRecordDB.getByPatientId(patientId);
        if (records.isEmpty()) {
            System.out.println("No appointment outcome records found for patient " + patientId);
        } else {
            System.out.println("Viewing past appointment outcome records...");
            System.out.println("========================================");
            System.out.println("Appointment outcome records for patient " + patientId + ":");
            for (AppointmentOutcomeRecord record : records) {
                System.out.println("----------------------------------------");
                System.out.println("Appointment ID: " + record.getAppointmentId());
                System.out.println("Date: " + record.getAppointmentDate());
                System.out.println("Service Provided: " + record.getServiceProvided());
                System.out.println("Prescribed Medications: " + formatMedications(record.getMedications()));
                System.out.println("Prescription Status: " + record.getPrescribedStatus());
                System.out.println("Consultation Notes: " + record.getConsultationNotes());
            }
            System.out.println("========================================");
        }
    }

    // Helper method to format prescribed medications nicely
    private String formatMedications(String prescribedMedications) {
        if (prescribedMedications == null || prescribedMedications.isEmpty()) {
            return "None";
        }
        StringBuilder formatted = new StringBuilder();
        String[] medications = prescribedMedications.split("\\|");
        for (String med : medications) {
            formatted.append("- ").append(med).append("\n");
        }
        return formatted.toString().trim();
    }

    // Update an existing appointment outcome record
    public boolean updateOutcomeRecord(AppointmentOutcomeRecord updatedRecord) {
        // TODO: implement
        return appointmentOutcomeRecordDB.update(updatedRecord);
    }

    // Method to update the status of prescriptions (e.g., Pending -> Fulfilled)
    public boolean updatePrescriptionStatus(String appointmentId, String newStatus) {
        AppointmentOutcomeRecord record = appointmentOutcomeRecordDB.getById(appointmentId);
        if (record != null) {
            record.setPrescribedStatus(newStatus);
            return appointmentOutcomeRecordDB.update(record);
        }
        return false;
    }
}
