package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.jxmusic.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
//import static seedu.jxmusic.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.jxmusic.ui.testutil.GuiTestAssert.assertListMatching;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
//import java.net.MalformedURLException;
//import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.PlaylistListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import guitests.guihandles.TrackListPanelHandle;
//import seedu.jxmusic.MainApp;
import seedu.jxmusic.TestApp;
import seedu.jxmusic.commons.core.EventsCenter;
import seedu.jxmusic.commons.core.index.Index;
//import seedu.jxmusic.logic.commands.ClearCommand;
import seedu.jxmusic.logic.commands.ClearCommand;
import seedu.jxmusic.logic.commands.PlaylistListCommand;
import seedu.jxmusic.logic.commands.PlaylistSearchCommand;
import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.testutil.TypicalPlaylistList;
import seedu.jxmusic.ui.CommandBox;

/**
 * A system test class for AddressBook, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class LibrarySystemTest {
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

        //waitUntilBrowserLoaded(getBrowserPanel());
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
    protected Library getInitialData() {
        return TypicalPlaylistList.getTypicalLibrary();
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

    public PlaylistListPanelHandle getPlaylistListPanel() {
        return mainWindowHandle.getPlaylistListPanel();
    }

    public TrackListPanelHandle getTrackListPanel() {
        return mainWindowHandle.getTrackListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    //public BrowserPanelHandle getBrowserPanel() {
    // return mainWindowHandle.getBrowserPanel();
    // }

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

        //waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Displays all playlists in the jxmusic player.
     */
    protected void showAllPlaylists() {
        executeCommand(PlaylistListCommand.COMMAND_PHRASE);
        assertEquals(getModel().getLibrary().getPlaylistList().size(), getModel().getFilteredPlaylistList().size());
    }

    /**
     * Displays all persons with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPlaylistsWithName(String keyword) {
        executeCommand(PlaylistSearchCommand.COMMAND_PHRASE + " " + keyword);
        assertTrue(getModel().getFilteredPlaylistList().size() <= getModel().getLibrary().getPlaylistList().size());
    }

    /**
     * Deletes all playlists in the library.
     */
    protected void deleteAllPlaylists() {
        executeCommand(ClearCommand.COMMAND_PHRASE);
        assertEquals(0, getModel().getLibrary().getPlaylistList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same playlist objects as {@code expectedModel}
     * and the playlist list panel displays the playlists in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new Library(expectedModel.getLibrary()), testApp.readStorageLibrary());
        assertListMatching(getPlaylistListPanel(), expectedModel.getFilteredPlaylistList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code PlaylistListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        //getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getPlaylistListPanel().rememberSelectedPlaylistCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected playlist.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     */
    //protected void assertSelectedCardDeselected() {
    //assertFalse(getBrowserPanel().isUrlChanged());
    //assertFalse(getPlaylistListPanel().isAnyCardSelected());
    //}

    /**
     * Asserts that the browser's url is changed to display the details of the playlist in the playlist list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     *
     * @see PlaylistListPanelHandle#isSelectedPlaylistCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getPlaylistListPanel().navigateToCard(getPlaylistListPanel().getSelectedCardIndex());
        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPlaylistListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the playlist list panel remain unchanged.
     *
     * @see PlaylistListPanelHandle#isSelectedPlaylistCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getPlaylistListPanel().isSelectedPlaylistCardChanged());
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
        assertListMatching(getPlaylistListPanel(), getModel().getFilteredPlaylistList());
        //assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE), getBrowserPanel().getLoadedUrl());
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
