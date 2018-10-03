package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;

/**
 * The Title. Provides the basic application layout containing
 * the application header and title.
 */
public class Title extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(Title.class);
    private static final String FXML = "Title.fxml";

    public Title() {
        super(FXML);
    }

}
