package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.Product;
import com.example.musicmaniaapplication.Utils.SceneFactory;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductInventoryController implements Initializable {
    @FXML
    public TableView productTableView;
    private Database database;
    private ObservableList<Product> products;
    private Product selectedProduct;

    public ProductInventoryController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        products = FXCollections.observableList(database.getProducts());
        productTableView.setItems(products);

        setInitSelection();

        productTableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Product>) (observableValue, oldProduct, newProduct) -> {
            if (productTableView.getSelectionModel().getSelectedItem() != null) {
                selectedProduct = newProduct;
            }
        });
    }

    private void setInitSelection() {
        if (!products.isEmpty()) {
            productTableView.getSelectionModel().selectFirst();
            selectedProduct = products.get(0);
        }
    }

    public void addProduct() {
        CreateProductDialogController controller = new CreateProductDialogController(database);
        SceneFactory.loadDialog("create-product-dialog.fxml", controller, "Create New Product");
        if (controller.getProductItem() != null) {
            products.add(controller.getProductItem());
            database.serializeDatabase();
        }
    }

    public void editProduct() {
        if (selectedProduct != null) {
            EditProductDialogController controller = new EditProductDialogController(database, selectedProduct);
            SceneFactory.loadDialog("edit-product-dialog.fxml", controller, "Edit New Product");
            Product updatedProduct = controller.getProductItem();
            if (updatedProduct != null) {
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
                    if (product.equals(selectedProduct)) {
                        products.set(i, updatedProduct);
                        selectedProduct = null;
                        break;
                    }
                }
                database.serializeDatabase();
            }
        }
    }

    public void deleteProduct() {
        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete " + selectedProduct.getName());
            alert.setContentText("Are you sure you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                products.remove(selectedProduct);
            }
            database.serializeDatabase();
        }
    }
}
