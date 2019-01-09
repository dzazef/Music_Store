package db;

import model.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class SessionFactoryManager {
    private SessionFactory sessionFactory;
    private String username;
    private String password;

    public SessionFactoryManager(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void buildSessionFactory() {
        if (sessionFactory==null) {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            System.setProperty("hibernate.connection.password",password);
            System.setProperty("hibernate.connection.username",username);
            cfg.setProperties(System.getProperties());
            sessionFactory = cfg.
                    addAnnotatedClass(AlbumsEntity.class).
                    addAnnotatedClass(ArtistsEntity.class).
                    addAnnotatedClass(DeliveryEntity.class).
                    addAnnotatedClass(InstrumentManufacturersEntity.class).
                    addAnnotatedClass(InstrumentsEntity.class).
                    addAnnotatedClass(OrdersEntity.class).
                    addAnnotatedClass(OrdersProductsEntity.class).
                    addAnnotatedClass(DeliveryEntity.class).
                    addAnnotatedClass(OtherEntity.class).
                    addAnnotatedClass(ProductsEntity.class).
                    addAnnotatedClass(StatusLogsEntity.class).
                    addAnnotatedClass(StorageEntity.class).
                    addAnnotatedClass(UsersEntity.class).
                    buildSessionFactory();
        }
    }
    public Session getNewSession() {
        return sessionFactory.openSession();
    }
    public void closeFactory() {
        sessionFactory.close();
    }
}
