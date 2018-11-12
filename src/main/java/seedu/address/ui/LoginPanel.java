package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the login screen.
 */
public class LoginPanel extends UiPart<Region> {

    private static final String FXML = "LoginPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LoginPanel.class);

    public LoginPanel() {
        super(FXML);
    }
}
