package authentication;

import java.util.HashMap;
import java.util.Map;

public class LoginSystem {
    private Map<String, String> users = new HashMap<>(); // Store userId -> password

    public LoginSystem() {
        // Sample users for testing
        users.put("patient1", "password123");
        users.put("doctor1", "password456");
    }

    public boolean login(String userId, String password) {
        if (users.containsKey(userId) && users.get(userId).equals(password)) {
            System.out.println("Login successful for user: " + userId);
            return true;
        }
        System.out.println("Login failed for user: " + userId);
        return false;
    }
}
