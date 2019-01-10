package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import java.util.*;

public class CartManager {
    public DoubleProperty total = new SimpleDoubleProperty(0.0);
    //TODO store two the same products in the cart
    //private List<ProductsEntity> products = new ArrayList<>();
    private Map<Integer,Double> productsIdsAndPrices = new LinkedHashMap<>();
    private Map<Integer,Integer> productIdAndCountInCart = new HashMap<>();

    void updatePrice(double value) {
        total.setValue(total.getValue() + value);
    }

    public void addProduct(Integer id,Double price) {
        updatePrice(price);
        if (!productIdAndCountInCart.keySet().contains(id)) {
            productsIdsAndPrices.put(id, price);
            productIdAndCountInCart.put(id,1);
        } else {
            productIdAndCountInCart.computeIfPresent(id,(k,v) -> v+1);
        }
    }
    public void removeProduct(Integer id) {
        if (productsIdsAndPrices.containsKey(id)) {
            updatePrice(-productsIdsAndPrices.get(id));
            productIdAndCountInCart.computeIfPresent(id, (k,v) -> v-1);
            if (productIdAndCountInCart.get(id).equals(0)) {
                productsIdsAndPrices.remove(id);
            }
        }
    }

    public Map<Integer, Double> getProductsIdsAndPricesMap() {
        return productsIdsAndPrices;
    }

    public Integer getProductCountById(Integer id) {
        return productIdAndCountInCart.getOrDefault(id, 0);
    }

    public void resetCart() {
        productIdAndCountInCart.clear();
        productsIdsAndPrices.clear();
        total.setValue(0.0);
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
