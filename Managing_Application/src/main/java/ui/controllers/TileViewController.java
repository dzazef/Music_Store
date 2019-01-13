package ui.controllers;

import db.LoginManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ui.Managing_Application;
import ui.controllers.tiles.browse_orders.BrowseOrdersViewController;
import ui.controllers.tiles.complete_orders.CompleteOrdersViewController;
import ui.controllers.tiles.manage_orders.ManageOrdersViewController;
import ui.controllers.tiles.payment_confirmation.ConfirmPaymentViewController;
import ui.views.LoginView;

import java.io.IOException;
import java.util.Objects;

public class TileViewController {
    public static void handleBrowseOrders() {
        System.out.println("handleBrowseOrders");
        try {
            FXMLLoader loader =  new FXMLLoader(TileViewController.class.getClassLoader().getResource("fxml/tiles/BrowseOrders.fxml"));
            Parent root = loader.load();
            Stage stage = Managing_Application.getStage();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Unable to load manage browse orders view.");
        }
    }

    public static void handleCompleteOrders() {
        System.out.println("handleCompleteOrders");
        try {
            FXMLLoader loader =  new FXMLLoader(TileViewController.class.getClassLoader().getResource("fxml/tiles/CompleteOrdersView.fxml"));
            loader.setController(new CompleteOrdersViewController());
            Parent root = loader.load();
            Stage stage = Managing_Application.getStage();
            stage.setScene(new Scene(root));
            stage.setTitle("Complete orders");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Unable to load manage complete orders view.");
        }
    }

    public static void handleChangeStorageLevels() {
        System.out.println("handleChangeStorageLevels");
    }

    public static void handleManageShipments() {
        System.out.println("handleManageShipments");
    }

    public static void handlePaymentConfirmation() {
        System.out.println("handlePaymentConfirmation");
        try {
            FXMLLoader loader =  new FXMLLoader(TileViewController.class.getClassLoader().getResource("fxml/tiles/CompleteOrdersView.fxml"));
            loader.setController(new ConfirmPaymentViewController());
            Parent root = loader.load();
            Stage stage = Managing_Application.getStage();
            stage.setScene(new Scene(root));
            stage.setTitle("Payment confirmation");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Unable to load manage confirm payment view.");
        }
    }

    public static void handleManageOrders() {
        System.out.println("handleManageOrders");
        try {
            FXMLLoader loader =  new FXMLLoader(TileViewController.class.getClassLoader().getResource("fxml/tiles/CompleteOrdersView.fxml"));
            loader.setController(new ManageOrdersViewController());
            Parent root = loader.load();
            Stage stage = Managing_Application.getStage();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Unable to load manage complete orders view.");
        }
    }

    public static void handleManageProducts() {
        System.out.println("handleManageProducts");
    }

    public static void handleManageDeliveryMethods() {
        System.out.println("handleManageDeliveryMethods");
        @SuppressWarnings("ConstantConditions") final Parent parent;
        try {
            parent = FXMLLoader
                    .load(Objects.requireNonNull(LoginView.class.getClassLoader().getResource("fxml/tiles/DeliveryManagementView.fxml")));
            Stage stage = Managing_Application.getStage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Manage delivery method");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Unable to load delivery management view.");
        }
    }

    public static void handleManageUsers() {
        System.out.println("handleManageUsers");
        @SuppressWarnings("ConstantConditions") final Parent parent;
        try {
            parent = FXMLLoader
                    .load(Objects.requireNonNull(LoginView.class.getClassLoader().getResource("fxml/tiles/ManageUsersView.fxml")));
            Stage stage = Managing_Application.getStage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Manage users");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Unable to load manage users view.");
        }
    }

    public static void handleShowLogs() {
        System.out.println("handleShowLogs");
        @SuppressWarnings("ConstantConditions") final Parent parent;
        try {
            parent = FXMLLoader
                    .load(Objects.requireNonNull(LoginView.class.getClassLoader().getResource("fxml/tiles/StatusLogsView.fxml")));
            Stage stage = Managing_Application.getStage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Status Logs");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Unable to load status logs view.");
        }
    }

    public static void handleBackupAndRestore() {
        System.out.println("handleBackupAndRestore");
        final Parent parent;
        try {
            parent = FXMLLoader
                    .load(Objects.requireNonNull(LoginView.class.getClassLoader().getResource("fxml/tiles/BackupRestore.fxml")));
            Stage stage = Managing_Application.getStage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Backup restore");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Unable to load backup&restore view.");
        }
    }

    public static void handleLogOut() {
        System.out.println("handleLogOut");
        LoginManager.clean();
        LoginView.initialize();
    }

    private static void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
