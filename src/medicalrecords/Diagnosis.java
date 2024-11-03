package medicalrecords;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Diagnosis {
    private String diagnosisName; // Name of the diagnosis
    private String severity; // Severity of the diagnosis (e.g., mild, severe)
    private LocalDate diagnosisDate; // Date of the diagnosis
    private String doctorName; // The doctor who made the diagnosis

    // Constructor
    public Diagnosis(String diagnosisName, String severity, LocalDate diagnosisDate, String doctorName) {
        this.diagnosisName = diagnosisName;
        this.severity = severity;
        this.diagnosisDate = diagnosisDate;
        this.doctorName = doctorName;
    }

    // Static method to parse a string and create a Diagnosis object
    public static Diagnosis fromCSV(String diagnosisString) {
        String[] parts = diagnosisString.split("\\|");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid diagnosis format. Expected 4 fields.");
        }

        String diagnosisName = parts[0];
        String severity = parts[1];
        LocalDate diagnosisDate;
        try {
            diagnosisDate = LocalDate.parse(parts[2]); // Parsing the date
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD.");
        }
        String doctorName = parts[3];

        return new Diagnosis(diagnosisName, severity, diagnosisDate, doctorName);
    }

    public String toCSV() {
        return getDiagnosisName() + "|" + getSeverity() + "|"
                + getDiagnosisDate() + "|" + getDoctorName();
    }

    // Getters
    public String getDiagnosisName() {
        return diagnosisName;
    }

    public String getSeverity() {
        return severity;
    }

    public LocalDate getDiagnosisDate() {
        return diagnosisDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    // Method to display diagnosis details
    public String getDiagnosisDetails() {
        return "Diagnosis: " + diagnosisName + "\nSeverity: " + severity + "\nDate: " + diagnosisDate
                + "\nDoctor: " + doctorName;
    }
}
