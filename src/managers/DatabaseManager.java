/**
 * Manages the initialization and saving of various databases used in the 
 * Hospital Management System (HMS). Provides access to individual database instances.
 */
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
    private ReplenishmentDB replenishmentDB;
    private AppointmentDB appointmentDB;
    private AppointmentOutcomeRecordDB appointmentOutcomeRecordDB;
    private DoctorUnavailabilityDB doctorAvailabilityDB;

    /**
     * Constructor to initialize the database manager with all database instances.
     */
    public DatabaseManager() {
        this.userDB = new UserDB();
        this.medicalRecordDB = new MedicalRecordDB();
        this.medicineDB = new MedicineDB();
        this.replenishmentDB = new ReplenishmentDB();
        this.appointmentDB = new AppointmentDB();
        this.appointmentOutcomeRecordDB = new AppointmentOutcomeRecordDB();
        this.doctorAvailabilityDB = new DoctorUnavailabilityDB();
    }

    /**
     * Initializes all databases by loading data from their respective sources.
     *
     * @throws IOException if an error occurs during data loading.
     */
    public void initialize() throws IOException {
        userDB.load();
        medicalRecordDB.load();
        medicineDB.load();
        replenishmentDB.load();
        appointmentDB.load();
        appointmentOutcomeRecordDB.load();
        doctorAvailabilityDB.load();
    }

    /**
     * Saves all database data to their respective storage sources.
     *
     * @throws IOException if an error occurs during data saving.
     */
    public void save() throws IOException {
        userDB.save();
        medicalRecordDB.save();
        medicineDB.save();
        replenishmentDB.save();
        appointmentDB.save();
        appointmentOutcomeRecordDB.save();
        doctorAvailabilityDB.save();
    }

    /**
     * Accessor for the UserDB instance.
     *
     * @return the UserDB instance.
     */
    public UserDB getUserDB() {
        return userDB;
    }

    /**
     * Accessor for the MedicalRecordDB instance.
     *
     * @return the MedicalRecordDB instance.
     */
    public MedicalRecordDB getMedicalRecordDB() {
        return medicalRecordDB;
    }

    /**
     * Accessor for the MedicineDB instance.
     *
     * @return the MedicineDB instance.
     */
    public MedicineDB getMedicineDB() {
        return medicineDB;
    }

    /**
     * Accessor for the ReplenishmentDB instance.
     *
     * @return the ReplenishmentDB instance.
     */
    public ReplenishmentDB getReplenishmentDB() {
        return replenishmentDB;
    }

    /**
     * Accessor for the AppointmentDB instance.
     *
     * @return the AppointmentDB instance.
     */
    public AppointmentDB getAppointmentDB() {
        return appointmentDB;
    }

    /**
     * Accessor for the AppointmentOutcomeRecordDB instance.
     *
     * @return the AppointmentOutcomeRecordDB instance.
     */
    public AppointmentOutcomeRecordDB getAppointmentOutcomeRecordDB() {
        return appointmentOutcomeRecordDB;
    }

    /**
     * Accessor for the DoctorUnavailabilityDB instance.
     *
     * @return the DoctorUnavailabilityDB instance.
     */
    public DoctorUnavailabilityDB getdoctorAvailabilityDB() {
        return doctorAvailabilityDB;
    }
}
