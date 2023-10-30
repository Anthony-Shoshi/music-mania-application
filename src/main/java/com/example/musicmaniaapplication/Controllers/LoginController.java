package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Controllers.MainController;
import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.User;
import com.example.musicmaniaapplication.Utils.AccountLockedException;
import com.example.musicmaniaapplication.Utils.Constants;
import com.example.musicmaniaapplication.Utils.Helper;
import com.example.musicmaniaapplication.Utils.SceneFactory;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class LoginController {

    public TextField username;
    public TextField passwordField;
    public Button loginButton;
    public Label displayMessage;
    public ImageView logo;
    private Database db;
    private int attemptCount = 0;

    public LoginController() {
        db = new Database();
    }

    public void initialize() {
        Helper.loadLogo(logo, Constants.LOGO);
    }

    public void handlePasswordChange(StringProperty observable, String oldValue, String newValue) {
        loginButton.setDisable(!validatePassword(newValue));
    }

    private boolean validatePassword(String newValue) {
        boolean hasChar = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        char[] charArray = newValue.toCharArray();

        for (char c : charArray) {
            if (Character.isLetter(c)) {
                hasChar = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecialChar = true;
            }
        }

        if (newValue.length() < 8) {
            displayMessage.setText("Password must be at least 8 character long");
            return false;
        } else if (!hasChar) {
            displayMessage.setText("Minimum 1 character is required");
            return false;
        } else if (!hasDigit) {
            displayMessage.setText("Minimum 1 number is required");
            return false;
        } else if (!hasSpecialChar) {
            displayMessage.setText("Minimum 1 special character is required");
            return false;
        } else {
            displayMessage.setText("");
            return true;
        }
    }

    public void handleSubmitButtonAction() {
        try {
            if (attemptCount > 3) {
                throw new AccountLockedException("Your account has been locked");
            } else {
                String username = this.username.getText();
                String password = passwordField.getText();
                User loggedInUser = db.getUser(username, password);
                if (loggedInUser != null) {
                    attemptCount = 0;
                    SceneFactory.loadScene("main-view.fxml", new MainController(loggedInUser), "Main View", (Stage) loginButton.getScene().getWindow());
                } else {
                    attemptCount++;
                    displayMessage.setText("Incorrect username or password. Please try again.");
                }
            }
        } catch (AccountLockedException e) {
            handleAccountLockException(e.getMessage());
        }
    }

    private void handleAccountLockException(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception");
        alert.setHeaderText("Account Locked");
        alert.setContentText(message);

        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);

        alert.setOnCloseRequest(event -> SceneFactory.closeScene((Stage) loginButton.getScene().getWindow()));

        alert.showAndWait();
    }

}
