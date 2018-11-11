package systemtests;

import org.junit.Rule;
import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.LoginHandle;
import seedu.address.commons.events.storage.AdminPasswordModificationEvent;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.PasswordCommand;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * A system test class for the command passwd, which contains interaction with other UI components.
 */
public class PasswordCommandSystemTest extends AddressBookSystemTest {

    public static final Password NEXT_PASSWORD = new Password("Test4321");

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private final GuiRobot guiRobot = new GuiRobot();

    private LoginHandle loginHandle;

    /**
     * Logs out then logs in as a normal user.
     */
    public void loginAsUser() {

        //Start off logged out.
        assert getModel().getLoggedInUser() != null;
        //use command box
        executeCommand(LogoutCommand.COMMAND_WORD);
        guiRobot.pauseForHuman();

        loginHandle = getMainWindowHandle().getLoginHandle();
        attemptLoginUser();
    }

    /**
     * Attempts to log in as a normal user. The current address book must show a login screen.
     */
    public void attemptLoginUser() {
        //The first person in the address book is alice
        Person p = TypicalPersons.ALICE;
        String username = p.getUsername().username;
        String password = p.getPassword().plaintext;
        guiRobot.pauseForHuman();

        loginHandle.attemptLogIn(username, password);
        guiRobot.pauseForHuman();

        assert getModel().getLoggedInUser() != null;
        assert getModel().getLoggedInUser().getPerson().equals(p);

        refreshAllQueries();
    }

    @Test
    public void adminChangeSuccess() {
        Password currentPassword = getModel().getLoggedInUser().getPassword();

        attemptPasswordChange(currentPassword.plaintext);
        //ensure password hasn't changed
        assert currentPassword.equals(getModel().getLoggedInUser().getPassword());

        Password newPassword = NEXT_PASSWORD;
        executeCommand(newPassword.plaintext);
        assert eventsCollectorRule.eventsCollector.isAny(AdminPasswordModificationEvent.class);
        assert newPassword.isSamePassword(getModel().getLoggedInUser().getPassword());

        verifyHistoryDoesNotContain(currentPassword.plaintext, newPassword.plaintext);
    }

    @Test
    public void adminChangeOldFailure() {
        Password currentPassword = getModel().getLoggedInUser().getPassword();
        adminChangeSuccess();

        eventsCollectorRule.eventsCollector.reset();
        attemptPasswordChange(currentPassword.plaintext);

        assert getResultDisplay().getText().equals(PasswordCommand.FAILED_PASSWORD_MESSAGE);
        assert User.getAdminUser().getPassword().isSamePassword(NEXT_PASSWORD);
        verifyHistoryDoesNotContain(currentPassword.plaintext);
    }

    @Test
    public void adminChangeNewSuccess() {
        adminChangeSuccess();

        eventsCollectorRule.eventsCollector.reset();
        attemptPasswordChange(NEXT_PASSWORD.plaintext);
        assert NEXT_PASSWORD.isSamePassword(getModel().getLoggedInUser().getPassword());

        Password newPassword = new Password("Pa55w0rd");
        executeCommand(newPassword.plaintext);
        assert eventsCollectorRule.eventsCollector.isAny(AdminPasswordModificationEvent.class);
        assert newPassword.isSamePassword(getModel().getLoggedInUser().getPassword());

        verifyHistoryDoesNotContain("Pa55w0rd", NEXT_PASSWORD.plaintext);
    }

    @Test
    public void adminPasswordDoesntMeetRequirements() {
        Password currentPassword = getModel().getLoggedInUser().getPassword();

        attemptPasswordChange(currentPassword.plaintext);
        //ensure password hasn't changed
        assert currentPassword.isSamePassword(getModel().getLoggedInUser().getPassword());

        executeCommand("12345678");
        assert getResultDisplay().getText().equals(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        assert !(eventsCollectorRule.eventsCollector.isAny(AdminPasswordModificationEvent.class));
        assert currentPassword.isSamePassword(getModel().getLoggedInUser().getPassword());
        verifyHistoryDoesNotContain(currentPassword.plaintext, "12345678");
    }

    @Test
    public void userChangeSuccess() {
        loginAsUser();

        Password currentPassword = TypicalPersons.ALICE.getPassword();

        attemptPasswordChange(currentPassword.plaintext);
        //ensure password hasn't changed
        assert currentPassword.isSamePassword(getModel().getLoggedInUser().getPassword());

        Password newPassword = NEXT_PASSWORD;
        executeCommand(newPassword.plaintext);
        assert newPassword.isSamePassword(getModel().getLoggedInUser().getPassword());

        verifyHistoryDoesNotContain(currentPassword.plaintext, newPassword.plaintext);
    }

    /**
     * Verifies that none of the commands in the history are any of the String inputs
     * @param items A series of Strings that should not be shown in the history.
     */
    public void verifyHistoryDoesNotContain(String... items) {
        executeCommand(HistoryCommand.COMMAND_WORD);
        String result = getResultDisplay().getText();
        for (String anItem : items) {
            assert !(result.contains(anItem));
        }
    }

    /**
     * Attempts to start the password changing command. If successful, the next command executed should change
     * the password to it.
     * @param currentPassword The current password of the currently logged in user.
     */
    public void attemptPasswordChange(String currentPassword) {
        executeCommand(PasswordCommand.COMMAND_WORD);
        assert !(eventsCollectorRule.eventsCollector.isAny(AdminPasswordModificationEvent.class));

        executeCommand(currentPassword);
        assert !(eventsCollectorRule.eventsCollector.isAny(AdminPasswordModificationEvent.class));
    }
}
