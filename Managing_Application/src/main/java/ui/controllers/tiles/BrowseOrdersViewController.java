package ui.controllers.tiles;

import db.LoginManager;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import ui.controllers.tiles.order_management.OrdersTable;
import ui.controllers.tiles.order_management.ViewController;

@SuppressWarnings("Duplicates")
public class BrowseOrdersViewController extends ViewController {

    @Override
    public void initialize() {
        createTableView();
        session = LoginManager.getSession();
        runQuery();
    }

    @Override
    public Callback<TableColumn<OrdersTable, Void>, TableCell<OrdersTable, Void>> callCreateButtonCellFactory(boolean isItProducts) {
        return createButtonCellFactory(isItProducts, null);
    }

    @Override
    public void buttonHandle(OrdersTable ordersTable) {

    }
}
