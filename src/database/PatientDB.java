package database;

import users.Patient; // Ensure the Patient class exists or adapt as needed.
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientDB extends Database<Patient> {
    private List<Patient> patientList;
    private static final String PATIENT_FILE = "csv_data/Patient_List.csv";
    private static final String PATIENT_HEADER = "PatientID,Name,DateOfBirth,Gender,BloodType,ContactInformation";

    public PatientDB() {
        super(PATIENT_FILE);
        patientList = new ArrayList<>();
    }

    @Override
    public boolean create(Patient patient) {
        if (patient != null) {
            patientList.add(patient);
            try {
                this.save();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public Patient getById(String id) {
        for (Patient patient : patientList) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }

    @Override
    public boolean update(Patient updatedPatient) {
        Patient existingPatient = getById(updatedPatient.getId());
        if (existingPatient != null) {
            patientList.remove(existingPatient);
            patientList.add(updatedPatient);
            try {
                save();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        Patient patient = getById(id);
        if (patient != null) {
            patientList.remove(patient);
            try {
                save();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean save() throws IOException {
        saveData(PATIENT_FILE, patientList, PATIENT_HEADER);
        return true;
    }

    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(PATIENT_FILE);

        for (String line : lines) {
            String[] tokens = splitLine(line);

            if (tokens.length == 6) { // Ensure all fields are present
                Patient patient = new Patient(
                        tokens[0], // PatientID
                        tokens[1], // Name
                        tokens[2], // DateOfBirth
                        tokens[3], // Gender
                        tokens[4], // BloodType
                        tokens[5]  // Contact Information
                );
                patientList.add(patient);
            } else {
                System.out.println("Invalid line in CSV: " + line);
            }
        }
        return true;
    }

    @Override
    public List<Patient> getAll() {
        return patientList;
    }
}
