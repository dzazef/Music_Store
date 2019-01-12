package ui.controllers.tiles.manage_orders;

import db.LoginManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entities.OrdersEntity;
import org.hibernate.Session;
import ui.controllers.tiles.order_management.OrdersTable;
import ui.controllers.tiles.order_management.ViewController;

import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("Duplicates")
public class ManageOrdersViewController extends ViewController {
    private static Stage stage;
    private static OrdersTable ordersTableToEdit;

    @Override
    public Callback<TableColumn<OrdersTable, Void>, TableCell<OrdersTable, Void>> callCreateButtonCellFactory(boolean isItProducts) {
        return createButtonCellFactory(isItProducts, "Status");
    }

    @Override
    public void buttonHandle(OrdersTable ordersTable) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("fxml/tiles/ChangeStatusManageProducts.fxml"));
            Parent root = loader.load();
            ChangeStatusManageController changeStatusManageController = loader.getController();
            ordersTableToEdit = ordersTable;
            changeStatusManageController.setInfo(ordersTable.getOrderId(), session);
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Status");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Unable to load manage status view.");
            alert.showAndWait();
        }
    }

    @Override
    public void runQuery() {
        data.clear();
        TypedQuery<OrdersEntity> completeOrdersQuery = session.
                createQuery("from OrdersEntity", OrdersEntity.class);
        List<OrdersEntity> ordersEntityList = completeOrdersQuery.getResultList();
        for (OrdersEntity ordersEntity : ordersEntityList) {
            data.add(new OrdersTable(ordersEntity));
        }
    }

    static void closeDialog(String status) {
        data.remove(ordersTableToEdit);
        ordersTableToEdit.setCurrentStatus(status);
        data.add(ordersTableToEdit);
        stage.close();
    }
}
