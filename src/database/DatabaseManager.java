package database;

import users.User;
import users.Staff;
import users.Patient;
import medicalrecords.MedicalRecord;
import java.io.IOException;

public class DatabaseManager {
    private UserDB userDB;
    private MedicalRecordDB medicalRecordDB;
    private MedicineDB medicineDB;
    private StaffDB staffDB;
    private PatientDB patientDB;

    public DatabaseManager() {
        this.userDB = new UserDB();
        this.medicalRecordDB = new MedicalRecordDB();
        this.medicineDB = new MedicineDB();
        this.staffDB = new StaffDB();
        this.patientDB = new PatientDB();
    }

    public void initialize() throws IOException {
        userDB.load();
        medicalRecordDB.load();
        medicineDB.load();
        staffDB.load();
        patientDB.load();
    }

    public void save() throws IOException {
        userDB.save();
        medicalRecordDB.save();
        medicineDB.save();
        staffDB.save();
        patientDB.save();
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

    public StaffDB getStaffDB() {
        return staffDB;
    }

    public PatientDB getPatientDB() {
        return patientDB;
    }

    // CRUD methods for UserDB
    public boolean createUser(User user) {
        return userDB.create(user);
    }

    public User getUserById(String id) {
        return userDB.getById(id);
    }

    // CRUD methods for MedicalRecordDB
    public boolean createMedicalRecord(MedicalRecord record) {
        return medicalRecordDB.create(record);
    }

    public MedicalRecord getMedicalRecordById(String patientId) {
        return medicalRecordDB.getById(patientId);
    }

    // CRUD methods for StaffDB
    public boolean createStaff(Staff staff) {
        return staffDB.create(staff);
    }

    public Staff getStaffById(String id) {
        return staffDB.getById(id);
    }

    // CRUD methods for PatientDB
    public boolean createPatient(Patient patient) {
        return patientDB.create(patient);
    }

    public Patient getPatientById(String id) {
        return patientDB.getById(id);
    }
}
