package com.example.ais_v5.client;

import javax.swing.*;
import java.awt.*;

public class TeacherPanel extends JFrame {
    public TeacherPanel() {
        setTitle("Teacher Dashboard - " + LoginPanel.AuthContext.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel gradesPanel = new JPanel();
        gradesPanel.add(new JLabel("Grades Management"));
        tabbedPane.addTab("Grades", gradesPanel);

        JPanel subjectGroupsPanel = new JPanel();
        subjectGroupsPanel.add(new JLabel("Subject Groups"));
        tabbedPane.addTab("Subject Groups", subjectGroupsPanel);

        add(tabbedPane);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            this.dispose();
            new LoginPanel().setVisible(true);
        });
        add(logoutButton, BorderLayout.SOUTH);
    }
}
