package database;

import inventory.ReplenishmentRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReplenishmentDB extends Database<ReplenishmentRequest> {
    private List<ReplenishmentRequest> replenishmentRequests;
    private static final String filename = "csv_data/Replenishment.csv";
    private static final String header = "MedicineID,Quantity";

    public ReplenishmentDB() {
        super(filename);
        this.replenishmentRequests = new ArrayList<>();
    }

    // Implement the CRUD operations for ReplenishmentRequest
    @Override
    public boolean create(ReplenishmentRequest request) {
        if (request != null) {
            replenishmentRequests.add(request);
            return true;
        }
        return false; // Invalid request
    }

    @Override
    public ReplenishmentRequest getById(String medicineId) {
        for (ReplenishmentRequest request : replenishmentRequests) {
            if (request.getMedicineId().equals(medicineId)) {
                return request; // Return the matching request
            }
        }
        return null; // Return null if not found
    }

    @Override
    public boolean update(ReplenishmentRequest updatedRequest) {
        ReplenishmentRequest existingRequest = getById(updatedRequest.getMedicineId());
        if (existingRequest != null) {
            replenishmentRequests.remove(existingRequest);
            replenishmentRequests.add(updatedRequest);
            return true;
        }
        return false; // Request not found
    }

    @Override
    public boolean delete(String medicineId) {
        ReplenishmentRequest existingRequest = getById(medicineId);
        if (existingRequest != null) {
            replenishmentRequests.remove(existingRequest);
            return true;
        }
        return false; // Request not found
    }

    @Override
    public boolean save() throws IOException {
        saveData(filename, replenishmentRequests, header);
        return true;
    }

    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(filename);
        for (String line : lines) {
            String[] tokens = splitLine(line);

            if (tokens.length == 2) {
                ReplenishmentRequest request = new ReplenishmentRequest(
                        tokens[0].trim(), // medicineId
                        Integer.parseInt(tokens[1].trim()) // quantity
                );
                replenishmentRequests.add(request);
            } else {
                System.out.println("Invalid line in CSV: " + line);
            }
        }
        return true;
    }

    @Override
    public List<ReplenishmentRequest> getAll() {
        return replenishmentRequests;
    }
}
