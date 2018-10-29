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
import seedu.address.commons.events.ui.ModulePanelSelectionChangedEvent;
import seedu.address.commons.events.ui.OccasionPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PERSON_PAGE_URL =
            "https://www.google.com/search?q=";
    public static final String SEARCH_MODULE_PAGE_URL = "https://nusmods.com/modules/";
    public static final String SEARCH_OCCASION_PAGE_URL =
            "https://www.google.com/search?q=";

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

    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PERSON_PAGE_URL + person.getName().fullName);
    }

    private void loadModulePage (Module module) {
        loadPage(SEARCH_MODULE_PAGE_URL + module.getModuleCode().fullModuleCode);
    }

    private void loadOccasionPage (Occasion occasion) {
        loadPage(SEARCH_OCCASION_PAGE_URL + occasion.getOccasionName().fullOccasionName);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
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
        loadPersonPage(event.getNewSelection());
    }

    @Subscribe
    private void handleModulePanelSelectionChangedEvent(ModulePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadModulePage(event.getNewSelection());
    }

    @Subscribe
    private void handleoccasionPanelSelectionChangedEvent(OccasionPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadOccasionPage(event.getNewSelection());
    }
}
