package ui.controllers.tiles.backup_restore;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.util.Duration;

public class MyButtonSkin extends ButtonSkin {

    public MyButtonSkin(Button control) {
        super(control);

        final RotateTransition rotateTransition = new RotateTransition(Duration.millis(3000), control);
        rotateTransition.setByAngle(360);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(3000),control);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setToX(Math.sin(control.getLayoutX()*300));
        translateTransition.setToY(-90);
//        final FadeTransition fadeIn = new FadeTransition(Duration.millis(100));
//        fadeIn.setNode(control);
//        fadeIn.setToValue(1);
//        control.setOnMouseEntered(e -> fadeIn.playFromStart());
        control.setOnMouseEntered(e -> {rotateTransition.playFromStart();  translateTransition.play();});

        final FadeTransition fadeOut = new FadeTransition(Duration.millis(100));
        fadeOut.setNode(control);
        fadeOut.setToValue(0.5);
        control.setOnMouseExited(e -> fadeOut.playFromStart());

        control.setOpacity(0.5);
    }

}