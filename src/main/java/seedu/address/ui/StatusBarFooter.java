package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.FoodZoomChangedEvent;
import seedu.address.commons.events.model.OrderBookChangedEvent;
import seedu.address.commons.events.model.UserLoggedInEvent;
import seedu.address.commons.events.model.UserLoggedOutEvent;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";
    public static final String MESSAGE_LOGGED_IN_AS = "Logged in as : ";
    public static final String MESSAGE_LOGGED_OUT = "Not logged in.";
    public static final String MESSAGE_UPDATING_STATUS_BAR = "Updating status bar.";
    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);
    private static final String FXML = "StatusBarFooter.fxml";
    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();
    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;
    @FXML
    private StatusBar usernameText;


    public StatusBarFooter(Path saveLocation) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation(Paths.get(".").resolve(saveLocation).toString());
        registerAsAnEventHandler(this);
        setUsername(MESSAGE_LOGGED_OUT);
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    private void setSaveLocation(String location) {
        Platform.runLater(() -> saveLocationStatus.setText(location));
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> syncStatus.setText(status));
    }

    private void setUsername(String username) {
        Platform.runLater(() -> usernameText.setText(username));
    }

    @Subscribe
    public void handleFoodZoomChangedEvent(FoodZoomChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
    }

    @Subscribe
    private void handleUserLoggedInEvent(UserLoggedInEvent event) {
        logger.info(MESSAGE_UPDATING_STATUS_BAR);
        setUsername(MESSAGE_LOGGED_IN_AS + event.data.getUsername().toString());
    }

    @Subscribe
    private void handleUserLogoutEvent(UserLoggedOutEvent event) {
        logger.info(MESSAGE_UPDATING_STATUS_BAR);
        setUsername(MESSAGE_LOGGED_OUT);
    }
}
