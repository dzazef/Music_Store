package ui.controllers;

import db.LoginManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.AccessLevel;
import ui.views.TileView;

public class LoginViewController {

    @FXML
    public TextField username;
    public PasswordField password;

    /**
     * Auto-run on view initialize.
     * Get a connection with database to check user's login, password and AccessLevel.
     */
    public void initialize() {
        LoginManager.connect();
    }

    /**
     * Method calling module that checks if given values are correct.
     */
    @FXML
    public void handleLogIn() {
        if (LoginManager.checkUser(username.getText(), password.getText())) {
            System.out.println("logowanie udane");
            AccessLevel acessLevel = LoginManager.getAccessLevel();
            LoginManager.clean();
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

    /**
     * ENTER handler.
     */
    @FXML
    public void onEnter() {
        handleLogIn();
    }
}
