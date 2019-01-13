package ui.controllers.tiles.change_storage_levels;

import db.LoginManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.entities.StorageEntity;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import ui.controllers.tiles.order_management.OrdersProductsTable;
import ui.controllers.tiles.order_management.ProductsViewController;
import ui.views.TileView;

import javax.persistence.TypedQuery;
import java.util.List;

public class ChangeStorageViewController {
    public TableView<OrdersProductsTable> tableBrowseOrdersProducts;
    public TableColumn<OrdersProductsTable, Integer> idColumnProducts;
    public TableColumn<OrdersProductsTable, String>  manufacturerColumnProducts;
    public TableColumn<OrdersProductsTable, String>  nameColumnProducts;
    public TableColumn<OrdersProductsTable, Integer>  quantityProducts;
    public TableColumn<OrdersProductsTable, Void>  changeStorageLevelsColumn;
    private static final ObservableList<OrdersProductsTable> dataProducts = FXCollections.observableArrayList();
    private Session session;

    public void goBackProducts() {
        TileView.initialize(LoginManager.getAccessLevel());
    }

    public void initialize() {
        createTable();
        session = LoginManager.getSession();
        runQuery();
    }

    public void insertProducts(OrdersProductsTable ordersProductsTable) {
        dataProducts.add(ordersProductsTable);
    }

    private void runQuery() {
        TypedQuery<StorageEntity> storageEntityTypedQuery = session.createQuery("from StorageEntity ", StorageEntity.class);
        List<StorageEntity> storageEntityList= storageEntityTypedQuery.getResultList();
        for (StorageEntity storageEntity : storageEntityList) {
            if (storageEntity.getAlbumViewEntity()!=null) {
                insertProducts(new OrdersProductsTable(
                        storageEntity.getProductId(), storageEntity.getAlbumViewEntity().getName(), storageEntity.getAlbumViewEntity().getTitle(), storageEntity.getProductsAvailable()
                ));
            }
            else if (storageEntity.getInstrumentViewEntity()!=null) {
                insertProducts(new OrdersProductsTable(
                        storageEntity.getProductId(), storageEntity.getInstrumentViewEntity().getManufacturerName(), storageEntity.getInstrumentViewEntity().getInstrumentName(), storageEntity.getProductsAvailable()
                ));
            }
            else if (storageEntity.getOtherViewEntity()!=null) {
                insertProducts(new OrdersProductsTable(
                        storageEntity.getProductId(), storageEntity.getOtherViewEntity().getProducer(), storageEntity.getOtherViewEntity().getName(), storageEntity.getProductsAvailable()
                ));
            }
        }
    }


    @SuppressWarnings("Duplicates")
    private void createTable() {
        tableBrowseOrdersProducts.setItems(dataProducts);
        idColumnProducts.setCellValueFactory(
                new PropertyValueFactory<>("productId"));
        manufacturerColumnProducts.setCellValueFactory(
                new PropertyValueFactory<>("manufacturer"));
        nameColumnProducts.setCellValueFactory(
                new PropertyValueFactory<>("productName"));
        quantityProducts.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));
        changeStorageLevelsColumn.setCellFactory(createButtonCellFactory());
    }

    public Callback<TableColumn<OrdersProductsTable, Void>, TableCell<OrdersProductsTable, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<OrdersProductsTable, Void>, TableCell<OrdersProductsTable, Void>>() {
            @Override
            public TableCell<OrdersProductsTable, Void> call(TableColumn<OrdersProductsTable, Void> param) {
                return new TableCell<OrdersProductsTable, Void>() {
                    private Button button = new Button("Change");
                    {
                        setAlignment(Pos.CENTER);
                            button.setOnAction((e) -> changeLevels(dataProducts.get(getIndex())));
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

    private void changeLevels(OrdersProductsTable ordersProductsTable) {
    }
}
