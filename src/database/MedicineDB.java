package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import medicine.Medicine;

/**
 * A class that manages medicines in the database.
 * This class allows creating, updating, deleting, retrieving, saving, and
 * loading medicine records from a CSV file.
 * 
 * @see Medicine
 */
public class MedicineDB extends Database<Medicine> {
    private List<Medicine> medicines; // List to store medicines
    private static final String MEDICINE_FILE = "csv_data/Inventory_List.csv"; // File path for saving/loading data
    private static final String MEDICINE_HEADER = "ID,Name,Dosage,StockLevel,LowStockLevelAlert"; // CSV header

    /**
     * Constructor for initializing the MedicineDB with the specified CSV file path.
     */
    public MedicineDB() {
        super(MEDICINE_FILE); // Pass the filename to the parent class
        medicines = new ArrayList<>();
    }

    /**
     * Creates a new medicine record and adds it to the database.
     * Automatically saves the data after creation.
     *
     * @param medicine the Medicine object to be added
     * @return true if the medicine was successfully created and saved, false
     *         otherwise
     */
    @Override
    public boolean create(Medicine medicine) {
        if (medicine != null) {
            medicines.add(medicine);
            try {
                save(); // Automatically save after creation
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false; // Invalid medicine
    }

    /**
     * Retrieves a medicine record by its ID.
     *
     * @param id the ID of the medicine
     * @return the Medicine object if found, or null if not found
     */
    @Override
    public Medicine getById(String id) {
        for (Medicine medicine : medicines) {
            if (medicine.getId().equals(id)) {
                return medicine;
            }
        }
        return null; // Return null if not found
    }

    /**
     * Updates an existing medicine record in the database.
     * Automatically saves the data after updating.
     *
     * @param updatedMedicine the updated Medicine object
     * @return true if the medicine was successfully updated and saved, false
     *         otherwise
     */
    @Override
    public boolean update(Medicine updatedMedicine) {
        Medicine existingMedicine = getById(updatedMedicine.getId());
        if (existingMedicine != null) {
            medicines.remove(existingMedicine);
            medicines.add(updatedMedicine);
            try {
                save(); // Automatically save after updating
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false; // Medicine not found
    }

    /**
     * Deletes a medicine record by its ID.
     * Automatically saves the data after deletion.
     *
     * @param id the ID of the medicine to be deleted
     * @return true if the medicine was successfully deleted and saved, false
     *         otherwise
     */
    @Override
    public boolean delete(String id) {
        Medicine medicine = getById(id);
        if (medicine != null) {
            medicines.remove(medicine);
            try {
                save(); // Automatically save after deletion
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false; // Medicine not found
    }

    /**
     * Saves all medicine records to a CSV file.
     *
     * @return true if the data was successfully saved
     * @throws IOException if an I/O error occurs during saving
     */
    @Override
    public boolean save() throws IOException {
        saveData(MEDICINE_FILE, medicines, MEDICINE_HEADER);
        return true;
    }

    /**
     * Loads medicine records from a CSV file into the database.
     *
     * @return true if the data was successfully loaded
     * @throws IOException if an I/O error occurs during loading
     */
    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(MEDICINE_FILE); // Read the CSV file
        for (String line : lines) {
            String[] tokens = splitLine(line); // Split line into tokens
            if (tokens.length == 5) { // Ensure there are 5 tokens
                String id = tokens[0].split(": ")[1].trim(); // Extract and trim the value after "ID:"
                String name = tokens[1].split(": ")[1].trim(); // Extract and trim the value after "Name:"
                String dosage = tokens[2].split(": ")[1].trim(); // Extract and trim the value after "Dosage:"
                int stockLevel = Integer.parseInt(tokens[3].split(": ")[1].trim()); // Parse the value after "Stock
                                                                                    // Level:"
                int lowStockLevelAlert = Integer.parseInt(tokens[4].split(": ")[1].trim()); // Parse the value after
                                                                                            // "Low Stock Level:"

                Medicine medicine = new Medicine(id, name, dosage, stockLevel, lowStockLevelAlert);
                medicines.add(medicine); // Add the new medicine to the list
            } else {
                System.out.println("Invalid line in CSV: " + line); // Invalid line
            }
        }
        return true;
    }

    /**
     * Retrieves all medicine records in the database.
     *
     * @return a list of all Medicine objects
     */
    @Override
    public List<Medicine> getAll() {
        return medicines;
    }
}
