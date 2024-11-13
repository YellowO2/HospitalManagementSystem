package database;

import java.io.IOException;

public class DatabaseManager {
    private UserDB userDB;
    private MedicalRecordDB medicalRecordDB;
    private MedicineDB medicineDB; // Add MedicineDB instance

    public DatabaseManager() {
        this.userDB = new UserDB();
        this.medicalRecordDB = new MedicalRecordDB();
        this.medicineDB = new MedicineDB(); // Initialize MedicineDB
    }

    public void initialize() throws IOException {
        userDB.load();
        medicalRecordDB.load();
        medicineDB.load(); // Load data for MedicineDB
    }

    public void save() throws IOException {
        userDB.save();
        medicalRecordDB.save();
        medicineDB.save(); // Save data for MedicineDB
    }

    // Accessor methods for individual databases
    public UserDB getUserDB() {
        return userDB;
    }

    public MedicalRecordDB getMedicalRecordDB() {
        return medicalRecordDB;
    }

    public MedicineDB getMedicineDB() { // Accessor method for MedicineDB
        return medicineDB;
    }
}