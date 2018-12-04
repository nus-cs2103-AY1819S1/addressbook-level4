package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.thanepark.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.thanepark.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
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
import guitests.guihandles.RideListPanelHandle;
import guitests.guihandles.StatusBarFooterHandle;

import seedu.thanepark.TestApp;
import seedu.thanepark.commons.core.EventsCenter;
import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.commands.ClearCommand;
import seedu.thanepark.logic.commands.FindCommand;
import seedu.thanepark.logic.commands.ViewAllCommand;
import seedu.thanepark.logic.commands.ViewCommand;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ThanePark;
import seedu.thanepark.testutil.TypicalRides;
import seedu.thanepark.ui.CommandBox;
import seedu.thanepark.ui.browser.BrowserPanel;
import seedu.thanepark.ui.browser.HelpWindow;

/**
 *
 * A system test class for ThanePark, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class ThaneParkSystemTest {
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
    public void setUp() throws IOException {
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
    protected ThanePark getInitialData() {
        return TypicalRides.getTypicalThanePark();
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

    public RideListPanelHandle getRideListPanel() {
        return mainWindowHandle.getRideListPanel();
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

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
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
     * Displays all rides in the thanepark book.
     */
    protected void showAllRides() {
        executeCommand(ViewAllCommand.COMMAND_WORD);
        assertEquals(getModel().getThanePark().getRideList().size(), getModel().getFilteredRideList().size());
    }

    /**
     * Displays all rides with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showRidesWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredRideList().size() < getModel().getThanePark().getRideList().size());
    }

    /**
     * Selects the ride at {@code index} of the displayed list.
     */
    protected void selectRide(Index index) {
        executeCommand(ViewCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getRideListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all rides in the thanepark book.
     */
    protected void deleteAllRides() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getThanePark().getRideList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same ride objects as {@code expectedModel}
     * and the ride list panel displays the rides in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new ThanePark(expectedModel.getThanePark()), testApp.readStorageThanePark());
        assertListMatching(getRideListPanel(), expectedModel.getFilteredRideList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code RideListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getRideListPanel().rememberSelectedRideCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected ride.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getRideListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the ride in the ride list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see RideListPanelHandle#isSelectedRideCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) throws IOException {
        getRideListPanel().navigateToCard(getRideListPanel().getSelectedCardIndex());
        //TODO: Check that the ride information displays the correct name
        String selectedCardName = getRideListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl = BrowserPanel.RIDE_PAGE_PATH.filePathToUrl();
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getRideListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the ride list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see RideListPanelHandle#isSelectedRideCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getRideListPanel().isSelectedRideCardChanged());
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
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
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
    private void assertApplicationStartingStateIsCorrect() throws IOException {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getRideListPanel(), getModel().getFilteredRideList());
        assertEquals(HelpWindow.SHORT_HELP_FILE_PATH.filePathToUrl(), getBrowserPanel().getLoadedUrl());
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
