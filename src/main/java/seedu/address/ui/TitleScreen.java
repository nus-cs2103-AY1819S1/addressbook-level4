package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * A ui that display the app title screen.
 */
public class TitleScreen extends UiPart<Region> {
    private static final String FXML = "TitleScreen.fxml";

    public TitleScreen() {
        super(FXML);
    }
}
