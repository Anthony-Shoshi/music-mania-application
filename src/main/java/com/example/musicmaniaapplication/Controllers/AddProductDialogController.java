package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.OrderProduct;
import com.example.musicmaniaapplication.Models.Product;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddProductDialogController implements Initializable {

    public TableView productTableView;
    public TextField productQuantity;
    public TextField searchBox;
    public Label quantityErrorMessage;
    public Button addToOrder;
    private ObservableList<Product> products;
    private Product selectedProduct;
    private Database database;
    private OrderProduct orderItem;

    public AddProductDialogController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        products = FXCollections.observableList(database.getProducts());
        productTableView.setItems(products);

        if (!products.isEmpty()) {
            productTableView.getSelectionModel().selectFirst();
            selectedProduct = products.get(0);
        }
        productTableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Product>) (observableValue, oldProduct, newProduct) -> {
            if (productTableView.getSelectionModel().getSelectedItem() != null) {
                selectedProduct = newProduct;
            }
        });
    }

    public void quantityOnChange(StringProperty observable, String oldValue, String newValue) {
        try {
            int quantity = Integer.parseInt(newValue);
            if (quantity <= 0) {
                quantityErrorMessage.setText("Quantity must be a positive value");
                addToOrder.setDisable(true);
            } else {
                quantityErrorMessage.setText("");
                addToOrder.setDisable(false);
            }
        } catch (NumberFormatException e) {
            quantityErrorMessage.setText("Quantity must be a numeric value");
            addToOrder.setDisable(true);
        }
    }

    public void searchOnChange(StringProperty observable, String oldValue, String newValue) {
        if (newValue.length() > 2) {
            ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
            for (Product product : products) {
                if (product.getName().toLowerCase().contains(newValue.toLowerCase())) {
                    filteredProducts.add(product);
                }
            }
            productTableView.setItems(filteredProducts);
        } else {
            productTableView.setItems(products);
        }
    }

    public void addToOrderClick(ActionEvent event) {
        int quantity = Integer.parseInt(productQuantity.getText());
        if (selectedProduct.getStock() < quantity) {
            quantityErrorMessage.setText("Insufficient stock for this product.");
        } else {
            orderItem = new OrderProduct(selectedProduct, quantity);
            selectedProduct.setStock(selectedProduct.getStock() - quantity);
            closeDialog(event);
        }
    }

    private static void closeDialog(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cancelClick(ActionEvent event) {
        closeDialog(event);
    }

    public OrderProduct getOrderItem() {
        return orderItem;
    }
}
