package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.User;
import com.example.musicmaniaapplication.Utils.Constants;
import com.example.musicmaniaapplication.Utils.Helper;
import com.example.musicmaniaapplication.Utils.SceneFactory;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController {

    public TextField username;
    public TextField passwordField;
    public Button loginButton;
    public Label displayMessage;
    public ImageView logo;

    private Database db;

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

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        String username = this.username.getText();
        String password = passwordField.getText();
        db = new Database();
        User loggedInUser = db.getUser(username, password);
        if (loggedInUser != null) {
            SceneFactory.loadScene("main-view.fxml", new MainController(loggedInUser), "Main View", (Stage) loginButton.getScene().getWindow(), 800, 550);
        } else {
            displayMessage.setText("Incorrect username or password. Please try again.");
        }
    }

}
