package ui.controllers.tiles.shipments_management;

import db.LoginManager;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import model.Status;
import model.entities.OrdersEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ui.controllers.tiles.order_management.OrdersTable;
import ui.controllers.tiles.order_management.ViewController;

import javax.persistence.TypedQuery;
import java.util.List;

public class ShipmentManagementViewController extends ViewController {
    @Override
    public void runQuery() {
        data.clear();
        Session session = LoginManager.getSession();
        TypedQuery<OrdersEntity> completeOrdersQuery = session.
                createQuery("from OrdersEntity where currentStatus=(:par)", OrdersEntity.class).setParameter("par", Status.prepared_for_sending);
        List<OrdersEntity> ordersEntityList = completeOrdersQuery.getResultList();
        session.close();
        for (OrdersEntity ordersEntity : ordersEntityList) {
            data.add(new OrdersTable(ordersEntity));
        }
    }

    @Override
    public void initialize() {
        confirmColumn.setText("Shipment");
        createTableView();
        confirmColumn.setCellFactory(callCreateButtonCellFactory(false));
        runQuery();
    }

    @Override
    public Callback<TableColumn<OrdersTable, Void>, TableCell<OrdersTable, Void>> callCreateButtonCellFactory(boolean isItProducts) {
        return createButtonCellFactory(isItProducts, "Confirm");
    }

    @Override
    public void buttonHandle(OrdersTable ordersTable) {
        Session session = LoginManager.getSession();
        session.beginTransaction();
        Query query = session.
                createSQLQuery("CALL music_store.update_order_status((:orderId), (:newStatus), (:userId))")
                .setParameter("orderId", ordersTable.getOrderId())
                .setParameter("newStatus", 3)
                .setParameter("userId", LoginManager.getUsername());
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        data.removeAll(ordersTable);
    }

}
