package seedu.address.ui;

import java.io.File;
import java.io.IOException;
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

    public static final String RIDE_PAGE_PATH = "src/main/resources/docs/ride.html";
    private static final String RIDE_PAGE_TITLE = "Ride Information";
    private static final String TEXT_REPLACEMENT_JAVASCRIPT =
        "function updateRide(listOfFields) {" +
            "for (index in listOfFields) {" +
                "document.getElementById(listOfFields[index][0]).innerHTML = listOfFields[index][1];" +
            "}" +
        "}";

    private static final String FXML = "BrowserPanel.fxml";
    private static final String MESSAGE_FILE_ERROR = "%1$s cannot be accessed!";
    private static final String URL_HEADER = "file:/";

    private String queuedJavascript = "";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        try {
            RidePageGenerator.getInstance().generateHtml(RIDE_PAGE_TITLE, Ride.RIDE_TEMPLATE, RIDE_PAGE_PATH);

            loadDefaultPage();
            registerAsAnEventHandler(this);

            browser.getEngine().setJavaScriptEnabled(true);
            browser.getEngine().getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {

                        if (newState == Worker.State.SUCCEEDED) {
                            runQueuedJavascript();
                        }

                    }
                });
        } catch (IOException ie) {
            ie.printStackTrace();
            logger.warning("Unable to create ride page.");
        }
    }

    private void loadRidePage(Ride ride) throws IOException {
        loadPage(RIDE_PAGE_PATH);
        int totalFields = ride.getFieldHeaders().size();
        StringBuilder parameters = new StringBuilder();
        parameters.append(String.format("[\"%1s\", \"%2s\"], ", "name", ride.getName().fullName));
        for (int i = 0; i < totalFields; i++) {
            parameters.append(String.format("[\"%1s\", \"%2s\"], ", ride.getFieldHeaders().get(i), ride.getFields().get(i)));
        }
        queuedJavascript = String.format("updateRide([%1s])", parameters.toString());
        System.out.println(queuedJavascript);
    }

    private void runQueuedJavascript() {
        browser.getEngine().executeScript(TEXT_REPLACEMENT_JAVASCRIPT);
        browser.getEngine().executeScript(queuedJavascript);
    }

    public void loadPage(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists() || !file.canRead()) {
            throw new IOException(String.format(MESSAGE_FILE_ERROR, fileName));
        }
        String url = URL_HEADER + file.getAbsolutePath();
        Platform.runLater(() -> browser.getEngine().load(url));
    }


    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    public void loadDefaultPage() {
        try {
            loadPage(HelpWindow.SHORT_HELP_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }
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
        try {
            loadRidePage(event.getNewSelection());
        } catch (IOException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }
    }
}
