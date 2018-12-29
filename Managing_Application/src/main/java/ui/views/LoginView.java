package ui.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.Managing_Application;

import java.io.IOException;

public class LoginView {
    public static void initialize() {
        try {
            @SuppressWarnings("ConstantConditions") final Parent parent = FXMLLoader.load(LoginView.class.getClassLoader().getResource("fxml/LoginView.fxml"));
            Stage stage = Managing_Application.getStage();
            stage.setScene(new Scene(parent));
            stage.setTitle("TODO xD");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while initializing LoginView");
        }
    }
}
