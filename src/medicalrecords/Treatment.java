package medicalrecords;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Treatment {
    private String treatmentName; // Name of the treatment
    private LocalDate treatmentDate; // Date when the treatment was administered
    private String doctorName; // The doctor who administered the treatment
    private String treatmentDetails; // Additional details of the treatment

    // Constructor
    public Treatment(String treatmentName, LocalDate treatmentDate, String doctorName, String treatmentDetails) {
        this.treatmentName = treatmentName;
        this.treatmentDate = treatmentDate;
        this.doctorName = doctorName;
        this.treatmentDetails = treatmentDetails;
    }

    // Static factory method to create Treatment from CSV string
    public static Treatment fromCSV(String treatmentString) {
        String[] parts = treatmentString.split("\\|");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid treatment format. Expected 4 fields.");
        }

        String treatmentName = parts[0];
        LocalDate treatmentDate;
        try {
            treatmentDate = LocalDate.parse(parts[1]); // Parsing the date
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD.");
        }
        String doctorName = parts[2];
        String treatmentDetails = parts[3];

        return new Treatment(treatmentName, treatmentDate, doctorName, treatmentDetails);
    }

    public String toCSV() {
        return getTreatmentName() + "|" + getTreatmentDate() + "|" + getDoctorName()
                + "|" + getTreatmentDetails();
    }

    // Getters
    public String getTreatmentName() {
        return treatmentName;
    }

    public LocalDate getTreatmentDate() {
        return treatmentDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getTreatmentDetails() {
        return treatmentDetails;
    }

    // Method to display treatment details
    public String getTreatment() {

        return "Treatment: " + treatmentName + "\nDate: " + treatmentDate + "\nDoctor: " + doctorName
                + "\nDetails: " + treatmentDetails;
    }
}
