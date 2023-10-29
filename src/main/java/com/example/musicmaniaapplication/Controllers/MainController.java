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
import javafx.scene.Node;
import javafx.scene.Parent;
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
        if (userType == UserType.MANAGER) {
            hideMenuButton(createOrderButton);
        } else if (userType == UserType.SALES) {
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
        setActiveStyleClass(dashboardButton);
        if (loggedInUser != null) {
            SceneFactory.loadContent("dashboard-view.fxml", new DashboardController(loggedInUser), mainContent);
        }
        database = new Database();
    }

    private void setActiveStyleClass(Button activeButton) {
        Parent container = activeButton.getParent();
        for (Node node : container.getChildrenUnmodifiable()) {
            if (node instanceof Button button) {
                if (button == activeButton) {
                    button.getStyleClass().clear();
                    button.getStyleClass().add("active-button");
                } else {
                    button.getStyleClass().clear();
                    button.getStyleClass().add("menu-buttons");
                }
            }
        }
    }

    public void handleDashboardButton(ActionEvent actionEvent) {
        SceneFactory.loadContent("dashboard-view.fxml", new DashboardController(loggedInUser), mainContent);
        setActiveStyleClass(dashboardButton);
    }

    public void handleCreateOrderButton(ActionEvent actionEvent) {
        SceneFactory.loadContent("create-order.fxml", new CreateOrderController(database), mainContent);
        setActiveStyleClass(createOrderButton);
    }

    public void handleProductInventoryButton(ActionEvent actionEvent) {
        SceneFactory.loadContent("product-inventory.fxml", new ProductInventoryController(database), mainContent);
        setActiveStyleClass(productInventoryButton);
    }

    public void handleOrderHistoryButton(ActionEvent actionEvent) {
        SceneFactory.loadContent("order-history.fxml", new OrderHistoryController(database), mainContent);
        setActiveStyleClass(orderHistoryButton);
    }

    public void handleLogoutButton(ActionEvent actionEvent) {
        SceneFactory.loadScene("login.fxml", new LoginController(), "Login", (Stage) logoutButton.getScene().getWindow());
    }
}
