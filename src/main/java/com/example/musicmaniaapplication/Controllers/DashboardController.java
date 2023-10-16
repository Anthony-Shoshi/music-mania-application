package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Models.User;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    public Label username;
    public Label role;
    public Label currentTime;
    private final LocalDateTime localDateTime = LocalDateTime.now();
    private final String currentDateTime;
    private final User loggedInUser;

    public DashboardController(User loggedInUser) {
        currentDateTime = formatCurrentDateTime();
        this.loggedInUser = loggedInUser;
    }

    public void initialize() {
        if (loggedInUser != null) {
            setDashboardContent();
        }
    }

    private String formatCurrentDateTime() {
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    private void setDashboardContent() {
        username.setText("Welcome " + loggedInUser.getUsername());
        role.setText("Your role is: " + loggedInUser.getUserType());
        currentTime.setText("It is now: " + currentDateTime);
    }
}
