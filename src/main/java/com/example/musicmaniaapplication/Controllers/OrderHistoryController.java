package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
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
    private Database database;
    private ObservableList<Order> orders;

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
    }
}
