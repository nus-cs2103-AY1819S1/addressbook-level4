package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.parking.ui.BrowserPanel.SEARCH_PAGE_URL;
import static seedu.parking.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.parking.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.parking.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.parking.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import com.google.gson.JsonArray;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CarparkListPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;

import seedu.parking.MainApp;
import seedu.parking.TestApp;
import seedu.parking.commons.core.EventsCenter;
import seedu.parking.commons.core.index.Index;
import seedu.parking.logic.commands.ClearCommand;
import seedu.parking.logic.commands.FindCommand;
import seedu.parking.logic.commands.ListCommand;
import seedu.parking.logic.commands.SelectCommand;
import seedu.parking.model.CarparkFinder;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.testutil.TypicalCarparks;
import seedu.parking.ui.CommandBox;

/**
 * A system test class for CarparkFinder, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class CarparkFinderSystemTest {
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
    protected CarparkFinder getInitialData() {
        return TypicalCarparks.getTypicalCarparkFinder();
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

    public CarparkListPanelHandle getCarparkListPanel() {
        return mainWindowHandle.getCarparkListPanel();
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
     * Displays all car parks in the car park finder.
     */
    protected void showAllCarparks() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getCarparkFinder().getCarparkList().size(), getModel().getFilteredCarparkList().size());
    }

    /**
     * Displays all car parks with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showCarparksWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredCarparkList().size() < getModel().getCarparkFinder().getCarparkList().size());
    }

    /**
     * Selects the car park at {@code index} of the displayed list.
     */
    protected void selectCarpark(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getCarparkListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all car parks in the car park finder.
     */
    protected void deleteAllCarparks() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getCarparkFinder().getCarparkList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same car park objects as {@code expectedModel}
     * and the car park list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new CarparkFinder(expectedModel.getCarparkFinder()), testApp.readStorageCarparkFinder());
        assertListMatching(getCarparkListPanel(), expectedModel.getFilteredCarparkList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code CarparkListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getCarparkListPanel().rememberSelectedCarparkCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected car park.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getCarparkListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the car park in the car park list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see CarparkListPanelHandle#isSelectedCarparkCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getCarparkListPanel().navigateToCard(getCarparkListPanel().getSelectedCardIndex());
        String selectedCardCarparkNumber = null;
        try {
            JsonArray arr = new JsonArray();
            arr.add(getCarparkListPanel().getHandleToSelectedCard().getCarparkNumber());
            selectedCardCarparkNumber = URLEncoder.encode(arr.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        URL expectedUrl;
        try {
            expectedUrl = new URL(SEARCH_PAGE_URL + "jsonArr=" + selectedCardCarparkNumber);
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getCarparkListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the car park in the car park list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see CarparkListPanelHandle#isSelectedCarparkCardChanged()
     */
    protected void assertSelectedCardChangedMulti(Carpark[] carprks) {
        String selectedCardCarparkNumber = null;
        try {
            JsonArray arr = new JsonArray();
            for (Carpark cp : carprks) {
                arr.add(cp.getCarparkNumber().value);
            }
            selectedCardCarparkNumber = URLEncoder.encode(arr.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        URL expectedUrl;
        try {
            expectedUrl = new URL(SEARCH_PAGE_URL + "jsonArr=" + selectedCardCarparkNumber);
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the car park in the car park list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see CarparkListPanelHandle#isSelectedCarparkCardChanged()
     */
    protected void assertSelectedCardChangedSingle(Index expectedSelectedCardIndex) {
        getCarparkListPanel().navigateToCard(getCarparkListPanel().getSelectedCardIndex());
        String selectedCardCarparkNumber = null;
        try {
            JsonArray arr = new JsonArray();
            arr.add(getCarparkListPanel().getHandleToSelectedCard().getCarparkNumber());
            selectedCardCarparkNumber = URLEncoder.encode(arr.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        URL expectedUrl;
        try {
            expectedUrl = new URL(SEARCH_PAGE_URL + "json=" + selectedCardCarparkNumber);
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());
    }

    /**
     * Asserts that the browser's url and the selected card in the car park list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see CarparkListPanelHandle#isSelectedCarparkCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getCarparkListPanel().isSelectedCarparkCardChanged());
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
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getCarparkListPanel(), getModel().getFilteredCarparkList());
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
