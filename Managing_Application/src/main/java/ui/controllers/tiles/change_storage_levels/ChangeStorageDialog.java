package ui.controllers.tiles.change_storage_levels;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ChangeStorageDialog {
    public TextField csdTextField;
    private ChangeStorageTable changeStorageTable;

    void setLevel(ChangeStorageTable changeStorageTable){
        this.changeStorageTable = changeStorageTable;
        this.csdTextField.setText(Integer.toString(changeStorageTable.getQuantity()));
    }
    public void confirm() {
        int value;
        try {
            value = Integer.parseInt(csdTextField.getText());
            ChangeStorageViewController.changeLevelsDialogClose(value, changeStorageTable);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Incorrect value. Try again.");
            alert.showAndWait();
        }
    }
}
