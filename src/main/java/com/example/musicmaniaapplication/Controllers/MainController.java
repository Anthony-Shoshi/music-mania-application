package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Models.User;
import com.example.musicmaniaapplication.Models.UserType;
import com.example.musicmaniaapplication.Utils.Constants;
import com.example.musicmaniaapplication.Utils.Helper;
import com.example.musicmaniaapplication.Utils.SceneFactory;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public VBox menuItems;
    public Button orderHistoryButton;
    public Button productInventoryButton;
    public Button dashboardButton;
    public Button createOrderButton;
    public Button logoutButton;
    private final User loggedInUser;
    private LocalDateTime localDateTime = LocalDateTime.now();
    private String currentDateTime;
    public Label username;
    public Label role;
    public Label currentTime;
    public ImageView logo;

    public void initialize() {
        menuShowHide();
    }

    public MainController(User user) {
        this.loggedInUser = user;
        currentDateTime = formatCurrentDateTime();
    }

    private void menuShowHide() {
        UserType userType = loggedInUser.getUserType();
        if (userType == UserType.Manager) {
            hideMenuButton(createOrderButton);
        } else if (userType == UserType.Sales) {
            hideMenuButton(productInventoryButton);
        }
    }

    private void hideMenuButton(Button button) {
        button.setVisible(false);
        menuItems.getChildren().remove(button);
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
        Helper.loadLogo(logo, Constants.LOGO);
        menuShowHide();
    }

    public void handleDashboardButton(ActionEvent actionEvent) {
    }

    public void handleCreateOrderButton(ActionEvent actionEvent) {
    }

    public void handleProductInventoryButton(ActionEvent actionEvent) {
    }

    public void handleOrderHistoryButton(ActionEvent actionEvent) {
    }

    public void handleLogoutButton(ActionEvent actionEvent) {
        SceneFactory.loadScene("login.fxml", new LoginController(), "Login", (Stage) logoutButton.getScene().getWindow(), 600, 430);
    }
}
