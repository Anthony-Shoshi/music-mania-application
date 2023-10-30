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
    private String userDisplayName;

    public DashboardController(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        currentDateTime = formatCurrentDateTime();
        userDisplayName = capitalizeFirstLetter(loggedInUser.getUsername());
    }

    public void initialize() {
        if (loggedInUser != null) {
            setDashboardContent();
        }
    }

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private String formatCurrentDateTime() {
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    private void setDashboardContent() {
        username.setText("Welcome " + userDisplayName);
        role.setText("Your role is: " + loggedInUser.getUserType().toString().charAt(0) + loggedInUser.getUserType().toString().substring(1).toLowerCase());
        currentTime.setText("It is now: " + currentDateTime);
    }
}
