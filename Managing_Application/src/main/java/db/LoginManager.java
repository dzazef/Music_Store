package db;

import model.AccessLevel;
import model.Payment;
import model.Status;
import model.TransactionDocument;
import model.entities.OrdersEntity;
import model.entities.UsersEntity;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

public class LoginManager {
    private static Session session;
    private static SessionFactoryManager loginSessionFactory;
    private static AccessLevel accessLevel;

    public static AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public static void connect() {
        loginSessionFactory = new SessionFactoryManager("admin", "admin");
        loginSessionFactory.buildSessionFactory();
        session = loginSessionFactory.getNewSession();
    }

    /**
     * Function returns access level for given username and password. If user does not exist returns null.
     * @param username username
     * @param password password
     * @return access level.
     */
    public static boolean checkUser(String username, String password) {
        TypedQuery<UsersEntity> query = session.
                createQuery("from UsersEntity where userId like (:username)", UsersEntity.class)
                .setParameter("username", username);
        List<UsersEntity> usersEntityList = query.getResultList();
        if (usersEntityList.size()>0) {
//            if (BCrypt.checkpw(password, usersEntityList.get(0).getPassword())) {
            if(password.equals(usersEntityList.get(0).getPassword())) {
                accessLevel = usersEntityList.get(0).getAccessLevel();
                //TODO
//                OrdersEntity ordersEntity = new OrdersEntity();
//                ordersEntity.setCurrentStatus(Status.cancelled);
//                ordersEntity.setCustomerAdress("1");
//                ordersEntity.setCustomerName("1");
//                ordersEntity.setDeliveryId(1);
//                ordersEntity.setPayment(Payment.bank_transfer);
//                ordersEntity.setPhoneNumber("123456789");
//                ordersEntity.setTransactionDocument(TransactionDocument.invoice);
//                session.beginTransaction();
//                session.save(ordersEntity);
//                session.getTransaction().commit();

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static void clean() {
        session.close();
        loginSessionFactory.closeFactory();
    }
}
