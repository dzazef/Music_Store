package ui.controllers.tiles.manage_orders;

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
import model.entities.OrdersProductsEntity;
import model.entities.OrdersProductsEntityPK;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import ui.controllers.tiles.order_management.OrdersProductsTable;
import ui.controllers.tiles.order_management.OrdersTable;

import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("Duplicates")
public class ManageProductsViewController {
    public ScrollPane scrollPane;
    public TableView<OrdersProductsTable> tableBrowseOrdersProducts;
    public TableColumn<OrdersProductsTable, Integer> idColumnProducts;
    public TableColumn<OrdersProductsTable, String>  manufacturerColumnProducts;
    public TableColumn<OrdersProductsTable, String>  nameColumnProducts;
    public TableColumn<OrdersProductsTable, Integer> quantityProducts;
    public TableColumn<OrdersProductsTable, Void> removeColumn;
    private static final ObservableList<OrdersProductsTable> dataProducts = FXCollections.observableArrayList();
    public TableColumn<OrdersProductsTable, Void> addColumn;
    private static OrdersTable ordersTable;
    private static Stage stage;

    static void insertData(OrdersTable ordersTable) {
        Session session = LoginManager.getSession();
        TypedQuery<OrdersProductsEntity> ordersProductsEntityTypedQuery =
                session.createQuery("from OrdersProductsEntity where orderId=(:id)", OrdersProductsEntity.class).setParameter("id", ordersTable.getOrderId());
        List<OrdersProductsEntity> ordersProductsEntityList = ordersProductsEntityTypedQuery.getResultList();
        for (OrdersProductsEntity ordersProductsEntity : ordersProductsEntityList) {
            try {
                System.out.println(ordersProductsEntity.getOrderId());
                try {
                    dataProducts.add(
                            new OrdersProductsTable(
                                    ordersProductsEntity.getProductId(),
                                    ordersProductsEntity.getAlbumViewEntity().getName(),
                                    ordersProductsEntity.getAlbumViewEntity().getTitle(),
                                    ordersProductsEntity.getQuantity()));
                } catch (ObjectNotFoundException e){//
                }
                try {
                    dataProducts.add(
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
                    dataProducts.add(
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

    public static void closeAddProductWindow() {
        stage.close();
        dataProducts.clear();
        insertData(ordersTable);
    }

    public void initialize() {
        dataProducts.clear();
        createTable();
    }

    public void createTable() {
        tableBrowseOrdersProducts.setItems(dataProducts);
        idColumnProducts.setCellValueFactory(
                new PropertyValueFactory<>("productId"));
        manufacturerColumnProducts.setCellValueFactory(
                new PropertyValueFactory<>("manufacturer"));
        nameColumnProducts.setCellValueFactory(
                new PropertyValueFactory<>("productName"));
        quantityProducts.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));
        removeColumn.setCellFactory(createButtonCellFactory("−"));
        addColumn.setCellFactory(createButtonCellFactory("+"));
    }

    public Callback<TableColumn<OrdersProductsTable, Void>, TableCell<OrdersProductsTable, Void>> createButtonCellFactory(String message) {
        return new Callback<TableColumn<OrdersProductsTable, Void>, TableCell<OrdersProductsTable, Void>>() {
            @Override
            public TableCell<OrdersProductsTable, Void> call(TableColumn<OrdersProductsTable, Void> param) {
                return new TableCell<OrdersProductsTable, Void>() {
                    private Button button = new Button(message);

                    {
                        setAlignment(Pos.CENTER);
                        if (message.equals("−")) button.setOnAction((e) -> minus(dataProducts.get(getIndex())));
                        else button.setOnAction((e) -> plus(dataProducts.get(getIndex())));
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

    public void setInfo(OrdersTable ordersTable) {
        this.ordersTable = ordersTable;
    }

    private void plus(OrdersProductsTable ordersProductsTable) {
        Session session = LoginManager.getSession();
        session.beginTransaction();
        OrdersProductsEntityPK ordersProductsEntityPK = new OrdersProductsEntityPK();
        ordersProductsEntityPK.setOrderId(ordersTable.getOrderId());
        ordersProductsEntityPK.setProductId(ordersProductsTable.getProductId());
        OrdersProductsEntity ordersProductsEntity = session.load(OrdersProductsEntity.class, ordersProductsEntityPK);
        ordersProductsEntity.setQuantity(ordersProductsEntity.getQuantity()+1);
        session.update(ordersProductsEntity);
        session.getTransaction().commit();


        dataProducts.remove(ordersProductsTable);
        ordersProductsTable.setQuantity(ordersProductsTable.getQuantity()+1);
        dataProducts.add(ordersProductsTable);
        session.close();
    }

    private void minus(OrdersProductsTable ordersProductsTable) {
        Session session = LoginManager.getSession();
        session.beginTransaction();
        OrdersProductsEntityPK ordersProductsEntityPK = new OrdersProductsEntityPK();
        ordersProductsEntityPK.setOrderId(ordersTable.getOrderId());
        ordersProductsEntityPK.setProductId(ordersProductsTable.getProductId());
        OrdersProductsEntity ordersProductsEntity = session.load(OrdersProductsEntity.class, ordersProductsEntityPK);

        if (ordersProductsEntity.getQuantity()>1) {
            ordersProductsEntity.setQuantity(ordersProductsEntity.getQuantity()-1);
            session.update(ordersProductsEntity);
            session.getTransaction().commit();
            dataProducts.remove(ordersProductsTable);
            ordersProductsTable.setQuantity(ordersProductsTable.getQuantity()-1);
            dataProducts.add(ordersProductsTable);
        } else {
            session.remove(ordersProductsEntity);
            session.getTransaction().commit();
            dataProducts.remove(ordersProductsTable);
        }
        session.close();
    }

    @FXML
    public void goBackProducts() {
        System.out.println("tu");
        ManageOrdersViewController.closeDialogWindow();
    }

    @FXML
    public void addProductButton() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("fxml/tiles/ManageOrdersAddProducts.fxml"));
            Parent root = loader.load();
            ((AddProductToOrderViewController) loader.getController()).setInfo(ordersTable.getOrderId());
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add product");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Unable to load manage status view.");
            alert.showAndWait();
        }
    }
}

