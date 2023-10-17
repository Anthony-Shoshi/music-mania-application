package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {
    public TextField customerFirstName;
    public TextField customerLastName;
    public TextField customerEmail;
    public TextField customerPhoneNumber;
    public TableView productTableView;
    private ObservableList<Product> products;
    private Database database;

    public CreateOrderController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        products = FXCollections.observableList(database.getProducts());
        productTableView.setItems(products);
    }

    public void addProductClick(ActionEvent actionEvent) {

    }

    public void deleteProductClick(ActionEvent actionEvent) {
    }

    public void createOrderClick(ActionEvent actionEvent) {
    }
}
