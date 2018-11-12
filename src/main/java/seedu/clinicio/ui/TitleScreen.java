package seedu.clinicio.ui;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * A ui to display the title screen
 */
public class TitleScreen extends UiPart<Region> {
    private static final String FXML = "TitleScreen.fxml";

    private FadeTransition transition;

    @FXML
    private ImageView titleImage;

    public TitleScreen() {
        super(FXML);
        setAnimation();
        startAnimation();
    }

    /**
     * Setup the animation.
     */
    private void setAnimation() {
        transition = new FadeTransition();
        transition.setNode(titleImage);
        transition.setDuration(Duration.seconds(1));
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
    }

    /**
     * Start the animation.
     */
    void startAnimation() {
        transition.play();
    }
}
