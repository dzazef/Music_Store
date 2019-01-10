package ui.controllers;

import db.LoginManager;
import ui.views.LoginView;
import ui.views.tiles.BrowseOrdersViewController;

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
}
