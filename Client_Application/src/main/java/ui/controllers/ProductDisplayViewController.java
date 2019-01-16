package ui.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.CartManager;
import model.FilterProductFunctions;
import model.ProductCategory;
import model.entities.AlbumViewEntity;
import model.entities.InstrumentViewEntity;
import model.entities.OtherViewEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDisplayViewController {
    private BorderPane stage;
    public HBox mainDisplayProductsView;
    public VBox filtersMenuBox;
    public VBox boxForScrollPane;
    public HBox internalMainBox;
    public TilePane tilePaneWithProducts;
    public ScrollPane productDisplay;
    public Button doFilterButton;
    private CartManager cartManager;
    private ProductCategory category;
    private Map<String,String> filters = new HashMap<>();
    //column & user input
    public void initialize() {
//        internalMainBox.prefWidthProperty().bind(mainDisplayProductsView.prefWidthProperty());
//        internalMainBox.prefHeightProperty().bind(mainDisplayProductsView.heightProperty());
        filtersMenuBox.prefWidthProperty().bind(mainDisplayProductsView.widthProperty().divide(5));
        boxForScrollPane.prefWidthProperty().bind(mainDisplayProductsView.widthProperty().divide(1.25));
        //boxForScrollPane.setStyle("-fx-background-color: #445599");
        tilePaneWithProducts.prefColumnsProperty().bind(boxForScrollPane.widthProperty().divide(184));
//        tilePaneWithProducts.setPrefColumns(15);
//        tilePaneWithProducts.prefWidthProperty().bind(mainDisplayProductsView.prefWidthProperty().subtract(filtersMenuBox.prefWidthProperty().subtract(40)));
        productDisplay.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void addFilterTextField(String name, String column) {
        Label label = new Label(name);
        HBox container = new HBox();
        container.setAlignment(Pos.TOP_CENTER);
        TextField textField = new TextField();
        textField.prefWidth(150.0);
        ToggleButton toggleButton = new ToggleButton("");
        toggleButton.prefWidthProperty().bind(toggleButton.heightProperty());
        toggleButton.applyCss();
        EventHandler<ActionEvent> actionEventEventHandler = (actionEvent) -> {
            if (toggleButton.isSelected())
                filters.put(column,textField.getText());
            else filters.remove(column);
        };
        textField.textProperty().addListener((observable, oldValue, newValue)-> {actionEventEventHandler.handle(new ActionEvent());});
        toggleButton.setOnAction(actionEventEventHandler);
        container.getChildren().addAll(textField,toggleButton);
        filtersMenuBox.getChildren().addAll(label,container);
    }

    public void doFilter(ActionEvent event) {
        tilePaneWithProducts.getChildren().clear();
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
        for (OtherViewEntity product : products) {
            VBox vBox = new VBox();
            vBox.setPadding(new Insets(0,10,0,10));
//            ImageView imageView = new ImageView(new Image());
//            imageView.setFitWidth(160.0);
//            imageView.setFitHeight(140.0);
            Label titleLabel = new Label(product.getName()+"\nProducer: \n"+product.getProducer()+
                    "\nType: \n"+product.getType()+"\n"+product.getPrice()+"$\n"
                    + "Quantity: \n"+product.getStorageEntity().getProductsAvailable());
            Button addDoCartButton = new Button("ADD DO CART");
            addDoCartButton.setOnAction((actionEvent) -> addToCart(product.getProductId(),product.getPrice(),1));
            vBox.getChildren().addAll(titleLabel,addDoCartButton);
            tilePaneWithProducts.getChildren().add(vBox);
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
                    "\nType:\n"+instrument.getType()+"\n"+instrument.getPrice()+"$\n"+ "Quantity: "+
                    instrument.getStorageEntity().getProductsAvailable());
            Button addDoCartButton = new Button("ADD DO CART");
            addDoCartButton.setOnAction((actionEvent) -> addToCart(instrument.getProductId(),instrument.getPrice(),1));
            vBox.getChildren().addAll(titleLabel,addDoCartButton);
            tilePaneWithProducts.getChildren().addAll(vBox);
        }
    }

    private void presentAlbums() {
        List<AlbumViewEntity> albums = FilterProductFunctions.filterAlbums(filters);
        for (AlbumViewEntity album : albums) {
            VBox vBox = new VBox();
            vBox.prefWidth(200);
            vBox.setAlignment(Pos.TOP_CENTER);
            ImageView imageView = new ImageView(new Image(album.getImageLink()));
            imageView.setOnMouseClicked((event) -> changeToAlbumView(album));
            imageView.setFitWidth(160.0);
            imageView.setFitHeight(160.0);
            Label titleLabel = new Label("Artist: "+album.getName()+"\nTitle: "+album.getTitle()+"\nPrice: "
                    +album.getPrice()+"$\n" + "Quantity: "+album.getStorageEntity().getProductsAvailable());
            titleLabel.setPrefWidth(160.0);
            titleLabel.setWrapText(true);
            Button addDoCartButton = new Button("ADD DO CART");
            addDoCartButton.setOnAction((actionEvent) -> addToCart(album.getProductId(),album.getPrice(),album.getStorageEntity().getProductsAvailable()));
            vBox.getChildren().addAll(imageView,titleLabel,addDoCartButton);
            tilePaneWithProducts.getChildren().addAll(vBox);
        }
    }

    private synchronized void changeToAlbumView(AlbumViewEntity albumViewEntity) {
        System.err.println("Changing to album view called.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProductView.fxml"));
            internalMainBox.getChildren().get(1).setManaged(false);
            internalMainBox.getChildren().get(1).setVisible(false);
            internalMainBox.getChildren().add(loader.load());
            ProductViewController controller = loader.getController();
            controller.setParentNode(internalMainBox);
            controller.setAlbumViewEntity(albumViewEntity);
            controller.setCartManager(cartManager);
            controller.initialize();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    ProductDisplayViewController(CartManager manager,ProductCategory category,BorderPane stage) {
        setStage(stage);
        setCartManager(manager);
        setCategory(category);
    }

    private void addToCart(Integer productId,Double price,Integer available) {
        cartManager.addProduct(productId,price);
    }

    private void removeFromCart(Integer productId) {
        cartManager.removeProduct(productId);
    }

    private void setCartManager(CartManager manager) {
        cartManager = manager;
    }

    private void setCategory(ProductCategory cat) {
        category = cat;
    }

    private void setStage(BorderPane stage) {
        this.stage = stage;
    }
}
