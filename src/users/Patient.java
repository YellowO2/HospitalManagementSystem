/* 
 * The Patient class represents a patient who has access to a medical record and can schedule appointments.
 * It should contain methods that manage and manipulate patient data, 
 * but not handle interaction with the user as that is done by the menu.
 */
package users;

public class Patient extends User {

    /**
     * Constructs a Patient object with the specified details.
     *
     * @param id           the unique hospital ID for the patient
     * @param name         the full name of the patient
     * @param dateOfBirth  the date of birth of the patient in the format yyyy-MM-dd
     * @param gender       the gender of the patient
     * @param phoneNumber  the contact number of the patient
     * @param emailAddress the email address of the patient
     * @param password     the password for the patient's account
     */
    public Patient(String id, String name, String dateOfBirth, String gender, String phoneNumber,
                   String emailAddress, String password) {
        super(id, name, "Patient", password, phoneNumber, emailAddress, dateOfBirth, gender);
    }
}