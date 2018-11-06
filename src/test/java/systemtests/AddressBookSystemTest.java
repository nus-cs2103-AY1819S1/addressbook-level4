package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import guitests.guihandles.ModuleBrowserPanelHandle;
import guitests.guihandles.OccasionBrowserPanelHandle;
import guitests.guihandles.PersonBrowserPanelHandle;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ModuleListPanelHandle;
import guitests.guihandles.OccasionListPanelHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.MainApp;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindModuleCommand;
import seedu.address.logic.commands.FindOccasionCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.ListModuleCommand;
import seedu.address.logic.commands.ListOccasionCommand;
import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalModules;
import seedu.address.testutil.TypicalOccasions;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.BrowserPanel;
import seedu.address.ui.CommandBox;

/**
 * A system test class for AddressBook, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class AddressBookSystemTest {
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
        getBrowserPanel();
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
    protected AddressBook getInitialData() {
        AddressBook thisOne = new AddressBook();
        thisOne.setPersons(TypicalPersons.getTypicalPersonsAddressBook().getPersonList());
        thisOne.setModules(TypicalModules.getTypicalModulesAddressBook().getModuleList());
        thisOne.setOccasions(TypicalOccasions.getTypicalOccasionsAddressBook().getOccasionList());
        return thisOne;
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

    public PersonListPanelHandle getPersonListPanel() {
        return mainWindowHandle.getPersonListPanel();
    }

    public ModuleListPanelHandle getModuleListPanel() {
        return mainWindowHandle.getModuleListPanel();
    }

    public OccasionListPanelHandle getOccasionListPanel() {
        return mainWindowHandle.getOccasionListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public PersonBrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getPersonBrowserPanel();
    }

    public ModuleBrowserPanelHandle getModuleBrowserPanel() {
        return mainWindowHandle.getModuleBrowserPanel();
    }

    public OccasionBrowserPanelHandle getOccasionBrowserPanel() {
        return mainWindowHandle.getOccasionBrowserPanel();
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

        //waitUntilBrowserLoaded(getBrowserPanel());
        getBrowserPanel();
    }

    /**
     * Displays all persons in the address book.
     */
    protected void showAllPersons() {
        executeCommand(ListPersonCommand.COMMAND_WORD);
        assertEquals(getModel().getAddressBook().getPersonList().size(), getModel().getFilteredPersonList().size());
    }

    /**
     * Displays all persons with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPersonsWithName(String keyword) {
        executeCommand(FindPersonCommand.COMMAND_WORD + " n/" + " " + keyword);
        assertTrue(getModel().getFilteredPersonList().size() < getModel().getAddressBook().getPersonList().size());
    }

    /**
     * Selects the person at {@code index} of the displayed list.
     */
    protected void selectPerson(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all persons in the address book.
     */
    protected void deleteAllPersons() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getAddressBook().getPersonList().size());
    }

    /**
     * Displays all modules in the address book.
     */
    protected void showAllModules() {
        executeCommand(ListModuleCommand.COMMAND_WORD);
        assertEquals(getModel().getAddressBook().getModuleList().size(), getModel().getFilteredModuleList().size());
    }

    /**
     * Displays all modules with any parts of their titles matching {@code keyword} (case-insensitive).
     */
    protected void showModulesWithTitle(String keyword) {
        executeCommand(FindModuleCommand.COMMAND_WORD + " mc/" + " " + keyword);
        assertTrue(getModel().getFilteredModuleList().size() < getModel().getAddressBook().getModuleList().size());
    }

    /**
     * Selects the module at {@code index} of the displayed list.
     */
    protected void selectModule(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        // TODO: -> getModuleListPanel implemented.
        assertEquals(index.getZeroBased(), getModuleListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all modules in the address book.
     */
    protected void deleteAllModules() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getAddressBook().getModuleList().size());
    }

    /**
     * Displays all occasions in the address book.
     */
    protected void showAllOccasions() {
        executeCommand(ListOccasionCommand.COMMAND_WORD);
        assertEquals(getModel().getAddressBook().getOccasionList().size(), getModel().getFilteredOccasionList().size());
    }

    /**
     * Displays all occasions with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showOccasionsWithName(String keyword) {
        executeCommand(FindOccasionCommand.COMMAND_WORD + " on/" + " " + keyword);
        assertTrue(getModel().getFilteredOccasionList().size() < getModel().getAddressBook().getOccasionList().size());
    }

    /**
     * Selects the occasion at {@code index} of the displayed list.
     */
    protected void selectOccasion(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        // TODO: -> getOccasionListPanel implemented. Need to morph select command.
        assertEquals(index.getZeroBased(), getOccasionListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all occasions in the address book.
     */
    protected void deleteAllOccasions() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getAddressBook().getOccasionList().size());
    }


    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpectedPerson(String expectedCommandInput, String expectedResultMessage,
                                                           Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new AddressBook(expectedModel.getAddressBook()), testApp.readStorageAddressBook());
        assertListMatching(getPersonListPanel(), expectedModel.getFilteredPersonList());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpectedModule(String expectedCommandInput, String expectedResultMessage,
                                                           Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new AddressBook(expectedModel.getAddressBook()), testApp.readStorageAddressBook());
        assertListMatching(getModuleListPanel(), expectedModel.getFilteredModuleList());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpectedOccasion(String expectedCommandInput, String expectedResultMessage,
                                                             Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new AddressBook(expectedModel.getAddressBook()), testApp.readStorageAddressBook());
        assertListMatching(getOccasionListPanel(), expectedModel.getFilteredOccasionList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code PersonListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        // getBrowserPanel().rememberUrl();
        // getBrowserPanel().getLastRememberedPerson();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getPersonListPanel().rememberSelectedPersonCard();
    }

    /**
     * Asserts that the previously selected card is now deselected.
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getPersonListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the person in the person list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedPersonCardChanged(Index expectedSelectedCardIndex) { // TODO FIX THIS TEST.
        getPersonListPanel().navigateToCard(getPersonListPanel().getSelectedCardIndex());
        String selectedCardName = getPersonListPanel().getHandleToSelectedCard().getName();
        Person expectedPerson = getModel().getFilteredPersonList().get(expectedSelectedCardIndex.getZeroBased());
        URL expectedUrl;
        try {
            expectedUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + selectedCardName.replaceAll(" ", "%20"));
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }
        //assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the module in the module list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see ModuleListPanelHandle#isSelectedModuleCardChanged()
     */
    protected void assertSelectedModuleCardChanged(Index expectedSelectedCardIndex) { // TODO FIX
        getModuleListPanel().navigateToCard(getModuleListPanel().getSelectedCardIndex());
        String selectedCardName = getModuleListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl;
        try {
            expectedUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + selectedCardName.replaceAll(" ", "%20"));
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }
        //assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getModuleListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the occasion in the person list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see OccasionListPanelHandle#isSelectedOccasionCardChanged()
     */
    protected void assertSelectedOccasionCardChanged(Index expectedSelectedCardIndex) { // TODO FIX
        getOccasionListPanel().navigateToCard(getOccasionListPanel().getSelectedCardIndex());
        String selectedCardName = getOccasionListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl;
        try {
            expectedUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + selectedCardName.replaceAll(" ", "%20"));
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }
        //assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getOccasionListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the person list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardUnchanged() { // TODO FIX
        // assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getPersonListPanel().isSelectedPersonCardChanged());
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
    private void assertApplicationStartingStateIsCorrect() { // TODO FIX
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getPersonListPanel(), getModel().getFilteredPersonList());
        // assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE), getBrowserPanel().getLoadedUrl());
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
