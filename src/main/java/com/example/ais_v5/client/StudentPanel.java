package com.example.ais_v5.client;

import javax.swing.*;
import java.awt.*;

public class StudentPanel extends JFrame {
    public StudentPanel() {
        setTitle("Student Dashboard - " + LoginPanel.AuthContext.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // My grades tab
        JPanel gradesPanel = new JPanel();
        gradesPanel.add(new JLabel("My Grades"));
        tabbedPane.addTab("Grades", gradesPanel);

        // My subjects tab
        JPanel subjectsPanel = new JPanel();
        subjectsPanel.add(new JLabel("My Subjects"));
        tabbedPane.addTab("Subjects", subjectsPanel);

        add(tabbedPane);

        // Add logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            this.dispose();
            new LoginPanel().setVisible(true);
        });
        add(logoutButton, BorderLayout.SOUTH);
    }
}
