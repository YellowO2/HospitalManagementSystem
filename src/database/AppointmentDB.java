package database;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import appointments.Appointment;

/**
 * A database class to manage Appointment objects.
 * Handles CRUD operations and provides methods to interact with appointment
 * data,
 * including loading and saving to a CSV file.
 */
public class AppointmentDB extends Database<Appointment> {
    private List<Appointment> appointments; // List of appointments
    private static final String filename = "csv_data/Appointment_List.csv"; // Filepath for CSV file
    private static final String header = "AppointmentID,DoctorID,PatientID,AppointmentDate,TimeSlot,Status"; // CSV file
                                                                                                             // header

    /**
     * Constructs an AppointmentDB instance and initializes the list of
     * appointments.
     */
    public AppointmentDB() {
        super(filename); // Pass the filename to the parent class
        this.appointments = new ArrayList<>();
    }

    /**
     * Creates a new appointment and saves the changes to the file.
     *
     * @param appointment the Appointment object to be created
     * @return true if the appointment was successfully added, false otherwise
     */
    @Override
    public boolean create(Appointment appointment) {
        if (appointment != null) {
            appointments.add(appointment);
            try {
                save(); // Automatically save after creating
            } catch (IOException e) {
                System.err.println("Error saving data after creating appointment: " + e.getMessage());
            }
            return true;
        }
        return false;
    }

    /**
     * Retrieves an appointment by its unique ID.
     *
     * @param appointmentId the unique ID of the appointment
     * @return the Appointment object if found, or null otherwise
     */
    @Override
    public Appointment getById(String appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return appointment;
            }
        }
        return null; // Appointment not found
    }

    /**
     * Retrieves all appointments stored in the database.
     *
     * @return a list of all Appointment objects
     */
    @Override
    public List<Appointment> getAll() {
        return appointments;
    }

    /**
     * Updates an existing appointment and saves the changes to the file.
     *
     * @param appointment the updated Appointment object
     * @return true if the appointment was successfully updated, false otherwise
     */
    @Override
    public boolean update(Appointment appointment) {
        Appointment existingAppointment = getById(appointment.getAppointmentId());
        if (existingAppointment != null) {
            appointments.remove(existingAppointment);
            appointments.add(appointment);
            try {
                save(); // Automatically save after updating
            } catch (IOException e) {
                System.err.println("Error saving data after updating appointment: " + e.getMessage());
            }
            return true;
        }
        return false; // Appointment not found
    }

    /**
     * Deletes an appointment by its unique ID and saves the changes to the file.
     *
     * @param appointmentId the unique ID of the appointment to delete
     * @return true if the appointment was successfully deleted, false otherwise
     */
    @Override
    public boolean delete(String appointmentId) {
        Appointment existingAppointment = getById(appointmentId);
        if (existingAppointment != null) {
            appointments.remove(existingAppointment);
            try {
                save(); // Automatically save after deleting
            } catch (IOException e) {
                System.err.println("Error saving data after deleting appointment: " + e.getMessage());
            }
            return true;
        }
        return false; // Appointment not found
    }

    /**
     * Saves all appointments to the CSV file.
     *
     * @return true if the data was successfully saved
     * @throws IOException if there is an error saving the data
     */
    @Override
    public boolean save() throws IOException {
        saveData(filename, appointments, header);
        return true;
    }

    /**
     * Loads appointments from the CSV file into the database.
     *
     * @return true if the data was successfully loaded
     * @throws IOException if there is an error reading the file
     */
    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(filename); // Read the CSV file
        for (String line : lines) {
            String[] tokens = splitLine(line); // Split line into tokens

            if (tokens.length == 6) { // Make sure there are enough tokens in the line
                Appointment appointment = new Appointment(
                        tokens[0], // AppointmentID
                        tokens[1], // PatientID
                        tokens[2], // DoctorID
                        LocalDate.parse(tokens[3]), // AppointmentDate
                        LocalTime.parse(tokens[4]), // AppointmentTime
                        tokens[5] // Status
                );
                appointments.add(appointment);
            } else {
                System.out.println("Invalid line in " + filename + ": " + line);
            }
        }
        return true;
    }

    /**
     * Retrieves all appointments for a specific doctor.
     *
     * @param doctorId the unique ID of the doctor
     * @return a list of Appointment objects for the specified doctor
     */
    public List<Appointment> getDoctorAppointments(String doctorId) {
        List<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorId().equals(doctorId)) {
                doctorAppointments.add(appointment);
            }
        }
        return doctorAppointments;
    }

    /**
     * Retrieves all appointments for a specific patient.
     *
     * @param patientId the unique ID of the patient
     * @return a list of Appointment objects for the specified patient
     */
    public List<Appointment> getPatientAppointments(String patientId) {
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientId().equals(patientId)) {
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }
}
