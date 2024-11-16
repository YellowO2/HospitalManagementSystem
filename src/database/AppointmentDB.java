package database;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import appointments.Appointment;

public class AppointmentDB extends Database<Appointment> {
    private List<Appointment> appointments;
    private static final String filename = "csv_data/Appointment_List.csv";
    private static final String header = "AppointmentID,DoctorID,PatientID,AppointmentDate,TimeSlot,Status";

    // Constructor
    public AppointmentDB() {
        super(filename); // Pass the filename to the parent class
        this.appointments = new ArrayList<>();
    }

    // Create an appointment
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

    // Get an appointment by its ID
    @Override
    public Appointment getById(String appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return appointment;
            }
        }
        return null; // Appointment not found
    }

    @Override
    public List<Appointment> getAll() {
        return appointments;
    }

    // Update an existing appointment
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

    // Delete an appointment by ID
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

    // Save appointments to CSV
    @Override
    public boolean save() throws IOException {
        saveData(filename, appointments, header);
        return true;
    }

    // Load appointments from CSV
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

    public List<Appointment> getDoctorAppointments(String doctorId) {
        List<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorId().equals(doctorId)) {
                doctorAppointments.add(appointment);
            }
        }
        return doctorAppointments;
    }

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
