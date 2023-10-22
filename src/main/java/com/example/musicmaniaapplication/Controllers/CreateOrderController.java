package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.Order;
import com.example.musicmaniaapplication.Models.OrderProduct;
import com.example.musicmaniaapplication.Models.Product;
import com.example.musicmaniaapplication.Utils.SceneFactory;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class CreateOrderController implements Initializable {
    @FXML
    public TableView orderItemTableView;
    public TextField customerFirstName;
    public TextField customerLastName;
    public TextField customerEmail;
    public TextField customerPhoneNumber;
    public Label orderErrorMessage;
    @FXML
    public TableColumn<OrderProduct, String> productName;
    @FXML
    public TableColumn<OrderProduct, String> category;
    @FXML
    public TableColumn<OrderProduct, String> price;
    private ObservableList<OrderProduct> orderProducts;
    private Database database;
    private OrderProduct selectedProduct = null;

    public CreateOrderController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderProducts = FXCollections.observableList(database.getOrderProducts());
        orderItemTableView.setItems(orderProducts);

        addCustomColumns();

        orderItemTableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<OrderProduct>) (observableValue, oldOrderProduct, newOrderProduct) -> selectedProduct = newOrderProduct);
    }

    private void addCustomColumns() {
        productName.setCellValueFactory(pn -> new SimpleStringProperty(pn.getValue().getProduct().getName()));
        category.setCellValueFactory(cat -> new SimpleStringProperty(cat.getValue().getProduct().getCategory()));
        price.setCellValueFactory(cellData -> {
            OrderProduct orderProduct = cellData.getValue();
            Double productPrice = orderProduct.getProduct().getPrice();
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String formattedPrice = decimalFormat.format(productPrice);
            return new SimpleStringProperty(formattedPrice);
        });
    }

    public void addProductClick() {
        AddProductDialogController controller = new AddProductDialogController(database);
        SceneFactory.loadDialog("add-product-dialog.fxml", controller, "Add Product");
        if (controller.getOrderItem() != null) {
            orderProducts.add(controller.getOrderItem());
        }
    }

    public void deleteProductClick() {
        if (selectedProduct == null) {
            orderErrorMessage.setText("Please select product to remove.");
        } else {
            orderProducts.remove(selectedProduct);
            orderErrorMessage.setText("");
            orderErrorMessage.setTextFill(Color.RED);
        }
    }

    public void createOrderClick() {
        String cusFirstName = customerFirstName.getText();
        String cusLastName = customerLastName.getText();
        String cusEmail = customerEmail.getText();
        String cusPhone = customerPhoneNumber.getText();
        if (cusFirstName.isEmpty()) {
            orderErrorMessage.setText("First Name field is required.");
        } else if (cusLastName.isEmpty()) {
            orderErrorMessage.setText("Last Name field is required.");
        } else if (cusEmail.isEmpty()) {
            orderErrorMessage.setText("Email field is required.");
        } else if (cusPhone.isEmpty()) {
            orderErrorMessage.setText("Phone Number field is required.");
        } else {
            orderErrorMessage.setTextFill(Color.GREEN);
            orderErrorMessage.setText("Order placed successfully!");

            List<OrderProduct> products = new ArrayList<>();
            products.addAll(database.getOrderProducts());

            Order order = new Order(cusFirstName, cusLastName, cusEmail, cusPhone, products);
            database.getOrders().add(order);

            updateStock();
            resetOrderForm();
        }
    }

    private void updateStock() {
        for (OrderProduct product :
                orderProducts) {
            product.getProduct().setStock(product.getProduct().getStock() - product.getQuantity());
        }
    }

    private void resetOrderForm() {
        selectedProduct = null;
        orderProducts.clear();
        customerFirstName.setText("");
        customerLastName.setText("");
        customerEmail.setText("");
        customerPhoneNumber.setText("");
    }
}
