package database;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An abstract generic class that defines the structure for a database.
 * This class provides basic CRUD operations and methods for saving and loading
 * data from a CSV file.
 * It uses a singleton eager-loading approach, ensuring data is saved only when
 * the program is closed.
 * 
 * @param <T> the type of entities managed by this database class
 */
public abstract class Database<T> {
    /**
     * The name of the file where data is stored.
     */
    protected String filename;

    /**
     * The separator used in the CSV file.
     */
    private static final String SEPARATOR = ",";

    /**
     * Constructs a Database object with the specified file name.
     *
     * @param filename the name of the CSV file where data will be stored
     */
    public Database(String filename) {
        this.filename = filename;
    }

    // Abstract CRUD methods

    /**
     * Creates a new entity in the database.
     *
     * @param entity the entity to be created
     * @return true if the entity was successfully created, false otherwise
     */
    public abstract boolean create(T entity);

    /**
     * Retrieves an entity by its unique ID.
     *
     * @param id the unique identifier of the entity
     * @return the entity with the specified ID, or null if not found
     */
    public abstract T getById(String id);

    /**
     * Retrieves all entities in the database.
     *
     * @return a list of all entities in the database
     */
    public abstract List<T> getAll();

    /**
     * Updates an existing entity in the database.
     *
     * @param entity the entity with updated data
     * @return true if the entity was successfully updated, false otherwise
     */
    public abstract boolean update(T entity);

    /**
     * Deletes an entity by its unique ID.
     *
     * @param id the unique identifier of the entity to be deleted
     * @return true if the entity was successfully deleted, false otherwise
     */
    public abstract boolean delete(String id);

    // Abstract methods for loading and saving data

    /**
     * Saves the current data in the database to the specified file.
     *
     * @return true if the data was successfully saved
     * @throws IOException if an I/O error occurs during saving
     */
    public abstract boolean save() throws IOException;

    /**
     * Loads the data from the specified file into the database.
     *
     * @return true if the data was successfully loaded
     * @throws IOException if an I/O error occurs during loading
     */
    public abstract boolean load() throws IOException;

    /**
     * Saves a list of data objects to a CSV file.
     *
     * @param filename the name of the CSV file to save data to
     * @param dataList the list of data objects to be saved
     * @param header   the header to be written at the top of the CSV file
     * @throws IOException if an I/O error occurs while saving data
     */
    public void saveData(String filename, List<?> dataList, String header) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        try {
            out.println(header); // Write the header
            for (Object obj : dataList) {
                out.println(obj.toString()); // Write each object to the file, assuming proper toString method
                                             // implementation
            }
        } finally {
            out.close();
        }
    }

    /**
     * Reads all lines from a CSV file, excluding the header, and returns them as a
     * list of strings.
     *
     * @param filename the name of the CSV file to read
     * @return a list of strings, each representing a line in the CSV file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<String> readFile(String filename) throws IOException {

        List<String> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(filename))) {

            // Skip the first line (header)
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Display a warning if the CSV file contains only the header row, implying it's
            // empty
            if (!scanner.hasNextLine()) {
                System.out.println("Warning: The CSV file " + filename + " is empty or contains only the header row.");
            }

            // Read the remaining lines
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        }
        return data;
    }

    /**
     * Splits a line of text into an array of strings using the specified separator
     * (comma).
     *
     * @param line the line of text to be split
     * @return an array of strings resulting from the split operation
     */
    public String[] splitLine(String line) {
        return line.split(SEPARATOR);
    }
}
