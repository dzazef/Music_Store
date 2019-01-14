package ui.controllers.tiles.change_storage_levels;

import db.LoginManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entities.StorageEntity;
import org.hibernate.Session;
import ui.controllers.tiles.order_management.OrdersProductsTable;
import ui.views.TileView;

import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

public class ChangeStorageViewController {
    public TableView<ChangeStorageTable> tableBrowseOrdersProducts;
    public TableColumn<ChangeStorageTable, Integer> idColumnProducts;
    public TableColumn<ChangeStorageTable, String>  manufacturerColumnProducts;
    public TableColumn<ChangeStorageTable, String>  nameColumnProducts;
    public TableColumn<ChangeStorageTable, Integer>  quantityProducts;
    public TableColumn<ChangeStorageTable, Void>  changeStorageLevelsColumn;
    private static final ObservableList<ChangeStorageTable> dataProducts = FXCollections.observableArrayList();

    @SuppressWarnings("unused")
    public void goBackProducts() {
        dataProducts.clear();
        TileView.initialize(LoginManager.getAccessLevel());
    }

    public void initialize() {
        createTable();
        runQuery();
    }

    public void insertProducts(ChangeStorageTable changeStorageTable) {
        Platform.runLater(() -> dataProducts.add(changeStorageTable));
    }

    private void runQuery() {
        new Thread(() -> {
            Session session = LoginManager.getSession();
            TypedQuery<StorageEntity> storageEntityTypedQuery = session.createQuery("from StorageEntity ", StorageEntity.class);
            List<StorageEntity> storageEntityList= storageEntityTypedQuery.getResultList();
            session.close();
            for (StorageEntity storageEntity : storageEntityList) {
                if (storageEntity.getAlbumViewEntity()!=null) {
                    insertProducts(new ChangeStorageTable(
                            storageEntity.getProductId(), storageEntity.getAlbumViewEntity().getName(), storageEntity.getAlbumViewEntity().getTitle(), storageEntity.getProductsAvailable()
                    ));
                }
                else if (storageEntity.getInstrumentViewEntity()!=null) {
                    insertProducts(new ChangeStorageTable(
                            storageEntity.getProductId(), storageEntity.getInstrumentViewEntity().getManufacturerName(), storageEntity.getInstrumentViewEntity().getInstrumentName(), storageEntity.getProductsAvailable()
                    ));
                }
                else if (storageEntity.getOtherViewEntity()!=null) {
                    insertProducts(new ChangeStorageTable(
                            storageEntity.getProductId(), storageEntity.getOtherViewEntity().getProducer(), storageEntity.getOtherViewEntity().getName(), storageEntity.getProductsAvailable()
                    ));
                }
            }
        }).start();
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
        quantityProducts.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        changeStorageLevelsColumn.setCellFactory(createButtonCellFactory());
    }

    public Callback<TableColumn<ChangeStorageTable, Void>, TableCell<ChangeStorageTable, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<ChangeStorageTable, Void>, TableCell<ChangeStorageTable, Void>>() {
            @Override
            public TableCell<ChangeStorageTable, Void> call(TableColumn<ChangeStorageTable, Void> param) {
                return new TableCell<ChangeStorageTable, Void>() {
                    private Button button = new Button("Change");

                    {
                        button.setOnAction((e) -> changeLevelsDialogOpen(dataProducts.get(getIndex())));
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

    private void changeLevelsDialogOpen(ChangeStorageTable changeStorageTable) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader().getResource("fxml/tiles/ChangeStorageDialog.fxml"));
            final Parent parent = fxmlLoader.load();
            ((ChangeStorageDialog) fxmlLoader.getController()).setLevel(changeStorageTable, tableBrowseOrdersProducts);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Products");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Unable to load manage storage levels view.");
            alert.showAndWait();
        }
    }
}
