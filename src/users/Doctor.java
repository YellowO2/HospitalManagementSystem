package users;

/**
 * Represents a Doctor user in the hospital system, extending the User class.
 * The Doctor class includes properties for managing appointments and patient
 * medical records.
 */
public class Doctor extends User {

    /**
     * Constructs a new Doctor with the specified details.
     * 
     * @param id           the unique identifier for the doctor
     * @param name         the full name of the doctor
     * @param dateOfBirth  the date of birth of the doctor (yyyy-MM-dd format)
     * @param gender       the gender of the doctor
     * @param phoneNumber  the contact phone number of the doctor
     * @param emailAddress the email address of the doctor
     * @param password     the password for the doctor
     */
    public Doctor(String id, String name, String dateOfBirth, String gender, String phoneNumber,
            String emailAddress, String password) {
        super(id, name, "Doctor", password, phoneNumber, emailAddress, dateOfBirth, gender);
    }
}
