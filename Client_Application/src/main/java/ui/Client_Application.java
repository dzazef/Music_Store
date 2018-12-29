package ui;//    compile group: 'com.sun.xl.bind', name: 'jaxb-impl', version: '2.3.1'


import models.DeliveryEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("Duplicates")
public class Client_Application {
    public static void main(String[] args) {
        try {
            SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(DeliveryEntity.class).buildSessionFactory();
            Session session = factory.openSession();
            DeliveryEntity deliveryEntity = new DeliveryEntity();
            deliveryEntity.setDeliveryTime(10);
            deliveryEntity.setName("kotgg");
            deliveryEntity.setPrice(10f);
            session.beginTransaction();
            session.save(deliveryEntity);
            session.getTransaction().commit();
            session.close();
            factory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}