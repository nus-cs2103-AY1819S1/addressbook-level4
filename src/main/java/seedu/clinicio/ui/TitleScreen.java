package seedu.clinicio.ui;

import javafx.scene.layout.Region;

/**
 * A ui to display the title screen
 */
public class TitleScreen extends UiPart<Region> {
    private static final String FXML = "TitleScreen.fxml";

    public TitleScreen() {
        super(FXML);
    }
}
