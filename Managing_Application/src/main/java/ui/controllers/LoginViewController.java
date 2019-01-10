package ui.controllers;

import db.LoginManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.AccessLevel;
import ui.views.TileView;

public class LoginViewController {

    @FXML
    public Button loginButton;
    public VBox loginPane;
    public TextField username;
    public PasswordField password;

    public LoginManager loginManager;

    public void initialize() {
        loginButton.prefWidthProperty().bind(loginPane.widthProperty().divide(5));
        loginManager  = new LoginManager();
        loginManager.connect();
    }

    @FXML
    public void handleLogIn() {
        if (loginManager.checkUser(username.getText(), password.getText())) {
            System.out.println("logowanie udane");
            AccessLevel acessLevel = loginManager.getAccessLevel();
            loginManager.clean();
            TileView.initialize(acessLevel);
        } else {
            System.out.println("logowanie nie udane");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong username or password");
            alert.setHeaderText(null);
            alert.setContentText("Wrong username or password. Try again!");
            alert.showAndWait();
        }
    }
}
