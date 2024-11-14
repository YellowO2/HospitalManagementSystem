package database;

import java.io.IOException;

// import appointments.AppointmentOutcomeRecord;
// import appointments.AppointmentOutcomeRecord;

public class DatabaseManager {
    private UserDB userDB;
    private MedicalRecordDB medicalRecordDB;
    private AppointmentDB appointmentDB;
    private AppointmentOutcomeRecordDB appointmentOutcomeRecordDB;
    private DoctorAvailabilityDB doctorAvailabilityDB;

    public DatabaseManager() { 
        this.userDB = new UserDB();
        this.medicalRecordDB = new MedicalRecordDB();
        this.appointmentDB = new AppointmentDB();
        this.appointmentOutcomeRecordDB = new AppointmentOutcomeRecordDB();
        this.doctorAvailabilityDB = new DoctorAvailabilityDB();
    }

    public void initialize() throws IOException {
        userDB.load();
        medicalRecordDB.load();
        appointmentDB.load();
        appointmentOutcomeRecordDB.load();
        doctorAvailabilityDB.load();

    }

    public void save() throws IOException {
        userDB.save();
        medicalRecordDB.save();
        appointmentDB.save();
        appointmentOutcomeRecordDB.save();
        doctorAvailabilityDB.save();
    }

    // Accessor methods for individual databases
    public UserDB getUserDB() {
        return userDB;
    }

    public MedicalRecordDB getMedicalRecordDB() {
        return medicalRecordDB;
    }

    public AppointmentDB getAppointmentDB() {
        return appointmentDB;
    }

    public AppointmentOutcomeRecordDB getAppointmentOutcomeRecordDB(){
        return appointmentOutcomeRecordDB;
    }

    public DoctorAvailabilityDB getdoctorAvailabilityDB() {
        return doctorAvailabilityDB;
    }
}
