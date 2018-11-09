package seedu.learnvocabulary.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.learnvocabulary.MainApp;
import seedu.learnvocabulary.commons.core.LogsCenter;
import seedu.learnvocabulary.commons.events.ui.WordPanelSelectionChangedEvent;
import seedu.learnvocabulary.model.word.Word;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {
    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL = "https://cs2103-ay1819s1-t10-3.github.io/main/WordSearchPage.html";

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

    private void loadWordPage(Word word) {
        String searchPage = SEARCH_PAGE_URL;
        loadPage(searchPage + "?name=" + word.getName().fullName
                + "&meaning=" + word.getMeaning().fullMeaning.replaceAll("%", "%25"));
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
    private void handleWordPanelSelectionChangedEvent(WordPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadWordPage(event.getNewSelection());
    }
}
