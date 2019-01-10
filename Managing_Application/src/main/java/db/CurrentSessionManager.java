package db;

import model.AccessLevel;
import org.hibernate.Session;

/**
 * Class used when client's permission, login and password already checked to get a connection with database.
 */
public class CurrentSessionManager {
    private static SessionFactoryManager sessionFactoryManager;
    public static void initialize(AccessLevel accessLevel) {
        switch (accessLevel) {
            case storekeeper: {
                sessionFactoryManager = new SessionFactoryManager("storekeeper", "storekeeper");
                sessionFactoryManager.buildSessionFactory();
            }
            case storage_manager: {
                sessionFactoryManager = new SessionFactoryManager("storage_manager", "storage_manager");
                sessionFactoryManager.buildSessionFactory();
            }
            case manager: {
                sessionFactoryManager = new SessionFactoryManager("manager", "manager");
                sessionFactoryManager.buildSessionFactory();
            }
            case administrator: {
                sessionFactoryManager = new SessionFactoryManager("administrator", "administrator");
                sessionFactoryManager.buildSessionFactory();
            }
        }
    }

    public static Session getSession() {
        return sessionFactoryManager.getNewSession();
    }

    public static void clean() {
        sessionFactoryManager.closeFactory();
        sessionFactoryManager = null;
    }
}
