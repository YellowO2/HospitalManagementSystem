package users;

public class Pharmacist extends User {
    public Pharmacist(String id, String name, String dateOfBirth, String gender, String phoneNumber, String emailAddress, String password) {
        super(id, name, "Pharmacist", password, phoneNumber, emailAddress, dateOfBirth, gender);
    }
}