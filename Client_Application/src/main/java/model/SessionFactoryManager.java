package model;

import model.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class SessionFactoryManager {
    private static SessionFactory sessionFactory;
    public static synchronized void buildSessionFactory() {
        if (sessionFactory == null)
            System.setProperty("hibernate.connection.password","client");
            System.setProperty("hibernate.connection.username","client");
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").
                addAnnotatedClass(AlbumsEntity.class).
                addAnnotatedClass(AlbumViewEntity.class).
                addAnnotatedClass(InstrumentViewEntity.class).
                addAnnotatedClass(OtherViewEntity.class).
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
    public static Session getNewSession() {
        buildSessionFactory();
        return sessionFactory.openSession();
    }
    public static synchronized void closeFactory() {
        if (sessionFactory != null)
            sessionFactory.close();
    }
}
