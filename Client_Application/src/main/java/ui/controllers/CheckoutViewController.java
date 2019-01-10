package ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.*;
import model.entities.DeliveryEntity;
import model.entities.OrdersEntity;
import model.entities.OrdersProductsEntity;
import org.hibernate.Session;

public class CheckoutViewController {
    CartManager cartManager;
    String bankAccountCode = "00-00000-00000-00000";
    public VBox mainBox;
    public ChoiceBox<String> paymentChoiceBox;
    public ChoiceBox<TransactionDocument> transactionDocumentChoiceBox;
    public ChoiceBox<String> deliveryChoiceBox;
    public Label totalLabel;
    public TextField clientNameField;
    public TextField clientAddressField;
    public TextField phoneNumberField;

    public void initialize() {
        totalLabel.setText(totalLabel.getText().concat(String.format("%.2f", cartManager.total.getValue()) + "$"));
        paymentChoiceBox.getItems().addAll(Payment.on_delivery.toString(), Payment.bank_transfer.toString());
        paymentChoiceBox.setValue(Payment.bank_transfer.toString());
        transactionDocumentChoiceBox.getItems().addAll(TransactionDocument.receipt, TransactionDocument.invoice);
        transactionDocumentChoiceBox.setValue(TransactionDocument.receipt);
        //TODO show delivery
        deliveryChoiceBox.getItems().add("letter");
        deliveryChoiceBox.setValue("letter");

    }

    public void applyButtonClicked(ActionEvent event) {
        if (clientNameField.getText().isEmpty() || clientAddressField.getText().isEmpty() || phoneNumberField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Some fields are empty.");
            alert.initOwner(mainBox.getScene().getWindow());
            alert.showAndWait();
        } else {
            Session session = SessionFactoryManager.getNewSession();
            OrdersEntity order = new OrdersEntity();
            order.setCurrentStatus(Status.in_progress);
            order.setTransactionDocument(transactionDocumentChoiceBox.getValue());
            order.setPayment(Payment.getByValue(paymentChoiceBox.getValue()));
            //TODO change it
            //here we assume that we have Deliver entity
            order.setDeliveryId(12);
            DeliveryEntity deliveryEntity = session.load(DeliveryEntity.class,12);

            cartManager.total.setValue(cartManager.total.getValue()+deliveryEntity.getPrice());

            order.setCustomerAdress(clientAddressField.getText());
            order.setCustomerName(clientNameField.getText());
            order.setPhoneNumber(phoneNumberField.getText());
            session.beginTransaction();
            session.save(order);
            for (Integer id : cartManager.getProductsIdsAndPricesMap().keySet()) {
                OrdersProductsEntity ordersProductsEntity = new OrdersProductsEntity();
                ordersProductsEntity.setProductId(id);
                ordersProductsEntity.setOrderId(order.getOrderId());
                session.save(ordersProductsEntity);
            }
            session.getTransaction().commit();
            session.close();
            if (paymentChoiceBox.getValue().equals(Payment.bank_transfer.toString())) {
                mainBox.getChildren().clear();
                mainBox.getChildren().add(new Label("Thank you for purchasing!\nYou should transfer "+
                        String.format("%.2f",cartManager.total.doubleValue())+" to our bank account\nOur bank account code is: " + bankAccountCode));
            } else {
                mainBox.getChildren().clear();
                mainBox.getChildren().add(new Label("\"Thank you for purchasing!\nA courier will arrive soon!"));
            }
            cartManager.resetCart();
        }
    }

    private void setCartManager(CartManager manager) {
        cartManager = manager;
    }

    CheckoutViewController(CartManager manager) {
        setCartManager(manager);
    }
}
