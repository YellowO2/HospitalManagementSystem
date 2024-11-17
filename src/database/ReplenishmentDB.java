package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import medicine.ReplenishmentRequest;

public class ReplenishmentDB extends Database<ReplenishmentRequest> {
    private List<ReplenishmentRequest> replenishmentRequests;
    private static final String filename = "csv_data/Replenishment.csv";
    private static final String header = "MedicineID,Quantity";

    // Constructor
    public ReplenishmentDB() {
        super(filename);
        this.replenishmentRequests = new ArrayList<>();
    }

    // Create a new replenishment request
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

    // Get a replenishment request by its medicine ID
    @Override
    public ReplenishmentRequest getById(String medicineId) {
        for (ReplenishmentRequest request : replenishmentRequests) {
            if (request.getMedicineId().equals(medicineId)) {
                return request; // Return the matching request
            }
        }
        return null; // Request not found
    }

    // Get all replenishment requests
    @Override
    public List<ReplenishmentRequest> getAll() {
        return new ArrayList<>(replenishmentRequests); // Return a copy for safety
    }

    // Update an existing replenishment request
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

    // Delete a replenishment request by its medicine ID
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

    // Save the current list of replenishment requests to the CSV file
    @Override
    public boolean save() throws IOException {
        saveData(filename, replenishmentRequests, header);
        return true;
    }

    // Load replenishment requests from the CSV file
    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(filename);
        for (String line : lines) {
            String[] tokens = splitLine(line);

            if (tokens.length == 2) { // Ensure the line has the correct number of tokens
                try {
                    String medicineId = tokens[0].trim();
                    int quantity = Integer.parseInt(tokens[1].trim());

                    // Create a new ReplenishmentRequest object and set its values
                    ReplenishmentRequest request = new ReplenishmentRequest(this);
                    request.setMedicineId(medicineId);
                    request.setQuantity(quantity);

                    replenishmentRequests.add(request);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in line: " + line);
                }
            } else {
                System.err.println("Invalid line in " + filename + ": " + line);
            }
        }
        return true;
    }
}
