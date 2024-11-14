package database;

import inventory.Medicine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDB extends Database<Medicine> {
    private List<Medicine> medicines;
    private static final String MEDICINE_FILE = "csv_data/Inventory_List.csv";
    private static final String MEDICINE_HEADER = "ID,Name,Dosage,StockLevel,LowStockLevelAlert";

    public MedicineDB() {
        super(MEDICINE_FILE);
        medicines = new ArrayList<>();
    }

    @Override
    public boolean create(Medicine medicine) {
        if (medicine != null) {
            medicines.add(medicine);
            try {
                save();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public Medicine getById(String id) {
        for (Medicine medicine : medicines) {
            if (medicine.getId().equals(id)) {
                return medicine;
            }
        }
        return null;
    }

    @Override
    public boolean update(Medicine updatedMedicine) {
        Medicine existingMedicine = getById(updatedMedicine.getId());
        if (existingMedicine != null) {
            medicines.remove(existingMedicine);
            medicines.add(updatedMedicine);
            try {
                save();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        Medicine medicine = getById(id);
        if (medicine != null) {
            medicines.remove(medicine);
            try {
                save();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean save() throws IOException {
        saveData(MEDICINE_FILE, medicines, MEDICINE_HEADER);
        return true;
    }

    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(MEDICINE_FILE);
        for (String line : lines) {
            String[] tokens = splitLine(line);
            if (tokens.length == 5) {
                String id = tokens[0].split(": ")[1].trim(); // Extract and trim the value after "ID:"
                String name = tokens[1].split(": ")[1].trim(); // Extract and trim the value after "Name:"
                String dosage = tokens[2].split(": ")[1].trim(); // Extract and trim the value after "Dosage:"
                int stockLevel = Integer.parseInt(tokens[3].split(": ")[1].trim()); // Parse and trim the value after "Stock Level:"
                int lowStockLevelAlert = Integer.parseInt(tokens[4].split(": ")[1].trim()); // Parse and trim the value after "Low Stock Alert Level:"

                Medicine medicine = new Medicine(id, name, dosage, stockLevel, lowStockLevelAlert);
                medicines.add(medicine);
            } else {
                System.out.println("Invalid line in CSV: " + line);
            }
        }
        return true;
    }

    @Override
    public List<Medicine> getAll() {
        return medicines;
    }
}