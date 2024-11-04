package users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import appointments.Appointment;

public class Doctor extends User {
    private List<Appointment> appointmentSchedule;

    // ID,Name,Date of Birth,Gender,Phone Number,Email Address,Password,Role
    public Doctor(String id, String name, String dateOfBirth, String gender, String phoneNumber,
            String emailAddress, String password) {
        super(id, name, "Doctor", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.appointmentSchedule = new ArrayList<>();
    }

    // TODO: These methods needs to be moved into Medical Record Manager
    // public String viewPatientMedicalRecord(Patient patient){
    // return patient.getMedicalRecord().getMedicalRecordDescription();
    // }

    // public void addDiagnosis(Patient patient, Diagnosis diagnosis) { //"Roland" -
    // do we put in all the scanners for diagnosis in this function?
    // patient.getMedicalRecord().addDiagnosis(diagnosis); // Ensure method access
    // control
    // }

    // public void addPrescription(Patient patient, Prescription prescription) {
    // patient.getMedicalRecord().addPrescription(prescription); // Ensure method
    // access control
    // }

    // public void addTreatment(Patient patient, Treatment treatment) {
    // patient.getMedicalRecord().addTreatment(treatment); // Ensure method access
    // control
    // }

    // Work In Progress
    // public void acceptAppointment(Appointment appointment){
    // appointment.setStatus("Accepted");
    // }

    // public void declineAppointment(Appointment appointment) {
    // // Logic to decline the appointment and update the status
    // appointment.setStatus("Declined");
    // }

    // public List<Appointment> getUpcomingAppointments() {
    // return upcomingAppointments;
    // }
}
