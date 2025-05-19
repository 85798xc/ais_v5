package com.example.ais_v5.client;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class LoginPanel extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private static final String API_BASE_URL = "http://localhost:8080/api/v1";

    public LoginPanel() {
        setTitle("Login to System");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        panel.add(loginButton, gbc);

        add(panel);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginPanel.this,
                        "Please enter both username and password",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            authenticateUser(username, password);
        }
    }

    private void authenticateUser(String username, String password) {
        SwingWorker<AuthResponse, Void> worker = new SwingWorker<AuthResponse, Void>() {
            @Override
            protected AuthResponse doInBackground() throws Exception {
                try {
                    // Try to access a protected endpoint to verify credentials and get role
                    URL url = new URL(API_BASE_URL + "/users");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // Basic Auth header
                    String auth = username + ":" + password;
                    String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
                    connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Get role from the "WWW-Authenticate" header or another endpoint
                        // For simplicity, we'll use a separate endpoint to get user role
                        String role = getUserRole(username, password);
                        return new AuthResponse(true, role, "Login successful");
                    } else {
                        return new AuthResponse(false, null, "Invalid credentials");
                    }
                } catch (Exception e) {
                    return new AuthResponse(false, null, "Connection error: " + e.getMessage());
                }
            }

            @Override
            protected void done() {
                try {
                    AuthResponse response = get();
                    if (response.isSuccess()) {
                        // Store credentials for future API calls
                        AuthContext.setCredentials(usernameField.getText(),
                                new String(passwordField.getPassword()),
                                response.getRole());

                        // Redirect based on role
                        redirectToPanel(response.getRole());
                    } else {
                        JOptionPane.showMessageDialog(LoginPanel.this,
                                response.getMessage(),
                                "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(LoginPanel.this,
                            "Error during authentication: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }

    private String getUserRole(String username, String password) throws Exception {
        // You might want to create a specific endpoint in your UserController to return role
        URL url = new URL(API_BASE_URL + "/users/role?username=" + username);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String role = in.readLine();
            in.close();
            return role;
        }
        return "ROLE_STUDENT"; // Default role if endpoint not available
    }

    private void redirectToPanel(String role) {
        this.dispose(); // Close login window

        if (role.equals("ROLE_ADMIN")) {
            AdminPanel adminPanel = new AdminPanel();
            adminPanel.setVisible(true);
        } else if (role.equals("ROLE_TEACHER")) {
            TeacherPanel teacherPanel = new TeacherPanel();
            teacherPanel.setVisible(true);
        } else {
            StudentPanel studentPanel = new StudentPanel();
            studentPanel.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPanel loginPanel = new LoginPanel();
            loginPanel.setVisible(true);
        });
    }

    // Helper class for authentication response
    private static class AuthResponse {
        private final boolean success;
        private final String role;
        private final String message;

        public AuthResponse(boolean success, String role, String message) {
            this.success = success;
            this.role = role;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getRole() { return role; }
        public String getMessage() { return message; }
    }

    // Class to store authentication context
    public static class AuthContext {
        @Getter
        private static String username;
        private static String password;
        @Getter
        private static String role;

        public static void setCredentials(String user, String pass, String userRole) {
            username = user;
            password = pass;
            role = userRole;
        }

        public static String getAuthHeader() {
            String auth = username + ":" + password;
            return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
        }

    }
}
