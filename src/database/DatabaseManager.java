package database;

import java.io.IOException;

public class DatabaseManager {
    private UserDB userDB;
    private MedicalRecordDB medicalRecordDB;

    public DatabaseManager() {
        this.userDB = new UserDB();
        this.medicalRecordDB = new MedicalRecordDB();
    }

    public void initialize() throws IOException {
        userDB.load();
        medicalRecordDB.load();
    }

    public void save() throws IOException {
        userDB.save();
        medicalRecordDB.save();
    }

    // Accessor methods for individual databases
    public UserDB getUserDB() {
        return userDB;
    }

    public MedicalRecordDB getMedicalRecordDB() {
        return medicalRecordDB;
    }
}