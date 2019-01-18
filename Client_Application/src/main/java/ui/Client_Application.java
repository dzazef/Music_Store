package ui;//    compile group: 'com.sun.xl.bind', name: 'jaxb-impl', version: '2.3.1'


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.SessionFactoryManager;

import java.util.Objects;

@SuppressWarnings("Duplicates")
public class Client_Application extends Application {
    private static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setMinHeight(500.0);
        primaryStage.setMinWidth(700.0);
        primaryStage.setTitle("Music Store Client Application");
        final Parent parent = FXMLLoader.load(Objects.requireNonNull(FXMLLoader.getDefaultClassLoader().getResource("fxml/WelcomeView.fxml")));
        primaryStage.setScene(new Scene(parent,700,500));
        primaryStage.getIcons().add(new Image(Client_Application.class.getResourceAsStream("../images/icon.png")));
        primaryStage.show();
        SessionFactoryManager.buildSessionFactory();
    }

    @Override
    public void stop() {
        SessionFactoryManager.closeFactory();
        System.exit(0);
    }
}