package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.WishPanelSelectionChangedEvent;
import seedu.address.model.wish.Wish;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL = "https://www.google.com";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private String agent = "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en)  Version/3.0 Mobile/1A543a Safari/419.3";

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);
        // To prevent triggering events for typing inside the loaded Web page.

        getRoot().setOnKeyPressed(Event::consume);

        browser.getEngine().setJavaScriptEnabled(true);
        browser.getEngine().setUserAgent(agent);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadWishPage(Wish wish) {
        loadPage(wish.getUrl().toString());
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(SEARCH_PAGE_URL);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleWishPanelSelectionChangedEvent(WishPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadWishPage(event.getNewSelection());
    }
}
