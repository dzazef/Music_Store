package ui.controllers.tiles.manage_users;

import db.LoginManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import model.AccessLevel;
import model.entities.UsersEntity;
import org.hibernate.Session;
import ui.views.TileView;

import javax.persistence.OptimisticLockException;
import javax.persistence.Query;
import java.util.List;

public class ManageUsersController {
    private final ObservableList<UsersEntity> data = FXCollections.observableArrayList();
    public TableColumn passwordColumn;
    public TableColumn loginColumn;
    public TableColumn accessLevelColumn;
    public BorderPane mainPane;
    public TableView tableView;
    public Button addUserButton;
    public TableColumn removeButtonsColumn;
    public ChoiceBox<AccessLevel> accessLevelChoiceBox;
    public TextField loginTextField;
    public PasswordField passwordField;
    public Button removeButton = new Button("REMOVE");

    public void initialize() {

        tableView.prefWidthProperty().bind(mainPane.widthProperty().divide(1.5));
        passwordColumn.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        loginColumn.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        accessLevelColumn.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        removeButtonsColumn.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        accessLevelChoiceBox.getItems().addAll(AccessLevel.values());
        Session session = LoginManager.getSession();
        Query query = session.createQuery("FROM UsersEntity ");
        data.addAll(query.getResultList());
        tableView.setItems(data);
        session.close();
        //tableView.setEditable(true);
        loginColumn.setCellValueFactory(new PropertyValueFactory<UsersEntity, String>("userId"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<UsersEntity, String>("password"));
        accessLevelColumn.setCellValueFactory(new PropertyValueFactory<UsersEntity, String>("accessLevel"));
        removeButtonsColumn.setCellFactory(createButtonCellFactory());
//
//        UsersEntity usersEntity = new UsersEntity();
//        usersEntity.setPassword("pies");
//        usersEntity.setUserId("Tanja");
//        usersEntity.setAccessLevel(AccessLevel.storekeeper);
//        List<UsersEntity> users = new LinkedList<>();
//        users.add(usersEntity);
//        showUsersInTable(users);
    }

    private Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>>() {
            @Override
            public TableCell<UsersEntity, Void> call(TableColumn<UsersEntity, Void> param) {
                return new TableCell<UsersEntity, Void>() {
                    private Button button = new Button("REMOVE");

                    {
                        setAlignment(Pos.CENTER);
                        button.setOnAction((event) -> {
                            removeUser(data.get(getIndex()));});
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            }
        };
    }

    private void removeUser(UsersEntity user) {
        if (showConfirmationAlert("Are you sure you want to remove user "+
                user.getUserId()+"?\nThis cannot be undone!")) {
            //TODO handle deleting oneself
            try (Session session = LoginManager.getSession()) {
                session.beginTransaction();
                session.remove(user);
                session.getTransaction().commit();
                data.remove(user);
            } catch (OptimisticLockException exception) {
                showErrorAlert("Unable to remove user '"+user.getUserId()+"'.\nUser is present in the logs.\n" +
                        "To remove this user clean the logs.");
            }
        }
    }

    private void showUsersInTable(List<UsersEntity> users) {
        data.addAll(users);
    }

    @FXML
    public void goBack() {
        TileView.initialize(LoginManager.getAccessLevel());
    }

    public void addUserButtonClicked(ActionEvent actionEvent) {
        if (loginTextField.getText().isEmpty() || passwordField.getText().isEmpty() || accessLevelChoiceBox.getValue() == null) {
            showErrorAlert("Login, password and access level cannot be empty!");
            return;
        }
        for (UsersEntity user : data) {
            if (user
                    .getUserId().equals(loginTextField.getText())) {
                showErrorAlert("User with " + loginTextField.getText() + " already exists!");
                return;
            }
        }
        UsersEntity user = new UsersEntity();
        user.setUserId(loginTextField.getText());
        user.setPassword(passwordField.getText());
        user.setAccessLevel(accessLevelChoiceBox.getValue());
        data.add(user);
        Session session = LoginManager.getSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();

    }

    private void showErrorAlert(String mess) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mess);
        alert.showAndWait();
    }

    private boolean showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return !alert.showAndWait().get().getButtonData().isCancelButton();
    }
}
