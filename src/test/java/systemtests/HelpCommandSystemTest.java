package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_RIDE;
import static seedu.thanepark.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.HelpWindowHandle;

import seedu.thanepark.logic.commands.DeleteCommand;
import seedu.thanepark.logic.commands.HelpCommand;
import seedu.thanepark.logic.commands.ViewCommand;
import seedu.thanepark.ui.StatusBarFooter;
import seedu.thanepark.ui.browser.HelpWindow;

/**
 * A system test class for the help window, which contains interaction with other UI components.
 */
public class HelpCommandSystemTest extends ThaneParkSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openHelpWindow() throws IOException {
        //use accelerator
        getCommandBox().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        getResultDisplay().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        getRideListPanel().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        getBrowserPanel().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowNotOpen();

        //use menu button
        getMainMenu().openHelpWindowUsingMenu();
        assertHelpWindowOpen();

        //use command box
        executeCommand(HelpCommand.COMMAND_WORD + " more");
        assertHelpWindowOpen();

        // open help window and give it focus
        executeCommand(HelpCommand.COMMAND_WORD + " more");
        getMainWindowHandle().focus();

        // assert that while the help window is open the UI updates correctly for a command execution
        executeCommand(ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_RIDE.getOneBased());
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        assertNotEquals(HelpCommand.SHOWING_HELP_MESSAGE, getResultDisplay().getText());
        assertNotEquals(HelpWindow.SHORT_HELP_FILE_PATH.filePathToUrl(), getBrowserPanel().getLoadedUrl());
        assertListMatching(getRideListPanel(), getModel().getFilteredRideList());
        // assert that the status bar too is updated correctly while the help window is open
        // note: the select command tested above does not update the status bar
        executeCommand(DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_RIDE.getOneBased());
        assertNotEquals(StatusBarFooter.SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    @Test
    public void openHelpSummary() throws IOException {
        //already open on startup
        assertShortHelpDisplayed();

        //select something
        executeCommand(ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_RIDE.getOneBased());
        assertShortHelpNotDisplayed();

        //use command box
        executeCommand(HelpCommand.COMMAND_WORD);
        assertShortHelpDisplayed();
    }

    @Test
    public void openHelpWindowAtCommand() {
        executeCommand(HelpCommand.COMMAND_WORD + " add");
        assertHelpWindowOpen();

        executeCommand(HelpCommand.COMMAND_WORD + " delete");
        assertHelpWindowOpen();

        executeCommand(HelpCommand.COMMAND_WORD + " asdgf");
        assertHelpWindowNotOpen();
    }

    @Test
    public void help_multipleCommands_onlyOneHelpWindowOpen() {
        getMainMenu().openHelpWindowUsingMenu();

        getMainWindowHandle().focus();
        getMainMenu().openHelpWindowUsingAccelerator();

        getMainWindowHandle().focus();
        executeCommand(HelpCommand.COMMAND_WORD + " more");

        assertEquals(1, guiRobot.getNumberOfWindowsShown(HelpWindowHandle.HELP_WINDOW_TITLE));
    }

    /**
     * Asserts that the help window is open, and closes it after checking.
     */
    private void assertHelpWindowOpen() {
        assertTrue(ERROR_MESSAGE, HelpWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new HelpWindowHandle(guiRobot.getStage(HelpWindowHandle.HELP_WINDOW_TITLE)).close();
        getMainWindowHandle().focus();
    }

    /**
     * Asserts that the help window isn't open.
     */
    private void assertHelpWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, HelpWindowHandle.isWindowPresent());
    }

    /**
     * Asserts that the browser's url is changed to display the short help
     *
     * @see BrowserPanelHandle#isHelpUrl()
     */
    private void assertShortHelpDisplayed() throws IOException {
        assertTrue(getMainWindowHandle().getBrowserPanel().isHelpUrl());
    }

    /**
     * Asserts that the browser's url is not displaying the short help
     *
     * @see BrowserPanelHandle#isHelpUrl()
     */
    private void assertShortHelpNotDisplayed() throws IOException {
        assertFalse(getMainWindowHandle().getBrowserPanel().isHelpUrl());
    }

}
