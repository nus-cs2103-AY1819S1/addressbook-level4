package seedu.address.logic;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FailedLoginEvent;
import seedu.address.commons.events.ui.LoginEvent;
import seedu.address.commons.events.ui.LogoutEvent;
import seedu.address.commons.events.ui.SuccessfulLoginEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.leaveapplication.LeaveApplicationWithEmployee;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.model.person.Username;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<LeaveApplicationWithEmployee> getFilteredLeaveApplicationList() {
        return model.getFilteredLeaveApplicationList();

    @Override
    public ObservableList<Person> getArchivedPersonList() {
        return model.getArchivedPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    /**
     * Listens for a login event to check if it is successful or not.
     * Ends up raising either a {@link SuccessfulLoginEvent SuccessfulLoginEvent} or
     * {@link FailedLoginEvent FailedLoginEvent}.
     * @param loginEvent the login event raised by the UI.
     */

    @Subscribe
    public void processLogin(LoginEvent loginEvent) {
        //Make sure it's valid
        if (!Username.isValidUsername(loginEvent.getUsername())
            || !Password.isValidPassword(loginEvent.getPassword())) {
            raise(new FailedLoginEvent(FailedLoginEvent.NON_CONFORMING_INPUTS));
            return;
        }

        Username username = new Username(loginEvent.getUsername());
        Password password = new Password(loginEvent.getPassword());

        if (username.equals(User.ADMIN_USERNAME) && password.equals(User.ADMIN_PASSWORD)) {
            User admin = User.getAdminUser();
            model.setLoggedInUser(admin);
            raise(new SuccessfulLoginEvent(admin));
            return;
        }

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<Person> people = getFilteredPersonList();

        boolean someoneFound = false;
        for (Person p : people) {
            if (p.getUsername().equals(username)) {
                if (p.getPassword().equals(password)) {
                    User loggedInUser = new User(p);
                    model.setLoggedInUser(loggedInUser);
                    raise(new SuccessfulLoginEvent(loggedInUser));
                } else {
                    raise(new FailedLoginEvent(FailedLoginEvent.INVALID_PASSWORD));
                }
                someoneFound = true;
                break;
            }
        }

        if (!someoneFound) {
            raise(new FailedLoginEvent(FailedLoginEvent.INVALID_USERNAME));
        }
    }

    @Subscribe
    public void processLogout(LogoutEvent logoutEvent) {
        model.setLoggedInUser(null);
        model.restartAddressBook();
    }
}
