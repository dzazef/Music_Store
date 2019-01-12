package ui.controllers.tiles.backup_restore;

import db.LoginManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import ui.Managing_Application;
import ui.views.TileView;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupRestoreViewController {
    @FXML
    public void backup() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Dump Directory");
        File file = directoryChooser.showDialog(Managing_Application.getStage());
        if (file!=null) {
            String command = "mysqldump -u admin -padmin --databases music_store";
            try {
                final Process p = Runtime.getRuntime().exec(command);
                InputStream in = new BufferedInputStream(p.getInputStream());
                OutputStream out = new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath() + "/music_store_dump" + new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss").format(new Date())));
                int cnt;
                byte[] buffer = new byte[1024];
                while ((cnt = in.read(buffer)) != -1) {
                    out.write(buffer, 0, cnt);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            showAlert("Dump successfully saved");
        }
    }
    @FXML
    public void restore() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(Managing_Application.getStage());
        if (file!=null) {
            String[] command = new String[]{"mysql", "--user=" + "admin", "--password=" + "admin", "music_store", "-e", " source " + file.getAbsolutePath()};
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
            showAlert("Schema successfully restored");
        }
    }

    @FXML
    public void goBack() {
        TileView.initialize(LoginManager.getAccessLevel());
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
