package ui.controllers;

import db.LoginManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.Status;
import model.entities.StatusLogsEntity;
import model.entities.UsersEntity;
import org.hibernate.Session;
import ui.views.TileView;

import javax.persistence.Query;
import java.util.List;

public class StatusLogsViewController {
    public Label currentPageNumberLabel;
    public IntegerProperty currentPageNumber = new SimpleIntegerProperty(1);
    //private int currentPageNumber = 1;
    private final ObservableList<StatusLogsEntity> data = FXCollections.observableArrayList();
    private final int maxLogCountOnOnePage = 70;
    public TableView tableView;
    public TableColumn orderColumn;
    public TableColumn userColumn;
    public TableColumn timeColumn;
    public TableColumn oldStatusColumn;
    public TableColumn newStatusColumn;
    public BorderPane mainPane;
    public Button clearLogsButton;
    public Button exportLogsToFileButton;
    public Button nextPageButton;
    public Button previousPageButton;

    public void initialize() {
        currentPageNumberLabel.textProperty().bind(currentPageNumber.asString());
        tableView.prefWidthProperty().bind(mainPane.widthProperty());
        orderColumn.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        userColumn.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        timeColumn.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        oldStatusColumn.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        newStatusColumn.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        previousPageButton.setVisible(false);
        orderColumn.setCellValueFactory(new PropertyValueFactory<StatusLogsEntity, String>("orderId"));
        userColumn.setCellValueFactory(new PropertyValueFactory<StatusLogsEntity, String>("userId"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<StatusLogsEntity, String>("dateTime"));
        oldStatusColumn.setCellValueFactory(new PropertyValueFactory<StatusLogsEntity, String>("statusOld"));
        newStatusColumn.setCellValueFactory(new PropertyValueFactory<StatusLogsEntity, String>("statusNew"));
        tableView.setItems(data);
        goToNextPage(currentPageNumber.getValue());
    }

    public void goToNextPage(int pageNumber) {
        Session session = LoginManager.getSession();
        Query query = session.createQuery("from StatusLogsEntity ");
        query.setMaxResults(maxLogCountOnOnePage);
        query.setFirstResult((pageNumber-1)*maxLogCountOnOnePage);
        data.clear();
        data.addAll((List<StatusLogsEntity>)query.getResultList());
        //currentPageNumber++;
    }
    @FXML
    public void goBack() {
        TileView.initialize(LoginManager.getAccessLevel());
    }


    public void previousPageClicked(ActionEvent actionEvent) {
        currentPageNumber.setValue(currentPageNumber.getValue()-1);
        goToNextPage(currentPageNumber.getValue());
        if (currentPageNumber.getValue().equals(1)) {
            previousPageButton.setVisible(false);
        }
    }

    public void nextPageClicked(ActionEvent actionEvent) {
        currentPageNumber.setValue(currentPageNumber.getValue()+1);
        goToNextPage(currentPageNumber.getValue());
        if (currentPageNumber.getValue() >= 2) {
            previousPageButton.setVisible(true);

        }
    }
}
