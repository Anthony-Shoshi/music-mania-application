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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class CreateOrderController implements Initializable {
    public TableView orderItemTableView;
    public TextField customerFirstName;
    public TextField customerLastName;
    public TextField customerEmail;
    public TextField customerPhoneNumber;
    public Label orderErrorMessage;
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
        TableColumn<OrderProduct, String> productNameColumn = new TableColumn<>("Name");
        TableColumn<OrderProduct, String> productCategoryColumn = new TableColumn<>("Category");
        TableColumn<OrderProduct, Number> productPriceColumn = new TableColumn<>("Price");

        productNameColumn.setCellValueFactory(pn -> {
            OrderProduct orderItem = pn.getValue();
            return new SimpleStringProperty(orderItem.getProduct().getName());
        });
        productCategoryColumn.setCellValueFactory(pc -> {
            OrderProduct orderItem = pc.getValue();
            return new SimpleStringProperty(orderItem.getProduct().getCategory());
        });
        productPriceColumn.setCellValueFactory(pp -> {
            OrderProduct orderItem = pp.getValue();
            return new SimpleDoubleProperty(orderItem.getProduct().getPrice());
        });

        orderItemTableView.getColumns().addAll(productNameColumn, productCategoryColumn, productPriceColumn);
        orderItemTableView.setPrefWidth(USE_COMPUTED_SIZE);
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
            orderErrorMessage.setText("Order placed successfully!");
            orderErrorMessage.setTextFill(Color.GREEN);
            database.getOrders().add(new Order(cusFirstName, cusLastName, cusEmail, cusPhone, orderProducts));
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
        customerFirstName.setText("");
        customerLastName.setText("");
        customerEmail.setText("");
        customerPhoneNumber.setText("");
        orderErrorMessage.setTextFill(Color.RED);
    }
}
