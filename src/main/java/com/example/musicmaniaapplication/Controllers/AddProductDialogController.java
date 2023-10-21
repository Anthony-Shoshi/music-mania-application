package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.OrderProduct;
import com.example.musicmaniaapplication.Models.Product;
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
import java.util.ResourceBundle;

public class AddProductDialogController implements Initializable {

    public TableView productTableView;
    public TextField productQuantity;
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

        if (!products.isEmpty())
        {
            productTableView.getSelectionModel().selectFirst();
            selectedProduct = products.get(0);
        }
        productTableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Product>) (observableValue, oldProduct, newProduct) -> {
            if (productTableView.getSelectionModel().getSelectedItem() != null) {
                selectedProduct = newProduct;
            }
        });
    }

    public void quantityOnChange(String newValue) {
        if (!newValue.matches("\\d*") || newValue.isEmpty()) {
            quantityErrorMessage.setText("Quantity must be numeric value");
            productQuantity.setText("");
            addToOrder.setDisable(true);
        } else {
            quantityErrorMessage.setText("");
            addToOrder.setDisable(false);
        }
    }

    public void addToOrderClick(ActionEvent event) {
        orderItem = new OrderProduct(selectedProduct, Integer.parseInt(productQuantity.getText()));
        closeDialog(event);
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
