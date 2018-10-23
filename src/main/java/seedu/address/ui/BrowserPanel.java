package seedu.address.ui;

import java.io.FileInputStream;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.EmailViewEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonProfileViewEvent;
import seedu.address.model.EmailModel;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    @FXML
    private StackPane personProfilePlaceholder;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    public void loadPersonProfile(Person person) {
        PersonProfile personProfile = new PersonProfile(person);
        if (getClass() == null) {
            System.out.println("1");
        } else if (getClass().getResource("fail2.png") == null) {
            System.out.println("2");
        } else if (getClass().getResource("fail2.png").toExternalForm() == null) {
            System.out.println("3");
        }
        ImageView imgView = new ImageView(getClass().getResource("fail2.png").toExternalForm());
        personProfilePlaceholder.getChildren().add(imgView);
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
    private void handlePersonProfileViewEvent(PersonProfileViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonProfile(event.getPersonSelected());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection());
    }

    //@@author EatOrBeEaten
    @Subscribe
    private void handleEmailViewEvent(EmailViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadEmail(event.getEmailModel());
    }

    /**
     * Loads HTML text preview of email.
     * @param emailModel The emailModel containing the saved email.
     */
    public void loadEmail(EmailModel emailModel) {
        Platform.runLater(() -> browser.getEngine().loadContent(emailModel.getPreview()));
    }

}
