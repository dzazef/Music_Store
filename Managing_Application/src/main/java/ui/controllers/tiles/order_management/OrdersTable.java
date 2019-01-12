package ui.controllers.tiles.order_management;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.entities.OrdersEntity;

@SuppressWarnings("unused")
public class OrdersTable {
    private final SimpleIntegerProperty orderId;
    private final SimpleStringProperty deliveryName;
    private final SimpleStringProperty customerName;
    private final SimpleStringProperty customerAdress;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty currentStatus;
    private final SimpleStringProperty payment;
    private final SimpleStringProperty transactionDocument;
    public OrdersTable(OrdersEntity ordersEntity) {
        this.orderId = new SimpleIntegerProperty(ordersEntity.getOrderId());
        this.deliveryName = new SimpleStringProperty(ordersEntity.getDeliveryEntity().getName());
        this.customerName = new SimpleStringProperty(ordersEntity.getDeliveryEntity().getName());
        this.customerAdress = new SimpleStringProperty(ordersEntity.getCustomerName());
        this.phoneNumber = new SimpleStringProperty(ordersEntity.getPhoneNumber());
        this.currentStatus = new SimpleStringProperty(ordersEntity.getCurrentStatus().toString());
        this.payment = new SimpleStringProperty(ordersEntity.getPayment().toString());
        this.transactionDocument = new SimpleStringProperty(ordersEntity.getTransactionDocument().toString());
    }
    public int getOrderId() {
        return orderId.get();
    }
    public void setOrderId(int value) {
        orderId.set(value);
    }
    public String getDeliveryName() {
        return deliveryName.get();
    }
    public void setDeliveryName(String value) {
        deliveryName.set(value);
    }
    public String getCustomerName() {
        return customerName.get();
    }
    public void setCustomerName(String value) {
        customerName.set(value);
    }
    public String getCustomerAdress() {
        return customerAdress.get();
    }
    public void setCustomerAdress(String value) {
        customerAdress.set(value);
    }
    public String getPhoneNumber() {
        return phoneNumber.get();
    }
    public void setPhoneNumber(String value) {
        phoneNumber.set(value);
    }
    public String getCurrentStatus() {
        return currentStatus.get();
    }
    public void setCurrentStatus(String value) {
        currentStatus.set(value);
    }
    public String getPayment() {
        return payment.get();
    }
    public void setPayment(String value) {
        payment.set(value);
    }
    public String getTransactionDocument() {
        return transactionDocument.get();
    }
    public void setTransactionDocument(String value) {
        transactionDocument.set(value);
    }
}