package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.util.RidePageGenerator;
import seedu.address.model.ride.Ride;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String RIDE_INFO_PATH =
        "/docs/ride.html";
    private static final String TEXT_REPLACEMENT_JAVASCRIPT =
        "function updateRide(name, status, waitTime) {" +
            "document.getElementById(\"name\").innerHTML = name;" +
            "document.getElementById(\"status\").innerHTML = status;" +
            "document.getElementById(\"waitTime\").innerHTML = waitTime;" +
        "}";

    private static final String FXML = "BrowserPanel.fxml";

    private String queuedJavascript = "";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        RidePageGenerator.generateRidePage();

        loadDefaultPage();
        registerAsAnEventHandler(this);

        browser.getEngine().setJavaScriptEnabled(true);
        browser.getEngine().getLoadWorker().stateProperty().addListener(
            new ChangeListener<Worker.State>() {
                @Override public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {

                    if (newState == Worker.State.SUCCEEDED) {
                        runQueuedJavascript();
                    }

                }
            });
    }

    private void loadRidePage(Ride ride) {
        String ridePage = getClass().getResource(RIDE_INFO_PATH).toString();
        loadPage(ridePage);
        queuedJavascript = String.format("updateRide(\"%1s\", \"%2s\", \"%3s\")",
            ride.getName().fullName, ride.getStatus(), ride.getWaitingTime());
    }

    private void runQueuedJavascript() {
        browser.getEngine().executeScript(TEXT_REPLACEMENT_JAVASCRIPT);
        browser.getEngine().executeScript(queuedJavascript);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }


    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultHelpPage = getClass().getResource(HelpWindow.SHORT_HELP_FILE_PATH);
        loadPage(defaultHelpPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadRidePage(event.getNewSelection());
    }
}
