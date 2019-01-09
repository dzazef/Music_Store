package ui.controllers;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.CartManager;

public class CartViewController {
    public HBox mainCartView;
    public ScrollPane productDisplay;
    private CartManager cartManager;

    public void initialize() {
        productDisplay.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    CartViewController(CartManager manager) {
        setCartManager(manager);
    }

    private void setCartManager(CartManager manager) {
        cartManager = manager;
    }
}
