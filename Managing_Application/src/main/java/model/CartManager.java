package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.entities.ProductsEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartManager {
    public DoubleProperty total = new SimpleDoubleProperty(0.0);
    private List<ProductsEntity> products = new ArrayList<>();

    void updatePrice(double value) {
        total.setValue(total.getValue() + value);
    }
    void addProduct(ProductsEntity product) {
        products.add(product);
        updatePrice(product.getPrice());
    }
    void removeProduct(int id) {
        for (ProductsEntity prod : products) {
            if (Objects.equals(prod.getProductId(),id)) {
                products.remove(prod);
                updatePrice(-prod.getPrice());
                break;
            }
        }
    }
}
