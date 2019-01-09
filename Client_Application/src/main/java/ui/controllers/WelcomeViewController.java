package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CartManager;

import java.io.IOException;
import java.util.Objects;

public class WelcomeViewController {
    public Label priceLabel;
    public BorderPane mainPane;
    private CartManager cartManager;
    public ImageView logoImageView;
    public VBox topVBox;
    public HBox searchPanel;
    public HBox hBoxForLogoAndForSearch;
    public Button albumsButton;
    public Button instrumentsButton;
    public Button othersButton;
    public Button deliveryButton;
    public Button contactButton;
    //public ChoiceBox searchFilterChoiceBox;

    public void initialize() {
        searchPanel.prefWidthProperty().bind(topVBox.widthProperty().divide(1.8));
        albumsButton.prefWidthProperty().bind(topVBox.widthProperty().divide(5));
        instrumentsButton.prefWidthProperty().bind(topVBox.widthProperty().divide(5));
        othersButton.prefWidthProperty().bind(topVBox.widthProperty().divide(5));
        deliveryButton.prefWidthProperty().bind(topVBox.widthProperty().divide(5));
        contactButton.prefWidthProperty().bind(topVBox.widthProperty().divide(5));
        setCartManager(new CartManager());
        priceLabel.textProperty().bind(cartManager.total.asString());
    }

    public void setCartManager(CartManager cartManager) {
        this.cartManager = cartManager;
    }

    public void SearchClicked(ActionEvent actionEvent) {
        //TODO db connection
    }

    public void onContactButtonClicked(ActionEvent actionEvent) {
        try {
            mainPane.setCenter(
                    FXMLLoader.load(Objects.requireNonNull(FXMLLoader.getDefaultClassLoader()
                            .getResource("fxml/ContactView.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public void cartClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CartView.fxml"));
            CartViewController cartViewController = new CartViewController(cartManager);
            mainPane.setCenter(loader.load());
            loader.setController(cartViewController);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
