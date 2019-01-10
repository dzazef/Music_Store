package ui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
            showThatCartIsEmpty();
        }
        else {
            scrollPane.setVisible(true);
            Button checkoutButton = new Button("BUY NOW!");
            checkoutButton.setOnAction( (actionEvent) -> proceedToCheckout());
            mainCartView.getChildren().add(0,checkoutButton);
            Iterator it = cartManager.getProductsIdsAndPricesMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setSpacing(20.0);
                Label productInfo = new Label(produceProductInfoString((Integer) pair.getKey()));
                Button removeFromCartButton = new Button("REMOVE FROM CART");
                removeFromCartButton.setOnAction((actionEvent) -> {
                    cartManager.removeProduct((Integer) pair.getKey());
                    if (cartManager.getProductCountById((Integer) pair.getKey()).equals(0)) {
                        productsInCart.getChildren().remove(hBox);
                    } else {
                        productInfo.setText(produceProductInfoString((Integer) pair.getKey()));
                    }
                    if (cartManager.getProductsIdsAndPricesMap().isEmpty()) {
                        mainCartView.getChildren().remove(checkoutButton);
                        showThatCartIsEmpty();
                    }
                });
                hBox.getChildren().addAll(productInfo, removeFromCartButton);
                productsInCart.getChildren().add(hBox);
            }

        }
    }

    private void showThatCartIsEmpty() {
        scrollPane.setVisible(false);
        Label label = new Label("YOUR CART IS EMPTY!");
        label.setStyle("-fx-font-size: 45.0");
        mainCartView.getChildren().add(label);
    }

    private String produceProductInfoString(Integer key) {
        Integer quantity = cartManager.getProductCountById(key);
        return "Product ID: " + key + "\nPrice: "
                + quantity * cartManager.getProductsIdsAndPricesMap().get(key)
                +"\nQuantity: "+ quantity;
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
