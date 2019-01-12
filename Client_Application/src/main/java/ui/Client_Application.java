package ui;//    compile group: 'com.sun.xl.bind', name: 'jaxb-impl', version: '2.3.1'


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.SessionFactoryManager;
import model.entities.DeliveryEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Objects;

@SuppressWarnings("Duplicates")
public class Client_Application extends Application {
    private static Stage stage;
    public static void main(String[] args) {
        launch(args);
        try {
//            SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
//                    .addAnnotatedClass(DeliveryEntity.class).buildSessionFactory();
//            Session session = factory.openSession();
//            DeliveryEntity deliveryEntity = new DeliveryEntity();
//            deliveryEntity.setDeliveryTime(10);
//            deliveryEntity.setName("kotgg");
//            deliveryEntity.setPrice(10f);
//            session.beginTransaction();
//            session.save(deliveryEntity);
//            session.getTransaction().commit();
//            session.close();
//            factory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//        primaryStage.setOnCloseRequest((event) -> SessionFactoryManager.closeFactory());
//        primaryStage.getScene().getWindow().setOnCloseRequest((event -> SessionFactoryManager.closeFactory()));
//        Configuration cfg = new Configuration();
//        cfg.configure();
//        System.setProperty("hibernate.connection.password","123");
////        System.setProperty("hibernate.connection.username","client");
//        cfg.setProperties(System.getProperties());
//        SessionFactory sessionFactory = cfg.buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        DeliveryEntity deliveryEntity = new DeliveryEntity();
//        deliveryEntity.setDeliveryTime(5);
//        deliveryEntity.setName("niewolnik z listem");
//        deliveryEntity.setPrice(10f);
//        session.beginTransaction();
//        session.save(deliveryEntity);
//        session.getTransaction().commit();
//        session.close();

    }

    @Override
    public void stop() {
        SessionFactoryManager.closeFactory();
        System.exit(0);
    }
}