package users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import appointments.AppointmentOutcomeRecord;
import inventory.Inventory;
import medicalrecords.Prescription;

public class Pharmacist extends User {
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords;
    private Inventory inventory;

    public Pharmacist(String id, String name, String password, String phoneNumber, String emailAddress,
            LocalDate dateOfBirth, String gender) {
        super(id, name, "PHARMACIST", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.appointmentOutcomeRecords = new ArrayList<>();
        this.inventory = new Inventory();
    }

    public void viewAppointmentOutcomeRecords() {
        for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
            System.out.println("Appointment Date: " + record.getAppointmentDate());
            System.out.println("Service Type: " + record.getServiceProvided());
            System.out.println("Consultation Notes: " + record.getConsultationNotes());
            System.out.println("Prescribed Medications:");
            for (Prescription medication : record.getPrescriptions()) {
                System.out.println(" - " + medication);
            }
            System.out.println();
        }
    }

    public void updatePrescriptionStatus(String medicationName, int newStatus) {
        for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
            for (Prescription medication : record.getPrescriptions()) {
                if (medication.getMedicationName().equals(medicationName)) {
                    medication.setStatus(newStatus);
                    System.out.println("Updated status for " + medicationName + " to " + newStatus);
                    return;
                }
            }
        }
        System.out.println("Medication not found in records.");
    }

    public void viewMedicationInventory() {
        inventory.displayInventory();
    }

    public void submitReplenishmentRequest(String medicationId, int quantity) {
        if (inventory.isLow(medicationId, quantity)) {
            System.out.println("Replenishment request submitted for " + quantity + " units of medication ID: " + medicationId);
        } else {
            System.out.println("Stock levels are sufficient.");
        }
    }

    public void addAppointmentOutcomeRecord(AppointmentOutcomeRecord record) {
        this.appointmentOutcomeRecords.add(record);
    }
}