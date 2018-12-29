package ui.controllers;

import javafx.fxml.FXML;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ui.db.SessionFactoryManager;
import ui.views.MainAppView;

public class LoginViewController {
    @FXML
    public void handleLogIn() {
        //TODO
//        SessionFactoryManager.buildSessionFactory();
//        Session session = SessionFactoryManager.getNewSession();
//        Query query = session.createQuery("from users");
        MainAppView.initialize(4);
    }
}
