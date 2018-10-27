package seedu.address.ui;

import java.net.URL;
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
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ShowModuleRequestEvent;
import seedu.address.commons.events.ui.ShowOccasionRequestEvent;
import seedu.address.commons.events.ui.ShowPersonRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The MainWindow parent class from which children entity windows are spawned from.
 */
abstract class MainWindow extends UiPart<Stage> {

    public final Logger logger = LogsCenter.getLogger(getClass());

    private UserPrefs prefs;
    private Stage primaryStage;
    private HelpWindow helpWindow;

    private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private Config config;
    private Logic logic;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane moduleListPanelPlaceholder;

    @FXML
    private StackPane occasionListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem personMenuItem;

    @FXML
    private MenuItem moduleWindowItem;

    @FXML
    private MenuItem occasionWindowItem;



    MainWindow(String fxml, Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(fxml, primaryStage);
        this.primaryStage = primaryStage;
        helpWindow = new HelpWindow();
        this.config = config;
        this.prefs = prefs;
        this.logic = logic;
    }

    void hide() {
        primaryStage.hide();
    }

    void show() {
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    void setAccelerators() {
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
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    abstract void releaseResources();

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
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
     * Opens up the module window on this primaryStage.
     */
    @FXML
    private void handleModule() {
        URL moduleWindowUrl = getFxmlFileUrl("ModuleWindow.fxml");
        loadFxmlFile(moduleWindowUrl, primaryStage);

        MainWindow moduleWindow = new ModuleWindow(this.primaryStage, this.config, this.prefs, this.logic);

        moduleWindow.fillInnerParts();
    }

    /**
     * Opens up the persons window on this primaryStage.
     */
    @FXML
    private void handlePerson() {
        URL personWindowUrl = getFxmlFileUrl("PersonWindow.fxml");
        loadFxmlFile(personWindowUrl, primaryStage);

        MainWindow personWindow = new PersonWindow(this.primaryStage, this.config, this.prefs, this.logic);

        personWindow.fillInnerParts();
    }

    /**
     * Opens up the occasions window on this primaryStage.
     */
    @FXML
    private void handleOccasion() {
        URL occasionWindowUrl = getFxmlFileUrl("OccasionWindow.fxml");
        loadFxmlFile(occasionWindowUrl, primaryStage);

        MainWindow occasionWindow = new OccasionWindow(this.primaryStage, this.config, this.prefs, this.logic);

        occasionWindow.fillInnerParts();
    }

    abstract void fillInnerParts();

    /**
     * Sets the default size based on user preferences.
     */
    protected void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    @Subscribe
    private void handleShowModuleEvent(ShowModuleRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleModule();
    }

    @Subscribe
    private void handleShowPersonEvent(ShowPersonRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handlePerson();
    }

    @Subscribe
    private void handleShowOccasionEvent(ShowOccasionRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleOccasion();
    }

    public StackPane getPersonListPanelPlaceholder() {
        return personListPanelPlaceholder;
    }

    public StackPane getModuleListPanelPlaceholder() {
        return moduleListPanelPlaceholder;
    }

    public StackPane getOccasionListPanelPlaceholder() {
        return occasionListPanelPlaceholder;
    }

    public StackPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    public StackPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    public StackPane getBrowserPlaceholder() {
        return browserPlaceholder;
    }

    public StackPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    public MenuItem getPersonMenuItem() {
        return personMenuItem;
    }

    public MenuItem getModuleWindowItem() {
        return moduleWindowItem;
    }

    public void setModuleWindowItem(MenuItem moduleWindowItem) {
        this.moduleWindowItem = moduleWindowItem;
    }

    public MenuItem getOccasionWindowItem() {
        return occasionWindowItem;
    }
}
