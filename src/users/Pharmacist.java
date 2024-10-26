package users;

import java.util.ArrayList;
import java.util.List;

public class Pharmacist extends User {
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords; // Need update prescription class
    private Inventory inventory;

    public Pharmacist(String id, String name, String password) {
        super(id, name, "PHARMACIST", password);
        this.appointmentOutcomeRecords = new ArrayList<>();
        this.inventory = new Inventory();
    }

    @Override
    public void viewProfile() {
        System.out.println("Pharmacist Profile: " + getName() + " (ID: " + getId() + ")");
    }

    public void viewAppointmentOutcomeRecords() {
        for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
            System.out.println("Appointment Date: " + record.getAppointmentDate());
            System.out.println("Service Type: " + record.getServiceType());
            System.out.println("Consultation Notes: " + record.getConsultationNotes());
            System.out.println("Prescribed Medications:");
            for (Prescription medication : record.getPrescribedMedications()) {
                System.out.println(" - " + medication);
            }
            System.out.println();
        }
    }

    public void updatePrescriptionStatus(String medicationName, String newStatus) {
        for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
            for (Prescription medication : record.getPrescribedMedications()) {
                if (medication.getName().equals(medicationName)) {
                    medication.setStatus(newStatus);
                    System.out.println("Updated status for " + medicationName + " to " + newStatus);
                    return;
                }
            }
        }
        System.out.println("Medication not found in records.");
    }

    public void viewMedicationInventory() {
        inventory.displayStockLevels();
    }

    public void submitReplenishmentRequest(String medicationId, int quantity) {
        if (inventory.isLow(medicationId)) {
            System.out.println("Replenishment request submitted for " + quantity + " units of medication ID: " + medicationId);
        } else {
            System.out.println("Stock levels are sufficient.");
        }
    }

}