package com.example.ais_v5.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentPanel extends JFrame {

    private static final String GRADES_URL = "http://localhost:8080/api/v1/grades/grades";
    private static final ObjectMapper mapper = new ObjectMapper();

    private JPanel gradesPanel;
    private JPanel subjectsPanel;

    public StudentPanel() {
        setTitle("Student Dashboard - " + LoginPanel.AuthContext.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Grades Tab
        gradesPanel = new JPanel();
        gradesPanel.setLayout(new BoxLayout(gradesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollGrades = new JScrollPane(gradesPanel);
        tabbedPane.addTab("Grades", scrollGrades);

        // Subjects Tab
        subjectsPanel = new JPanel();
        subjectsPanel.setLayout(new BoxLayout(subjectsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollSubjects = new JScrollPane(subjectsPanel);
        tabbedPane.addTab("Subjects", scrollSubjects);

        add(tabbedPane, BorderLayout.CENTER);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener((ActionEvent e) -> {
            this.dispose();
            new LoginPanel().setVisible(true);
        });

        add(logoutButton, BorderLayout.SOUTH);

        // Load data asynchronously
        new SwingWorker<Void, Void>() {
            private List<Map<String, Object>> grades = new ArrayList<>();

            @Override
            protected Void doInBackground() {
                try {
                    grades = fetchGradesFromApi();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        gradesPanel.add(new JLabel("Error loading grades."));
                        subjectsPanel.add(new JLabel("Error loading subjects."));
                    });
                }
                return null;
            }

            @Override
            protected void done() {
                gradesPanel.removeAll();
                subjectsPanel.removeAll();


                if (grades.isEmpty()) {
                    gradesPanel.add(new JLabel("No grades found."));
                    subjectsPanel.add(new JLabel("No subjects found."));
                } else {
                    for (Map<String, Object> entry : grades) {
                        Map<String, Object> userDto = (Map<String, Object>) entry.get("userdto");
                        String fullName = ((String) userDto.get("fullName")).trim();


                        String subject = (String) entry.get("subject");
                        int gradeValue = ((Number) entry.get("grade")).intValue();

                        JLabel label = new JLabel(subject + ": " + gradeValue);
                        gradesPanel.add(label);
                    }

                    // Collect unique subjects
                    grades.stream()
                            .map(map -> (String) map.get("subject"))
                            .distinct()
                            .sorted()
                            .forEach(subject -> subjectsPanel.add(new JLabel(subject)));
                }

                gradesPanel.revalidate();
                gradesPanel.repaint();
                subjectsPanel.revalidate();
                subjectsPanel.repaint();
            }

            private List<Map<String, Object>> fetchGradesFromApi() throws IOException {
                URL url = new URL(GRADES_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");

                // Get credentials from login context or wherever you stored them
                String username = LoginPanel.AuthContext.getUsername();
                String password = LoginPanel.AuthContext.getPassword(); // Make sure this is available!

                // Encode credentials as Base64 for Basic Auth
                String auth = username + ":" + password;
                String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

                // Set Authorization header
                conn.setRequestProperty("Authorization", "Basic " + encodedAuth);

                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("HTTP error code: " + responseCode);
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder jsonResponse = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    jsonResponse.append(line);
                    System.out.println(line);
                }
                reader.close();

                return mapper.readValue(jsonResponse.toString(), new TypeReference<List<Map<String, Object>>>() {});
            }
        }.execute();
    }
}
