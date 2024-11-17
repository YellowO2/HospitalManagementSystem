package medicalrecords;

/**
 * The Prescription class represents a medical prescription for a patient.
 * It includes information about the medication, dosage, frequency, amount,
 * instructions, and the status of the prescription.
 */
public class Prescription {
    private String prescriptionId;
    private String medicationName;
    private String dosage;
    private String frequency;
    private int amount;
    private String instructions;
    private int status;

    /**
     * Constructs a Prescription object with the specified details.
     *
     * @param medicationName the name of the medication
     * @param dosage         the dosage information (e.g., "500mg twice a day")
     * @param instructions   any special instructions for taking the medication
     * @param frequency      the frequency of administration (e.g., "2 times per day")
     * @param amount         the number of units prescribed (e.g., 30 tablets)
     * @param status         the status of the prescription (e.g., pending, prescribed, filled)
     */
    public Prescription(String medicationName, String dosage, String instructions, String frequency, int amount,
                        int status) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.amount = amount;
        this.instructions = instructions;
        this.status = status;
    }

    /**
     * Creates a Prescription object from a CSV string.
     * The string should be formatted as "medicationName|dosage|frequency|amount|instructions|status".
     *
     * @param csvString the CSV string to parse
     * @return a Prescription object created from the parsed CSV string
     * @throws IllegalArgumentException if the CSV format is invalid
     */
    public static Prescription fromCSV(String csvString) {
        String[] parts = csvString.split("\\|");
        if (parts.length == 6) {
            String medicationName = parts[0];
            String dosage = parts[1];
            String frequency = parts[2];
            int amount = Integer.parseInt(parts[3]);
            String instructions = parts[4];
            int status = Integer.parseInt(parts[5]);

            return new Prescription(medicationName, dosage, instructions, frequency, amount, status);
        }
        throw new IllegalArgumentException("Invalid CSV format for Prescription: " + csvString);
    }

    /**
     * Returns a CSV-formatted string representation of the prescription.
     *
     * @return a string in the format "medicationName|dosage|frequency|amount|instructions|status"
     */
    @Override
    public String toString() {
        return String.format("%s|%s|%s|%d|%s|%d", medicationName, dosage, frequency, amount, instructions, status);
    }

    // Getters and Setters

    /**
     * Gets the unique ID of the prescription.
     *
     * @return the prescription ID
     */
    public String getPrescriptionId() {
        return prescriptionId;
    }

    /**
     * Gets the name of the medication.
     *
     * @return the medication name
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Sets the name of the medication.
     *
     * @param medicationName the new medication name
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    /**
     * Gets the dosage information.
     *
     * @return the dosage
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * Sets the dosage information.
     *
     * @param dosage the new dosage
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * Gets the frequency of administration.
     *
     * @return the frequency
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * Sets the frequency of administration.
     *
     * @param frequency the new frequency
     */
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     * Gets the number of units prescribed.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the number of units prescribed.
     *
     * @param amount the new amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Gets the special instructions for taking the medication.
     *
     * @return the instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Sets the special instructions for taking the medication.
     *
     * @param instructions the new instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * Gets the status of the prescription.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the prescription.
     *
     * @param status the new status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns a detailed description of the prescription.
     *
     * @return a formatted string containing the details of the prescription
     */
    public String getDescriptionDetails() {
        return "Medication Name: " + medicationName + "\nDosage: " + dosage
                + "\nFrequency: " + frequency + "\nStatus: " + status + "\nAmount: " + amount
                + "\nInstructions: " + instructions + "\n";
    }
}