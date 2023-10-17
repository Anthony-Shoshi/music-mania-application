package com.example.musicmaniaapplication.Controllers;

import com.example.musicmaniaapplication.Data.Database;
import com.example.musicmaniaapplication.Models.OrderItems;
import com.example.musicmaniaapplication.Utils.SceneFactory;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {
    public TextField customerFirstName;
    public TextField customerLastName;
    public TextField customerEmail;
    public TextField customerPhoneNumber;
    public TableView orderItemTableView;
    private ObservableList<OrderItems> orderItems;
    private Database database;

    public CreateOrderController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        orderItems = FXCollections.observableList(database.getOrderItems());
        orderItemTableView.setItems(orderItems);

        addCustomColumns();
    }

    private void addCustomColumns() {
        TableColumn<OrderItems, String> productNameColumn = new TableColumn<>("Name");
        TableColumn<OrderItems, String> productCategoryColumn = new TableColumn<>("Category");
        TableColumn<OrderItems, Number> productPriceColumn = new TableColumn<>("Price");

        productNameColumn.setCellValueFactory(pn -> {
            OrderItems orderItem = pn.getValue();
            return new SimpleStringProperty(orderItem.getProduct().getName());
        });
        productCategoryColumn.setCellValueFactory(pc -> {
            OrderItems orderItem = pc.getValue();
            return new SimpleStringProperty(orderItem.getProduct().getCategory());
        });
        productPriceColumn.setCellValueFactory(pp -> {
            OrderItems orderItem = pp.getValue();
            return new SimpleDoubleProperty(orderItem.getProduct().getPrice());
        });

//        productNameColumn.setPrefWidth(200);
//        productNameColumn.setPrefWidth(150);
//        productNameColumn.setPrefWidth(90);

        orderItemTableView.getColumns().addAll(productNameColumn, productCategoryColumn, productPriceColumn);
        orderItemTableView.setPrefWidth(TableView.USE_COMPUTED_SIZE);
    }

    public void addProductClick(ActionEvent actionEvent) {
        AddProductDialogController controller = new AddProductDialogController(database);
        SceneFactory.loadDialog("add-product-dialog.fxml", controller, "Add Product");
        if (controller.getOrderItem() != null) {
            orderItems.add(controller.getOrderItem());
        }
    }

    public void deleteProductClick(ActionEvent actionEvent) {
    }

    public void createOrderClick(ActionEvent actionEvent) {
    }
}
