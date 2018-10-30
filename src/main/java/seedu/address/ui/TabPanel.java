package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookEventChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;


/**
 * Panel containing two tabs: {@code BrowserPanel} and {@code EventListPanel}.
 * Each panel is initialized as {@code BrowserPanel} and {@code EventListPanel} objects.
 */
public class TabPanel extends UiPart<Region> {

    private static final String FXML = "TabPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private BrowserPanel browserPanel;

    private EventListPanel eventListPanel;

    @FXML
    private TabPane tabPane;

    private SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

    @FXML
    private Tab webpageTab;

    @FXML
    private Tab eventsTab;

    public TabPanel(ObservableList<List<seedu.address.model.event.Event>> eventList) {
        super(FXML);

        browserPanel = new BrowserPanel();
        webpageTab.setContent(browserPanel.getRoot());

        eventListPanel = new EventListPanel(eventList);
        eventsTab.setContent(eventListPanel.getRoot());

        // set default tab
        selectionModel.select(eventsTab);

        registerAsAnEventHandler(this);
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public Tab getWebpageTab() {
        return webpageTab;
    }

    public Tab getEventsTab() {
        return eventsTab;
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        webpageTab = null;
        eventsTab = null;
    }

    @Subscribe
    private void handleAddressBookEventChangedEvent(AddressBookEventChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        // switch active tab
        selectionModel.select(eventsTab);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        // switch active tab
        selectionModel.select(webpageTab);
    }
}
