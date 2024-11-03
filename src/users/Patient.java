/* 
 * The Patient class represents a patient who has access to a medical record and can schedule appointments.
 * It should contain methods that manage and manipulate patient data, 
 * but not handle interaction with the user as that is done by the menu.
 */
package users;

public class Patient extends User {

    private String bloodType;

    // ID,Name,Date of Birth,Gender,Phone Number,Email Address,Password,Role
    public Patient(String id, String name, String dateOfBirth, String gender, String phoneNumber,
            String emailAddress, String password) {
        super(id, name, "Patient", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.bloodType = bloodType;
    }

}
