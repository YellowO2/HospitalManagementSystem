package database;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
// import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import appointments.Appointment;
// import medicalrecords.MedicalRecord;

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

            if (tokens.length >= 7) { // Make sure there are enough tokens in the line
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
                System.out.println("Invalid line in CSV: " + line);
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
}
