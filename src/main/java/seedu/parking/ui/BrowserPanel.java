package seedu.parking.ui;

import java.io.UnsupportedEncodingException;
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

    public static final String DEFAULT_PAGE =
            "https://cs2103-ay1819s1-t09-4.github.io/main/DummySearchPage.html?isDefault=1";
    public static final String SEARCH_PAGE_URL =
            "https://cs2103-ay1819s1-t09-4.github.io/main/DummySearchPage.html?";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the car park HTML file with a json data parsed in UTF-8.
     */
    private void loadCarparkPage(Carpark carpark) {
        try {
            loadPage(SEARCH_PAGE_URL + "json=" + carpark.toJson());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the car park HTML file with a list of car parks in json data parsed in UTF-8.
     */
    private void loadCarparkPage(Carpark[] carparks) {
        JsonArray arr = new JsonArray();
        for (int i = 0; i < carparks.length; i++) {
            arr.add(carparks[i].getCarparkNumber().value);
        }
        try {
            String utf8arr = URLEncoder.encode(arr.toString(), "UTF-8");
            loadPage(SEARCH_PAGE_URL + "jsonArr=" + utf8arr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a URL page with a url parsed in.
     */
    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleCarparkPanelSelectionChangedEvent(CarparkPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCarparkPage(event.getNewSelection());
    }

    @Subscribe
    private void handleListCarparkRequestEvent(ListCarparkRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDefaultPage();
    }

    @Subscribe
    private void handleCarparkFindResultChangedEvent(FindResultChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCarparkPage(event.getreturnList());
    }

    @Subscribe
    private void handleCarparkFilterResultChangedEvent(FilterResultChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCarparkPage(event.getreturnList());
    }
}
