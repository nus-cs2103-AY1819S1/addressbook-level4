package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

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
import seedu.address.commons.events.ui.AssignmentListEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.LeaveListEvent;
import seedu.address.commons.events.ui.ListPickerSelectionChangedEvent;
import seedu.address.commons.events.ui.LogoutEvent;
import seedu.address.commons.events.ui.PersonListEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.SuccessfulLoginEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private AssignmentListPanel assignmentListPanel;
    private PersonListPanel personListPanel;
    private LeaveListPanel leaveListPanel;
    private PersonListPanel archivedListPanel;
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    private ListPicker listPicker;

    // Independent UI parts for login.
    private LoginIntroduction loginIntroduction;
    private LoginForm loginForm;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane assignmentListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane listPickerPlaceholder;

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

        setAccelerators();
        registerAsAnEventHandler(this);

        helpWindow = new HelpWindow();
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
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        fillPersonListParts();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        assignmentListPanel = new AssignmentListPanel(logic.getFilteredAssignmentList());

        listPicker = new ListPicker();
        listPickerPlaceholder.getChildren().add(listPicker.getRoot());

        archivedListPanel = new PersonListPanel(logic.getArchivedPersonList());
    }

    /**
     * Fills up the window with a login screen
     */
    void fillLoginParts() {
        loginIntroduction = new LoginIntroduction();
        commandBoxPlaceholder.getChildren().add(loginIntroduction.getRoot());

        loginForm = new LoginForm();
        personListPanelPlaceholder.getChildren().add(loginForm.getRoot());
    }

    /**
     * Fills up the person list placeholder with the person list
     */
    void fillPersonListParts() {
        if (personListPanel == null) {
            personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        }
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    /**
     * Fills up the person list placeholder with the leave application list
     */
    void fillLeaveParts() {
        if (leaveListPanel == null) {
            leaveListPanel = new LeaveListPanel(logic.getFilteredLeaveApplicationList());
        }
        personListPanelPlaceholder.getChildren().add(leaveListPanel.getRoot());
    }

    /**
     * Fills up the person list placeholder with the leave application list
     */
    void fillAssignmentListParts() {
        if (assignmentListPanel == null) {
            assignmentListPanel = new AssignmentListPanel(logic.getFilteredAssignmentList());
        }
        personListPanelPlaceholder.getChildren().add(assignmentListPanel.getRoot());
    }

    /**
     * Listens for a login Event from the EventBus. This will be triggered when a LoginEvent is pushed to the EventBus.
     * @param loginEvent The login information
     */
    @Subscribe
    void processLogin(SuccessfulLoginEvent loginEvent) {
        removeLoginWindow();
        fillInnerParts();
    }

    /**
     * Listens for a login Event from the EventBus. This will be triggered when a LoginEvent is pushed to the EventBus.
     * @param logoutEvent The login information
     */
    @Subscribe
    void processLogout(LogoutEvent logoutEvent) {
        removeInnerElements();
        fillLoginParts();
    }

    /**
     * Listens for a person list Event from the EventBus. This will be triggered when a PersonListEvent is pushed
     * to the EventBus.
     * @param personListEvent The event information
     */
    @Subscribe
    void processPersonList(PersonListEvent personListEvent) {
        removePersonListPanelPlaceholderElements();
        fillPersonListParts();
    }

    /**
     * Listens for a leave list Event from the EventBus. This will be triggered when a LeaveListEvent is pushed
     * to the EventBus.
     * @param leaveListEvent The event information
     */
    @Subscribe
    void processLeaveList(LeaveListEvent leaveListEvent) {
        removePersonListPanelPlaceholderElements();
        fillLeaveParts();
    }

    /**
     * Listens for a assignment list Event from the EventBus. This will be triggered when a AssignmentListEvent
     * is pushed to the EventBus.
     * @param assignmentListEvent The event information
     */
    @Subscribe
    void processAssignmentList(AssignmentListEvent assignmentListEvent) {
        removePersonListPanelPlaceholderElements();
        fillAssignmentListParts();
    }

    private void removeLoginWindow() {
        commandBoxPlaceholder.getChildren().remove(loginIntroduction.getRoot());
        personListPanelPlaceholder.getChildren().remove(loginForm.getRoot());
    }

    /**
     * Removes the elements built in fillInnerParts() to reset to a blank screen.
     */
    private void removeInnerElements() {

        this.releaseResources();
        browserPlaceholder.getChildren().remove(browserPanel.getRoot());
        removePersonListPanelPlaceholderElements();
        resultDisplayPlaceholder.getChildren().remove(resultDisplay.getRoot());
        statusbarPlaceholder.getChildren().remove(statusBarFooter.getRoot());
        commandBoxPlaceholder.getChildren().remove(commandBox.getRoot());
        listPickerPlaceholder.getChildren().remove(listPicker.getRoot());
    }

    /**
     * Removes the elements in the person list panel placeholder.
     */
    private void removePersonListPanelPlaceholderElements() {
        if (personListPanel != null) {
            personListPanelPlaceholder.getChildren().remove(personListPanel.getRoot());
        }
        if (archivedListPanel != null) {
            personListPanelPlaceholder.getChildren().remove(archivedListPanel.getRoot());
        }
        if (leaveListPanel != null) {
            personListPanelPlaceholder.getChildren().remove(leaveListPanel.getRoot());
        }
        if (assignmentListPanel != null) {
            personListPanelPlaceholder.getChildren().remove(assignmentListPanel.getRoot());
        }
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
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    @Subscribe
    private void handleListPickerSelectionChangedEvent(ListPickerSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getNewSelection() == 2) {
            personListPanelPlaceholder.getChildren().remove(personListPanel.getRoot());
            personListPanelPlaceholder.getChildren().remove(assignmentListPanel.getRoot());
            personListPanelPlaceholder.getChildren().add(archivedListPanel.getRoot());
        }
        if (event.getNewSelection() == 1) {
            personListPanelPlaceholder.getChildren().remove(archivedListPanel.getRoot());
            personListPanelPlaceholder.getChildren().remove(assignmentListPanel.getRoot());
            personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        }
        if (event.getNewSelection() == 3) {
            personListPanelPlaceholder.getChildren().remove(archivedListPanel.getRoot());
            personListPanelPlaceholder.getChildren().remove(personListPanel.getRoot());
            personListPanelPlaceholder.getChildren().add(assignmentListPanel.getRoot());
        }
    }
}
