package ui.controllers.tiles.manage_orders;

import db.LoginManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import model.entities.OrdersProductsEntity;
import model.entities.ProductsEntity;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;

import java.sql.SQLException;

public class AddProductToOrderViewController {
    public Spinner<Integer> quantitySpinner;
    public TextField productTextField;
    private int orderId;

    public void addProductButton() {
        Session session = LoginManager.getSession();
        try {
            session.get(ProductsEntity.class, Integer.parseInt(productTextField.getText()));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Product does not exist");
            alert.showAndWait();
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Incorrect product ID");
            alert.showAndWait();
        } catch (GenericJDBCException e) {
            session.beginTransaction();
            OrdersProductsEntity ordersProductsEntity = new OrdersProductsEntity();
            ordersProductsEntity.setOrderId(orderId);
            ordersProductsEntity.setProductId(Integer.parseInt(productTextField.getText()));
            ordersProductsEntity.setQuantity(quantitySpinner.getValue());
            session.save(ordersProductsEntity);
            session.getTransaction().commit();
            ManageProductsViewController.closeAddProductWindow();
        } catch (ConstraintViolationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Product already in this order");
            alert.showAndWait();
        }
        session.close();
    }

    public void initialize() {
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));
    }

    public void setInfo(int orderId) {
        this.orderId = orderId;
    }
}

//@Deprecated
//public class AddProductToOrderViewController {
//    public ChoiceBox<String> categoryChoiceBox;
//    public ChoiceBox<String> productChoiceBox;
//
//    public ObservableList<String> categoryList = FXCollections.observableArrayList();
//
//    @FXML
//    public void addProductButton() {
//        ManageProductsViewController.closeAddProductWindow();
//    }
//
//    public void initialize() {
//        categoryList.add("album");
//        categoryList.add("instrument");
//        categoryList.add("other");
//        categoryChoiceBox.setItems(categoryList);
//        categoryChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> refreshProductChoiceBox(newValue));
//    }
//
//    private void refreshProductChoiceBox(Number newValue) {
//        productChoiceBox.getItems().clear();
//        Session session = LoginManager.getSession();
//        switch (newValue.intValue()) {
//            case 0: {
//                TypedQuery<AlbumsEntity> albumsEntityTypedQuery = session.createQuery("from AlbumsEntity ", AlbumsEntity.class);
//                List<AlbumsEntity> albumsEntityList = albumsEntityTypedQuery.getResultList();
//                List<String> stringAlbumsEntityList = new ArrayList<>();
//                for (AlbumsEntity albumsEntity : albumsEntityList) {
//                    stringAlbumsEntityList.add(albumsEntity.getTitle());
//                }
//                Collections.sort(stringAlbumsEntityList);
//                productChoiceBox.getItems().addAll(stringAlbumsEntityList);
//            }
//            case 1: {
//                TypedQuery<InstrumentsEntity> instrumentsEntityTypedQuery = session.createQuery("from InstrumentsEntity ", InstrumentsEntity.class);
//                List<InstrumentsEntity> instrumentsEntityList = instrumentsEntityTypedQuery.getResultList();
//                List<String> stringInstrumentEntityList = new ArrayList<>();
//                for (InstrumentsEntity instrumentsEntity : instrumentsEntityList) {
//                    stringInstrumentEntityList.add(instrumentsEntity.getName());
//                }
//                Collections.sort(stringInstrumentEntityList);
//                productChoiceBox.getItems().addAll(stringInstrumentEntityList);
//            }
//            case 2: {
//                TypedQuery<OtherEntity> otherEntityTypedQuery = session.createQuery("from OtherEntity", OtherEntity.class);
//                List<OtherEntity> otherEntityList = otherEntityTypedQuery.getResultList();
//                List<String> stringOtherEntityList = new ArrayList<>();
//                for (OtherEntity otherEntity : otherEntityList) {
//                    stringOtherEntityList.add(otherEntity.getName());
//                }
//                Collections.sort(stringOtherEntityList);
//                productChoiceBox.getItems().addAll(stringOtherEntityList);
//
//            }
//        }
//        session.close();
//    }
//}
