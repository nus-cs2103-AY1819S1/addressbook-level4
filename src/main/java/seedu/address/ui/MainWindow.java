package seedu.address.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DisplayAutoMatchResultRequestEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.LoginSuccessEvent;
import seedu.address.commons.events.ui.LogoutRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.AutoMatchResult;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Service;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Stage loginStage;
    private Logic logic;
    private boolean hasFilledParts;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private ServiceListPanel photoListPanel;
    private ServiceListPanel hotelListPanel;
    private ServiceListPanel cateringListPanel;
    private ServiceListPanel dressListPanel;
    private ServiceListPanel ringListPanel;
    private ServiceListPanel transportListPanel;
    private ServiceListPanel invitationListPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    private LoginWindow loginWindow;
    private StatusBarFooter statusBarFooter;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane photoListPanelPlaceholder;

    @FXML
    private StackPane hotelListPanelPlaceholder;

    @FXML
    private StackPane cateringListPanelPlaceholder;

    @FXML
    private StackPane dressListPanelPlaceholder;

    @FXML
    private StackPane ringListPanelPlaceholder;

    @FXML
    private StackPane transportListPanelPlaceholder;

    @FXML
    private StackPane invitationListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);
        primaryStage.centerOnScreen();

        setAccelerators();
        registerAsAnEventHandler(this);

        helpWindow = new HelpWindow();
        hasFilledParts = false;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Creates a new login stage and displays the login window
     */
    void displayLoginWindow() {
        // Create new login stage for login window
        loginStage = new Stage();
        loginStage.centerOnScreen();
        loginWindow = new LoginWindow(loginStage, config, prefs, logic);
        loginStage.show();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        // Fill up person list
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        // Show display
        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        // Show status bar
        statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        // Show command box
        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        // Set to true that parts have been initialised.
        hasFilledParts = true;
    }

    /**
     * Handles logout event
     * Hides main window and displays login window.
     */
    public void handleLogout() {
        hide();
        displayLoginWindow();
    }

    /**
     * Sieves out the contacts based on the service type.
     * @param resultMap Map of all related contacts.
     * @param serviceType Type of service.
     * @return List of Contacts of this service type.
     */
    private ObservableList<Contact> getfilteredServiceList(
            Map<Contact, Collection<Service>> resultMap, String serviceType) {
        List<Contact> serviceList = new ArrayList<>();
        resultMap.forEach(((
                contact, temp) -> resultMap.get(contact)
                .forEach(service -> {
                    if (service.getName().equals(serviceType)) {
                        serviceList.add(contact);
                    }
                })));
        return FXCollections.observableList(serviceList);
    }

    /**
     * Handles the display of suitable service providers for the client using the automatch results
     */
    public void showAutoMatchDisplay() {
        AutoMatchResult autoMatchResult = logic.getAutoMatchResult();
        Map<Contact, Collection<Service>> resultMap = autoMatchResult.getContactAndServicesMap();

        ObservableList<Contact> photographerList = getfilteredServiceList(resultMap, "photographer");
        ObservableList<Contact> hotelList = getfilteredServiceList(resultMap, "hotel");
        ObservableList<Contact> cateringList = getfilteredServiceList(resultMap, "catering");
        ObservableList<Contact> dressList = getfilteredServiceList(resultMap, "dress");
        ObservableList<Contact> ringList = getfilteredServiceList(resultMap, "ring");
        ObservableList<Contact> transportList = getfilteredServiceList(resultMap, "transport");
        ObservableList<Contact> invitationList = getfilteredServiceList(resultMap, "invitation");

        // TODO Fill up each service panel
        photoListPanel = new ServiceListPanel(photographerList, "photographer");
        photoListPanelPlaceholder.getChildren().add(photoListPanel.getRoot());

        hotelListPanel = new ServiceListPanel(hotelList, "hotel");
        hotelListPanelPlaceholder.getChildren().add(hotelListPanel.getRoot());

        cateringListPanel = new ServiceListPanel(cateringList, "catering");
        cateringListPanelPlaceholder.getChildren().add(cateringListPanel.getRoot());

        dressListPanel = new ServiceListPanel(dressList, "dress");
        dressListPanelPlaceholder.getChildren().add(dressListPanel.getRoot());

        ringListPanel = new ServiceListPanel(ringList, "ring");
        ringListPanelPlaceholder.getChildren().add(ringListPanel.getRoot());

        transportListPanel = new ServiceListPanel(transportList, "transport");
        transportListPanelPlaceholder.getChildren().add(transportListPanel.getRoot());

        invitationListPanel = new ServiceListPanel(invitationList, "invitation");
        invitationListPanelPlaceholder.getChildren().add(invitationListPanel.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        logger.fine("Showing main window.");
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    void releaseResources() {
        if (browserPanel != null) {
            browserPanel.freeResources();
        }
    }

    @Subscribe
    private void handleLoginSuccessEvent(LoginSuccessEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        show();
        if (!hasFilledParts) {
            fillInnerParts();
        } else {
            statusBarFooter.setUsernameStatus("username: " + UserPrefs.getUsernameAndRoleToDisplay());
        }
    }


    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    @Subscribe
    private void handleLogoutEvent(LogoutRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleLogout();
    }

    @Subscribe
    private void handleAutoMatchEvent(DisplayAutoMatchResultRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showAutoMatchDisplay();
    }
}
