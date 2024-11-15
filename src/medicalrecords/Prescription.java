package medicalrecords;

public class Prescription {
    private String prescriptionId; // Unique ID for the prescriptions 
    private String medicationName; // Name of the medication
    private String dosage; // Dosage information (e.g., "500mg twice a day")
    private String frequency; // Frequency of administration (e.g., "2 times per day")
    private int amount; // Number of units prescribed (e.g., 30 tablets)
    private String instructions; // Special instructions (e.g., "Take after meals")
    private int status; // Status of the prescription (e.g., pending, prescribed, filled)

    // Constructor
    public Prescription(String medicationName, String dosage, String instructions, String frequency, int amount,
            int status) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.amount = amount;
        this.instructions = instructions;
        this.status = status;
    }

    // Static factory method to create a Prescription from a CSV format
    public static Prescription fromCSV(String csvString) {
        String[] parts = csvString.split("\\|");
        if (parts.length == 6) { // Ensure you have all required fields
            String medicationName = parts[0];
            String dosage = parts[1];
            String frequency = parts[2];
            int amount = Integer.parseInt(parts[3]); // Ensure to handle exceptions if needed
            String instructions = parts[4];
            int status = Integer.parseInt(parts[5]);

            return new Prescription(medicationName, dosage, instructions, frequency, amount, status);
        }
        throw new IllegalArgumentException("Invalid CSV format for Prescription: " + csvString);
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%d|%s|%d", medicationName, dosage, frequency, amount, instructions, status);
    }

    // Getters and Setters
    public String getPrescriptionId() {
        return prescriptionId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDescriptionDetails() {
        return "Medication Name: " + medicationName + "\nDosage: " + dosage
                + "\nFrequency: " + frequency + "\nStatus: " + status + "\nAmount: " + amount
                + "\nInstructions: " + instructions + "\n";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}