package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import medicine.ReplenishmentRequest;

/**
 * A class that manages replenishment requests in the database.
 * This class provides methods for creating, updating, deleting, retrieving,
 * saving, and loading replenishment requests from a CSV file.
 * 
 * @see ReplenishmentRequest
 */
public class ReplenishmentDB extends Database<ReplenishmentRequest> {
    private List<ReplenishmentRequest> replenishmentRequests; // List to store replenishment requests
    private static final String filename = "csv_data/Replenishment.csv"; // File path for saving/loading data
    private static final String header = "MedicineID,Quantity"; // CSV header

    /**
     * Constructor for initializing the ReplenishmentDB with the specified CSV file
     * path.
     */
    public ReplenishmentDB() {
        super(filename); // Pass the filename to the parent class
        this.replenishmentRequests = new ArrayList<>();
    }

    /**
     * Creates a new replenishment request and adds it to the database.
     * Automatically saves the data after creation.
     *
     * @param request the ReplenishmentRequest object to be added
     * @return true if the replenishment request was successfully created and saved,
     *         false otherwise
     */
    @Override
    public boolean create(ReplenishmentRequest request) {
        if (request != null) {
            replenishmentRequests.add(request);
            try {
                save(); // Automatically save after creation
            } catch (IOException e) {
                System.err.println("Error saving data after creating replenishment request: " + e.getMessage());
            }
            return true;
        }
        return false; // Invalid request
    }

    /**
     * Retrieves a replenishment request by its medicine ID.
     *
     * @param medicineId the ID of the medicine
     * @return the ReplenishmentRequest object if found, or null if not found
     */
    @Override
    public ReplenishmentRequest getById(String medicineId) {
        for (ReplenishmentRequest request : replenishmentRequests) {
            if (request.getMedicineId().equals(medicineId)) {
                return request; // Return the matching request
            }
        }
        return null; // Request not found
    }

    /**
     * Retrieves all replenishment requests in the database.
     * 
     * @return a list of all ReplenishmentRequest objects
     */
    @Override
    public List<ReplenishmentRequest> getAll() {
        return new ArrayList<>(replenishmentRequests); // Return a copy for safety
    }

    /**
     * Updates an existing replenishment request in the database.
     * Automatically saves the data after update.
     *
     * @param updatedRequest the updated ReplenishmentRequest object
     * @return true if the replenishment request was successfully updated and saved,
     *         false otherwise
     */
    @Override
    public boolean update(ReplenishmentRequest updatedRequest) {
        ReplenishmentRequest existingRequest = getById(updatedRequest.getMedicineId());
        if (existingRequest != null) {
            replenishmentRequests.remove(existingRequest);
            replenishmentRequests.add(updatedRequest);
            try {
                save(); // Automatically save after update
            } catch (IOException e) {
                System.err.println("Error saving data after updating replenishment request: " + e.getMessage());
            }
            return true;
        }
        return false; // Request not found
    }

    /**
     * Deletes a replenishment request by its medicine ID.
     * Automatically saves the data after deletion.
     *
     * @param medicineId the ID of the medicine whose replenishment request will be
     *                   deleted
     * @return true if the replenishment request was successfully deleted and saved,
     *         false otherwise
     */
    @Override
    public boolean delete(String medicineId) {
        ReplenishmentRequest existingRequest = getById(medicineId);
        if (existingRequest != null) {
            replenishmentRequests.remove(existingRequest);
            try {
                save(); // Automatically save after deletion
            } catch (IOException e) {
                System.err.println("Error saving data after deleting replenishment request: " + e.getMessage());
            }
            return true;
        }
        return false; // Request not found
    }

    /**
     * Saves the current list of replenishment requests to the CSV file.
     *
     * @return true if the data was successfully saved
     * @throws IOException if an I/O error occurs during saving
     */
    @Override
    public boolean save() throws IOException {
        saveData(filename, replenishmentRequests, header);
        return true;
    }

    /**
     * Loads replenishment requests from the CSV file into the database.
     *
     * @return true if the data was successfully loaded
     * @throws IOException if an I/O error occurs during loading
     */
    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(filename); // Read the CSV file
        for (String line : lines) {
            String[] tokens = splitLine(line); // Split line into tokens

            if (tokens.length == 2) { // Ensure the line has the correct number of tokens
                try {
                    String medicineId = tokens[0].trim();
                    int quantity = Integer.parseInt(tokens[1].trim());

                    // Create a new ReplenishmentRequest object and set its values
                    ReplenishmentRequest request = new ReplenishmentRequest(this);
                    request.setMedicineId(medicineId);
                    request.setQuantity(quantity);

                    replenishmentRequests.add(request); // Add the new request to the list
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in line: " + line);
                }
            } else {
                System.err.println("Invalid line in " + filename + ": " + line); // Invalid line
            }
        }
        return true;
    }
}
