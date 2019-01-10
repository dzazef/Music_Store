package db;

import model.AccessLevel;
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
