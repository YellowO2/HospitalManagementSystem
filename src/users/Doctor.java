package users;

import appointments.Appointment;
import java.util.ArrayList;
import java.util.List;
import managers.MedicalRecordManager;

/**
 * The Doctor class represents a doctor in the hospital system. It extends the
 * User class and includes additional properties for managing a doctor's
 * appointment schedule and access to medical records.
 */
public class Doctor extends User {
    private List<Appointment> appointmentSchedule;
    private MedicalRecordManager medicalRecordManager;

    /**
     * Constructs a Doctor object with the specified details.
     *
     * @param id the unique hospital ID for the doctor
     * @param name the full name of the doctor
     * @param dateOfBirth the date of birth of the doctor in the format
     * yyyy-MM-dd
     * @param gender the gender of the doctor
     * @param phoneNumber the contact number of the doctor
     * @param emailAddress the email address of the doctor
     * @param password the password for the doctor's account
     */
    public Doctor(String id, String name, String dateOfBirth, String gender, String phoneNumber,
            String emailAddress, String password) {
        super(id, name, "Doctor", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.appointmentSchedule = new ArrayList<>();
        this.medicalRecordManager = medicalRecordManager;
    }
    // Additional methods and properties can be added here for more functionality
}
