package ui.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.CartManager;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class CartViewController {
    public VBox mainCartView;
    public ScrollPane scrollPane;
    public VBox productsInCart;
    private CartManager cartManager;
    private BorderPane stage;

    public void initialize() {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        showProductsInCart();
    }

    private void showProductsInCart() {
        if (cartManager.getProductsIdsAndPricesMap().isEmpty()) {
            productsInCart.setVisible(false);
            Label label = new Label("YOUR CART IS EMPTY!");
            label.setStyle("-fx-font-size: 45.0");
            mainCartView.getChildren().add(label);
        }
        else {
            productsInCart.setVisible(true);
            Button checkoutButton = new Button("BUY NOW!");
            checkoutButton.setOnAction( (actionEvent) -> proceedToCheckout());
            mainCartView.getChildren().add(0,checkoutButton);
            Iterator it = cartManager.getProductsIdsAndPricesMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                HBox hBox = new HBox();
                Label productInfo = new Label("Product ID: " + pair.getKey() + "\nPrice: " + pair.getValue());
                Button removeFromCartButton = new Button("REMOVE FROM CART");
                removeFromCartButton.setOnAction((actionEvent) -> {
                        cartManager.removeProduct((Integer) pair.getKey());
                        productsInCart.getChildren().remove(hBox);});
                hBox.getChildren().addAll(productInfo, removeFromCartButton);
                productsInCart.getChildren().add(hBox);
            }

        }
    }

    private void proceedToCheckout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CheckoutView.fxml"));
            CheckoutViewController checkoutViewController = new CheckoutViewController(cartManager);
            loader.setController(checkoutViewController);
            stage.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    CartViewController(CartManager manager, BorderPane primaryStage) {
        setCartManager(manager);
        setStage(primaryStage);
    }

    private void setCartManager(CartManager manager) {
        cartManager = manager;
    }

    public void setStage(BorderPane stage) {
        this.stage = stage;
    }

    public BorderPane getStage() {
        return stage;
    }
}
