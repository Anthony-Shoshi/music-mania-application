package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditProductDialogController implements Initializable {
    public TextField productName;
    public TextField productCategory;
    public TextField productPrice;
    public TextField productStock;
    public TextArea productDescription;
    private Database database;
    private Product selectedProduct;
    public Label createProductErrorMessage;
    private Product updatedProduct;

    public EditProductDialogController(Database database, Product selectedProduct) {
        this.database = database;
        this.selectedProduct = selectedProduct;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productName.setText(selectedProduct.getName());
        productCategory.setText(selectedProduct.getName());
        productPrice.setText(Double.toString(selectedProduct.getPrice()));
        productStock.setText(Integer.toString(selectedProduct.getStock()));
        productDescription.setText(selectedProduct.getDescription());
    }
    public void updateButtonClick(ActionEvent event) {
        String name = productName.getText();
        String category = productCategory.getText();
        String priceText = productPrice.getText();
        String stockText = productStock.getText();
        String description = productDescription.getText();
        String errorMessage = null;

        if (name.isEmpty()) {
            errorMessage = "Name field is required.";
        } else if (category.isEmpty()) {
            errorMessage = "Category field is required.";
        } else if (priceText.isEmpty()) {
            errorMessage = "Price field is required.";
        } else if (stockText.isEmpty()) {
            errorMessage = "Stock field is required.";
        } else if (description.isEmpty()) {
            errorMessage = "Description field is required.";
        } else {
            double price = -1.0;
            int stock = -1;

            try {
                price = Double.parseDouble(priceText);
                if (price < 0) {
                    errorMessage = "Price cannot be negative.";
                }
            } catch (NumberFormatException e) {
                errorMessage = "Invalid price format.";
            }
            try {
                stock = Integer.parseInt(stockText);
                if (stock < 0) {
                    errorMessage = "Stock cannot be negative.";
                }
            } catch (NumberFormatException e) {
                errorMessage = "Invalid stock format.";
            }

            if (errorMessage == null) {
                updatedProduct = new Product(name, category, stock, price, description);
                closeDialog(event);
            }
        }

        createProductErrorMessage.setText(errorMessage);
    }

    private static void closeDialog(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void closeButtonClick(ActionEvent event) {
        closeDialog(event);
    }

    public Product getProductItem() {
        return updatedProduct;
    }
}
