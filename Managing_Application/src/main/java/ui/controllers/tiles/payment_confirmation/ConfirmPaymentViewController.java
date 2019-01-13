package ui.controllers.tiles.payment_confirmation;

import db.LoginManager;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import model.Status;
import model.entities.OrdersEntity;
import org.hibernate.query.Query;
import ui.controllers.tiles.order_management.OrdersTable;
import ui.controllers.tiles.order_management.ViewController;

import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("Duplicates")
public class ConfirmPaymentViewController extends ViewController {
    @Override
    public void runQuery() {
        data.clear();
        TypedQuery<OrdersEntity> completeOrdersQuery = session.
                createQuery("from OrdersEntity where currentStatus=(:par)", OrdersEntity.class).setParameter("par", Status.waiting_for_payment);
        List<OrdersEntity> ordersEntityList = completeOrdersQuery.getResultList();
        for (OrdersEntity ordersEntity : ordersEntityList) {
            data.add(new OrdersTable(ordersEntity));
        }
    }

    @Override
    public void initialize() {
        confirmColumn.setText("Payment");
        createTableView();
        session = LoginManager.getSession();
        confirmColumn.setCellFactory(callCreateButtonCellFactory(false));
        runQuery();
    }

    @Override
    public Callback<TableColumn<OrdersTable, Void>, TableCell<OrdersTable, Void>> callCreateButtonCellFactory(boolean isItProducts) {
        return createButtonCellFactory(isItProducts, "Confirm");
    }

    @Override
    public void buttonHandle(OrdersTable ordersTable) {
        session.beginTransaction();
        Query query = session.
                createSQLQuery("CALL music_store.update_order_status((:orderId), (:newStatus), (:userId))")
                .setParameter("orderId", ordersTable.getOrderId())
                .setParameter("newStatus", 1)
                .setParameter("userId", LoginManager.getUsername());
        query.executeUpdate();
        session.getTransaction().commit();
        data.removeAll(ordersTable);
    }


}
