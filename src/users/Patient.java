package users;

// import medicalrecords.MedicalRecord;
// import appointments.Appointment;

import java.util.List;
import java.util.ArrayList;

public class Patient extends User {
    // private MedicalRecord medicalRecord; // Patient's medical record
    // private List<Appointment> appointments; // List of patient's appointments

    // Constructor
    public Patient(String id, String name, String password) {
        super(id, name, "Patient", password);
        // this.medicalRecord = new MedicalRecord(id); // Create a new medical record
        // for the patient
        // this.appointments = new ArrayList<>(); // Initialize the appointments list
    }

    // Override abstract method from User
    @Override
    public void viewProfile() {
        System.out.println("Patient Profile:");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Role: " + getRole());
        System.out.println("Medical Record:");
        // medicalRecord.viewMedicalRecord();
    }

    // // Add a diagnosis to the patient's medical record
    // public void addDiagnosis(String diagnosis) {
    // medicalRecord.addDiagnosis(diagnosis);
    // }

    // // Add a prescription to the patient's medical record
    // public void addPrescription(String prescription) {
    // medicalRecord.addPrescription(prescription);
    // }

    // View medical record
    public void viewMedicalRecord() {
        System.out.println("Patient Medical Record: not implemented yet");
        // medicalRecord.viewMedicalRecord();
    }

    // // Schedule an appointment
    // public void scheduleAppointment(Appointment appointment) {
    // appointments.add(appointment);
    // System.out.println("Appointment scheduled: " + appointment.getDetails());
    // }

    // // Cancel an appointment
    // public void cancelAppointment(Appointment appointment) {
    // if (appointments.contains(appointment)) {
    // appointment.cancel();
    // System.out.println("Appointment canceled: " + appointment.getDetails());
    // } else {
    // System.out.println("No such appointment found.");
    // }
    // }

    // // View all appointments
    // public void viewAppointments() {
    // System.out.println("Patient Appointments:");
    // for (Appointment appointment : appointments) {
    // System.out.println(appointment.getDetails());
    // }
    // }
}
