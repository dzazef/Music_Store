package ui;

import db.SessionFactoryManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.entities.DeliveryEntity;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import ui.views.LoginView;

public class Managing_Application extends Application {
    private static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SessionFactoryManager sessionFactoryManager = new SessionFactoryManager("admin","admin");
        sessionFactoryManager.buildSessionFactory();
        Session session = sessionFactoryManager.getNewSession();
        session.beginTransaction();
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.setDeliveryTime(5);
        deliveryEntity.setName("przesyłka ekomoniczna");
        deliveryEntity.setPrice(4.99);
        session.save(deliveryEntity);
        session.getTransaction().commit();

//        stage = primaryStage;
//        primaryStage.getIcons().add(new Image(Managing_Application.class.getResourceAsStream("../graphics/icon.png")));
//        LoginView.initialize();
//        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}