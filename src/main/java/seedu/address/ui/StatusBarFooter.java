package seedu.address.ui;

import java.util.Objects;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeDirectoryEvent;
import seedu.address.commons.events.ui.LoginStatusEvent;
import seedu.address.commons.events.ui.LogoutStatusEvent;

//@@author chivent

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String LOGIN_STATUS_INITIAL = "Not connected to Google Photos";
    public static final String LOGIN_STATUS_UPDATED = "Connected to Google Photos as: %s";
    public static final String LOGOUT_MESSAGE = "User logged out";

    public static final String DIRECTORY_ERROR = "Unable to detect directory location";

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private StatusBar loginStatus;

    @FXML
    private StatusBar directoryDisplay;


    public StatusBarFooter(String user, String currentDirectory) {
        super(FXML);
        if (user != null) {
            setLoginStatus(String.format(LOGIN_STATUS_UPDATED, user));
        } else {
            setLoginStatus(LOGIN_STATUS_INITIAL);
        }
        setDirectoryDisplay(Objects.requireNonNullElse(currentDirectory, DIRECTORY_ERROR));
        registerAsAnEventHandler(this);
    }

    private void setLoginStatus(String status) {
        Platform.runLater(() -> loginStatus.setText(status));
    }

    private void setDirectoryDisplay(String status) {
        Platform.runLater(() -> directoryDisplay.setText(status));
    }

    @Subscribe
    public void handleLoginStatusEvent(LoginStatusEvent event) {
        if (event.loggedIn) {
            setLoginStatus(String.format(LOGIN_STATUS_UPDATED, event.user));
            logger.info(LogsCenter.getEventHandlingLogMessage(event, "User logged in as " + event.user));
        } else {
            setLoginStatus(LOGIN_STATUS_INITIAL);
            logger.info(LogsCenter.getEventHandlingLogMessage(event, "User is not logged in to google photos"));
        }
    }

    @Subscribe
    public void handleDirectoryChangeEvent(ChangeDirectoryEvent event) {
        if (event.directory.isEmpty()) {
            setDirectoryDisplay(DIRECTORY_ERROR);
            logger.info(LogsCenter.getEventHandlingLogMessage(event,
                    "User's current directory location could not be determined"));
        } else {
            setDirectoryDisplay(event.directory);
            logger.info(LogsCenter.getEventHandlingLogMessage(event, "User's current directory: " + event.directory));
        }
    }

    @Subscribe
    public void handleLogoutStatusEvent(LogoutStatusEvent event) {
        setLoginStatus(LOGIN_STATUS_INITIAL);
        logger.info(LogsCenter.getEventHandlingLogMessage(event, LOGOUT_MESSAGE));
    }
}
