package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.Product;
import com.example.musicmaniaapplication.Utils.SceneFactory;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class ProductInventoryController implements Initializable {
    @FXML
    public TableView productTableView;
    @FXML
    public Button importProducts;
    @FXML
    public Label productInventoryMsg;
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

    public void importProductsFromCsv() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(importProducts.getScene().getWindow());

        if (selectedFile != null) {
            List<Product> importedProducts = readProductsFromCSV(selectedFile);
            if (!importedProducts.isEmpty()) {
                database.getProducts().addAll(importedProducts);
                database.serializeDatabase();
                products = FXCollections.observableList(database.getProducts());
                productTableView.setItems(products);
                productInventoryMsg.setTextFill(Color.GREEN);
                productInventoryMsg.setText("Products imported successfully.");
            } else {
                productInventoryMsg.setTextFill(Color.RED);
                productInventoryMsg.setText("No valid products found in the CSV file.");
            }
        } else {
            productInventoryMsg.setTextFill(Color.RED);
            productInventoryMsg.setText("No file selected.");
        }
    }

    private List<Product> readProductsFromCSV(File file) {
        List<Product> importedProducts = new ArrayList<>();
        Path filePath = file.toPath();
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    String name = parts[0];
                    String category = parts[1];
                    int stock = Integer.parseInt(parts[4]);
                    double price = Double.parseDouble(parts[2]);
                    String description = parts[3];
                    importedProducts.add(new Product(name, category, stock, price, description));
                } else {
                    productInventoryMsg.setText("Skipped an invalid line in the CSV file.");
                }
            }
        } catch (IOException e) {
            productInventoryMsg.setText("Error while reading the CSV file: " + e.getMessage());
        }

        return importedProducts;
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
