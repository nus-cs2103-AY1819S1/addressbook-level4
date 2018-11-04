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
import seedu.address.commons.events.ui.FacultyLocationDisplayChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RandomMeetingLocationGeneratedEvent;
import seedu.address.logic.EmbedGoogleMaps;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class LocationDisplayPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "locationLanding.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String API_KEY = "AIza"
            + "SyAUAMhSz-X72KN47J2YdyCE5VtDtcSmvmU";


    private static String locationContentA = "<iframe width=\"1150\" height=\"550\" frameborder=\"0\""
            + "style=\"border:0\" src=\""
            + "https://www.google"
            + ".com/maps/embed/v1/place?q=place_id:";

    private static String locationContentB = "&key=AIzaSyAUAMhSz-X72KN47J2YdyCE5VtDtcSmvmU\" "
            + "allowfullscreen></iframe>";

    private static final String FXML = "LocationDisplayPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView locationBrowser;

    public LocationDisplayPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonLocation(Person person) {
        loadIframe(prepareLocationContent(person));
    }

    private void loadRandomMeetingLocation(String placeId) {
        loadIframe(prepareLocationContent(placeId));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> locationBrowser.getEngine().load(url));
    }

    /**
     * Prepares the final iframe location content string with the Faculty of the Person.
     */
    public String prepareLocationContent(Person person) {
        String faculty = person.getFaculty().toString();
        String placeId = EmbedGoogleMaps.getPlaceId(faculty);
        String finalLocationContent = locationContentA + placeId + locationContentB;
        return finalLocationContent;
    }

    /**
     * Overloaded method that prepares the final iframe location content string using placeId directly instead.
     */
    public String prepareLocationContent(String placeId) {
        String finalLocationContent = locationContentA + placeId + locationContentB;
        return finalLocationContent;
    }

    public void loadIframe(String iframeContent) {
        locationBrowser.getEngine().loadContent(iframeContent);
    }

    /**
     * Loads a default HTML file.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        locationBrowser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonLocation(event.getNewSelection());
    }

    @Subscribe
    private void handleShowFacultyLocationSelectionEvent (FacultyLocationDisplayChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonLocation(event.getSelectedPerson());
    }

    @Subscribe
    private void handleRandomMeetingLocationGeneratedEvent(RandomMeetingLocationGeneratedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadRandomMeetingLocation(event.getMeetingPlaceId());
    }
}
