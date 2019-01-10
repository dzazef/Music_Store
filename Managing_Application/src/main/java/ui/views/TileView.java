package ui.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.AccessLevel;
import ui.Managing_Application;
import ui.controllers.TileViewController;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class TileView {
    private static TilePane tilePane;
    private static Scene scene;

    /**
     * Method creating TileView.
     * @param accessLevel Access Level of current user.
     */
    public static void initialize(AccessLevel accessLevel) {
        tilePane = new TilePane();
        tilePane.setHgap(10);
        tilePane.setVgap(10);
        tilePane.setOpaqueInsets(new Insets(50, 50, 50, 50));
        scene = new Scene(tilePane, 600, 600);
        scene.getStylesheets().add("css/style.css");
        Stage stage = Managing_Application.getStage();
        stage.setScene(scene);
        stage.setTitle("Choose module");

        tilePane.getChildren().addAll(createButtons(accessLevel));
    }

    /**
     * Generating tiles.
     * @param accessLevel Access Level of current user
     * @return list of buttons (tiles)
     */
    private static List<Button> createButtons(AccessLevel accessLevel) {
        int id = 0;
        switch (accessLevel) {
            case storage_manager: id=1; break;
            case manager: id=2; break;
            case administrator: id=3; break;
        }
        List<Button> buttonList = new ArrayList<>();

        buttonList.add(makeButton("Browse orders", e -> TileViewController.handleBrowseOrders()));
        buttonList.add(makeButton("Complete orders", e -> TileViewController.handleCompleteOrders()));
        if (id>=1) buttonList.add(makeButton("Change storage levels", e -> TileViewController.handleChangeStorageLevels()));
        if (id>=1) buttonList.add(makeButton("Manage shipments", e -> TileViewController.handleManageShipments()));
        if (id>=2) buttonList.add(makeButton("Payment confirmation", e -> TileViewController.handlePaymentConfirmation()));
        if (id>=2) buttonList.add(makeButton("Manage orders", e -> TileViewController.handleManageOrders()));
        if (id>=2) buttonList.add(makeButton("Manage products", e -> TileViewController.handleManageProducts()));
        if (id>=2) buttonList.add(makeButton("Manage delivery methods", e -> TileViewController.handleManageDeliveryMethods()));
        if (id>=3) buttonList.add(makeButton("Manage users", e -> TileViewController.handleManageUsers()));
        if (id>=3) buttonList.add(makeButton("Show logs", e -> TileViewController.handleShowLogs()));
        if (id>=3) buttonList.add(makeButton("Backup&restore", e -> TileViewController.handleBackupAndRestore()));
        buttonList.add(makeButton("Log out", e -> TileViewController.handleLogOut()));
        return buttonList;
    }

    /**
     * Method creating single button.
     * @param value button text
     * @param event event that is supposed to happen on button click.
     * @return
     */
    private static Button makeButton(String value, EventHandler<ActionEvent> event) {
        Button button = new Button(value);
        button.setOnAction(event);
        button.setId("tile");
        return button;
    }
}
