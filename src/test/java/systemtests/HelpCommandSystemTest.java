package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.HelpWindowHandle;
import seedu.address.logic.commands.HelpCommand;

/**
 * A system test class for the help window, which contains interaction with other UI components.
 */
public class HelpCommandSystemTest extends PiconsoSystemTest {
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

        // TODO: check usage of other UI components as well

        // open help window and give it focus
        executeCommand(HelpCommand.COMMAND_WORD);
        getMainWindowHandle().focus();

        /* TODO: assert that while the help window is open the UI updates correctly for
         a command execution (for all UI components) */
    }

    @Test
    public void helpMultipleCommandsOnlyOneHelpWindowOpen() {
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
