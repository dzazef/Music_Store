package ui.controllers.tiles.complete_orders;

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
public class CompleteOrdersViewController extends ViewController {

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
    @Override
    public void runQuery() {
        TypedQuery<OrdersEntity> completeOrdersQuery = session.
                createQuery("from OrdersEntity where currentStatus=(:par)", OrdersEntity.class).setParameter("par", Status.in_progress);
        List<OrdersEntity> ordersEntityList = completeOrdersQuery.getResultList();
        for (OrdersEntity ordersEntity : ordersEntityList) {
            data.add(new OrdersTable(ordersEntity));
        }
    }
}
