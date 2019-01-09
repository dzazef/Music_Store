package ui.controllers;

import model.CartManager;

public class CartViewController {
    private CartManager cartManager;
    CartViewController(CartManager manager) {
        setCartManager(manager);
    }
    private void setCartManager(CartManager manager) {
        cartManager=manager;
    }
}
