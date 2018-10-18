package systemtests;

import org.junit.Test;

import guitests.GuiRobot;
import seedu.address.logic.commands.LogoutCommand;

/**
 * A system test class for the help window, which contains interaction with other UI components.
 */
public class LogoutCommandSystemTest extends AddressBookSystemTest {
    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void attemptLogout() {
        //use command box
        executeCommand(LogoutCommand.COMMAND_WORD);
        assertLogout();

    }

    private void assertLogout() {
        assert getBrowserPlaceholder().getChildren().size() == 0;
    }

}
