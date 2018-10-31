//TODO: Left as an example, to be deleted/replaced.
//package systemtests;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.ui.StatusBarFooter.LOGIN_STATUS_INITIAL;
//import static seedu.address.ui.StatusBarFooter.LOGIN_STATUS_UPDATED;
//import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;
//
//import java.nio.file.Path;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.BeforeClass;
//
//import guitests.guihandles.CommandBoxHandle;
//import guitests.guihandles.MainMenuHandle;
//import guitests.guihandles.MainWindowHandle;
//import guitests.guihandles.PersonListPanelHandle;
//import guitests.guihandles.ResultDisplayHandle;
//import guitests.guihandles.StatusBarFooterHandle;
//import seedu.address.TestApp;
//import seedu.address.commons.core.EventsCenter;
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.ClearCommand;
//import seedu.address.logic.commands.FindCommand;
//import seedu.address.logic.commands.SelectCommand;
//import seedu.address.model.AddressBook;
//import seedu.address.model.Model;
//import seedu.address.testutil.TypicalPersons;
//import seedu.address.ui.CommandBox;
//
///**
// * A system test class for AddressBook, which provides access to handles of GUI components and helper methods
// * for test verification.
// */
//public abstract class AddressBookSystemTest {
//
//    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
//    private static final List<String> COMMAND_BOX_ERROR_STYLE =
//            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);
//
//    private MainWindowHandle mainWindowHandle;
//    private TestApp testApp;
//    private SystemTestSetupHelper setupHelper;
//
//    @BeforeClass
//    public static void setupBeforeClass() {
//        SystemTestSetupHelper.initialize();
//    }
//
//    @Before
//    public void setUp() {
//        setupHelper = new SystemTestSetupHelper();
//        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
//        //mainWindowHandle = setupHelper.setupMainWindowHandle();
//        //assertApplicationStartingStateIsCorrect();
//    }
//
//    @After
//    public void tearDown() {
//        setupHelper.tearDownStage();
//        EventsCenter.clearSubscribers();
//    }
//
//    /**
//     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
//     */
//    protected AddressBook getInitialData() {
//        return TypicalPersons.getTypicalAddressBook();
//    }
//
//    /**
//     * Returns the directory of the data file.
//     */
//    protected Path getDataFileLocation() {
//        return TestApp.SAVE_LOCATION_FOR_TESTING;
//    }
//
//    public MainWindowHandle getMainWindowHandle() {
//        return mainWindowHandle;
//    }
//
//    public CommandBoxHandle getCommandBox() {
//        return mainWindowHandle.getCommandBox();
//    }
//
//    public PersonListPanelHandle getPersonListPanel() {
//        return mainWindowHandle.getPersonListPanel();
//    }
//
//    public MainMenuHandle getMainMenu() {
//        return mainWindowHandle.getMainMenu();
//    }
//
//    public StatusBarFooterHandle getStatusBarFooter() {
//        return mainWindowHandle.getStatusBarFooter();
//    }
//
//    public ResultDisplayHandle getResultDisplay() {
//        return mainWindowHandle.getResultDisplay();
//    }
//
//    /**
//     * Executes {@code command} in the application's {@code CommandBox}.
//     * Method returns after UI components have been updated.
//     */
//    protected void executeCommand(String command) {
//        rememberStates();
//
//        mainWindowHandle.getCommandBox().run(command);
//    }
//
//    /**
//     * Displays all persons with any parts of their names matching {@code keyword} (case-insensitive).
//     */
//    protected void showPersonsWithName(String keyword) {
//        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
//        assertTrue(getModel().getFilteredPersonList().size() < getModel().getAddressBook().getPersonList().size());
//    }
//
//    /**
//     * Selects the person at {@code index} of the displayed list.
//     */
//    protected void selectPerson(Index index) {
//        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
//        assertEquals(index.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
//    }
//
//    /**
//     * Deletes all persons in the address book.
//     */
//    protected void deleteAllPersons() {
//        executeCommand(ClearCommand.COMMAND_WORD);
//        assertEquals(0, getModel().getAddressBook().getPersonList().size());
//    }
//
//    /**
//     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
//     * {@code expectedResultMessage}, the storage contains the same person objects as {@code expectedModel}
//     * and the person list panel displays the persons in the model correctly.
//     */
//    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
//            Model expectedModel) {
//        assertEquals(expectedCommandInput, getCommandBox().getInput());
//        assertEquals(expectedResultMessage, getResultDisplay().getText());
//        assertEquals(new AddressBook(expectedModel.getAddressBook()), testApp.readStorageAddressBook());
//        assertListMatching(getPersonListPanel(), expectedModel.getFilteredPersonList());
//    }
//
//    /**
//     * Calls {@code BrowserPanelHandle}, {@code PersonListPanelHandle} and {@code StatusBarFooterHandle} to remember
//     * their current state.
//     */
//    private void rememberStates() {
//        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
//        statusBarFooterHandle.rememberLoginStatus();
//        getPersonListPanel().rememberSelectedPersonCard();
//    }
//
//    /**
//     * Asserts that the previously selected card is now deselected and the browser's
//     * url remains displaying the details
//     * of the previously selected person.
//     */
//    protected void assertSelectedCardDeselected() {
//        assertFalse(getPersonListPanel().isAnyCardSelected());
//    }
//
//    /**
//     * Asserts that the browser's url is changed to display the details of the person in the person list panel at
//     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
//     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
//     */
//    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
//        getPersonListPanel().navigateToCard(getPersonListPanel().getSelectedCardIndex());
//        String selectedCardName = getPersonListPanel().getHandleToSelectedCard().getName();
//
//        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
//    }
//
//    /**
//     * Asserts that the browser's url and the selected card in the person list panel remain unchanged.
//     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
//     */
//    protected void assertSelectedCardUnchanged() {
//        assertFalse(getPersonListPanel().isSelectedPersonCardChanged());
//    }
//
//    /**
//     * Asserts that the command box's shows the default style.
//     */
//    protected void assertCommandBoxShowsDefaultStyle() {
//        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
//    }
//
//    /**
//     * Asserts that the command box's shows the error style.
//     */
//    protected void assertCommandBoxShowsErrorStyle() {
//        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
//    }
//
//    /**
//     * Asserts that the entire status bar remains the same.
//     */
//    protected void assertStatusBarUnchanged() {
//        StatusBarFooterHandle handle = getStatusBarFooter();
//        assertFalse(handle.isLoginStatusChanged());
//    }
//
//    /**
//     * Asserts that only the sync status in the status bar was changed to the timing of
//     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
//     */
//    protected void assertStatusBarUnchangedExceptSyncStatus() {
//        StatusBarFooterHandle handle = getStatusBarFooter();
//        String expectedLoginStatus = String.format(LOGIN_STATUS_UPDATED, "temp");
//        assertEquals(expectedLoginStatus, handle.getLoginStatus());
//    }
//
//    /**
//     * Asserts that the starting state of the application is correct.
//     */
//    private void assertApplicationStartingStateIsCorrect() {
//        assertEquals("", getCommandBox().getInput());
//        assertEquals("", getResultDisplay().getText());
//        assertListMatching(getPersonListPanel(), getModel().getFilteredPersonList());
//        assertEquals(LOGIN_STATUS_INITIAL, getStatusBarFooter().getLoginStatus());
//    }
//
//    /**
//     * Returns a defensive copy of the current model.
//     */
//    protected Model getModel() {
//        return testApp.getModel();
//    }
//}
