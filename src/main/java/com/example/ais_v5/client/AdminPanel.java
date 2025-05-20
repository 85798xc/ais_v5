package com.example.ais_v5.client;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JFrame {
    public AdminPanel() {
        setTitle("Admin Dashboard - " + LoginPanel.AuthContext.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel usersPanel = new JPanel();
        usersPanel.add(new JLabel("Users Management"));
        tabbedPane.addTab("Users", usersPanel);

        JPanel groupsPanel = new JPanel();
        groupsPanel.add(new JLabel("Groups Management"));
        tabbedPane.addTab("Groups", groupsPanel);

        JPanel subjectsPanel = new JPanel();
        subjectsPanel.add(new JLabel("Subjects Management"));
        tabbedPane.addTab("Subjects", subjectsPanel);

        add(tabbedPane);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            this.dispose();
            new LoginPanel().setVisible(true);
        });
        add(logoutButton, BorderLayout.SOUTH);
    }
}
