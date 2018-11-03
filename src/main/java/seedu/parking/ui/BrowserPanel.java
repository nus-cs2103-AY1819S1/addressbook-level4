package seedu.parking.ui;

import java.net.URLEncoder;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonArray;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.parking.commons.core.LogsCenter;
import seedu.parking.commons.events.ui.CarparkPanelSelectionChangedEvent;
import seedu.parking.commons.events.ui.FilterResultChangedEvent;
import seedu.parking.commons.events.ui.FindResultChangedEvent;
import seedu.parking.commons.events.ui.ListCarparkRequestEvent;
import seedu.parking.model.carpark.Carpark;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String MAINPAGE_FILE_PATH = "/docs/MainPage.html";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        String mainUrl = getClass().getResource(MAINPAGE_FILE_PATH).toString() + "?isDefault=1";
        loadPage(mainUrl);
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the car park HTML file with a json data parsed in UTF-8.
     */
    private void loadCarparkPage(Carpark carpark) throws Exception {
        String indivUrl = getClass().getResource(MAINPAGE_FILE_PATH).toString() + "?json=" + carpark.toJson();
        loadPage(indivUrl);
    }

    /**
     * Loads the car park HTML file with a list of car parks in json data parsed in UTF-8.
     */
    private void loadCarparkPage(Carpark[] carparks) throws Exception {
        JsonArray arr = new JsonArray();
        for (int i = 0; i < carparks.length; i++) {
            arr.add(carparks[i].getCarparkNumber().value);
        }
        String utf8arr = URLEncoder.encode(arr.toString(), "UTF-8");
        String indivUrl = getClass().getResource(MAINPAGE_FILE_PATH).toString() + "?jsonArr=" + utf8arr;
        loadPage(indivUrl);
    }

    /**
     * Loads a URL page with a url parsed in.
     */
    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleCarparkPanelSelectionChangedEvent(CarparkPanelSelectionChangedEvent event) throws Exception {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCarparkPage(event.getNewSelection());
    }

    @Subscribe
    private void handleListCarparkRequestEvent(ListCarparkRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String mainUrl = getClass().getResource(MAINPAGE_FILE_PATH).toString() + "?isDefault=1";
        loadPage(mainUrl);
    }

    @Subscribe
    private void handleCarparkFindResultChangedEvent(FindResultChangedEvent event) throws Exception {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCarparkPage(event.getreturnList());
    }

    @Subscribe
    private void handleCarparkFilterResultChangedEvent(FilterResultChangedEvent event)throws Exception {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCarparkPage(event.getreturnList());
    }
}
