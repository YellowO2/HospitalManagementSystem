package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import users.Administrator;
import users.Doctor;
import users.Patient;
import users.Pharmacist;
import users.User;

/**
 * Manages a collection of users, providing methods for creating, retrieving,
 * updating, deleting, and saving user data to a CSV file.
 */
public class UserDB extends Database<User> {

    private List<User> users;
    private static final String USER_FILE = "csv_data/User_List.csv";
    private static final String USER_HEADER = "ID,Name,Date of Birth,Gender,Phone Number,Email Address,Password,Role";

    /**
     * Constructs a new UserDB instance and initializes the list of users.
     */
    public UserDB() {
        super(USER_FILE);
        users = new ArrayList<>();
    }

    /**
     * Creates a new user entry and saves it to the CSV file.
     *
     * @param user The user to be added.
     * @return true if the user is successfully added, false otherwise.
     */
    @Override
    public boolean create(User user) {
        if (user == null || exists(user.getId())) {
            System.out.println("Invalid user data or user with ID " + user.getId() + " already exists.");
            return false; // Prevent adding duplicates or null objects
        }
        users.add(user);
        try {
            save(); // Automatically save after addition
            return true;
        } catch (IOException e) {
            System.err.println("Error saving data after adding user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a user with the given ID already exists in the database.
     *
     * @param id The ID of the user to check.
     * @return true if the user exists, false otherwise.
     */
    public boolean exists(String id) {
        return users.stream().anyMatch(user -> user.getId().equals(id));
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user if found, null otherwise.
     */
    @Override
    public User getById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null); // Return null if no match
    }

    /**
     * Retrieves all users in the database.
     *
     * @return A list of all users.
     */
    @Override
    public List<User> getAll() {
        return new ArrayList<>(users); // Return a copy for safety
    }

    /**
     * Updates an existing user's details in the database.
     *
     * @param updatedUser The user with updated details.
     * @return true if the update is successful, false otherwise.
     */
    @Override
    public boolean update(User updatedUser) {
        if (updatedUser == null)
            return false; // Prevent null input
        User existingUser = getById(updatedUser.getId());
        if (existingUser != null) {
            users.remove(existingUser);
            users.add(updatedUser);
            try {
                save(); // Automatically save after update
                return true;
            } catch (IOException e) {
                System.err.println("Error saving data after updating user: " + e.getMessage());
                return false;
            }
        }
        return false; // User not found
    }

    /**
     * Deletes a user by their ID from the database.
     *
     * @param id The ID of the user to delete.
     * @return true if the user is successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(String id) {
        User user = getById(id);
        if (user != null) {
            users.remove(user);
            try {
                save(); // Automatically save after deletion
                return true;
            } catch (IOException e) {
                System.err.println("Error saving data after deleting user: " + e.getMessage());
                return false;
            }
        }
        return false; // User not found
    }

    /**
     * Saves all the users to the CSV file.
     *
     * @return true if the data is successfully saved, false otherwise.
     * @throws IOException If an error occurs while saving data.
     */
    @Override
    public boolean save() throws IOException {
        saveData(USER_FILE, users, USER_HEADER);
        return true;
    }

    /**
     * Loads users from the CSV file into the database.
     *
     * @return true if the data is successfully loaded, false otherwise.
     * @throws IOException If an error occurs while reading the file.
     */
    @Override
    public boolean load() throws IOException {
        List<String> lines = readFile(USER_FILE);

        for (String line : lines) {
            String[] tokens = splitLine(line);

            if (tokens.length == 8) { // Ensure all necessary fields are present
                try {
                    String id = tokens[0].trim();
                    String name = tokens[1].trim();
                    String dob = tokens[2].trim();
                    String gender = tokens[3].trim();
                    String phoneNumber = tokens[4].trim();
                    String emailAddress = tokens[5].trim();
                    String password = tokens[6].trim();
                    String role = tokens[7].trim();

                    // Create specific User objects based on the role
                    User user = createUserByRole(id, name, dob, gender, phoneNumber, emailAddress, password, role);
                    if (user != null) {
                        users.add(user);
                    } else {
                        System.err.println("Invalid role: " + role + " for user ID: " + id);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line + " - " + e.getMessage());
                }
            } else {
                System.err.println("Invalid line format in " + USER_FILE + ": " + line);
            }
        }
        return true;
    }

    /**
     * Creates a user object based on the role specified.
     *
     * @param id           The user's ID.
     * @param name         The user's name.
     * @param dob          The user's date of birth.
     * @param gender       The user's gender.
     * @param phoneNumber  The user's phone number.
     * @param emailAddress The user's email address.
     * @param password     The user's password.
     * @param role         The user's role.
     * @return A User object, or null if the role is invalid.
     */
    private User createUserByRole(String id, String name, String dob, String gender, String phoneNumber,
            String emailAddress, String password, String role) {
        switch (role) {
            case "Patient":
                return new Patient(id, name, dob, gender, phoneNumber, emailAddress, password);
            case "Doctor":
                return new Doctor(id, name, dob, gender, phoneNumber, emailAddress, password);
            case "Pharmacist":
                return new Pharmacist(id, name, dob, gender, phoneNumber, emailAddress, password);
            case "Administrator":
                return new Administrator(id, name, dob, gender, phoneNumber, emailAddress, password);
            default:
                return null; // Invalid role
        }
    }

    /**
     * Retrieves all doctors from the database.
     *
     * @return A list of all doctors.
     */
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Doctor) {
                doctors.add((Doctor) user);
            }
        }
        return doctors;
    }

    /**
     * Retrieves all patients from the database.
     *
     * @return A list of all patients.
     */
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Patient) {
                patients.add((Patient) user);
            }
        }
        return patients;
    }
}
