package managers;

import java.io.IOException;

import database.AppointmentDB;
import database.AppointmentOutcomeRecordDB;
import database.DoctorUnavailabilityDB;
import database.MedicalRecordDB;
import database.MedicineDB;
import database.ReplenishmentDB;
import database.UserDB;

public class DatabaseManager {
    private UserDB userDB;
    private MedicalRecordDB medicalRecordDB;
    private MedicineDB medicineDB;
    private ReplenishmentDB replenishmentDB; // Add ReplenishmentDB instance
    private AppointmentDB appointmentDB;
    private AppointmentOutcomeRecordDB appointmentOutcomeRecordDB;
    private DoctorUnavailabilityDB doctorAvailabilityDB;

    public DatabaseManager() {
        this.userDB = new UserDB();
        this.medicalRecordDB = new MedicalRecordDB();
        this.medicineDB = new MedicineDB();
        this.replenishmentDB = new ReplenishmentDB(); // Initialize ReplenishmentDB
        this.appointmentDB = new AppointmentDB();
        this.appointmentOutcomeRecordDB = new AppointmentOutcomeRecordDB();
        this.doctorAvailabilityDB = new DoctorUnavailabilityDB();
    }

    public void initialize() throws IOException {
        userDB.load();
        medicalRecordDB.load();
        medicineDB.load();
        replenishmentDB.load(); // Load data for ReplenishmentDB
        appointmentDB.load();
        appointmentOutcomeRecordDB.load();
        doctorAvailabilityDB.load();
    }

    public void save() throws IOException {
        userDB.save();
        medicalRecordDB.save();
        medicineDB.save();
        replenishmentDB.save(); // Save data for ReplenishmentDB
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

    public MedicineDB getMedicineDB() {
        return medicineDB;
    }

    public ReplenishmentDB getReplenishmentDB() { // Accessor method for ReplenishmentDB
        return replenishmentDB;
    }

    public AppointmentDB getAppointmentDB() {
        return appointmentDB;
    }

    public AppointmentOutcomeRecordDB getAppointmentOutcomeRecordDB() {
        return appointmentOutcomeRecordDB;
    }

    public DoctorUnavailabilityDB getdoctorAvailabilityDB() {
        return doctorAvailabilityDB;
    }
}
