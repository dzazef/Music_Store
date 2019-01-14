package ui.controllers.tiles.order_management;

import db.LoginManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entities.OrdersEntity;
import model.entities.OrdersProductsEntity;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import ui.views.TileView;

import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("Duplicates")
public abstract class ViewController {
    protected Stage stage;
    public ScrollPane scrollPane;
    public TableView<OrdersTable> tableBrowseOrders;
    public TableColumn<OrdersTable, String>  TransactionDocumentColumn;
    public TableColumn<OrdersTable, Integer> idColumn;
    public TableColumn<OrdersTable, String> nameColumn;
    public TableColumn<OrdersTable, String> addressColumn;
    public TableColumn<OrdersTable, String> phoneColumn;
    public TableColumn<OrdersTable, String>  deliveryColumn;
    public TableColumn<OrdersTable, String>  statusColumn;
    public TableColumn<OrdersTable, String>  paymentColumn;
    public TableColumn<OrdersTable, Void> productsColumn;
    public TableColumn<OrdersTable, Void> confirmColumn;
    public static final ObservableList<OrdersTable> data = FXCollections.observableArrayList();

    public abstract void initialize();

    protected void createTableView() {
        tableBrowseOrders.setItems(data);
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderId"));
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(
                new PropertyValueFactory<>("customerAdress"));
        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<>("phoneNumber"));
        deliveryColumn.setCellValueFactory(
                new PropertyValueFactory<>("deliveryName"));
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("currentStatus"));
        paymentColumn.setCellValueFactory(
                new PropertyValueFactory<>("payment"));
        TransactionDocumentColumn.setCellValueFactory(
                new PropertyValueFactory<>("transactionDocument"));
        productsColumn.setCellFactory(callCreateButtonCellFactory(true));
    }

    public Callback<TableColumn<OrdersTable, Void>, TableCell<OrdersTable, Void>> createButtonCellFactory(boolean isItProducts, String buttonMessage) {
        return new Callback<TableColumn<OrdersTable, Void>, TableCell<OrdersTable, Void>>() {
            @Override
            public TableCell<OrdersTable, Void> call(TableColumn<OrdersTable, Void> param) {
                return new TableCell<OrdersTable, Void>() {
                    private Button button = new Button((isItProducts ? "Show" : buttonMessage));
                    {
                        setAlignment(Pos.CENTER);
                        if (isItProducts)
                            button.setOnAction((e) -> showProducts(data.get(getIndex())));
                        else
                            button.setOnAction(e -> buttonHandle(data.get(getIndex())));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else {
                            setGraphic(button);
                        }
                    }
                };
            }
        };
    }

    public abstract Callback<TableColumn<OrdersTable, Void>, TableCell<OrdersTable, Void>> callCreateButtonCellFactory(boolean isItProducts);

    public abstract void buttonHandle(OrdersTable ordersTable);

    protected void showProducts(OrdersTable ordersTable) {
        @SuppressWarnings("ConstantConditions") final Parent parent;
        try {
            parent = FXMLLoader
                    .load(this.getClass().getClassLoader().getResource("fxml/tiles/CompleteOrdersProducts.fxml"));
            stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Products");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Unable to load manage products view.");
            alert.showAndWait();
        }

        Session session = LoginManager.getSession();
        TypedQuery<OrdersProductsEntity> ordersProductsEntityTypedQuery =
                session.createQuery("from OrdersProductsEntity where orderId=(:id)", OrdersProductsEntity.class).setParameter("id", ordersTable.getOrderId());
        List<OrdersProductsEntity> ordersProductsEntityList = ordersProductsEntityTypedQuery.getResultList();
        for (OrdersProductsEntity ordersProductsEntity : ordersProductsEntityList) {
            try {
                System.out.println(ordersProductsEntity.getOrderId());
                try {
                    ProductsViewController.insertData(
                            new OrdersProductsTable(
                                    ordersProductsEntity.getProductId(),
                                    ordersProductsEntity.getAlbumViewEntity().getName(),
                                    ordersProductsEntity.getAlbumViewEntity().getTitle(),
                                    ordersProductsEntity.getQuantity()));
                } catch (ObjectNotFoundException e){//
                }
                try {
                    ProductsViewController.insertData(
                            new OrdersProductsTable(
                                    ordersProductsEntity.getProductId(),
                                    ordersProductsEntity.getInstrumentViewEntity().getManufacturerName(),
                                    ordersProductsEntity.getInstrumentViewEntity().getInstrumentName(),
                                    ordersProductsEntity.getQuantity()
                            )
                    );
                } catch (ObjectNotFoundException e){//
                }
                try {
                    ProductsViewController.insertData(
                            new OrdersProductsTable(
                                    ordersProductsEntity.getProductId(),
                                    ordersProductsEntity.getOtherViewEntity().getProducer(),
                                    ordersProductsEntity.getOtherViewEntity().getName(),
                                    ordersProductsEntity.getQuantity()
                            )
                    );
                } catch (ObjectNotFoundException e){//
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        session.close();
    }

    public void runQuery() {
        data.clear();
        Session session = LoginManager.getSession();
        TypedQuery<OrdersEntity> completeOrdersQuery = session.
                createQuery("from OrdersEntity", OrdersEntity.class);
        List<OrdersEntity> ordersEntityList = completeOrdersQuery.getResultList();
        session.close();
        for (OrdersEntity ordersEntity : ordersEntityList) {
            data.add(new OrdersTable(ordersEntity));
        }
    }

    @FXML
    public void goBack() {
        TileView.initialize(LoginManager.getAccessLevel());
    }
}
