/**
 * This package contains classes related to inventory management.
 */
package inventory;

/**
 * The Medicine class represents a medical item with an ID, name, dosage, stock level,
 * and a low stock alert threshold.
 */
public class Medicine {
    private String id;
    private String name;
    private String dosage;
    private int stockLevel;
    private int lowStockLevelAlert; // Field to track the low stock alert level

    /**
     * Constructs a Medicine object with the given details.
     *
     * @param id                the unique identifier for the medicine
     * @param name              the name of the medicine
     * @param dosage            the dosage information of the medicine
     * @param stockLevel        the current stock level of the medicine
     * @param lowStockLevelAlert the threshold level to trigger a low stock alert
     */
    public Medicine(String id, String name, String dosage, int stockLevel, int lowStockLevelAlert) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.stockLevel = stockLevel;
        this.lowStockLevelAlert = lowStockLevelAlert;
    }

    /**
     * Factory method to create a Medicine object from a CSV line.
     *
     * @param csvString a string containing the details of a medicine, separated by commas
     * @return a new Medicine object created from the CSV line
     * @throws IllegalArgumentException if the CSV format is invalid
     */
    public static Medicine fromCSV(String csvString) {
        String[] parts = csvString.split(",");
        if (parts.length == 5) { // Ensure all fields are available
            String id = parts[0].trim();
            String name = parts[1].trim();
            String dosage = parts[2].trim();
            int stockLevel = Integer.parseInt(parts[3].trim());
            int lowStockLevelAlert = Integer.parseInt(parts[4].trim());

            return new Medicine(id, name, dosage, stockLevel, lowStockLevelAlert);
        }
        throw new IllegalArgumentException("Invalid CSV format for Medicine: " + csvString);
    }

    // Getters and setters

    /**
     * Gets the ID of the medicine.
     *
     * @return the ID of the medicine
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the medicine.
     *
     * @return the name of the medicine
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the dosage of the medicine.
     *
     * @return the dosage of the medicine
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * Gets the current stock level of the medicine.
     *
     * @return the current stock level
     */
    public int getStockLevel() {
        return stockLevel;
    }

    /**
     * Sets the stock level of the medicine.
     *
     * @param stockLevel the new stock level to set
     */
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    /**
     * Gets the low stock alert level of the medicine.
     *
     * @return the low stock alert level
     */
    public int getLowStockLevelAlert() {
        return lowStockLevelAlert;
    }

    /**
     * Sets the low stock alert level of the medicine.
     *
     * @param lowStockLevelAlert the new low stock alert level to set
     */
    public void setLowStockLevelAlert(int lowStockLevelAlert) {
        this.lowStockLevelAlert = lowStockLevelAlert;
    }

    /**
     * Checks if the stock level is below the low stock alert threshold.
     *
     * @return true if the stock level is at or below the alert level, false otherwise
     */
    public boolean isStockLow() {
        return stockLevel <= lowStockLevelAlert;
    }

    /**
     * Returns a string representation of the medicine.
     *
     * @return a string containing the medicine's details
     */
    @Override
    public String toString() {
        return "Medicine ID: " + id + ", Name: " + name + ", Dosage: " + dosage +
               ", Stock Level: " + stockLevel + ", Low Stock Alert Level: " + lowStockLevelAlert;
    }
}
