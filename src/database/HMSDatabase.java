/*
 * This is a singleton eagerloading database class that only saves to the csv before the program is closed.
 */

package database;

import users.Doctor;
import users.Patient;
import users.Pharmacist;
import users.User;
import medicalrecords.MedicalRecord;
import java.io.*;
import java.util.*;

public class HMSDatabase {
    public static final String SEPARATOR = ",";

    // Singleton instance
    private static HMSDatabase instance;

    // Data fields to store all users and medical records
    private List<User> users;
    private List<MedicalRecord> medicalRecords;

    // Private constructor to prevent instantiation
    private HMSDatabase() {
        users = new ArrayList<>();
        medicalRecords = new ArrayList<>();
    }

    // Static method to provide access to the single instance
    public static HMSDatabase getInstance() {
        if (instance == null) {
            instance = new HMSDatabase();
        }
        return instance;
    }

    // Load data from CSV files into memory
    public void initializeDatabase() throws IOException {
        loadUsers("csv_data/User_List.csv"); // Load users from the single user list
        loadMedicalRecords("csv_data/Medical_Record.csv");
    }

    public void closeDatabase() throws IOException {
        saveUsers();
        saveMedicalRecords();
    }

    // Load users (patients, staff, etc.) based on role from the CSV file
    private void loadUsers(String filename) throws IOException {
        List<String> lines = readFile(filename);

        // Skip the header by starting from 1
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] tokens = line.split(SEPARATOR);

            if (tokens.length == 8) { // Ensure all necessary fields are present
                String id = tokens[0];
                String name = tokens[1];
                String dob = tokens[2]; // Date of birth
                String gender = tokens[3];
                String phoneNumber = tokens[4];
                String emailAddress = tokens[5];
                String password = tokens[6];
                String role = tokens[7];

                System.out.println(id + " " + name + " " + dob + " " + gender + " " + phoneNumber + " " + emailAddress
                        + " " + password + " " + role);

                // Based on the role, create specific User objects
                switch (role) {
                    // Order of params: ID, Name, Date of Birth, Gender, Phone Number, Email
                    // Address,
                    // Password, Role
                    case "Patient":
                        Patient patient = new Patient(id, name, dob, gender, phoneNumber, emailAddress, password);
                        users.add(patient);
                        break;

                    case "Doctor":
                        Doctor doctor = new Doctor(id, name, dob, gender, phoneNumber, emailAddress, password);
                        users.add(doctor);
                        break;

                    case "Pharmacist":
                        Pharmacist pharmacist = new Pharmacist(id, name, dob, gender, phoneNumber, emailAddress,
                                password);
                        users.add(pharmacist);
                        break;

                    case "Administrator":
                        // TODO

                        break;

                    default:
                        System.out.println("Unknown user role for ID: " + id);
                        break;
                }
            } else {
                System.out.println("Invalid line in CSV: " + line);
            }
        }
    }

    private void loadMedicalRecords(String filename) throws IOException {
        List<String> lines = readFile(filename);
        // Start processing from index 1 to skip the header
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] tokens = line.split(SEPARATOR);

            if (tokens.length >= 10) { // Ensure there are enough tokens for all fields
                // Extract fields including diagnoses, treatments, and prescriptions
                MedicalRecord record = new MedicalRecord(
                        tokens[0], // patientId
                        tokens[1], // name
                        tokens[2], // dateOfBirth
                        tokens[3], // gender
                        tokens[4], // bloodType
                        tokens[5], // phoneNumber
                        tokens[6], // emailAddress
                        tokens[7], // diagnoses (as a string)
                        tokens[8], // treatments (as a string)
                        tokens[9] // prescriptions (as a string)
                );

                medicalRecords.add(record);
            } else {
                System.out.println("Invalid line in CSV: " + line);
            }
        }
    }

    private void saveData(String filename, List<?> dataList) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        try {
            for (Object obj : dataList) {
                out.println(obj.toString()); // Ensure that User and MedicalRecord have proper toString methods
            }
        } finally {
            out.close();
        }
    }

    private List<String> readFile(String filename) throws IOException {
        List<String> data = new ArrayList<>();
        Scanner scanner = new Scanner(new FileInputStream(filename));
        try {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
        return data;
    }

    // ========================= CRUD OPERATIONS FOR USERS =========================

    public boolean createUser(User user) {
        if (user != null) {
            users.add(user);
            try {
                saveUsers(); // Save changes immediately or manage saving elsewhere
                return true;
            } catch (IOException e) {
                e.printStackTrace(); // Handle errors appropriately
                return false;
            }
        }
        return false; // Invalid user
    }

    public User getUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user; // Return the matching user
            }
        }
        return null; // Return null if not found
    }

    public boolean updateUser(User updatedUser) {
        User existingUser = getUserById(updatedUser.getId());
        if (existingUser != null) {
            users.remove(existingUser);
            users.add(updatedUser);
            try {
                saveUsers(); // Save changes immediately or manage saving elsewhere
                return true;
            } catch (IOException e) {
                e.printStackTrace(); // Handle errors appropriately
                return false;
            }
        }
        return false; // User not found
    }

    public boolean deleteUser(String id) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            users.remove(existingUser);
            try {
                saveUsers(); // Save changes immediately or manage saving elsewhere
                return true;
            } catch (IOException e) {
                e.printStackTrace(); // Handle errors appropriately
                return false;
            }
        }
        return false; // User not found
    }

    public void saveUsers() throws IOException {
        saveData("csv_data/User_List.csv", users);
    }

    // ================== CRUD OPERATIONS FOR MEDICAL RECORDS ====================
    public boolean createMedicalRecord(MedicalRecord medicalRecord) {
        if (medicalRecord != null) {
            medicalRecords.add(medicalRecord);
        }
        return false; // Invalid record
    }

    public MedicalRecord getMedicalRecordByPatientId(String patientId) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getPatientId().equals(patientId)) {
                return record; // Return the matching record
            }
        }
        return null; // Return null if not found
    }

    public boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecord existingRecord = getMedicalRecordByPatientId(medicalRecord.getPatientId());
        if (existingRecord != null) {
            medicalRecords.remove(existingRecord);
            medicalRecords.add(medicalRecord);
        }
        return false; // Record not found
    }

    public boolean deleteMedicalRecord(String patientId) {
        MedicalRecord existingRecord = getMedicalRecordByPatientId(patientId);
        if (existingRecord != null) {
            medicalRecords.remove(existingRecord);
        }
        return false; // Record not found
    }

    public void saveMedicalRecords() throws IOException {
        saveData("csv_data/Medical_Record.csv", medicalRecords);
    }
}
