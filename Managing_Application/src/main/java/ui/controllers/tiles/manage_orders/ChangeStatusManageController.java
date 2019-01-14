package ui.controllers.tiles.manage_orders;

import db.LoginManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import model.Status;
import org.hibernate.Session;
import org.hibernate.query.Query;

@SuppressWarnings("Duplicates")
public class ChangeStatusManageController {
    public ChoiceBox<Status> chooseStatus;
    private int orderId;
    private int tableId;

    public void initialize() {
        chooseStatus.setItems(FXCollections.observableArrayList(Status.values()));
        chooseStatus.getSelectionModel().selectFirst();
    }

    @FXML
    public void confirm() {
        int status = 0;
        status = chooseStatus.getValue().ordinal();
        Session session = LoginManager.getSession();
        session.beginTransaction();
        Query query = session.
                createSQLQuery("CALL music_store.update_order_status((:orderId), (:newStatus), (:userId))")
                .setParameter("orderId", orderId)
                .setParameter("newStatus", status)
                .setParameter("userId", LoginManager.getUsername());
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        ManageOrdersViewController.closeDialog(chooseStatus.getValue().toString(), tableId);
    }

    public void setInfo(int id, int tableId) {
        this.orderId = id;
        this.tableId = tableId;
    }
}