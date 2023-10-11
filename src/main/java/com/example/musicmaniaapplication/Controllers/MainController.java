package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Models.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private User loggedInUser;
    private LocalDateTime localDateTime = LocalDateTime.now();
    private String currentDateTime;

    public Label username;
    public Label role;
    public Label currentTime;

    public MainController(User user) {
        this.loggedInUser = user;
        currentDateTime = formatCurrentDateTime();
    }

    private String formatCurrentDateTime() {
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText("Welcome " + loggedInUser.getUsername());
        role.setText("Your role is: " + loggedInUser.getUserType());
        currentTime.setText("It is now: " + currentDateTime);
    }
}
