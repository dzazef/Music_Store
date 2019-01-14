package ui.controllers.tiles.manage_orders;

import db.LoginManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Status;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ui.controllers.tiles.order_management.OrdersTable;

@SuppressWarnings("Duplicates")
public class ChangeStatusManageController {
    public ChoiceBox<Status> chooseStatus;
    private OrdersTable ordersTable;
    private TableView tableView;

    public void initialize() {
        chooseStatus.setItems(FXCollections.observableArrayList(Status.values()));
        chooseStatus.getSelectionModel().selectFirst();
    }

    @FXML
    public void confirm() {
        int status = 0;
        status = chooseStatus.getValue().ordinal();
        Session session = LoginManager.getSession();
        try{
            session.beginTransaction();
            Query query = session.
                    createSQLQuery("CALL music_store.update_order_status((:orderId), (:newStatus), (:userId))")
                    .setParameter("orderId", ordersTable.getOrderId())
                    .setParameter("newStatus", status)
                    .setParameter("userId", LoginManager.getUsername());
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Status for order "+ordersTable.getOrderId()+" is already set to "+chooseStatus.getValue().toString());
            alert.showAndWait();
        }

        session.close();
        ordersTable.setCurrentStatus(chooseStatus.getValue().toString());
        tableView.refresh();
        ((Stage)chooseStatus.getScene().getWindow()).close();
    }

    void setInfo(OrdersTable ordersTable, TableView tableView) {
        this.ordersTable = ordersTable;
        this.tableView = tableView;
    }
}