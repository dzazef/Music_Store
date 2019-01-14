package ui.controllers.tiles.order_management;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ProductsViewController {
    public TableView<OrdersProductsTable> tableBrowseOrdersProducts;
    public TableColumn<OrdersProductsTable, Integer> idColumnProducts;
    public TableColumn<OrdersProductsTable, String>  manufacturerColumnProducts;
    public TableColumn<OrdersProductsTable, String>  nameColumnProducts;
    public TableColumn<OrdersProductsTable, Integer>  quantityProducts;
    private static final ObservableList<OrdersProductsTable> dataProducts = FXCollections.observableArrayList();
    public BorderPane borderPaneProducts;

    public void initialize() {
        dataProducts.clear();
        createOrdersProductsTable();
    }


    private void createOrdersProductsTable() {
        tableBrowseOrdersProducts.setItems(dataProducts);
        idColumnProducts.setCellValueFactory(
                new PropertyValueFactory<>("productId"));
        manufacturerColumnProducts.setCellValueFactory(
                new PropertyValueFactory<>("manufacturer"));
        nameColumnProducts.setCellValueFactory(
                new PropertyValueFactory<>("productName"));
        quantityProducts.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));
    }

    public static void insertData(OrdersProductsTable ordersProductsTable) {
        dataProducts.add(ordersProductsTable);
    }


    @FXML
    public void goBackProducts() {
        ((Stage)borderPaneProducts.getScene().getWindow()).close();
    }
}
