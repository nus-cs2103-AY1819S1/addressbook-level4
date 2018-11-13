package seedu.address.ui;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.SortPanelViewEvent;
import seedu.address.commons.events.ui.SwapPanelViewEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.SortCommand.SortOrder;
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

    // A HashMap of panel names to their corresponding swappable objects.
    private HashMap<SwappablePanelName, Swappable> panels = new HashMap<>();

    // The current panel to display in the Ui container.
    private Swappable currentPanel;

    // Swappable panels
    private BlankPanel blankPanel;
    private MedicationView medicationView;
    private HistoryView historyView;
    private DietView dietView;
    private AppointmentView appointmentView;
    private VisitorView visitorView;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;

    @FXML
    private StackPane panelPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

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
     * Initalises the members of the MainWindow and the HashMap of swappable panels.
     */
    void init() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());

        // Construct swappables
        blankPanel = new BlankPanel();
        medicationView = new MedicationView(logic.getFilteredPersonList());
        historyView = new HistoryView(logic.getFilteredPersonList());
        dietView = new DietView(logic.getFilteredPersonList());
        appointmentView = new AppointmentView(logic.getFilteredPersonList());
        visitorView = new VisitorView(logic.getFilteredPersonList());

        // Set up the HashMap of Swappable panels
        panels.put(SwappablePanelName.BLANK, blankPanel);
        panels.put(SwappablePanelName.MEDICATION, medicationView);
        panels.put(SwappablePanelName.HISTORY, historyView);
        panels.put(SwappablePanelName.DIET, dietView);
        panels.put(SwappablePanelName.APPOINTMENT, appointmentView);
        panels.put(SwappablePanelName.VISITOR, visitorView);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        setCurrentPanel(SwappablePanelName.BLANK);

        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getHealthBaseFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void setCurrentPanel(SwappablePanelName panelName) {
        Swappable panel = panels.get(panelName);

        if (panel == null) {
            return;
        }

        if (currentPanel != null) {
            // Reminder that all Swappable* implementations should extend UiPart<Region>
            panelPlaceholder.getChildren().remove(((UiPart<Region>) currentPanel).getRoot());
        }

        panelPlaceholder.getChildren().add(((UiPart<Region>) panel).getRoot());

        currentPanel = panel;
        currentPanel.refreshView();
    }

    /**
     * Sorts the current panel given a sorting order.
     */
    void sortCurrentPanel(SortOrder order, int[] colIdx) {
        Objects.requireNonNull(order);
        Objects.requireNonNull(colIdx);

        if (currentPanel == null) {
            return;
        }

        if (!(currentPanel instanceof Sortable)) {
            // TODO: Make it fail non-silently?
            return;
        }

        Sortable sortablePanel = (Sortable) currentPanel;
        sortablePanel.sortView(order, colIdx);
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
    }

    //==================== Event Handling Code ===============================================================

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }


    @Subscribe
    private void handleSwapPanelViewEvent(SwapPanelViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setCurrentPanel(event.getSwappablePanelName());
    }

    @Subscribe
    private void handleSortPanelViewEvent(SortPanelViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        sortCurrentPanel(event.getOrder(), event.getColIdx());
    }
}
