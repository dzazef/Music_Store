package ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.CartManager;
import model.entities.AlbumViewEntity;

public class ProductViewController {
    public Button returnButton;
    public VBox leftBox;
    public VBox rightBox;
    public Spinner<Integer> spinner;
    public Button addToCartButton;
    public VBox mainBox;
    public ImageView albumPicture;
    public Label releaseYearLabel;
    public Label titleLabel;
    public Label artistLabel;
    public Label durationLabel;
    public Text trackListText;
    public Label priceLabel;
    private HBox parentNode;
    private AlbumViewEntity album;
    private CartManager cart;

    public void initialize() {
        rightBox.prefWidthProperty().bind(mainBox.widthProperty().subtract(40).divide(2));
        leftBox.prefWidthProperty().bind(mainBox.widthProperty().subtract(40).divide(2));
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10,1));
        if (album != null) {
           initializeAlbum();
        }
    }

    private void initializeAlbum() {
        albumPicture.setImage(new Image(album.getImageLink()));
        albumPicture.setFitWidth(250);
        albumPicture.setFitHeight(250);
        titleLabel.setText("TITLE: "+album.getTitle());
        releaseYearLabel.setText("RELEASE YEAR: "+album.getReleaseYear().toString());
        artistLabel.setText("ARTIST: "+album.getName());
        durationLabel.setText("DURATION: "+(album.getDuration()/1000/60));
        trackListText.setText(album.getTracklist().replace('*','\n'));
        priceLabel.setText("PRICE: "+album.getPrice()+"$");
    }

    public void setParentNode(HBox parentNode) {
        this.parentNode = parentNode;
    }

    public void setCartManager(CartManager cartManager) {
        cart = cartManager;
    }

    public void setAlbumViewEntity(AlbumViewEntity albumViewEntity) {
        album = albumViewEntity;
    }

    public void switchToProductDisplayView(ActionEvent actionEvent) {
        parentNode.getChildren().get(1).setManaged(true);
        parentNode.getChildren().get(1).setVisible(true);
        parentNode.getChildren().remove(2);
    }

    public synchronized void addToCart(ActionEvent actionEvent) {
        for (int i = 0; i < spinner.getValue(); i++) {
            cart.addProduct(album.getProductId(),album.getPrice());
        }
        showInformationAlert("Product added to cart.\nQuantity: "+spinner.getValue());
    }

    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
