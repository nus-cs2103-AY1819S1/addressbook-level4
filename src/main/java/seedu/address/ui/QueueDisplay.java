package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

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

        loadQueueDisplay();
        registerAsAnEventHandler(this);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> display.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadQueueDisplay() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        display = null;
    }

//    @Subscribe
//    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
//        logger.info(LogsCenter.getEventHandlingLogMessage(event));
//        loadPersonPage(event.getNewSelection());
//    }
}
