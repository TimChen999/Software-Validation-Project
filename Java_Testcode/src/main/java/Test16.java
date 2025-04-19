package main.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A user registration system.
 */
public class Test16 {
    private Map<String, User> users;
    private static final int MIN_PASSWORD_LENGTH = 8;

    public Test16() {
        this.users = new HashMap<>();
    }

    /**
     * User class to store user information
     */
    public static class User {
        private String username;
        private String password;
        private String email;
        private Date birthDate;
        private String phoneNumber;

        public User(String username, String password, String email, Date birthDate, String phoneNumber) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.birthDate = birthDate;
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String toString() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return "User{username='" + username + "', email='" + email +
                    "', birthDate=" + sdf.format(birthDate) +
                    ", phoneNumber='" + phoneNumber + "'}";
        }
    }

    /**
     * Registers a new user
     */
    public void registerUser(String username, String password, String email,
            String birthDateStr, String phoneNumber) {
        // Bug: Inadequate validation and error handling
        try {
            // Bug: No null checks for parameters

            // Username validation
            if (users.containsKey(username)) {
                System.out.println("Username already exists");
                return;
            }

            // Password validation
            if (password.length() < MIN_PASSWORD_LENGTH) {
                System.out.println("Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
                return;
            }

            // Bug: Weak email validation
            if (!email.contains("@")) {
                System.out.println("Invalid email format");
                return;
            }

            // Birth date validation
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // Bug: Not using lenient(false)
            Date birthDate = sdf.parse(birthDateStr);

            // Bug: No validation for future dates

            // Phone number validation
            // Bug: Inadequate phone validation
            if (phoneNumber.length() < 10) {
                System.out.println("Invalid phone number");
                return;
            }

            User user = new User(username, password, email, birthDate, phoneNumber);
            users.put(username, user);
            System.out.println("User registered successfully: " + user);

        } catch (ParseException e) {
            // Bug: Generic error message without details
            System.out.println("Error registering user");
            // Bug: Exception details swallowed, no logging
        }
        // Bug: No catch for other potential exceptions
    }

    /**
     * Validates a user login
     */
    public boolean login(String username, String password) {
        // Bug: No null checks

        User user = users.get(username);

        // Bug: Information leakage in error messages
        if (user == null) {
            System.out.println("Username does not exist");
            return false;
        }

        // Bug: Password comparison using == instead of equals
        if (user.password == password) {
            System.out.println("Login successful");
            return true;
        } else {
            System.out.println("Incorrect password");
            return false;
        }
    }

    /**
     * Updates user email
     */
    public void updateEmail(String username, String newEmail) {
        // Bug: No validation or error handling
        User user = users.get(username);
        user.email = newEmail; // Bug: Potential NullPointerException
        System.out.println("Email updated successfully");
    }

    /**
     * Updates user phone number
     */
    public void updatePhoneNumber(String username, String newPhoneNumber) {
        try {
            User user = users.get(username);

            // Bug: Missing null check on user

            // Bug: Inadequate validation
            if (newPhoneNumber.length() >= 10) {
                user.phoneNumber = newPhoneNumber;
                System.out.println("Phone number updated successfully");
            } else {
                System.out.println("Invalid phone number format");
            }
        } catch (Exception e) {
            // Bug: Generic catch block
            System.out.println("Error updating phone number");
        }
    }

    /**
     * Validates an email format
     */
    public boolean isValidEmail(String email) {
        // Bug: Overly simplistic email validation
        return email != null && email.contains("@") && email.contains(".");
    }

    /**
     * Validates a phone number format
     */
    public boolean isValidPhoneNumber(String phoneNumber) {
        // Bug: Incorrect regex pattern for phone validation
        String pattern = "\\d{10}"; // Bug: Only accepts exactly 10 digits
        return Pattern.matches(pattern, phoneNumber);
    }

    /**
     * Example usage
     */
    public static void main(String[] args) {
        Test16 system = new Test16();

        // Register users
        system.registerUser("johndoe", "password123", "john@example.com",
                "1990-05-15", "1234567890");

        // Bug: No validation for strong password
        system.registerUser("janedoe", "pass", "jane@example.com",
                "1992-08-21", "9876543210");

        // Bug: No validation for invalid date format
        system.registerUser("bobsmith", "password456", "bob@example.com",
                "19950731", "5551234567");

        // Try to login
        system.login("johndoe", "password123");

        // Bug: String comparison with == will fail
        system.login("johndoe", new String("password123"));

        // Update email
        system.updateEmail("johndoe", "john.doe@example.com");

        // Bug: Will cause NullPointerException
        system.updateEmail("nonexistent", "test@example.com");
    }
}