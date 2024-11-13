package database;

import inventory.Medicine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDB extends Database<Medicine> {
    private List<Medicine> inventoryRecords;
    private static final String filename = "csv_data/Inventory_List.csv";
    private static final String header = "ID,Name,Dosage,StockLevel,LowStockLevelAlert";

    public MedicineDB() {
        super(filename);
        this.inventoryRecords = new ArrayList<>();
    }

    @Override
    public boolean create(Medicine medicine) {
        if (medicine != null) {
            inventoryRecords.add(medicine);
            return true;
        }
        return false;
    }

    @Override
    public Medicine getById(String id) {
        for (Medicine medicine : inventoryRecords) {
            if (medicine.getId().equalsIgnoreCase(id)) {
                return medicine;
            }
        }
        return null;
    }

    @Override
    public boolean update(Medicine updatedMedicine) {
        Medicine existingMedicine = getById(updatedMedicine.getId());
        if (existingMedicine != null) {
            inventoryRecords.remove(existingMedicine);
            inventoryRecords.add(updatedMedicine);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        Medicine existingMedicine = getById(id);
        if (existingMedicine != null) {
            inventoryRecords.remove(existingMedicine);
            return true;
        }
        return false;
    }

    @Override
    public boolean save() throws IOException {
        saveData(filename, inventoryRecords, header);
        return true;
    }

    @Override
    public boolean load() throws IOException {
        inventoryRecords.clear();
        List<String> lines = readFile(filename);
        for (String line : lines) {
            try {
                Medicine medicine = Medicine.fromCSV(line);
                inventoryRecords.add(medicine);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid line in CSV: " + line + " - " + e.getMessage());
            }
        }
        return true;
    }

    // Method to check for low stock alerts
    public List<Medicine> getLowStockAlerts() {
        List<Medicine> lowStockAlerts = new ArrayList<>();
        for (Medicine medicine : inventoryRecords) {
            if (medicine.isStockLow()) {
                lowStockAlerts.add(medicine);
            }
        }
        return lowStockAlerts;
    }

    @Override
    public List<Medicine> getAll() {
        return inventoryRecords;
    }
}