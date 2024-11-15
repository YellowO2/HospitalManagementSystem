package database;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import appointments.DoctorUnavailableSlots;

public class DoctorUnavailabilityDB extends Database<DoctorUnavailableSlots> {
    private List<DoctorUnavailableSlots> availabilities;
    private static final String filename = "csv_data/Doctor_Unavailability.csv";
    private static final String header = "DoctorID,Date,Time";

    // Constructor
    public DoctorUnavailabilityDB() {
        super(filename); // Pass the filename to the parent class
        this.availabilities = new ArrayList<>();
    }

    // Create a new availability slot
    @Override
    public boolean create(DoctorUnavailableSlots availability) {
        if (availability != null) {
            availabilities.add(availability);
            return true;
        }
        return false;
    }

    // Get unavailability by doctor ID and date
    public List<DoctorUnavailableSlots> getDoctorUnavailability(String doctorId, LocalDate date) {
        List<DoctorUnavailableSlots> doctorAvailability = new ArrayList<>();
        for (DoctorUnavailableSlots unavailability : availabilities) {
            if (unavailability.getDoctorId().equals(doctorId) && unavailability.getDate().equals(date)) {
                doctorAvailability.add(unavailability);
            }
        }
        return doctorAvailability;
    }

    // Get all availability slots
    @Override
    public List<DoctorUnavailableSlots> getAll() {
        return availabilities;
    }

    // Delete availability by doctor ID and time slot
    public boolean deleteAvailability(String doctorId, LocalDate date, LocalTime timeSlot) {
        DoctorUnavailableSlots existingAvailability = null;
        for (DoctorUnavailableSlots availability : availabilities) {
            if (availability.getDoctorId().equals(doctorId)
                    && availability.getDate().equals(date)
                    && availability.getTime().equals(timeSlot)) {
                existingAvailability = availability;
                break;
            }
        }
        if (existingAvailability != null) {
            availabilities.remove(existingAvailability);
            return true;
        }
        return false;
    }

    // Save availability to CSV
    @Override
    public boolean save() throws IOException {
        saveData(filename, availabilities, header);
        return true;
    }

    // Load availability from CSV
    @Override
    public boolean load() throws IOException {

        List<String> lines = readFile(filename); // Read the CSV file
        for (String line : lines) {

            String[] tokens = splitLine(line); // Split line into tokens

            if (tokens.length >= 3) { // Ensure there are enough tokens
                DoctorUnavailableSlots availability = new DoctorUnavailableSlots(
                        tokens[0], // DoctorID
                        LocalDate.parse(tokens[1]), // AvailableDate
                        LocalTime.parse(tokens[2]) // TimeSlot
                );
                availabilities.add(availability);
            } else {
                System.out.println("Invalid line in " + filename + ": " + line);
            }
        }
        return true;
    }

    @Override
    public DoctorUnavailableSlots getById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public boolean update(DoctorUnavailableSlots entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
