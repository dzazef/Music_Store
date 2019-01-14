package ui.controllers.tiles.delivery_management;

import db.LoginManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import model.entities.DeliveryEntity;
import model.entities.UsersEntity;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import ui.views.TileView;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class DeliveryManagementController {
    public Label currentPageNumberLabel;
    public IntegerProperty currentPageNumber = new SimpleIntegerProperty(1);
    private final ObservableList<DeliveryEntity> data = FXCollections.observableArrayList();
    private final int maxLogCountOnOnePage = 23;
    public BorderPane mainPane;
    public TextField deliveryNameTextField;
    public TextField priceTextField;
    public TextField timeTextField;
    public TableView tableView;
    public TableColumn deliveryNameColumn;
    public TableColumn deliveryIdColumn;
    public TableColumn deliveryPriceColumn;
    public TableColumn deliveryTimeColumn;
    public Button previousPageButton;
    public Button nextPageButton;
    public TableColumn removeButtonColumn;

    public void initialize() {
        currentPageNumberLabel.textProperty().bind(currentPageNumber.asString());
        tableView.prefWidthProperty().bind(mainPane.widthProperty());
        deliveryIdColumn.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        deliveryNameColumn.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        deliveryPriceColumn.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        deliveryTimeColumn.prefWidthProperty().bind(tableView.widthProperty().divide(5));
        removeButtonColumn.prefWidthProperty().bind(tableView.widthProperty().divide(5));

        previousPageButton.setVisible(false);
        deliveryIdColumn.setCellValueFactory(new PropertyValueFactory<DeliveryEntity, String>("deliveryId"));
        deliveryNameColumn.setCellValueFactory(new PropertyValueFactory<DeliveryEntity, String>("name"));
        deliveryPriceColumn.setCellValueFactory(new PropertyValueFactory<DeliveryEntity, String>("price"));
        deliveryTimeColumn.setCellValueFactory(new PropertyValueFactory<DeliveryEntity, String>("deliveryTime"));
        removeButtonColumn.setCellFactory(createButtonCellFactory());
        tableView.setItems(data);
        goToNextPage(currentPageNumber.getValue());
    }

    @FXML
    public void goBack() {
        TileView.initialize(LoginManager.getAccessLevel());
    }

    public void previousPageClicked(ActionEvent actionEvent) {
        currentPageNumber.setValue(currentPageNumber.getValue()-1);
        goToNextPage(currentPageNumber.getValue());
        if (currentPageNumber.getValue().equals(1)) {
            previousPageButton.setVisible(false);
        }
    }

    public void nextPageClicked(ActionEvent actionEvent) {
        currentPageNumber.setValue(currentPageNumber.getValue()+1);
        goToNextPage(currentPageNumber.getValue());
        if (currentPageNumber.getValue() >= 2) {
            previousPageButton.setVisible(true);

        }
    }

    public void goToNextPage(int pageNumber) {
        Session session = LoginManager.getSession();
        Query query = session.createQuery("from DeliveryEntity ");
        query.setMaxResults(maxLogCountOnOnePage);
        query.setFirstResult((pageNumber-1)*maxLogCountOnOnePage);
        data.clear();
        data.addAll((List<DeliveryEntity>)query.getResultList());
        //currentPageNumber++;
    }

    public void addDeliveryButtonClicked(ActionEvent actionEvent) {
        if (deliveryNameTextField.getText().isEmpty() || priceTextField.getText().isEmpty() || timeTextField.getText().isEmpty()) {
            showErrorAlert("Name, price and expected time cannot be empty!");
            return;
        }
        for (DeliveryEntity delivery : data) {
            if (delivery.getName().equals(deliveryNameTextField.getText())) {
                showErrorAlert("Delivery method with " + deliveryNameTextField.getText() + " already exists!");
                return;
            }
        }
        try {
            DeliveryEntity delivery = new DeliveryEntity();
            delivery.setName(deliveryNameTextField.getText());
            delivery.setPrice(Double.parseDouble(priceTextField.getText()));
            delivery.setDeliveryTime(Integer.parseInt(timeTextField.getText()));
            data.add(delivery);
            Session session = LoginManager.getSession();
            session.beginTransaction();
            session.save(delivery);
            session.getTransaction().commit();
            session.close();
        } catch (NumberFormatException e) {
            showErrorAlert("Wrong price or time.\nPrice must be float value and time must be integer value.");
        }
    }

    private Callback<TableColumn<DeliveryEntity, Void>, TableCell<DeliveryEntity, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<DeliveryEntity, Void>, TableCell<DeliveryEntity, Void>>() {
            @Override
            public TableCell<DeliveryEntity, Void> call(TableColumn<DeliveryEntity, Void> param) {
                return new TableCell<DeliveryEntity, Void>() {
                    private Button button = new Button("REMOVE");

                    {
                        setAlignment(Pos.CENTER);
                        button.setOnAction((event) -> {
                            removeDelivery(data.get(getIndex()));});
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            }
        };
    }

    private void removeDelivery(DeliveryEntity delivery) {
        if (showConfirmationAlert("Are you sure you want to remove delivery method "+
                delivery.getName()+"?\nThis cannot be undone!")) {
            try (Session session = LoginManager.getSession()) {
                session.beginTransaction();
                session.remove(session.merge(delivery));
                session.getTransaction().commit();
                data.remove(delivery);
            } catch (PersistenceException exception) {
                showErrorAlert("Unable to remove delivery '"+delivery.getName()+
                        "'.\nDelivery is present in at least one order.\n" +
                        "To remove this delivery method remove all orders\n" +
                        "having this delivery method. Delivery id: "+delivery.getDeliveryId()+"\n");
            }
        }

    }

    private void showErrorAlert(String mess) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mess);
        alert.showAndWait();
    }

    private boolean showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return !alert.showAndWait().get().getButtonData().isCancelButton();
    }
}
