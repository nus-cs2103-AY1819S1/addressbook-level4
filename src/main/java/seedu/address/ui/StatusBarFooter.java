package seedu.address.ui;

import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoginStatusEvent;

//@@author chivent

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String LOGIN_STATUS_INITIAL = "Not connected to Google Photos";
    public static final String LOGIN_STATUS_UPDATED = "Connected to Google Photos as: %s";

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private StatusBar loginStatus;


    public StatusBarFooter() {
        super(FXML);
        setSyncStatus(LOGIN_STATUS_INITIAL);
        registerAsAnEventHandler(this);
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> loginStatus.setText(status));
    }

    @Subscribe
    public void handleLoginStatusEvent(LoginStatusEvent event) {
        if (event.loggedIn) {
            setSyncStatus(String.format(LOGIN_STATUS_UPDATED, event.user));
            logger.info(LogsCenter.getEventHandlingLogMessage(event, "User logged in as " + event.user));
        } else {
            setSyncStatus(LOGIN_STATUS_INITIAL);
            logger.info(LogsCenter.getEventHandlingLogMessage(event, "User is not logged in to google photos"));
        }
    }
}
