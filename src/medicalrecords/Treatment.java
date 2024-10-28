package medicalrecords;

import java.time.LocalDate;

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
