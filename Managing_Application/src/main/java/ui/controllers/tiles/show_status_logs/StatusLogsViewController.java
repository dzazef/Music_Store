package ui.controllers.tiles.show_status_logs;

import db.LoginManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import model.entities.StatusLogsEntity;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import ui.views.TileView;

import javax.persistence.Query;
import java.io.*;
import java.util.List;

public class StatusLogsViewController {
    public Label currentPageNumberLabel;
    public IntegerProperty currentPageNumber = new SimpleIntegerProperty(1);
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

    public void clearAllLogs(ActionEvent actionEvent) {
        if (showConfirmationAlert("Are you sure you want to delete ALL logs?\n" +
                "\nThis action cannot be undone!" +
                "\nYou can export logs into a file by 'EXPORT TO FILE' option.")) {
            Session session = LoginManager.getSession();
            session.beginTransaction();
            session.createQuery("DELETE FROM StatusLogsEntity").executeUpdate();
            session.getTransaction().commit();
            session.close();
            data.clear();
        }
    }

    public void exportLogsToFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV","*.csv"),
                new FileChooser.ExtensionFilter("TXT","*.txt"));
        fileChooser.setTitle("Save logs");
        File file = fileChooser.showSaveDialog(mainPane.getScene().getWindow());
        if (file != null) {
            try {
                Character delimiter = ';';
                StatusLogsEntity temporaryStatusLogsEntity;
                Session session = LoginManager.getSession();
                PrintWriter writer = new PrintWriter(file);
                ScrollableResults scrollableResults = session.createQuery("from StatusLogsEntity ").
                        setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
                while (scrollableResults.next()) {
                    for (Object obj : scrollableResults.get()) {
                        temporaryStatusLogsEntity = (StatusLogsEntity)obj;
                        writer.println(
                                temporaryStatusLogsEntity.getLogId()+delimiter.toString()+temporaryStatusLogsEntity.getOrderId()+delimiter+
                                temporaryStatusLogsEntity.getUserId()+delimiter+temporaryStatusLogsEntity.getDateTime()+delimiter+
                                temporaryStatusLogsEntity.getStatusOld()+delimiter+temporaryStatusLogsEntity.getStatusNew()+delimiter);
                        session.evict(temporaryStatusLogsEntity);
                    }
                }
                writer.println("NOTE: first value is previous value, second is new.");
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showErrorAlert(String mess) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mess);
        alert.showAndWait();
    }

    private boolean showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return !alert.showAndWait().get().getButtonData().isCancelButton();
    }
}
