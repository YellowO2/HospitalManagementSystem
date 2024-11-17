// To Do: Uncomment after merging with git

package users;

import appointments.Appointment;
import managers.MedicalRecordManager;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private List<Appointment> appointmentSchedule;
    private MedicalRecordManager medicalRecordManager;

    // ID,Name,Date of Birth,Gender,Phone Number,Email Address,Password,Role
    // their ownn patients) and appointmentSchedule into constructor
    public Doctor(String id, String name, String dateOfBirth, String gender, String phoneNumber,
            String emailAddress, String password) {
        super(id, name, "Doctor", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.appointmentSchedule = new ArrayList<>();
        this.medicalRecordManager = medicalRecordManager;
    }
}
