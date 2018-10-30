package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.learnvocabulary.ui.BrowserPanel.SEARCH_PAGE_URL;
import static seedu.learnvocabulary.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.learnvocabulary.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.learnvocabulary.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.learnvocabulary.ui.testutil.GuiTestAssert.assertListMatching;

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
import guitests.guihandles.WordListPanelHandle;
import seedu.learnvocabulary.MainApp;
import seedu.learnvocabulary.TestApp;
import seedu.learnvocabulary.commons.core.EventsCenter;
import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.logic.commands.ClearCommand;
import seedu.learnvocabulary.logic.commands.FindCommand;
import seedu.learnvocabulary.logic.commands.ListCommand;
import seedu.learnvocabulary.logic.commands.SelectCommand;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.TypicalWords;
import seedu.learnvocabulary.ui.CommandBox;

/**
 * A system test class for LearnVocabulary, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class LearnVocabularySystemTest {
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
    protected LearnVocabulary getInitialData() {
        return TypicalWords.getTypicalLearnVocabulary();
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

    public WordListPanelHandle getWordListPanel() {
        return mainWindowHandle.getWordListPanel();
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
     * Displays all words in LearnVocabulary.
     */
    protected void showAllWords() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getLearnVocabulary().getWordList().size(), getModel().getFilteredWordList().size());
    }

    /**
     * Displays all words with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showWordsWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredWordList().size() < getModel().getLearnVocabulary().getWordList().size());
    }

    /**
     * Selects the word at {@code index} of the displayed list.
     */
    protected void selectWord(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getWordListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all words in the LearnVocabulary.
     */
    protected void deleteAllWords() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getLearnVocabulary().getWordList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same word objects as {@code expectedModel}
     * and the word list panel displays the words in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new LearnVocabulary(expectedModel.getLearnVocabulary()), testApp.readStorageLearnVocabulary());
        assertListMatching(getWordListPanel(), expectedModel.getFilteredWordList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code WordListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getWordListPanel().rememberSelectedWordCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected word.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getWordListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the word in the word list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see WordListPanelHandle#isSelectedWordCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getWordListPanel().navigateToCard(getWordListPanel().getSelectedCardIndex());
        Word selectedWord = getWordListPanel().getWord(getWordListPanel().getSelectedCardIndex());
        String expectedUrl = SEARCH_PAGE_URL;
        String expectedUrlInString = expectedUrl + "?name="
                + selectedWord.getName().fullName.replaceAll(" ", "%20")
                + "&meaning=" + selectedWord.getMeaning().fullMeaning.replaceAll(" ", "%20");

        assertEquals(expectedUrlInString, getBrowserPanel().getLoadedUrl().toExternalForm());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getWordListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the word list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see WordListPanelHandle#isSelectedWordCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getWordListPanel().isSelectedWordCardChanged());
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
        assertListMatching(getWordListPanel(), getModel().getFilteredWordList());
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
