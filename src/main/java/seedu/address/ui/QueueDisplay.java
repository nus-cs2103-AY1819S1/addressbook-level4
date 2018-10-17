package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.QueueUpdatedEvent;
import seedu.address.model.PatientQueue;

public class QueueDisplay extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "QueueDisplay.html";

    private static final String FXML = "QueueDisplay.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView display;

    public QueueDisplay() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadQueueDisplay(null);
        registerAsAnEventHandler(this);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> display.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadQueueDisplay(PatientQueue patientQueue) {
        String queueDisplayString = patientQueue != null ? patientQueue.displayQueue() : "QUEUE IS EMPTY";
        String queueDisplayPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE).toExternalForm();
        queueDisplayPage += "?queue=";
        queueDisplayPage += queueDisplayString;
        loadPage(queueDisplayPage);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        display = null;
    }

    @Subscribe
    private void handleQueueUpdatedEvent(QueueUpdatedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadQueueDisplay(event.getPatientQueue());
    }
}
