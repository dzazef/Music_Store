package ui.views.tiles;

import db.LoginManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.Managing_Application;
import ui.views.LoginView;
import ui.views.TileView;

import java.io.IOException;

public class BrowseOrdersViewController {
    public static void initialize() {
        try {
            @SuppressWarnings("ConstantConditions") final Parent parent = FXMLLoader.load(LoginView.class.getClassLoader().getResource("fxml/tiles/BrowseOrders.fxml"));
            Stage stage = Managing_Application.getStage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Browse orders");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while initializing LoginView");
        }
    }
    @FXML
    public void goBack() {
        TileView.initialize(LoginManager.getAccessLevel());
    }
}
