package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LoginViewController {

    public Button loginButton;
    public VBox loginPane;

    public void initialize() {
        loginButton.prefWidthProperty().bind(loginPane.widthProperty().divide(5));
    }

    @FXML
    public void handleLogIn() {
        //TODO
//        SessionFactoryManager.buildSessionFactory();
//        Session session = SessionFactoryManager.getNewSession();
//        Query query = session.createQuery("from users");
//        MainAppView.initialize(4);
    }
}
