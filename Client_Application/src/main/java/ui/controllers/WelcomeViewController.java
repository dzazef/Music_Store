package ui.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WelcomeViewController {
    public ImageView logoImageView;
    public Button albumsButton;
    public Button instrumentsButton;
    public Button accessoriesButton;
    public VBox topVBox;
    public HBox topButtonsHBox;
    public HBox searchPanel;
    public HBox hBoxForLogoAndHBoxWithButtons;
    public ChoiceBox searchFilterChoiceBox;

    public void initialize() {
        topButtonsHBox.prefWidthProperty().bind(hBoxForLogoAndHBoxWithButtons.widthProperty().add(-220));
        albumsButton.prefWidthProperty().bind(topButtonsHBox.widthProperty().divide(3));
        instrumentsButton.prefWidthProperty().bind(topButtonsHBox.widthProperty().divide(3));
        accessoriesButton.prefWidthProperty().bind(topButtonsHBox.widthProperty().divide(3));


    }
}
