package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import java.util.*;

public class CartManager {
    public DoubleProperty total = new SimpleDoubleProperty(0.0);
    //TODO store two the same products in the cart
    //private List<ProductsEntity> products = new ArrayList<>();
    private Map<Integer,Double> productsIdsAndPrices = new LinkedHashMap<>();

    void updatePrice(double value) {
        total.setValue(total.getValue() + value);
    }

    public void addProduct(Integer id,Double price) {
        updatePrice(price);
        productsIdsAndPrices.put(id, price);
    }
    public void removeProduct(Integer id) {
        if (productsIdsAndPrices.containsKey(id)) {
            updatePrice(-productsIdsAndPrices.get(id));
            productsIdsAndPrices.remove(id);
        }
    }

    public Map<Integer, Double> getProductsIdsAndPricesMap() {
        return productsIdsAndPrices;
    }
//    void addProduct(ProductsEntity product) {
//        products.add(product);
//        updatePrice(product.getPrice());
//    }
//    void removeProduct(int id) {
//        for (ProductsEntity prod : products) {
//            if (Objects.equals(prod.getProductId(),id)) {
//                products.remove(prod);
//                updatePrice(-prod.getPrice());
//                break;
//            }
//        }
//    }
}
