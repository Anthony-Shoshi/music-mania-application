package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.Order;
import com.example.musicmaniaapplication.Models.OrderProduct;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHistoryController implements Initializable {
    @FXML
    public TableView orderHistoryTableView;
    @FXML
    public TableColumn<Order, String> orderDate;
    @FXML
    public TableColumn<Order, String> customerFullName;
    @FXML
    public TableColumn<Order, String> totalPrice;
    @FXML
    public TableView orderedProductTableView;
    @FXML
    public TableColumn<OrderProduct, Number> productQuantity;
    @FXML
    public TableColumn<OrderProduct, String> productName;
    @FXML
    public TableColumn<OrderProduct, String> productCategory;
    @FXML
    public TableColumn<OrderProduct, String> productPrice;
    private Database database;
    private ObservableList<Order> orders;
    private ObservableList<OrderProduct> orderProducts;
    public OrderHistoryController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orders = FXCollections.observableList(database.getOrders());
        orderHistoryTableView.setItems(orders);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy H:mm:ss");
        orderDate.setCellValueFactory(od -> new SimpleStringProperty(od.getValue().getOrderDateTime().format(formatter)));
        customerFullName.setCellValueFactory(cfn -> new SimpleStringProperty(cfn.getValue().getCustomerFirstName()));
        totalPrice.setCellValueFactory(cellData -> {
            Order order = cellData.getValue();
            Double total = order.calculateTotalPrice();
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String formattedPrice = decimalFormat.format(total);
            return new SimpleStringProperty(formattedPrice);
        });

        orderHistoryTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observableValue, Order oldOrder, Order newOrder) {
                if (newOrder != null) {
                    orderProducts = FXCollections.observableList(newOrder.getProducts());
                    viewOrderedProduct();
                }
            }
        });
    }

    private void viewOrderedProduct() {
        orderedProductTableView.setItems(orderProducts);
        productQuantity.setCellValueFactory(pq -> new SimpleIntegerProperty(pq.getValue().getQuantity()));
        productName.setCellValueFactory(pn -> new SimpleStringProperty(pn.getValue().getProduct().getName()));
        productCategory.setCellValueFactory(pc -> new SimpleStringProperty(pc.getValue().getProduct().getCategory()));
        productPrice.setCellValueFactory(cellData -> {
            Double price = cellData.getValue().getProduct().getPrice();
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String formattedPrice = decimalFormat.format(price);
            return new SimpleStringProperty(formattedPrice);
        });
    }
}
