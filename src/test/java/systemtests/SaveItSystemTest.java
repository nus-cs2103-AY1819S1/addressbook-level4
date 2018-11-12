package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.saveit.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.saveit.ui.BrowserPanel.JAVADOC_PAGE;
import static seedu.saveit.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.saveit.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.saveit.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.saveit.ui.testutil.GuiTestAssert.assertListMatching;

import java.net.MalformedURLException;
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
import guitests.guihandles.IssueListPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.SolutionListPanelHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.saveit.MainApp;
import seedu.saveit.TestApp;
import seedu.saveit.commons.core.EventsCenter;
import seedu.saveit.commons.core.index.Index;
import seedu.saveit.logic.LogicManager;
import seedu.saveit.logic.commands.ClearCommand;
import seedu.saveit.logic.commands.FindCommand;
import seedu.saveit.logic.commands.ListCommand;
import seedu.saveit.logic.commands.SelectCommand;
import seedu.saveit.model.Model;
import seedu.saveit.model.SaveIt;
import seedu.saveit.testutil.TypicalIssues;
import seedu.saveit.ui.CommandBox;

/**
 * A system test class for SaveIt, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class SaveItSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("styled-text-area");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("styled-text-area", CommandBox.ERROR_STYLE_CLASS);

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
    protected SaveIt getInitialData() {
        return TypicalIssues.getTypicalSaveIt();
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

    public IssueListPanelHandle getIssueListPanel() {
        return mainWindowHandle.getIssueListPanel();
    }

    public SolutionListPanelHandle getSolutionListPanel() {
        return mainWindowHandle.getSolutionListPanel();
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
     * Displays all issues in the saveit book.
     */
    protected void showAllIssues() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getSaveIt().getIssueList().size(), getModel().getFilteredAndSortedIssueList().size());
    }

    /**
     * Displays all issues with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showIssuesWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredAndSortedIssueList().size() < getModel().getSaveIt().getIssueList().size());
    }

    /**
     * Selects the issue at {@code index} of the displayed list.
     */
    protected void selectIssue(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getIssueListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all issues in the saveit book.
     */
    protected void deleteAllIssues() {
        executeCommand(ClearCommand.COMMAND_WORD);
        executeCommand(LogicManager.CONFIRM_WORD);
        assertEquals(0, getModel().getSaveIt().getIssueList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same issue objects as {@code expectedModel}
     * and the issue list panel displays the issues in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new SaveIt(expectedModel.getSaveIt()), testApp.readStorageSaveIt());
        if (expectedModel.getCurrentDirectory().isSolutionLevel()) {
            assertListMatching(getSolutionListPanel(), expectedModel.getFilteredAndSortedSolutionList());
        }

        if (expectedModel.getCurrentDirectory().isRootLevel() || expectedModel.getCurrentDirectory().isIssueLevel()) {
            assertListMatching(getIssueListPanel(), expectedModel.getFilteredAndSortedIssueList());
        }
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code IssueListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getIssueListPanel().rememberSelectedIssueCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected issue.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getIssueListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the issue in the issue list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see IssueListPanelHandle#isSelectedIssueCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getIssueListPanel().navigateToCard(getIssueListPanel().getSelectedCardIndex());
        String selectedCardName = getIssueListPanel().getHandleToSelectedCard().getStatement();
        URL expectedUrl;
        try {
            expectedUrl = new URL(JAVADOC_PAGE);
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getIssueListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the issue list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see IssueListPanelHandle#isSelectedIssueCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getIssueListPanel().isSelectedIssueCardChanged());
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
        System.out.println(getResultDisplay().getText());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getIssueListPanel(), getModel().getFilteredAndSortedIssueList());
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
