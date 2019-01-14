package ui.controllers.tiles.manage_products;

import db.LoginManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.ProductCategory;
import model.entities.AlbumViewEntity;
import model.entities.InstrumentViewEntity;
import model.entities.OtherViewEntity;
import org.hibernate.Session;
import ui.views.TileView;

import javax.persistence.Query;
import java.io.IOException;

public class ManageProductsController {
    public BorderPane mainPane;
    private Class currentTypeInData;
    private IntegerProperty currentPageNumber = new SimpleIntegerProperty(1);
    public Button albumsButton;
    public Button instrumentsButton;
    public Button otherButton;
    public Button previousPageButton;
    public Label currentPageNumberLabel;
    public Button nextPageButton;
    private ObservableList<Object> data = FXCollections.observableArrayList();
//    private ObservableList<AlbumViewEntity> dataAlbums = FXCollections.observableArrayList();
//    private ObservableList<AlbumViewEntity> dataInstruments = FXCollections.observableArrayList();
//    private ObservableList<AlbumViewEntity> dataOthers = FXCollections.observableArrayList();
    public TableView tableView;
    public TilePane toolBox;
    public Button addingAlbumButton;
    public Button addingInstrumentButton;
    public Button addingOtherProductButton;
    public VBox mainVBox;
    private final int maxLogCountOnOnePage = 40;

    public void initialize() {
        currentPageNumberLabel.textProperty().bind(currentPageNumber.asString());
        previousPageButton.setVisible(false);
        tableView.setItems(data);
    }

    public void loadAddNewAlbum(ActionEvent actionEvent) {
        try {
            mainPane.getBottom().setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/tiles/AddingProductView.fxml"));
            Node center = mainPane.getCenter();
            mainPane.setCenter(loader.load());
            AddingProductController controller = loader.getController();
            controller.setProductCategory(ProductCategory.album);
            controller.setReturnData(mainPane,center);
            controller.initialize();
        } catch (IOException e) {
            showErrorAlert("Unable to load adding product panel.");
            e.printStackTrace();
        }
    }

    public void loadAddNewInstrument(ActionEvent actionEvent) {
        try {
            mainPane.getBottom().setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/tiles/AddingProductView.fxml"));
            mainPane.setCenter(loader.load());
            AddingProductController controller = loader.getController();
            controller.setProductCategory(ProductCategory.instrument);
            controller.initialize();
        } catch (IOException e) {
            showErrorAlert("Unable to load adding product panel.");
            e.printStackTrace();
        }
    }

    public void loadAddNewOtherProduct(ActionEvent actionEvent) {
        try {
            mainPane.getBottom().setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/tiles/AddingProductView.fxml"));
            mainPane.setCenter(loader.load());
            AddingProductController controller = loader.getController();
            controller.setProductCategory(ProductCategory.other);
            controller.initialize();
        } catch (IOException e) {
            showErrorAlert("Unable to load adding product panel.");
            e.printStackTrace();
        }
    }

    public void showOthers(ActionEvent actionEvent) {
        currentTypeInData = OtherViewEntity.class;
        setCurrentPageToOne();
        data.addAll(FilterProductFunctions.filterOther(null));
        goToNextPage(1, OtherViewEntity.class);
        addColumnsForOthers();
    }

    private void setCurrentPageToOne() {
        currentPageNumber.setValue(1);
        previousPageButton.setVisible(false);
    }


    public void showInstruments(ActionEvent actionEvent) {
        currentTypeInData = InstrumentViewEntity.class;
        setCurrentPageToOne();
        data.addAll(FilterProductFunctions.filterInstruments(null));
        goToNextPage(1, InstrumentViewEntity.class);
        addColumnsForInstrument();
    }


    public void showAlbums(ActionEvent actionEvent) {
        currentTypeInData = AlbumViewEntity.class;
        setCurrentPageToOne();
        data.addAll(FilterProductFunctions.filterAlbums(null));
        goToNextPage(1, AlbumViewEntity.class);
        addColumnsForAlbum();
    }

    private void addColumnsForAlbum() {
        tableView.getColumns().clear();
        TableColumn column0 = new TableColumn<AlbumViewEntity, String>("ID");
        column0.setCellValueFactory(new PropertyValueFactory<>("productId"));
        TableColumn column1 = new TableColumn<AlbumViewEntity, String>("TITLE");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn column2 = new TableColumn<AlbumViewEntity, String>("ARTIST");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn column3 = new TableColumn<AlbumViewEntity, String>("PRICE");
        column3.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView.getColumns().addAll(column0, column1, column2, column3);
    }


    private void addColumnsForOthers() {
        tableView.getColumns().clear();
        TableColumn column0 = new TableColumn<OtherViewEntity, String>("ID");
        column0.setCellValueFactory(new PropertyValueFactory<>("productId"));
        TableColumn column1 = new TableColumn<OtherViewEntity, String>("CATEGORY");
        column1.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableColumn column2 = new TableColumn<OtherViewEntity, String>("NAME");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn column3 = new TableColumn<OtherViewEntity, String>("PRICE");
        column3.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn column4 = new TableColumn<OtherViewEntity, String>("MANUFACTURER");
        column4.setCellValueFactory(new PropertyValueFactory<>("producer"));
        TableColumn column5 = new TableColumn<OtherViewEntity, String>("TYPE");
        column5.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableView.getColumns().addAll(column0, column1, column2, column4, column3, column5);
    }

    private void addColumnsForInstrument() {
        tableView.getColumns().clear();
        TableColumn column0 = new TableColumn<OtherViewEntity, String>("ID");
        column0.setCellValueFactory(new PropertyValueFactory<>("productId"));
        TableColumn column2 = new TableColumn<OtherViewEntity, String>("NAME");
        column2.setCellValueFactory(new PropertyValueFactory<>("instrumentName"));
        TableColumn column3 = new TableColumn<OtherViewEntity, String>("PRICE");
        column3.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn column4 = new TableColumn<OtherViewEntity, String>("MANUFACTURER");
        column4.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
        TableColumn column5 = new TableColumn<OtherViewEntity, String>("TYPE");
        column5.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableView.getColumns().addAll(column0, column2, column4, column3, column5);
    }

    private void goToNextPage(int pageNumber, Class class_) {
        Session session = LoginManager.getSession();
        Query query = session.createQuery("from " + class_.getName());
        query.setMaxResults(maxLogCountOnOnePage);
        query.setFirstResult((pageNumber - 1) * maxLogCountOnOnePage);
        data.clear();
        data.addAll(query.getResultList());
    }

    @FXML
    public void goBack() {
        TileView.initialize(LoginManager.getAccessLevel());
    }

    public void previousPageClicked(ActionEvent actionEvent) {
        currentPageNumber.setValue(currentPageNumber.getValue() - 1);
        goToNextPage(currentPageNumber.getValue(), currentTypeInData);
        if (currentPageNumber.getValue().equals(1)) {
            previousPageButton.setVisible(false);
        }
    }

    public void nextPageClicked(ActionEvent actionEvent) {
        currentPageNumber.setValue(currentPageNumber.getValue() + 1);
        goToNextPage(currentPageNumber.getValue(), currentTypeInData);
        if (currentPageNumber.getValue() >= 2) {
            previousPageButton.setVisible(true);

        }
    }

    private void showErrorAlert(String mess) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mess);
        alert.showAndWait();
    }
}
