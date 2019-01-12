package ui.controllers.tiles.manage_orders;

import db.LoginManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import org.hibernate.Session;
import org.hibernate.query.Query;

@SuppressWarnings("Duplicates")
public class ChangeStatusManageController {
    public ChoiceBox<String> chooseStatus;
    private int id;
    private Session session;

    public void initialize() {
        chooseStatus.setItems(FXCollections.observableArrayList(
                "in_progress", "prepared_for_sending", "sent", "delivered", "returned", "cancelled")
        );
        chooseStatus.getSelectionModel().selectFirst();
    }

    @FXML
    public void confirm() {
        int status = 0;
        switch (chooseStatus.getValue()) {
            case "in_progress" : status=0; break;
            case "prepared_for_sending" : status=1; break;
            case "sent" : status=2; break;
            case "delivered" : status=3; break;
            case "returned" : status=4; break;
            case "cancelled" : status=5;
        }
        session.beginTransaction();
        Query query = session.
                createSQLQuery("CALL music_store.update_order_status((:orderId), (:newStatus), (:userId))")
                .setParameter("orderId", id)
                .setParameter("newStatus", status)
                .setParameter("userId", LoginManager.getUsername());
        query.executeUpdate();
        session.getTransaction().commit();
        ManageOrdersViewController.closeDialog(chooseStatus.getValue());
    }

    public void setInfo(int id, Session session) {
        this.id = id;
        this.session = session;
    }
}