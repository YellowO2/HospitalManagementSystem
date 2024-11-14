package database;

import java.io.IOException;

public class DatabaseManager {
    private UserDB userDB;
    private MedicalRecordDB medicalRecordDB;
    private MedicineDB medicineDB;
    private ReplenishmentDB replenishmentDB; // Add ReplenishmentDB instance

    public DatabaseManager() {
        this.userDB = new UserDB();
        this.medicalRecordDB = new MedicalRecordDB();
        this.medicineDB = new MedicineDB();
        this.replenishmentDB = new ReplenishmentDB(); // Initialize ReplenishmentDB
    }

    public void initialize() throws IOException {
        userDB.load();
        medicalRecordDB.load();
        medicineDB.load();
        replenishmentDB.load(); // Load data for ReplenishmentDB
    }

    public void save() throws IOException {
        userDB.save();
        medicalRecordDB.save();
        medicineDB.save();
        replenishmentDB.save(); // Save data for ReplenishmentDB
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
}