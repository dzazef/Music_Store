package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.views.LoginView;

public class Managing_Application extends Application {
    private static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.getIcons().add(new Image(Managing_Application.class.getResourceAsStream("../graphics/icon.png")));
        LoginView.initialize();
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}