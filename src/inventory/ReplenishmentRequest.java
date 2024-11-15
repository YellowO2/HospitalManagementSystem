package inventory;

public class ReplenishmentRequest {
    private String medicineId;
    private int quantity;

    public ReplenishmentRequest(String medicineId, int quantity) {
        this.medicineId = medicineId;
        this.quantity = quantity;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return medicineId + "," + quantity;
    }
    // Setter method for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}