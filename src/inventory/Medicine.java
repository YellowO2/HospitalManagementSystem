package inventory;

public class Medicine {
    private String id;
    private String name;
    private String dosage;
    private int stockLevel;

    public Medicine(String id, String name, String dosage, int stockLevel) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.stockLevel = stockLevel;
    }

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

    @Override
    public String toString() {
        return "Medicine ID: " + id + ", Name: " + name + ", Dosage: " + dosage + ", Stock Level: " + stockLevel;
    }
}