package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.HelpWindowHandle;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.order.DeleteCommand;
import seedu.address.logic.commands.order.OrderCommand;
import seedu.address.logic.commands.order.SelectCommand;
import seedu.address.ui.StatusBarFooter;

/**
 * A system test class for the help window, which contains interaction with other UI components.
 */
public class HelpCommandSystemTest extends OrderBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openHelpWindow() {
        //use accelerator
        getCommandBox().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        getResultDisplay().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

        //use menu button
        getMainMenu().openHelpWindowUsingMenu();
        assertHelpWindowOpen();

        //use command box
        executeCommand(HelpCommand.COMMAND_WORD);
        assertHelpWindowOpen();

        // open help window and give it focus
        executeCommand(HelpCommand.COMMAND_WORD);
        getMainWindowHandle().focus();

        /* Login */
        String loginCommand = LoginCommand.COMMAND_WORD + " ";
        String command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;
        executeCommand(command);
        setUpOrderListPanel();

        // assert that while the help window is open the UI updates correctly for a command execution
        executeCommand(OrderCommand.COMMAND_WORD + " " + SelectCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased());
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        assertNotEquals(HelpCommand.SHOWING_HELP_MESSAGE, getResultDisplay().getText());
        assertListMatching(getOrderListPanel(), getModel().getFilteredOrderList());

        // assert that the status bar too is updated correctly while the help window is open
        // note: the select command tested above does not update the status bar
        executeCommand(OrderCommand.COMMAND_WORD + " " + DeleteCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased());
        assertNotEquals(StatusBarFooter.SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    @Test
    public void help_multipleCommands_onlyOneHelpWindowOpen() {
        getMainMenu().openHelpWindowUsingMenu();

        getMainWindowHandle().focus();
        getMainMenu().openHelpWindowUsingAccelerator();

        getMainWindowHandle().focus();
        executeCommand(HelpCommand.COMMAND_WORD);

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

}
