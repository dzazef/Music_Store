package ui.controllers.tiles.change_storage_levels;

import db.LoginManager;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.StorageEntity;
import org.hibernate.Session;

public class ChangeStorageDialog {
    public TextField csdTextField;
    private ChangeStorageTable changeStorageTable;
    private TableView tableView;

    void setLevel(ChangeStorageTable changeStorageTable, TableView tableView){
        this.tableView = tableView;
        this.changeStorageTable = changeStorageTable;
        this.csdTextField.setText(Integer.toString(changeStorageTable.getQuantity()));
    }
    public void confirm() {
        int value;
        try {
            value = Integer.parseInt(csdTextField.getText());
            if (value<0) throw new NumberFormatException();
            Session session = LoginManager.getSession();
            session.beginTransaction();
            StorageEntity storageEntity = session.load(StorageEntity.class, changeStorageTable.getProductId());
            storageEntity.setProductsAvailable(value);
            session.update(storageEntity);
            session.getTransaction().commit();
            session.close();
            changeStorageTable.setQuantity(value);
            tableView.refresh();
            Stage stage = (Stage) csdTextField.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Incorrect value. Try again.");
            alert.showAndWait();
        }
    }
}
