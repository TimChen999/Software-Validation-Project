import java.util.*;

public class UserManager {
    private Map<String, String> users;

    public UserManager() {
        users = new HashMap<>();
    }

    public void addUser(String username, String password) {
        if (!users.containsKey(username)) {
            users.put(username, password);
        }
    }

    public boolean authenticate(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword.equals(password);
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public void printAllUsers() {
        for (String username : users.keySet()) {
            System.out.println("User: " + username);
        }
    }

    public static void main(String[] args) {
        UserManager um = new UserManager();
        um.addUser("alice", "1234");
        um.addUser("bob", "pass");
        um.addUser("carol", "qwerty");

        if (um.authenticate("dave", "1234")) {
            System.out.println("Authentication succeeded");
        } else {
            System.out.println("Authentication failed");
        }

        um.removeUser("bob");
        um.printAllUsers();
    }
}