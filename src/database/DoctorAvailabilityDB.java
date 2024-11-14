package database;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import appointments.DoctorAvailability;
import medicalrecords.MedicalRecord;

public class DoctorAvailabilityDB extends Database<DoctorAvailability> {
    private List<DoctorAvailability> doctorAvailabilities;
    private static final String filename = "csv_data/Doctor_Availability_List.csv";
    private static final String header = "DoctorID,Date,Time Slot Start,Time Slot End,Availability Status";

    public DoctorAvailabilityDB() {
        super(filename);
        this.doctorAvailabilities = new ArrayList<>();
    }

    // Implement the CRUD operations for DoctorAvailability
    @Override
    public boolean create(DoctorAvailability availability) {
        if (availability != null) {
            doctorAvailabilities.add(availability);
            return true;
        }
        return false; // Invalid record
    }

    @Override
    public DoctorAvailability getById(String doctorId) {
        for (DoctorAvailability availability : doctorAvailabilities) {
            if (availability.getDoctorId().equals(doctorId)) {
                return availability; // Return the matching record
            }
        }
        return null; // Return null if not found
    }

    @Override
    public List<DoctorAvailability> getAll(){
        return doctorAvailabilities;
    } 

    @Override
    public boolean update(DoctorAvailability availability) {
        DoctorAvailability existingRecord = getById(availability.getDoctorId());
        if (existingRecord != null) {
            doctorAvailabilities.remove(existingRecord);
            doctorAvailabilities.add(availability);
            return true;
        }
        return false; // Record not found
    }

    @Override
    public boolean delete(String doctorId) {
        DoctorAvailability existingRecord = getById(doctorId);
        if (existingRecord != null) {
            doctorAvailabilities.remove(existingRecord);
            return true;
        }
        return false; // Record not found
    }

    @Override
    public boolean save() throws IOException {
        saveData(filename, doctorAvailabilities, header);
        return true;
    }

    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(filename);
        for (String line : lines) {
            String[] tokens = splitLine(line);

            if (tokens.length >= 5) {
                DoctorAvailability availability = new DoctorAvailability(
                        tokens[0], // DoctorID
                        LocalDate.parse(tokens[1]), // Date
                        LocalTime.parse(tokens[2]), // Time Slot Start
                        LocalTime.parse(tokens[3]), // Time Slot End
                        tokens[4] // Availability Status
                );
                doctorAvailabilities.add(availability);
            } else {
                System.out.println("Invalid line in CSV: " + line);
            }
        }
        return true;
    }
}
