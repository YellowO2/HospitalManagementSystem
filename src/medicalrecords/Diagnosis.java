package medicalrecords;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * The Diagnosis class represents a medical diagnosis made for a patient.
 * It includes the name of the diagnosis, its severity, the date it was made,
 * and the name of the doctor who made the diagnosis.
 */
public class Diagnosis {
    private String diagnosisName;
    private String severity;
    private LocalDate diagnosisDate;
    private String doctorName;

    /**
     * Constructs a Diagnosis object with the specified details.
     *
     * @param diagnosisName the name of the diagnosis
     * @param severity      the severity level of the diagnosis (e.g., mild, severe)
     * @param diagnosisDate the date the diagnosis was made
     * @param doctorName    the name of the doctor who made the diagnosis
     */
    public Diagnosis(String diagnosisName, String severity, LocalDate diagnosisDate, String doctorName) {
        this.diagnosisName = diagnosisName;
        this.severity = severity;
        this.diagnosisDate = diagnosisDate;
        this.doctorName = doctorName;
    }

    /**
     * Parses a string in CSV format and creates a Diagnosis object.
     * The string should contain four fields separated by '|':
     * diagnosis name, severity, date (in YYYY-MM-DD format), and doctor name.
     *
     * @param diagnosisString the string to parse
     * @return a Diagnosis object created from the parsed string
     * @throws IllegalArgumentException if the format is incorrect or the date is invalid
     */
    public static Diagnosis fromCSV(String diagnosisString) {
        String[] parts = diagnosisString.split("\\|");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid diagnosis format. Expected 4 fields.");
        }

        String diagnosisName = parts[0];
        String severity = parts[1];
        LocalDate diagnosisDate;
        try {
            diagnosisDate = LocalDate.parse(parts[2]);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD.");
        }
        String doctorName = parts[3];

        return new Diagnosis(diagnosisName, severity, diagnosisDate, doctorName);
    }

    /**
     * Returns a string representation of the diagnosis in CSV format.
     *
     * @return a string in the format "diagnosisName|severity|diagnosisDate|doctorName"
     */
    @Override
    public String toString() {
        return getDiagnosisName() + "|" + getSeverity() + "|"
                + getDiagnosisDate() + "|" + getDoctorName();
    }

    // Getters

    /**
     * Gets the name of the diagnosis.
     *
     * @return the diagnosis name
     */
    public String getDiagnosisName() {
        return diagnosisName;
    }

    /**
     * Gets the severity level of the diagnosis.
     *
     * @return the severity level
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * Gets the date the diagnosis was made.
     *
     * @return the diagnosis date
     */
    public LocalDate getDiagnosisDate() {
        return diagnosisDate;
    }

    /**
     * Gets the name of the doctor who made the diagnosis.
     *
     * @return the doctor's name
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * Returns a detailed description of the diagnosis.
     *
     * @return a formatted string with diagnosis details
     */
    public String getDiagnosisDetails() {
        return "Diagnosis: " + diagnosisName + "\nSeverity: " + severity + "\nDate: " + diagnosisDate
                + "\nDoctor: " + doctorName;
    }
}
