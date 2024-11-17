package medicalrecords;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * The Treatment class represents a medical treatment that has been administered to a patient.
 * It includes information about the treatment name, date, administering doctor, and any additional details.
 */
public class Treatment {
    private String treatmentName;
    private LocalDate treatmentDate;
    private String doctorName;
    private String treatmentDetails;

    /**
     * Constructs a Treatment object with the specified details.
     *
     * @param treatmentName   the name of the treatment
     * @param treatmentDate   the date when the treatment was administered
     * @param doctorName      the name of the doctor who administered the treatment
     * @param treatmentDetails additional details about the treatment
     */
    public Treatment(String treatmentName, LocalDate treatmentDate, String doctorName, String treatmentDetails) {
        this.treatmentName = treatmentName;
        this.treatmentDate = treatmentDate;
        this.doctorName = doctorName;
        this.treatmentDetails = treatmentDetails;
    }

    /**
     * Creates a Treatment object from a CSV string.
     * The string should be formatted as "treatmentName|treatmentDate|doctorName|treatmentDetails".
     *
     * @param treatmentString the CSV string to parse
     * @return a Treatment object created from the parsed CSV string
     * @throws IllegalArgumentException if the CSV format is invalid or the date format is incorrect
     */
    public static Treatment fromCSV(String treatmentString) {
        String[] parts = treatmentString.split("\\|");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid treatment format. Expected 4 fields.");
        }

        String treatmentName = parts[0];
        LocalDate treatmentDate;
        try {
            treatmentDate = LocalDate.parse(parts[1]);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD.");
        }
        String doctorName = parts[2];
        String treatmentDetails = parts[3];

        return new Treatment(treatmentName, treatmentDate, doctorName, treatmentDetails);
    }

    /**
     * Returns a CSV-formatted string representation of the treatment.
     *
     * @return a string in the format "treatmentName|treatmentDate|doctorName|treatmentDetails"
     */
    @Override
    public String toString() {
        return getTreatmentName() + "|" + getTreatmentDate() + "|" + getDoctorName() + "|" + getTreatmentDetails();
    }

    // Getters

    /**
     * Gets the name of the treatment.
     *
     * @return the treatment name
     */
    public String getTreatmentName() {
        return treatmentName;
    }

    /**
     * Gets the date when the treatment was administered.
     *
     * @return the treatment date
     */
    public LocalDate getTreatmentDate() {
        return treatmentDate;
    }

    /**
     * Gets the name of the doctor who administered the treatment.
     *
     * @return the doctor's name
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * Gets additional details about the treatment.
     *
     * @return the treatment details
     */
    public String getTreatmentDetails() {
        return treatmentDetails;
    }

    /**
     * Returns a detailed description of the treatment.
     *
     * @return a formatted string containing the details of the treatment
     */
    public String getTreatment() {
        return "Treatment: " + treatmentName + "\nDate: " + treatmentDate + "\nDoctor: " + doctorName
                + "\nDetails: " + treatmentDetails;
    }
}

