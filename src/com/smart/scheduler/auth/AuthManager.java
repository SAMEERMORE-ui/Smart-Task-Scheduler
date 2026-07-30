package com.smart.scheduler.auth;

import com.smart.scheduler.exception.AuthException;
import com.smart.scheduler.logger.CustomLogger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class AuthManager {
    private static AuthManager instance;
    private final UserRepository userRepository;
    private User currentUser;

    private AuthManager() {
        this.userRepository = new FileUserRepository("users.dat");
        seedDefaultAdmin();
    }

    public static synchronized AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    private void seedDefaultAdmin() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User(
                    UUID.randomUUID().toString(),
                    "admin",
                    hashPassword("admin123"),
                    "admin@smart.com",
                    Role.ADMIN
            );
            userRepository.save(admin);
        }
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return String.valueOf(password.hashCode());
        }
    }

    public synchronized User register(String username, String password, String email, Role role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new AuthException("Username already exists: " + username);
        }
        User newUser = new User(
                UUID.randomUUID().toString(),
                username,
                hashPassword(password),
                email,
                role
        );
        userRepository.save(newUser);
        CustomLogger.getInstance().info("User registered: " + username, "AuthManager");
        return newUser;
    }

    public synchronized User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException("Invalid username or password"));

        if (!user.getPasswordHash().equals(hashPassword(password))) {
            throw new AuthException("Invalid username or password");
        }

        this.currentUser = user;
        CustomLogger.getInstance().info("User logged in: " + username, "AuthManager");
        return user;
    }

    public synchronized void logout() {
        if (currentUser != null) {
            CustomLogger.getInstance().info("User logged out: " + currentUser.getUsername(), "AuthManager");
            this.currentUser = null;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
