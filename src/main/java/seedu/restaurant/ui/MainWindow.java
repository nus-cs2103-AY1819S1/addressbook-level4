package seedu.restaurant.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.restaurant.commons.core.Config;
import seedu.restaurant.commons.core.GuiSettings;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.commons.events.ui.ExitAppRequestEvent;
import seedu.restaurant.commons.events.ui.ShowHelpRequestEvent;
import seedu.restaurant.commons.events.ui.accounts.DisplayAccountListRequestEvent;
import seedu.restaurant.commons.events.ui.accounts.LoginEvent;
import seedu.restaurant.commons.events.ui.accounts.LogoutEvent;
import seedu.restaurant.commons.events.ui.ingredient.DisplayIngredientListRequestEvent;
import seedu.restaurant.commons.events.ui.ingredient.IngredientPanelSelectionChangedEvent;
import seedu.restaurant.commons.events.ui.menu.DisplayItemListRequestEvent;
import seedu.restaurant.commons.events.ui.menu.ItemPanelSelectionChangedEvent;
import seedu.restaurant.commons.events.ui.reservation.DisplayReservationListRequestEvent;
import seedu.restaurant.commons.events.ui.reservation.ReservationPanelSelectionChangedEvent;
import seedu.restaurant.commons.events.ui.sales.DisplayRankingEvent;
import seedu.restaurant.commons.events.ui.sales.DisplayRecordListRequestEvent;
import seedu.restaurant.commons.events.ui.sales.DisplaySalesChartEvent;
import seedu.restaurant.commons.events.ui.sales.DisplaySalesReportEvent;
import seedu.restaurant.commons.events.ui.sales.RecordPanelSelectionChangedEvent;
import seedu.restaurant.logic.Logic;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.ui.account.AccountListPanel;
import seedu.restaurant.ui.account.UsernameDisplay;
import seedu.restaurant.ui.ingredient.IngredientListPanel;
import seedu.restaurant.ui.ingredient.IngredientStackPanel;
import seedu.restaurant.ui.menu.ItemListPanel;
import seedu.restaurant.ui.menu.ItemStackPanel;
import seedu.restaurant.ui.reservation.ReservationListPanel;
import seedu.restaurant.ui.sales.RecordListPanel;
import seedu.restaurant.ui.sales.RecordStackPanel;
import seedu.restaurant.ui.sales.SalesChartWindow;
import seedu.restaurant.ui.sales.SalesRankingWindow;
import seedu.restaurant.ui.sales.SalesReportWindow;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where other JavaFX elements
 * can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final double FULL_OPACITY = 1.0;
    private static final double DISABLED_OPACITY = 0.15;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private BrowserPanel browserPanel;
    private AccountListPanel accountListPanel;
    private RecordListPanel recordListPanel;
    private IngredientListPanel ingredientListPanel;
    private ItemListPanel itemListPanel;
    private ReservationListPanel reservationListPanel;

    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;

    private FadeTransition ftListPanel;
    private FadeTransition ftStackPanel;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private Pane usernameDisplayPlaceholder;

    @FXML
    private StackPane dataListPanelPlaceholder;

    @FXML
    private StackPane detailPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private ImageView switchToAccountButton;

    @FXML
    private ImageView switchToMenuButton;

    @FXML
    private ImageView switchToSalesButton;

    @FXML
    private ImageView switchToIngredientButton;

    @FXML
    private ImageView switchToReservationButton;

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
        detailPanelPlaceholder.getChildren().add(browserPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        //@@author AZhiKai
        UsernameDisplay usernameDisplay = new UsernameDisplay();
        // Centralize the width
        usernameDisplay.getRoot().layoutXProperty().bind(usernameDisplayPlaceholder.widthProperty()
                .subtract(usernameDisplay.getRoot().widthProperty())
                .divide(2));
        // Centralize the height
        usernameDisplay.getRoot().layoutYProperty().bind(usernameDisplayPlaceholder.heightProperty()
                .subtract(usernameDisplay.getRoot().heightProperty())
                .divide(2));
        usernameDisplayPlaceholder.getChildren().add(usernameDisplay.getRoot());

        //@@author
        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getRestaurantBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        accountListPanel = new AccountListPanel(logic.getFilteredAccountList());
        itemListPanel = new ItemListPanel(logic.getFilteredItemList());
        recordListPanel = new RecordListPanel(logic.getFilteredRecordList());
        ingredientListPanel = new IngredientListPanel(logic.getFilteredIngredientList());
        reservationListPanel = new ReservationListPanel(logic.getFilteredReservationList());
        dataListPanelPlaceholder.getChildren().add(itemListPanel.getRoot());

        ftListPanel = getFadeTransition(Duration.millis(150), dataListPanelPlaceholder);
        ftStackPanel = getFadeTransition(Duration.millis(150), detailPanelPlaceholder);
    }

    //@@author yican95

    /**
     * Create the fade transition for the StackPane and set value from 0 to 1.
     */
    private FadeTransition getFadeTransition(Duration duration, StackPane placeholder) {
        FadeTransition ft = new FadeTransition(duration, placeholder);
        ft.setFromValue(0);
        ft.setToValue(1);
        return ft;
    }

    //@@author
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

    //@@author AZhiKai

    /**
     * Toggle the navigation button's opacity and disability based on whether {@code isLoggedIn} which is toggled by the
     * {@code LoginEvent} and {@code LogoutEvent}.
     *
     * @param isLoggedIn determines if the button can be clicked and with a full opacity of value 1.0.
     */
    private void toggleNavigationBar(boolean isLoggedIn) {
        setButtonDisableProperty(isLoggedIn);
        setButtonOpacityProperty(isLoggedIn);
    }

    /**
     * Sets the disable property of the {@code ImageButton}.
     */
    private void setButtonDisableProperty(boolean isLoggedIn) {
        switchToAccountButton.setDisable(!isLoggedIn);
        switchToIngredientButton.setDisable(!isLoggedIn);
        switchToSalesButton.setDisable(!isLoggedIn);
        switchToReservationButton.setDisable(!isLoggedIn);
    }

    /**
     * Sets the opacity property of the {@code ImageButton}.
     */
    private void setButtonOpacityProperty(boolean isLoggedIn) {
        switchToAccountButton.setOpacity(isLoggedIn ? FULL_OPACITY : DISABLED_OPACITY);
        switchToIngredientButton.setOpacity(isLoggedIn ? FULL_OPACITY : DISABLED_OPACITY);
        switchToSalesButton.setOpacity(isLoggedIn ? FULL_OPACITY : DISABLED_OPACITY);
        switchToReservationButton.setOpacity(isLoggedIn ? FULL_OPACITY : DISABLED_OPACITY);
    }

    /**
     * Switch the list panel to the given region
     */
    private void switchList(Region region) {
        detailPanelPlaceholder.getChildren().clear();
        dataListPanelPlaceholder.getChildren().clear();
        dataListPanelPlaceholder.getChildren().add(region);
        ftListPanel.play();
    }

    /**
     * Set the panel with the given {@code Node}.
     *
     * @param node to set to the panel.
     */
    private void setPanel(Node node) {
        detailPanelPlaceholder.getChildren().clear();
        detailPanelPlaceholder.getChildren().add(node);
        ftStackPanel.play();
    }

    /**
     * Switch to the menu view.
     */
    @FXML
    private void handleSwitchToMenu() {
        switchList(itemListPanel.getRoot());
        setPanel(browserPanel.getRoot());
    }

    /**
     * Switch to the account view.
     */
    @FXML
    private void handleSwitchToAccount() {
        switchList(accountListPanel.getRoot());
        setPanel(browserPanel.getRoot());
    }

    /**
     * Switch to the sales view.
     */
    @FXML
    private void handleSwitchToSales() {
        switchList(recordListPanel.getRoot());
        setPanel(browserPanel.getRoot());
    }

    /**
     * Switch to the ingredient view.
     */
    @FXML
    private void handleSwitchToIngredient() {
        switchList(ingredientListPanel.getRoot());
        setPanel(browserPanel.getRoot());
    }

    /**
     * Switch to the reservation view.
     */
    @FXML
    private void handleSwitchToReservation() {
        switchList(reservationListPanel.getRoot());
        setPanel(browserPanel.getRoot());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    private void handleHelp() {
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

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author yican95
    @Subscribe
    private void handleItemPanelSelectionChangedEvent(ItemPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setPanel(new ItemStackPanel(event.getNewSelection()).getRoot());
    }

    @Subscribe
    private void handleReservationPanelSelectionChangedEvent(ReservationPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        //setPanel(browserPanel.getRoot());
    }

    //@@author HyperionNKJ
    @Subscribe
    private void handleRecordPanelSelectionChangedEvent(RecordPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setPanel(new RecordStackPanel(event.getNewSelection()).getRoot());
    }

    //@@author rebstan97
    @Subscribe
    private void handleIngredientPanelSelectionChangedEvent(IngredientPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setPanel(new IngredientStackPanel(event.getNewSelection()).getRoot());
    }

    //@@author AZhiKai
    @Subscribe
    private void handleDisplayAccountListEvent(DisplayAccountListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSwitchToAccount();
    }

    @Subscribe
    private void handleDisplayItemListEvent(DisplayItemListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSwitchToMenu();
    }

    //@@author rebstan97
    @Subscribe
    private void handleDisplayIngredientListEvent(DisplayIngredientListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSwitchToIngredient();
    }

    //@@author HyperionNKJ
    @Subscribe
    private void handleDisplayRecordListEvent(DisplayRecordListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSwitchToSales();
    }

    @Subscribe
    private void handleDisplayRankingEvent(DisplayRankingEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSwitchToSales();
        SalesRankingWindow salesRankingWindow = new SalesRankingWindow(event.getRankingToDisplay());
        salesRankingWindow.show();
    }

    @Subscribe
    private void handleDisplaySalesReportEvent(DisplaySalesReportEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSwitchToSales();
        SalesReportWindow salesReportWindow = new SalesReportWindow(event.getSalesReportToDisplay());
        salesReportWindow.show();
    }

    @Subscribe
    private void handleDisplaySalesChartEvent(DisplaySalesChartEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSwitchToSales();
        SalesChartWindow salesChartWindow = new SalesChartWindow(event.getSalesData());
        salesChartWindow.show();
    }

    @Subscribe
    private void handleDisplayReservationEvent(DisplayReservationListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSwitchToReservation();
    }

    //@@author AZhiKai
    @Subscribe
    private void handleLoginEvent(LoginEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        toggleNavigationBar(true);
    }

    @Subscribe
    private void handleLogoutEvent(LogoutEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        toggleNavigationBar(false);
        handleSwitchToMenu();
    }
}
