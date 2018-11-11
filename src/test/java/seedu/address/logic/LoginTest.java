package seedu.address.logic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.FailedLoginEvent;
import seedu.address.commons.events.ui.LoginEvent;
import seedu.address.commons.events.ui.SuccessfulLoginEvent;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.testutil.TypicalAssignment;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.testutil.EventsCollectorRule;

public class LoginTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();


    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(),
            TypicalAssignment.getTypicalAssignmentList(), TypicalPersons.getTypicalArchiveList(), new UserPrefs());
    private Logic logic = new LogicManager(model);

    @Test
    public void login_admin_successful() {
        String adminUsername = User.ADMIN_DEFAULT_USERNAME.username;
        String adminPassword = User.ADMIN_DEFUALT_PASSWORD.plaintext;

        EventsCenter.getInstance().post(new LoginEvent(adminUsername, adminPassword));

        assertLastEventLoginSuccessful();
        assert model.getLoggedInUser().isAdminUser();
    }

    @Test
    public void login_admin_unsuccessful() {
        String adminUsername = User.ADMIN_DEFAULT_USERNAME.username;
        String adminPassword = User.ADMIN_DEFUALT_PASSWORD.plaintext;

        EventsCenter.getInstance().post(new LoginEvent(adminUsername, adminPassword + "hihihi"));

        assertLastEventLoginUnsuccessful(FailedLoginEvent.INVALID_PASSWORD);
    }

    @Test
    public void login_user_successful() {
        Person loginPerson = model.getAddressBook().getPersonList().get(0);
        String username = loginPerson.getUsername().username;
        String password = loginPerson.getPassword().plaintext;

        EventsCenter.getInstance().post(new LoginEvent(username, password));

        assertLastEventLoginSuccessful();;
        assert model.getLoggedInUser().getPerson() == loginPerson;
    }

    @Test
    public void login_nonConformingData_unsuccessful() {
        String username = "";
        String password = "";

        EventsCenter.getInstance().post(new LoginEvent(username, password));

        assertLastEventLoginUnsuccessful(FailedLoginEvent.NON_CONFORMING_INPUTS);
    }

    @Test
    public void login_username_unsuccessful() {
        String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String username = "";
        String password = "Pa55w0rd";

        boolean nonMatchingUsername;
        do {
            username = "";
            for (int i = 0; i < 12; i++) {
                int randomBreakpoint = (int) (Math.random() * values.length());
                username += values.substring(randomBreakpoint, randomBreakpoint + 1);
            }

            //verify that it doesn't match
            ObservableList<Person> personList = model.getAddressBook().getPersonList();
            nonMatchingUsername = true;
            for (Person p : personList) {
                if (p.getUsername().username.equals(username)) {
                    nonMatchingUsername = false;
                }
            }
        } while (!nonMatchingUsername);

        EventsCenter.getInstance().post(new LoginEvent(username, password));

        assertLastEventLoginUnsuccessful(FailedLoginEvent.INVALID_USERNAME);
    }

    @Test
    public void login_password_unsuccessful() {
        Person loginPerson = model.getAddressBook().getPersonList().get(0);
        String username = loginPerson.getUsername().username;
        String password = loginPerson.getPassword().plaintext + "1234";

        EventsCenter.getInstance().post(new LoginEvent(username, password));

        assertLastEventLoginUnsuccessful(FailedLoginEvent.INVALID_PASSWORD);
    }

    /**
     * Verifies that the last login is successful.
     */
    private void assertLastEventLoginSuccessful() {
        BaseEvent lastEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        assert lastEvent instanceof SuccessfulLoginEvent;
        assert ((SuccessfulLoginEvent) lastEvent).getLoggedInPerson() != null;
        assert model.getLoggedInUser() != null;
    }

    /**
     * Verifies that the login was unsuccessful, and for a certain reason
     * @param reason The Failed Login Event's message
     */
    private void assertLastEventLoginUnsuccessful(String reason) {
        BaseEvent lastEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        assert lastEvent instanceof FailedLoginEvent;
        FailedLoginEvent failedLoginEvent = (FailedLoginEvent) lastEvent;
        assert failedLoginEvent.getMessage().equals(reason);
        assert model.getLoggedInUser() == null;
    }
}
