package ui.controllers.tiles.completeOrders;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings("unused")
public class OrdersProductsTable {
    private final SimpleIntegerProperty productId;
    private final SimpleStringProperty manufacturer;
    private final SimpleStringProperty productName;
    private final SimpleIntegerProperty quantity;
    public OrdersProductsTable(int productId, String manufacturer, String productName, int quantity) {
        this.productId = new SimpleIntegerProperty(productId);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleIntegerProperty(quantity);
    }
    public int getProductId() {
        return productId.get();
    }
    public void setProductId(int value) {
        productId.set(value);
    }
    public String getManufacturer() {
        return manufacturer.get();
    }
    public void setManufacturer (String value) {
        manufacturer.set(value);
    }
    public String getProductName() {
        return productName.get();
    }
    public void setProductName(String value) {
        productName.set(value);
    }
    public int getQuantity() {
        return quantity.get();
    }
    public void setQuantity(int value) {
        quantity.set(value);
    }
}