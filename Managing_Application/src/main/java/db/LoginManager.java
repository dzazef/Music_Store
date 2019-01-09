package db;

import model.AccessLevel;
import model.entities.UsersEntity;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

public class LoginManager {
    private Session session;
    private SessionFactoryManager loginSessionFactory;
    private AccessLevel accessLevel;
    public void connect() {
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
    public boolean checkUser(String username, String password) {
        TypedQuery<UsersEntity> query = session.
                createQuery("from UsersEntity where userId like (:username) and password like (:password)", UsersEntity.class)
                .setParameter("username", username).setParameter("password", password);
        List<UsersEntity> usersEntityList = query.getResultList();
        if (usersEntityList.size()>0) {
            accessLevel = usersEntityList.get(0).getAccessLevel();
            return true;
        } else {
            return false;
        }
    }
    public void clean() {
        session.close();
        loginSessionFactory.closeFactory();
    }
}
