
package medicalrecords;

public class Prescription {
    private String medicationName; // Name of the medication
    private String dosage; // Dosage information (e.g., "500mg twice a day")
    private String frequency; // Frequency of administration (e.g., "2 times per day")
    private String status; // Status of the prescription (e.g., pending, prescribed, filled)
    private int amount; // Number of units prescribed (e.g., 30 tablets)
    private String instructions; // Special instructions (e.g., "Take after meals")

    // Constructor
    public Prescription(String medicationName, String dosage, String frequency, String status, int amount,
            String instructions) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.status = status;
        this.amount = amount;
        this.instructions = instructions;
    }

    // Getters and Setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "Prescription{" +
                "medicationName='" + medicationName + '\'' +
                ", dosage='" + dosage + '\'' +
                ", frequency='" + frequency + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
