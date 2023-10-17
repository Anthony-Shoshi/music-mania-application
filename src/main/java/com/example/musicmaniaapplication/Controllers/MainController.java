package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.User;
import com.example.musicmaniaapplication.Models.UserType;
import com.example.musicmaniaapplication.Utils.Constants;
import com.example.musicmaniaapplication.Utils.Helper;
import com.example.musicmaniaapplication.Utils.SceneFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    public AnchorPane mainContent;
    public ImageView logo;
    Database database;

    public MainController(User user) {
        this.loggedInUser = user;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Helper.loadLogo(logo, Constants.LOGO);
        menuShowHide();
        if (loggedInUser != null) {
            SceneFactory.loadContent("dashboard-view.fxml", new DashboardController(loggedInUser), mainContent);
        }
        database = new Database();
    }

    public void handleDashboardButton(ActionEvent actionEvent) {
        SceneFactory.loadContent("dashboard-view.fxml", new DashboardController(loggedInUser), mainContent);
    }

    public void handleCreateOrderButton(ActionEvent actionEvent) {
        SceneFactory.loadContent("create-order.fxml", new CreateOrderController(database), mainContent);
    }

    public void handleProductInventoryButton(ActionEvent actionEvent) {
    }

    public void handleOrderHistoryButton(ActionEvent actionEvent) {
    }

    public void handleLogoutButton(ActionEvent actionEvent) {
        SceneFactory.loadScene("login.fxml", new LoginController(), "Login", (Stage) logoutButton.getScene().getWindow());
    }
}
