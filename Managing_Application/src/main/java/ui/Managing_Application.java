package ui;

import db.LoginManager;
import javafx.application.Application;
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
//        SessionFactoryManager sessionFactoryManager = new SessionFactoryManager("admin","admin");
//        sessionFactoryManager.buildSessionFactory();
//        Session session = sessionFactoryManager.getNewSession();
//        session.beginTransaction();
//        UsersEntity usersEntity = new UsersEntity();
//        usersEntity.setAccessLevel(AccessLevel.administrator);
//        usersEntity.setUserId("halina2");
//        usersEntity.setPassword("halina2123");
//        session.save(usersEntity);
//        session.getTransaction().commit();
//        session.close();
//        sessionFactoryManager.closeFactory();
//
        stage = primaryStage;
        primaryStage.getIcons().add(new Image(ui.Managing_Application.class.getResourceAsStream("../graphics/icon.png")));
        LoginView.initialize();
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}