package seedu.address.ui;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Controller for notifications
 */

public class NotificationWindow {

    /**
     * Creates and displays a notification
     */
    public static void display(String title, String message) {

        Stage window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle(title);
        window.setX(0);
        window.setY(0);
        window.setWidth(200);
        window.setHeight(100);

        Label label = new Label();
        label.setText(message);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
        window.setAlwaysOnTop(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> window.close());
        delay.play();
    }
}
