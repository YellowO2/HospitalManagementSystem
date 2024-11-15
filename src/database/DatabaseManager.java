package database;

import java.io.IOException;

public class DatabaseManager {
    private UserDB userDB;
    private MedicalRecordDB medicalRecordDB;
    private MedicineDB medicineDB;
    private ReplenishmentDB replenishmentDB; // Existing ReplenishmentDB instance
    private AppointmentOutcomeRecordDB appointmentOutcomeRecordDB; // Add AppointmentOutcomeRecordDB instance

    public DatabaseManager() {
        this.userDB = new UserDB();
        this.medicalRecordDB = new MedicalRecordDB();
        this.medicineDB = new MedicineDB();
        this.replenishmentDB = new ReplenishmentDB(); // Initialize ReplenishmentDB
        this.appointmentOutcomeRecordDB = new AppointmentOutcomeRecordDB(); // Initialize AppointmentOutcomeRecordDB
    }

    public void initialize() throws IOException {
        userDB.load();
        medicalRecordDB.load();
        medicineDB.load();
        replenishmentDB.load(); // Load data for ReplenishmentDB
        appointmentOutcomeRecordDB.load(); // Load data for AppointmentOutcomeRecordDB
    }

    public void save() throws IOException {
        userDB.save();
        medicalRecordDB.save();
        medicineDB.save();
        replenishmentDB.save(); // Save data for ReplenishmentDB
        appointmentOutcomeRecordDB.save(); // Save data for AppointmentOutcomeRecordDB
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

    public AppointmentOutcomeRecordDB getAppointmentOutcomeRecordDB() { // Accessor method for AppointmentOutcomeRecordDB
        return appointmentOutcomeRecordDB;
    }
}