package inventory;

public class Medicine {
    private String id;
    private String name;
    private String dosage;
    private int stockLevel;
    private int lowStockLevelAlert; // New field to track the low stock alert level

    public Medicine(String id, String name, String dosage, int stockLevel, int lowStockLevelAlert) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.stockLevel = stockLevel;
        this.lowStockLevelAlert = lowStockLevelAlert;
    }

    // Factory method to create a Medicine object from a CSV line
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
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public int getLowStockLevelAlert() {
        return lowStockLevelAlert;
    }

    public void setLowStockLevelAlert(int lowStockLevelAlert) {
        this.lowStockLevelAlert = lowStockLevelAlert;
    }

    // Check if stock level is below the alert threshold
    public boolean isStockLow() {
        return stockLevel <= lowStockLevelAlert;
    }

    @Override
    public String toString() {
        return "Medicine ID: " + id + ", Name: " + name + ", Dosage: " + dosage + 
               ", Stock Level: " + stockLevel + ", Low Stock Alert Level: " + lowStockLevelAlert;
    }
}