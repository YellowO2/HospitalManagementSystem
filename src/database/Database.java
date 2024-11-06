/*
 * This is a singleton eagerloading database class that only saves to the csv before the program is closed.
 */

package database;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Database<T> {
    protected String filename;
    private static final String SEPARATOR = ",";

    // Constructor to initialize the file name
    public Database(String filename) {
        this.filename = filename;
    }

    // Abstract CRUD methods
    public abstract boolean create(T entity);

    public abstract T getById(String id);

    public abstract boolean update(T entity);

    public abstract boolean delete(String id);

    // Abstract methods for loading and saving data
    public abstract boolean save() throws IOException;

    public abstract boolean load() throws IOException;

    public void saveData(String filename, List<?> dataList, String header) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        try {
            out.println(header);
            for (Object obj : dataList) {
                out.println(obj.toString()); // Ensure that User, MedicalRecord and other data classes have proper
                                             // toString methods
            }
        } finally {
            out.close();
        }
    }

    public List<String> readFile(String filename) throws IOException {

        List<String> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(filename))) {

            // Skip the first line (header)
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Display a warning if the CSV file contains only the header row, implying
            // empty
            if (!scanner.hasNextLine()) {
                System.out.println("Warning: The CSV file is empty or contains only the header row.");
            }

            // Read the remaining lines
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        }
        return data;
    }

    public String[] splitLine(String line) {
        return line.split(SEPARATOR);
    }
}
