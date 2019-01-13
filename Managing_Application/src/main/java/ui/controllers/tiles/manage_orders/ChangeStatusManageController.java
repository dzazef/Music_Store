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
    private int id;
    private Session session;

    public void initialize() {
        chooseStatus.setItems(FXCollections.observableArrayList(Status.values()));
        chooseStatus.getSelectionModel().selectFirst();
    }

    @FXML
    public void confirm() {
        int status = 0;
        status = chooseStatus.getValue().ordinal();
        session.beginTransaction();
        Query query = session.
                createSQLQuery("CALL music_store.update_order_status((:orderId), (:newStatus), (:userId))")
                .setParameter("orderId", id)
                .setParameter("newStatus", status)
                .setParameter("userId", LoginManager.getUsername());
        query.executeUpdate();
        session.getTransaction().commit();
        ManageOrdersViewController.closeDialog(chooseStatus.getValue().toString());
    }

    public void setInfo(int id, Session session) {
        this.id = id;
        this.session = session;
    }
}