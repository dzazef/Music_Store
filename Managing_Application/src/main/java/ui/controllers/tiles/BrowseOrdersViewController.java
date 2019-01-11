package ui.controllers.tiles;

import db.LoginManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Payment;
import model.Status;
import model.TransactionDocument;
import model.entities.DeliveryEntity;
import model.entities.OrdersEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;
import ui.Managing_Application;
import ui.views.LoginView;
import ui.views.TileView;

import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

public class BrowseOrdersViewController {
    public ScrollPane scrollPane;
    public TableView tableBrowseOrders;
    public TableColumn TransactionDocumentColumn;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn addressColumn;
    public TableColumn phoneColumn;
    public TableColumn deliveryColumn;
    public TableColumn statusColumn;
    public TableColumn paymentColumn;

    @FXML
    public void test() {
        TypedQuery<OrdersEntity> browseOrdersQuery = LoginManager.getSession().
                createQuery("from OrdersEntity", OrdersEntity.class);
        List<OrdersEntity> ordersEntityList = browseOrdersQuery.getResultList();
        for (OrdersEntity ordersEntity : ordersEntityList) {
            System.out.println(ordersEntity.getOrderId());
            System.out.println(ordersEntity.getDeliveryEntity().getName());
            System.out.println(ordersEntity.getCustomerName());
            System.out.println(ordersEntity.getCustomerAdress());
            System.out.println(ordersEntity.getPhoneNumber());
        }
    }

    public static void initialize() {
        try {
            @SuppressWarnings("ConstantConditions") final Parent parent = FXMLLoader.load(LoginView.class.getClassLoader().getResource("fxml/tiles/BrowseOrders.fxml"));
            Stage stage = Managing_Application.getStage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Browse orders");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while initializing LoginView");
        }
    }

    public static void runQuery() {
    }
    @FXML
    public void goBack() {
        TileView.initialize(LoginManager.getAccessLevel());
    }
}

//        Session session = LoginManager.getSession();
//        OrdersEntity ordersEntity = new OrdersEntity();
//        ordersEntity.setDeliveryEntity(session.load(DeliveryEntity.class, 1));
//        ordersEntity.setCustomerName("aaa");
//        ordersEntity.setCustomerAdress("aaa");
//        ordersEntity.setPhoneNumber("aaa");
//        ordersEntity.setPayment(Payment.bank_transfer);
//        ordersEntity.setTransactionDocument(TransactionDocument.invoice);
//        ordersEntity.setCurrentStatus(Status.cancelled);
//        session.beginTransaction();
//        session.save(ordersEntity);
//        session.getTransaction().commit();