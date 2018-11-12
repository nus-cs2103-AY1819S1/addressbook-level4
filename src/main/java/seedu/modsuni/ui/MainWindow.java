package seedu.modsuni.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.modsuni.commons.core.Config;
import seedu.modsuni.commons.core.GuiSettings;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.events.ui.DatabaseModulePanelSelectionChangedEvent;
import seedu.modsuni.commons.events.ui.ExitAppRequestEvent;
import seedu.modsuni.commons.events.ui.LogoutRequestEvent;
import seedu.modsuni.commons.events.ui.NewCommandResultAvailableEvent;
import seedu.modsuni.commons.events.ui.NewGenerateResultAvailableEvent;
import seedu.modsuni.commons.events.ui.SaveDisplayRequestEvent;
import seedu.modsuni.commons.events.ui.ShowDatabaseTabRequestEvent;
import seedu.modsuni.commons.events.ui.ShowHelpRequestEvent;
import seedu.modsuni.commons.events.ui.ShowStagedTabRequestEvent;
import seedu.modsuni.commons.events.ui.ShowTakenTabRequestEvent;
import seedu.modsuni.commons.events.ui.ShowUserTabRequestEvent;
import seedu.modsuni.commons.events.ui.StagedModulePanelSelectionChangedEvent;
import seedu.modsuni.commons.events.ui.TakenModulePanelSelectionChangedEvent;
import seedu.modsuni.logic.Logic;
import seedu.modsuni.model.UserPrefs;

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
    private StagedModuleListPanel stagedModuleListPanel;
    private TakenModuleListPanel takenModuleListPanel;
    private DatabaseModuleListPanel databaseModuleListPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    private UserTab userTabController;
    private SaveDisplay saveDisplay;
    private ModuleDisplay moduleDisplay;

    private BrowserPanel loadingPanel;
    private GenerateDisplay generateDisplay;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane outputDisplayPlaceholder;

    @FXML
    private StackPane moduleDisplayPlaceholder;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab userTab;

    @FXML
    private Tab modulesStagedTab;

    @FXML
    private Tab modulesTakenTab;

    @FXML
    private Tab databaseTab;

    @FXML
    private StackPane userTabPlaceHolder;

    @FXML
    private StackPane moduleListPanelPlaceholder;

    @FXML
    private StackPane stagedModuleListPanelPlaceholder;

    @FXML
    private StackPane takenModuleListPanelPlaceholder;

    @FXML
    private StackPane databaseModuleListPanelPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.browserPanel = new BrowserPanel();
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
     *
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
         * OutputDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or OutputDisplay.
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
        userTabController = new UserTab();
        userTabPlaceHolder.getChildren().add(userTabController.getRoot());

        saveDisplay = new SaveDisplay();
        generateDisplay = new GenerateDisplay();
        moduleDisplay = new ModuleDisplay();

        loadingPanel = new BrowserPanel(BrowserPanel.LOADING_PAGE);
        browserPlaceholder.getChildren().add(loadingPanel.getRoot());

        stagedModuleListPanel = new StagedModuleListPanel(logic.getFilteredStagedModuleList());
        stagedModuleListPanelPlaceholder.getChildren().add(stagedModuleListPanel.getRoot());

        takenModuleListPanel = new TakenModuleListPanel(logic.getFilteredTakenModuleList());
        takenModuleListPanelPlaceholder.getChildren().add(takenModuleListPanel.getRoot());

        databaseModuleListPanel = new DatabaseModuleListPanel(logic.getFilteredDatabaseModuleList());
        databaseModuleListPanelPlaceholder.getChildren().add(databaseModuleListPanel.getRoot());

        OutputDisplay outputDisplay = new OutputDisplay();
        outputDisplayPlaceholder.getChildren().add(outputDisplay.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    void show() {
        primaryStage.show();
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

    void releaseResources() {
        browserPlaceholder.getChildren().clear();
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

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    /**
     * Selects the User Tab
     */
    @Subscribe
    public void handleUserTabSelected(ShowUserTabRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (!userTab.isSelected()) {
            tabPane.getSelectionModel().select(userTab);
        }
    }

    /**
     * Selects the Staged Tab
     */
    @Subscribe
    public void handleStagedTabSelected(ShowStagedTabRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (!modulesStagedTab.isSelected()) {
            tabPane.getSelectionModel().select(modulesStagedTab);
        }
    }

    /**
     * Selects the Taken Tab
     */
    @Subscribe
    public void handleTakenTabSelected(ShowTakenTabRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (!modulesTakenTab.isSelected()) {
            tabPane.getSelectionModel().select(modulesTakenTab);
        }
    }

    /**
     * Selects the Database Tab
     */
    @Subscribe
    public void handleDatabaseTabSelected(ShowDatabaseTabRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (!databaseTab.isSelected()) {
            tabPane.getSelectionModel().select(databaseTab);
        }
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    @Subscribe
    private void handleNewCommandResultAvailableEvent(NewCommandResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(event.getToBeDisplayed().getRoot());
    }

    @Subscribe
    private void handleSaveDisplayRequestEvent(SaveDisplayRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(saveDisplay.getRoot());
    }

    /**
     * Updates the browserPlaceholder to the Generate UI.
     */
    @Subscribe
    private void handleNewGenerateResultAvailableEvent(NewGenerateResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(generateDisplay.getRoot());
    }

    /**
     * Selects a module in the staged module list
     */
    @Subscribe
    private void handleStagedModulePanelSelectionChangedEvent(StagedModulePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        moduleDisplay = new ModuleDisplay(event.getNewSelection());
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(moduleDisplay.getRoot());
    }

    /**
     * Selects a module in the taken module list
     */
    @Subscribe
    private void handleTakenModulePanelSelectionChangedEvent(TakenModulePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        moduleDisplay = new ModuleDisplay(event.getNewSelection());
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(moduleDisplay.getRoot());
    }

    /**
     * Selects a module in the database module list
     */
    @Subscribe
    private void handleDatabaseModulePanelSelectionChangedEvent(DatabaseModulePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        moduleDisplay = new ModuleDisplay(event.getNewSelection());
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(moduleDisplay.getRoot());
    }

    @Subscribe
    private void handleLogoutResetUiEvent(LogoutRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(loadingPanel.getRoot());
    }
}
