package seedu.scheduler.ui;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * PopUp window for alert
 */
public class PopUp {

    /**
     * Display the Popup window
     * @param title
     * @param message
     */
    public static void display(String title, String message) {
        Stage window = new Stage();

        // the popup window needs to be taken care of first
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth((250));

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 20, 20, 20));
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
