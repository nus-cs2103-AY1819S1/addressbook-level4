package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class LoginIntroduction extends UiPart<Region> {

    public static final String TEXT = "Please log in";

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "LoginIntroduction.fxml";

    @FXML
    private Label introductionDisplay;

    public LoginIntroduction() {
        super(FXML);
        introductionDisplay.setText(TEXT);
        registerAsAnEventHandler(this);
    }
}
