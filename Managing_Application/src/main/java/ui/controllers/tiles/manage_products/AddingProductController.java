package ui.controllers.tiles.manage_products;

import db.LoginManager;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.ProductCategory;
import model.entities.AlbumViewEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.lang.reflect.Field;


public class AddingProductController {
    public Button addButton;
    private ProductCategory productCategory;
    public VBox mainAddingProductBox;
    private BorderPane mainPane;
    private Node previousCenter;

    public void initialize() {
        if (productCategory != null) {
            switch (productCategory) {
                case album:
                    prepareViewForAlbum();
                    break;
                case instrument:
                    prepareViewForInstrument();
                    break;
                case other:
                    prepareViewForOther();
                    break;
                default:
                    break;
            }
        }
    }

    private void prepareViewForOther() {
        //TODO finish it
    }

    private void prepareViewForInstrument() {
        //TODO finish it
    }

    private void prepareViewForAlbum() {
        //Label labelTitle = new Label("TITLE");
        VBox productsBox = new VBox();
        productsBox.setAlignment(Pos.TOP_CENTER);
        TextField priceField = new TextField();
        priceField.setPromptText("price");
        TextField titleField = new TextField();
        titleField.setPromptText("title");
        TextField releaseYearField = new TextField();
        releaseYearField.setPromptText("release year");
        TextField imageLinkField = new TextField();
        imageLinkField.setPromptText("image link");
        TextField artistNameField = new TextField();
        artistNameField.setPromptText("artistName");
        TextField artistGenreField = new TextField();
        artistGenreField.setPromptText("artistGenre");
        TextField songsCountField = new TextField();
        songsCountField.setPromptText("songsCount");
        TextField durationField = new TextField();
        durationField.setPromptText("duration in miliseconds");
        Label labelTrack = new Label("Tracklist:");
        TextArea tracklistField = new TextArea();
        tracklistField.setPromptText("tracklist");
        addButton.setOnAction((event)->{
            try {
                Session session = LoginManager.getSession();
                session.beginTransaction();
                Query query = session.
                        createSQLQuery("CALL music_store.add_album(" +
                                ":price    ,:title ,  :realease_year ,  :img_link ," +
                                ":artist_name ,  :artist_genre ,  :songs_count," +
                                ":tracklist,  :duration )")
                        .setParameter("price",Double.parseDouble(priceField.getText()))
                        .setParameter("title",titleField.getText())
                        .setParameter("realease_year",Integer.parseInt(releaseYearField.getText()))
                        .setParameter("img_link",imageLinkField.getText())
                        .setParameter("artist_name",artistNameField.getText())
                        .setParameter("artist_genre",artistGenreField.getText())
                        .setParameter("songs_count",Integer.parseInt(songsCountField.getText()))
                        .setParameter("tracklist",tracklistField.getText())
                        .setParameter("duration",Integer.parseInt(durationField.getText()));
                query.executeUpdate();
                session.getTransaction().commit();
                session.close();
            } catch (PersistenceException e) {
                e.printStackTrace();
                showErrorAlert("Error! Cannot add this album.");
            } catch (NumberFormatException e) {
                showErrorAlert("Wrong argument! Make sure that numeric arguments are valid.");
            }

        });
        Button returnButton = new Button("GO BACK");
        returnButton.setOnAction((event)->goToThePreviousViewState());
        productsBox.getChildren().addAll(priceField,titleField,releaseYearField,imageLinkField,artistNameField,
                artistGenreField,songsCountField,durationField,labelTrack,tracklistField);
        mainAddingProductBox.getChildren().addAll(productsBox,returnButton);
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    private void addOther() {

    }

    private void addInstrument() {

    }

    private void addAlbum() {

    }

    private void showErrorAlert(String mess) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mess);
        alert.showAndWait();
    }

    public void setReturnData(BorderPane mainPane, Node previousCenter) {
        this.mainPane = mainPane;
        this.previousCenter = previousCenter;
    }

    private void goToThePreviousViewState() {
        mainPane.setCenter(previousCenter);
        mainPane.getBottom().setVisible(true);
    }
}
