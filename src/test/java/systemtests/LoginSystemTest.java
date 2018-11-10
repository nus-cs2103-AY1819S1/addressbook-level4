package systemtests;

import org.junit.Before;
import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.LoginHandle;
import seedu.address.commons.events.ui.FailedLoginEvent;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.testutil.TypicalPersons;

/**
 * A system test class for the login scenario, which contains interaction with other UI components.
 */
public class LoginSystemTest extends AddressBookSystemTest {
    private final GuiRobot guiRobot = new GuiRobot();

    private LoginHandle loginHandle;

    @Before
    public void setUp() {
        super.setUp();

        //Start off logged out.
        assert getModel().getLoggedInUser() != null;
        //use command box
        executeCommand(LogoutCommand.COMMAND_WORD);
        guiRobot.pauseForHuman();

        loginHandle = getMainWindowHandle().getLoginHandle();
    }

    @Test
    public void attemptLoginAdmin() {
        loginHandle.attemptLogIn(User.ADMIN_DEFAULT_USERNAME.username, User.ADMIN_DEFUALT_PASSWORD.plaintext);

        assert getModel().getLoggedInUser() != null;
        assert getModel().getLoggedInUser().isAdminUser();
    }

    @Test
    public void attemptLoginUser() {
        //The first person in the address book is alice
        Person p = TypicalPersons.ALICE;
        String username = p.getUsername().username;
        String password = p.getPassword().plaintext;

        loginHandle.attemptLogIn(username, password);

        assert getModel().getLoggedInUser() != null;
        assert getModel().getLoggedInUser().getPerson().equals(p);
    }

    @Test
    public void attemptFailedLogin() {
        loginHandle.attemptLogIn("", "");

        assert getModel().getLoggedInUser() == null;
        assert loginHandle.getOutput().equals(FailedLoginEvent.NON_CONFORMING_INPUTS);
    }
}
