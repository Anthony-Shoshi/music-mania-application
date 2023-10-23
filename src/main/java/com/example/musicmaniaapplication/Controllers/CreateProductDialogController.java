package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.OrderProduct;
import com.example.musicmaniaapplication.Models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateProductDialogController {
    public TextField productName;
    public TextField productCategory;
    public TextField productPrice;
    public TextField productStock;
    public TextArea productDescription;
    private Database database;
    private Product product;
    @FXML
    public Label createProductErrorMessage;

    public CreateProductDialogController(Database database) {
        this.database = database;
    }

    public void createButtonClick(ActionEvent event) {
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
                product = new Product(name, category, stock, price, description);
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
        return product;
    }
}
