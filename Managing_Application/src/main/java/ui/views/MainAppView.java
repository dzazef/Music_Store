package ui.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.Managing_Application;

import java.io.IOException;

@SuppressWarnings("ConstantConditions")
public class MainAppView {
    public static void initialize(int id) {
        final Parent parent;
        try {
            if (id==2)
                parent = FXMLLoader.load(LoginView.class.getClassLoader().getResource("fxml/mainapp/MainAppView2.fxml"));
            else if (id==3)
                parent = FXMLLoader.load(LoginView.class.getClassLoader().getResource("fxml/mainapp/MainAppView3.fxml"));
            else if (id==4)
                parent = FXMLLoader.load(LoginView.class.getClassLoader().getResource("fxml/mainapp/MainAppView4.fxml"));
            else
                return;
            Stage stage = Managing_Application.getStage();
            stage.setScene(new Scene(parent));
            stage.setTitle("TODO xD");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load MainAppView");
        }

    }
}
