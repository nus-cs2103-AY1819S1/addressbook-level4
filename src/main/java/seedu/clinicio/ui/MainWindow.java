package seedu.clinicio.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import seedu.clinicio.commons.core.Config;
import seedu.clinicio.commons.core.GuiSettings;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.AnalyticsDisplayEvent;
import seedu.clinicio.commons.events.ui.AppointmentPanelSelectionChangedEvent;
import seedu.clinicio.commons.events.ui.ExitAppRequestEvent;
import seedu.clinicio.commons.events.ui.LoginSuccessEvent;
import seedu.clinicio.commons.events.ui.LogoutClinicIoEvent;
import seedu.clinicio.commons.events.ui.MedicinePanelSelectionChangedEvent;
import seedu.clinicio.commons.events.ui.PatientPanelSelectionChangedEvent;
import seedu.clinicio.commons.events.ui.ShowHelpRequestEvent;
import seedu.clinicio.commons.events.ui.SwitchTabEvent;
import seedu.clinicio.logic.Logic;

import seedu.clinicio.model.UserPrefs;

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
    private BrowserDisplayPanel browserDisplayPanel;
    private PatientListPanel patientListPanel;
    private AppointmentListPanel appointmentListPanel;
    private QueuePanel queuePanel;
    private MedicineListPanel medicineListPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    private AppointmentDetailsDisplayPanel appointmentDetailsDisplayPanel;
    private AnalyticsDisplayPanel analyticsDisplay;
    private PatientDetailsDisplayPanel patientDetailsDisplayPanel;
    private MedicineDetailsDisplayPanel medicineDetailsDisplayPanel;
    private TitleScreen titleScreen;

    @FXML
    private StackPane displayPanelPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane patientListPanelPlaceholder;

    @FXML
    private StackPane appointmentListPanelPlaceholder;

    @FXML
    private StackPane queuePanelPlaceholder;

    @FXML
    private VBox titleScreenPlaceHolder;

    @FXML
    private StackPane medicineListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TabPane tabLists;

    @FXML
    private Tab patientTab;

    @FXML
    private Tab queueTab;

    @FXML
    private Tab appointmentTab;

    @FXML
    private Tab medicineTab;

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
        setUpTab();

        setUpDisplayPanel();
        hideInnerParts();

        setUpListPanel();
        setUpOtherParts();
    }

    /**
     * Set up tabs and tab pane in {@code MainWindow.fxml}
     */
    private void setUpTab() {
        patientTab.setContent(patientListPanelPlaceholder);
        appointmentTab.setContent(appointmentListPanelPlaceholder);
        queueTab.setContent(queuePanelPlaceholder);
        medicineTab.setContent(medicineListPanelPlaceholder);

        tabLists = new TabPane(patientTab, appointmentTab, queueTab, medicineTab);
    }

    /**
     * Set up all display panels in {@code MainWindow}.
     */
    private void setUpDisplayPanel() {
        analyticsDisplay = new AnalyticsDisplayPanel();
        analyticsDisplay.setVisible(false);
        appointmentDetailsDisplayPanel = new AppointmentDetailsDisplayPanel();
        appointmentDetailsDisplayPanel.setVisible(false);
        patientDetailsDisplayPanel = new PatientDetailsDisplayPanel();
        patientDetailsDisplayPanel.getRoot().setVisible(false);
        medicineDetailsDisplayPanel = new MedicineDetailsDisplayPanel();
        medicineDetailsDisplayPanel.getRoot().setVisible(false);
        browserDisplayPanel = new BrowserDisplayPanel();
        browserDisplayPanel.setVisible(false);

        displayPanelPlaceholder.setAlignment(Pos.TOP_CENTER);
        displayPanelPlaceholder.getChildren().add(browserDisplayPanel.getRoot());
        displayPanelPlaceholder.getChildren().add(analyticsDisplay.getRoot());
        displayPanelPlaceholder.getChildren().add(appointmentDetailsDisplayPanel.getRoot());
        displayPanelPlaceholder.getChildren().add(patientDetailsDisplayPanel.getRoot());
        displayPanelPlaceholder.getChildren().add(medicineDetailsDisplayPanel.getRoot());
    }

    /**
     * Set up all list panels in {@code MainWindow}.
     */
    private void setUpListPanel() {
        patientListPanel = new PatientListPanel(logic.getFilteredPatientList());
        patientListPanelPlaceholder.getChildren().add(patientListPanel.getRoot());

        appointmentListPanel = new AppointmentListPanel(logic.getFilteredAppointmentList());
        appointmentListPanelPlaceholder.getChildren().add(appointmentListPanel.getRoot());

        queuePanel = new QueuePanel(logic.getAllPatientsInQueue());
        queuePanelPlaceholder.getChildren().add(queuePanel.getRoot());

        medicineListPanel = new MedicineListPanel(logic.getFilteredMedicineList());
        medicineListPanelPlaceholder.getChildren().add(medicineListPanel.getRoot());
    }

    /**
     * Set up other parts in {@code MainWindow}.
     * (E.g. {@code TitleScreen}, {@code StatusBarFooter} and etc.
     */
    private void setUpOtherParts() {
        titleScreen = new TitleScreen();
        titleScreenPlaceHolder.getChildren().add(titleScreen.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter();
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Hide the inner parts.
     */
    private void hideInnerParts() {
        displayPanelPlaceholder.setVisible(false);
        patientListPanelPlaceholder.setVisible(false);
        appointmentListPanelPlaceholder.setVisible(false);
        queuePanelPlaceholder.setVisible(false);
        medicineListPanelPlaceholder.setVisible(false);
        splitPane.setVisible(false);
        titleScreenPlaceHolder.setVisible(true);
        titleScreenPlaceHolder.setManaged(true);
        statusbarPlaceholder.setVisible(false);
    }

    /**
     * Display the inner parts.
     */
    private void showInnerParts() {
        displayPanelPlaceholder.setVisible(true);
        patientListPanelPlaceholder.setVisible(true);
        appointmentListPanelPlaceholder.setVisible(true);
        queuePanelPlaceholder.setVisible(true);
        medicineListPanelPlaceholder.setVisible(true);
        splitPane.setVisible(true);
        titleScreenPlaceHolder.setVisible(false);
        titleScreenPlaceHolder.setManaged(false);
        statusbarPlaceholder.setVisible(true);
    }

    /**
     * Switches the current tab to the tab of given index.
     * @param index The index position of the tab
     */
    public void switchTab(int index) {
        for (int i = 0; i < tabLists.getTabs().size(); i++) {
            tabLists.getSelectionModel().clearAndSelect(i);
        }
        tabLists.getSelectionModel().clearAndSelect(index);
    }

    /**
     * Subscribes to the event when tab is switched.
     * @param switchTabEvent the event when tab is switched.
     */
    @Subscribe
    public void handleSwitchTabEvent(SwitchTabEvent switchTabEvent) {
        switchTab(switchTabEvent.getTabIndex());
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
     * Set full screen in exclusive mode.
     */
    void viewInFullScreen() {
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }


    /**
     * Show the display panel according to event request.
     * @param isShowAnalytics Check whether want to show analytics display panel.
     * @param isShowBrowser Check whether want to show browser display panel.
     * @param isShowPatientDetails  Check whether want to show patient details display panel.
     * @param isShowMedicineDetails  Check whether want to show medicine details display panel.
     * @param isShowAppointmentDetails Check whether to show appointment details display panel.
     */
    private void showDisplayPanel(boolean isShowAnalytics, boolean isShowBrowser, boolean isShowPatientDetails,
                                  boolean isShowMedicineDetails, boolean isShowAppointmentDetails) {
        analyticsDisplay.setVisible(isShowAnalytics);
        browserDisplayPanel.setVisible(isShowBrowser);
        patientDetailsDisplayPanel.getRoot().setVisible(isShowPatientDetails);
        medicineDetailsDisplayPanel.getRoot().setVisible(isShowMedicineDetails);
        appointmentDetailsDisplayPanel.setVisible(isShowAppointmentDetails);
    }

    @Subscribe
    private void handleAnalyticsDisplayEvent(AnalyticsDisplayEvent event) {
        showDisplayPanel(true, false, false, false, false);
    }

    @Subscribe
    private void handleAppointmentPanelSelectionChangedEvent(AppointmentPanelSelectionChangedEvent event) {
        showDisplayPanel(false, true, false, false, true);
    }

    @Subscribe
    private void handlePatientPanelSelectionChangedEvent(PatientPanelSelectionChangedEvent event) {
        showDisplayPanel(false, false, true, false, false);
    }

    @Subscribe
    private void handleMedicinePanelSelectionChangedEvent(MedicinePanelSelectionChangedEvent event) {
        showDisplayPanel(false, false, false, true, false);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    @Subscribe
    public void handleLoginSuccessEvent(LoginSuccessEvent loginSuccessEvent) {
        logger.info(LogsCenter.getEventHandlingLogMessage(loginSuccessEvent));
        showInnerParts();
    }

    @Subscribe
    public void handleLogoutClinicIoEvent(LogoutClinicIoEvent logoutClinicIoEvent) {
        logger.info(LogsCenter.getEventHandlingLogMessage(logoutClinicIoEvent));
        hideInnerParts();
        titleScreen.startAnimation();
    }
}
