package systemtests;

import org.junit.Test;

import guitests.GuiRobot;
import seedu.address.logic.commands.LogoutCommand;

/**
 * A system test class for the logout command
 */
public class LogoutCommandSystemTest extends AddressBookSystemTest {
    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void attemptLogout() {
        assert getModel().getLoggedInUser() != null;
        //use command box
        executeCommand(LogoutCommand.COMMAND_WORD);
        assertLogout();

    }

    /**
     * Verifies that a logout is successful and all the variants are met.
     */
    private void assertLogout() {
        assert getBrowserPlaceholder().getChildren().size() == 0;
        assert getModel().getLoggedInUser() == null;
        assert !getModel().canRedoAddressBook();
        assert !getModel().canUndoAddressBook();
    }

}
