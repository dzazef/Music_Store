package db;

import model.AccessLevel;
import model.entities.UsersEntity;
import org.hibernate.Session;
import sh.LoadAlbums;

import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("Duplicates")
public class LoginManager {
    private static Session loginSession;
    private static SessionFactoryManager currentSessionFactoryManager;
    private static AccessLevel accessLevel;

    public static void connectUserCheck() {
        currentSessionFactoryManager = new SessionFactoryManager("root", "sqlpass.");
        currentSessionFactoryManager.buildSessionFactory();
        loginSession = currentSessionFactoryManager.getNewSession();
    }

    /**
     * Function returns access level for given username and password. If user does not exist returns null.
     * @param username username
     * @param password password
     * @return access level.
     */
    public static boolean checkUser(String username, String password) {
        TypedQuery<UsersEntity> query = loginSession.
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

    /**
     * Closes the loginSession if exists, and closes the session factory.
     */
    public static void clean() {
        if (loginSession!=null) loginSession.close();
        currentSessionFactoryManager.closeFactory();
    }

    public static AccessLevel getAccessLevel() {
        return accessLevel;
    }

    /**
     * Connect as current user. Used after correct log in.
     */
    public static void connectCurrentSession() {
        if (loginSession!=null) loginSession.close();
        switch (accessLevel) {
            case storekeeper: {
                currentSessionFactoryManager = new SessionFactoryManager("storekeeper", "storekeeper");
                currentSessionFactoryManager.buildSessionFactory();
            }
            case storage_manager: {
                currentSessionFactoryManager = new SessionFactoryManager("storage_manager", "storage_manager");
                currentSessionFactoryManager.buildSessionFactory();
            }
            case manager: {
                currentSessionFactoryManager = new SessionFactoryManager("manager", "manager");
                currentSessionFactoryManager.buildSessionFactory();
            }
            case administrator: {
                currentSessionFactoryManager = new SessionFactoryManager("admin", "admin");
                currentSessionFactoryManager.buildSessionFactory();
            }
        }
    }

    public static Session getSession() {
        return currentSessionFactoryManager.getNewSession();
    }
}
