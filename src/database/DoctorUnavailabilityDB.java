package database;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import appointments.DoctorUnavailableSlots;

/**
 * A class that manages doctor unavailability slots in the database.
 * This class allows creating, deleting, retrieving, and saving doctor
 * unavailability data to and from a CSV file.
 * 
 * @see DoctorUnavailableSlots
 */
public class DoctorUnavailabilityDB extends Database<DoctorUnavailableSlots> {
    private List<DoctorUnavailableSlots> availabilities; // List to store unavailability slots
    private static final String filename = "csv_data/Doctor_Unavailability.csv"; // File path for saving/loading data
    private static final String header = "DoctorID,Date,Time"; // Header for the CSV file

    /**
     * Constructor for initializing the DoctorUnavailabilityDB with the specified
     * CSV file path.
     */
    public DoctorUnavailabilityDB() {
        super(filename); // Pass the filename to the parent class
        this.availabilities = new ArrayList<>();
    }

    /**
     * Creates a new doctor unavailability slot and adds it to the list.
     *
     * @param availability the DoctorUnavailableSlots object to be added
     * @return true if the availability was successfully added, false otherwise
     */
    @Override
    public boolean create(DoctorUnavailableSlots availability) {
        if (availability != null) {
            availabilities.add(availability);
            return true;
        }
        return false;
    }

    /**
     * Retrieves all unavailability slots for a specific doctor on a particular
     * date.
     *
     * @param doctorId the ID of the doctor
     * @param date     the date of unavailability
     * @return a list of DoctorUnavailableSlots for the specified doctor and date
     */
    public List<DoctorUnavailableSlots> getDoctorUnavailability(String doctorId, LocalDate date) {
        List<DoctorUnavailableSlots> doctorAvailability = new ArrayList<>();
        for (DoctorUnavailableSlots unavailability : availabilities) {
            if (unavailability.getDoctorId().equals(doctorId) && unavailability.getDate().equals(date)) {
                doctorAvailability.add(unavailability);
            }
        }
        return doctorAvailability;
    }

    /**
     * Retrieves all unavailability slots in the database.
     *
     * @return a list of all DoctorUnavailableSlots
     */
    @Override
    public List<DoctorUnavailableSlots> getAll() {
        return availabilities;
    }

    /**
     * Deletes a specific unavailability slot based on doctor ID, date, and time
     * slot.
     *
     * @param doctorId the ID of the doctor
     * @param date     the date of unavailability
     * @param timeSlot the time slot of unavailability
     * @return true if the unavailability was successfully deleted, false otherwise
     */
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

    /**
     * Saves all doctor unavailability slots to a CSV file.
     *
     * @return true if the data was successfully saved
     * @throws IOException if an I/O error occurs during saving
     */
    @Override
    public boolean save() throws IOException {
        saveData(filename, availabilities, header);
        return true;
    }

    /**
     * Loads all doctor unavailability slots from a CSV file into the database.
     *
     * @return true if the data was successfully loaded
     * @throws IOException if an I/O error occurs during loading
     */
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

    // The following methods are not implemented as they are not needed for this
    // specific functionality:

    /**
     * Retrieves a doctor unavailability slot by ID. Not implemented in this class.
     *
     * @param id the unique ID of the doctor unavailability
     * @throws UnsupportedOperationException if called
     */
    @Override
    public DoctorUnavailableSlots getById(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    /**
     * Updates a doctor unavailability slot. Not implemented in this class.
     *
     * @param entity the entity to be updated
     * @throws UnsupportedOperationException if called
     */
    @Override
    public boolean update(DoctorUnavailableSlots entity) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    /**
     * Deletes a doctor unavailability slot by ID. Not implemented in this class.
     *
     * @param id the ID of the entity to be deleted
     * @throws UnsupportedOperationException if called
     */
    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
