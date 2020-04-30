package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;
import static seedu.restaurant.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.restaurant.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.restaurant.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.restaurant.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.restaurant.ui.testutil.GuiTestAssert.assertListMatching;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import guitests.guihandles.accounts.UsernameDisplayHandle;
import guitests.guihandles.menu.ItemListPanelHandle;
import seedu.restaurant.MainApp;
import seedu.restaurant.TestApp;
import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.menu.ClearMenuCommand;
import seedu.restaurant.logic.commands.menu.FindItemCommand;
import seedu.restaurant.logic.commands.menu.ListItemsCommand;
import seedu.restaurant.logic.commands.menu.SelectItemCommand;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.ui.CommandBox;
import seedu.restaurant.ui.account.UsernameDisplay;

/**
 * A system test class for RestaurantBook, which provides access to handles of GUI components and helper methods for
 * test verification.
 */
public abstract class RestaurantBookSystemTest {

    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilBrowserLoaded(getBrowserPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected RestaurantBook getInitialData() {
        return getTypicalRestaurantBook();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public ItemListPanelHandle getItemListPanel() {
        return mainWindowHandle.getItemListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    public UsernameDisplayHandle getUsernameDisplay() {
        return mainWindowHandle.getUsernameDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}. Method returns after UI components have been
     * updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Displays all items in the restaurant book.
     */
    protected void showAllItems() {
        executeCommand(ListItemsCommand.COMMAND_WORD);
        assertEquals(getModel().getRestaurantBook().getItemList().size(), getModel().getFilteredItemList().size());
    }

    /**
     * Displays all items with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showItemsWithName(String keyword) {
        executeCommand(FindItemCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredItemList().size() < getModel().getRestaurantBook().getItemList().size());
    }

    /**
     * Selects the item at {@code index} of the displayed list.
     */
    protected void selectItem(Index index) {
        executeCommand(SelectItemCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getItemListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all items in the restaurant book.
     */
    protected void deleteAllItems() {
        executeCommand(ClearMenuCommand.COMMAND_WORD);
        assertEquals(0, getModel().getRestaurantBook().getItemList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same item objects as {@code expectedModel} and the
     * item list panel displays the items in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new RestaurantBook(expectedModel.getRestaurantBook()), testApp.readStorageRestaurantBook());
        assertListMatching(getItemListPanel(), expectedModel.getFilteredItemList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code ItemListPanelHandle} and {@code StatusBarFooterHandle} to remember their
     * current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getItemListPanel().rememberSelectedItemCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected item.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getItemListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the item in the item list panel at {@code
     * expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see ItemListPanelHandle#isSelectedItemCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getItemListPanel().navigateToCard(getItemListPanel().getSelectedCardIndex());
        //String selectedCardName = getItemListPanel().getHandleToSelectedCard().getName();

        //This is a temporary fix as we are no longer using the SEARHC_PAGE_URL
        URL expectedUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);

        /*try {
            expectedUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + selectedCardName.replaceAll(" ", "%20"));
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }*/

        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getItemListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the item list panel remain unchanged.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see ItemListPanelHandle#isSelectedItemCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getItemListPanel().isSelectedItemCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of {@code
     * ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertEquals(UsernameDisplay.ACCOUNT_STATUS_GUEST, getUsernameDisplay().getText());
        assertListMatching(getItemListPanel(), getModel().getFilteredItemList());
        assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE), getBrowserPanel().getLoadedUrl());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
