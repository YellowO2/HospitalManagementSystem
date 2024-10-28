package medicalrecords;

import java.time.LocalDate;

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
    public void viewDiagnosis() {
        System.out.println("Diagnosis: " + diagnosisName);
        System.out.println("Severity: " + severity);
        System.out.println("Date: " + diagnosisDate);
        System.out.println("Doctor: " + doctorName);
    }
}
