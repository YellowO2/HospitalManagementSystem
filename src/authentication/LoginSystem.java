package authentication;

import users.User;
import users.Patient; // Add other user types as needed
// import users.Doctor; // If you have these user types

import java.util.HashMap;
import java.util.Map;

public class LoginSystem {
    private Map<String, User> users = new HashMap<>();

    public LoginSystem() {
        // Sample users for testing
        // TODO: id should eventually be unique
        String yx_id = "P69";
        users.put(yx_id, new Patient(yx_id, "Patient YX", "yxpass"));
    }

    public User login(String userId, String password) {
        User user = users.get(userId);
        if (user != null && user.validatePassword(password)) {
            System.out.println("Login successful for user: " + userId);
            return user;
        }
        System.out.println("Login failed for user: " + userId);
        return null;
    }
}
