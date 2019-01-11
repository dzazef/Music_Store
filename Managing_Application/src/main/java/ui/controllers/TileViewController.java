package ui.controllers;

import db.LoginManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ui.Managing_Application;
import ui.views.LoginView;
import ui.controllers.tiles.BrowseOrdersViewController;

import java.io.IOException;

public class TileViewController {
    public static void handleBrowseOrders() {
        System.out.println("handleBrowseOrders");
        BrowseOrdersViewController.initialize();
    }

    public static void handleCompleteOrders() {
        System.out.println("handleCompleteOrders");
    }

    public static void handleChangeStorageLevels() {
        System.out.println("handleChangeStorageLevels");
    }

    public static void handleManageShipments() {
        System.out.println("handleManageShipments");
    }

    public static void handlePaymentConfirmation() {
        System.out.println("handlePaymentConfirmation");
    }

    public static void handleManageOrders() {
        System.out.println("handleManageOrders");
    }

    public static void handleManageProducts() {
        System.out.println("handleManageProducts");
    }

    public static void handleManageDeliveryMethods() {
        System.out.println("handleManageDeliveryMethods");
    }

    public static void handleManageUsers() {
        System.out.println("handleManageUsers");
        @SuppressWarnings("ConstantConditions") final Parent parent;
        try {
            parent = FXMLLoader
                    .load(LoginView.class.getClassLoader().getResource("fxml/tiles/ManageUsersView.fxml"));
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
    }

    public static void handleBackupAndRestore() {
        System.out.println("handleBackupAndRestore");
    }

    public static void handleLogOut() {
        System.out.println("handleLogOut");
        LoginManager.clean();
        LoginView.initialize();
    }

    public static void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
