package systemtests;

import org.junit.Rule;
import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.LoginHandle;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.AdminPasswordModificationEvent;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.PasswordCommand;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * A system test class for the command passwd, which contains interaction with other UI components.
 */
public class PasswordCommandSystemTest extends AddressBookSystemTest {

    public static final Password nextPassword = new Password("Test4321");

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private final GuiRobot guiRobot = new GuiRobot();

    private LoginHandle loginHandle;

    public void loginAsUser() {

        //Start off logged out.
        assert getModel().getLoggedInUser() != null;
        //use command box
        executeCommand(LogoutCommand.COMMAND_WORD);
        guiRobot.pauseForHuman();

        loginHandle = getMainWindowHandle().getLoginHandle();
        attemptLoginUser();
    }

    public void attemptLoginUser() {
        Person p = getModel().getAddressBook().getPersonList().get(0);
        String username = p.getUsername().username;
        String password = p.getPassword().password;
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

        attemptPasswordChange(currentPassword.password);
        //ensure password hasn't changed
        assert currentPassword.equals(getModel().getLoggedInUser().getPassword());

        Password newPassword = nextPassword;
        executeCommand(newPassword.password);
        assert eventsCollectorRule.eventsCollector.isAny(AdminPasswordModificationEvent.class);
        assert newPassword.equals(getModel().getLoggedInUser().getPassword());

        verifyHistoryDoesNotContain(currentPassword.password, newPassword.password);
    }

    @Test
    public void adminChangeOldFailure() {
        Password currentPassword = getModel().getLoggedInUser().getPassword();
        adminChangeSuccess();

        eventsCollectorRule.eventsCollector.reset();
        attemptPasswordChange(currentPassword.password);

        assert getResultDisplay().getText().equals(PasswordCommand.FAILED_PASSWORD_MESSAGE);
        assert User.getAdminUser().getPassword().equals(nextPassword);
        verifyHistoryDoesNotContain(currentPassword.password);
    }

    @Test
    public void adminChangeNewSuccess() {
        adminChangeSuccess();

        eventsCollectorRule.eventsCollector.reset();
        attemptPasswordChange(nextPassword.password);
        assert nextPassword.equals(getModel().getLoggedInUser().getPassword());

        Password newPassword = new Password("Pa55w0rd");
        executeCommand(newPassword.password);
        assert eventsCollectorRule.eventsCollector.isAny(AdminPasswordModificationEvent.class);
        assert newPassword.equals(getModel().getLoggedInUser().getPassword());

        verifyHistoryDoesNotContain("Pa55w0rd", nextPassword.password);
    }

    @Test
    public void userChangeSuccess() {
        loginAsUser();

        Password currentPassword = getModel().getLoggedInUser().getPassword();

        attemptPasswordChange(currentPassword.password);
        //ensure password hasn't changed
        assert currentPassword.equals(getModel().getLoggedInUser().getPassword());

        Password newPassword = nextPassword;
        executeCommand(newPassword.password);
        assert newPassword.equals(getModel().getLoggedInUser().getPassword());

        verifyHistoryDoesNotContain(currentPassword.password, newPassword.password);
    }

    public void verifyHistoryDoesNotContain(String... items) {
        executeCommand(HistoryCommand.COMMAND_WORD);
        String result = getResultDisplay().getText();
        for (String anItem : items) {
            assert !(result.contains(anItem));
        }
    }

    public void attemptPasswordChange(String currentPassword) {
        executeCommand(PasswordCommand.COMMAND_WORD);
        assert !(eventsCollectorRule.eventsCollector.isAny(AdminPasswordModificationEvent.class));

        executeCommand(currentPassword);
        assert !(eventsCollectorRule.eventsCollector.isAny(AdminPasswordModificationEvent.class));
    }
}
