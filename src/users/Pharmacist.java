package users;

/**
 * The Pharmacist class extends the User class and represents a pharmacist user in the system.
 * Pharmacists have specific roles related to handling prescriptions and managing medication inventory.
 */
public class Pharmacist extends User {

    /**
     * Constructs a Pharmacist object with the specified details.
     *
     * @param id           the unique identifier for the pharmacist
     * @param name         the name of the pharmacist
     * @param dateOfBirth  the date of birth of the pharmacist
     * @param gender       the gender of the pharmacist
     * @param phoneNumber  the phone number of the pharmacist
     * @param emailAddress the email address of the pharmacist
     * @param password     the password for the pharmacist's account
     */
    public Pharmacist(String id, String name, String dateOfBirth, String gender, String phoneNumber, String emailAddress, String password) {
        super(id, name, "Pharmacist", password, phoneNumber, emailAddress, dateOfBirth, gender);
    }
}
