package ui.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.CartManager;
import model.FilterProductFunctions;
import model.ProductCategory;
import model.entities.AlbumViewEntity;
import model.entities.InstrumentViewEntity;
import model.entities.OtherViewEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDisplayViewController {
    public HBox mainCartView;
    public VBox filtersMenuBox;
    public TilePane tilePaneWithProducts;
    public ScrollPane productDisplay;
    public Button doFilterButton;
    private CartManager cartManager;
    private ProductCategory category;
    private Map<String,String> filters = new HashMap<>();
    //column & user input
    public void initialize() {
        tilePaneWithProducts.prefColumnsProperty().bind(mainCartView.widthProperty().divide(200));
        tilePaneWithProducts.prefWidthProperty().bind(mainCartView.widthProperty().subtract(filtersMenuBox.widthProperty()));
        productDisplay.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void addFilterTextField(String name, String column) {
        Label label = new Label(name);
        HBox container = new HBox();
        TextField textField = new TextField();
        ToggleButton toggleButton = new ToggleButton("");
        toggleButton.applyCss();
        EventHandler<ActionEvent> actionEventEventHandler = (actionEvent) -> {
            if (toggleButton.isSelected()) {
                filters.put(column,textField.getText());
            } else {
                filters.remove(column);
            }
        };
        toggleButton.setOnAction(actionEventEventHandler);
        container.getChildren().addAll(textField,toggleButton);
        filtersMenuBox.getChildren().addAll(label,container);
    }

    public void doFilter(ActionEvent event) {
        switch (category) {
            case album:
                presentAlbums();
                break;
            case instrument:
                presentInstruments();
                break;
            case other:
                presentOther();
                break;
        }
    }

    private void presentOther() {
        List<OtherViewEntity> products = FilterProductFunctions.filterOther(filters);
        for (OtherViewEntity instrument : products) {
            VBox vBox = new VBox();
            vBox.setPadding(new Insets(0,10,0,10));
//            ImageView imageView = new ImageView(new Image());
//            imageView.setFitWidth(160.0);
//            imageView.setFitHeight(140.0);
            Label titleLabel = new Label(instrument.getName()+"\nProducer: "+instrument.getProducer()+
                    "\nType: "+instrument.getType()+"\n"+instrument.getPrice()+"$\n");
            vBox.getChildren().addAll(titleLabel);
            tilePaneWithProducts.getChildren().addAll(vBox);
        }
    }

    private void presentInstruments() {
        List<InstrumentViewEntity> instruments = FilterProductFunctions.filterInstruments(filters);
        for (InstrumentViewEntity instrument : instruments) {
            VBox vBox = new VBox();
            vBox.setPadding(new Insets(0,10,0,10));
//            ImageView imageView = new ImageView(new Image());
//            imageView.setFitWidth(160.0);
//            imageView.setFitHeight(140.0);
            Label titleLabel = new Label(instrument.getInstrumentName()+"\nManufacturer: \n"+instrument.getManufacturerName()+
                    "\nType:\n"+instrument.getType()+"\n"+instrument.getPrice()+"$\n");
            vBox.getChildren().addAll(titleLabel);
            tilePaneWithProducts.getChildren().addAll(vBox);
        }
    }

    private void presentAlbums() {
        List<AlbumViewEntity> albums = FilterProductFunctions.filterAlbums(filters);
        for (AlbumViewEntity album : albums) {
            VBox vBox = new VBox();
            ImageView imageView = new ImageView(new Image(album.getImageLink()));
            imageView.setFitWidth(160.0);
            imageView.setFitHeight(140.0);
            Label titleLabel = new Label(album.getTitle()+" "+album.getPrice()+"$");
            vBox.getChildren().addAll(imageView,titleLabel);
            tilePaneWithProducts.getChildren().addAll(vBox);
        }
    }

    ProductDisplayViewController(CartManager manager,ProductCategory category) {
        setCartManager(manager);
        setCategory(category);
    }

    private void setCartManager(CartManager manager) {
        cartManager = manager;
    }

    private void setCategory(ProductCategory cat) {
        category = cat;
    }
}
