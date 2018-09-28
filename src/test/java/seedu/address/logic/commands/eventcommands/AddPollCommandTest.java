package seedu.address.logic.commands.eventcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddPollCommandTest {
    private static String POLLNAME = "Generic Poll";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_acceptedAddPoll() {
        AddPollCommand command = new AddPollCommand(POLLNAME);
        Person user = new PersonBuilder().build();
        commandHistory.setSelectedPerson(user);
        EventBuilder eventBuilder = new EventBuilder();
        eventBuilder.withOrganiser(user);
        Event event = eventBuilder.build();
        commandHistory.setSelectedEvent(event);
        String expectedMessage = String.format(command.MESSAGE_SUCCESS, POLLNAME, event);
        expectedModel.commitAddressBook();
        expectedModel.updateEvent(event, event);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noUserAddPoll() {
        AddPollCommand command = new AddPollCommand(POLLNAME);
        Event event = new EventBuilder().build();
        commandHistory.setSelectedEvent(event);
        String expectedMessage = String.format(Messages.MESSAGE_NO_USER_LOGGED_IN);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noEventAddPoll() {
        AddPollCommand command = new AddPollCommand(POLLNAME);
        String expectedMessage = String.format(Messages.MESSAGE_NO_EVENT_SELECTED);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_notEventOrganiserAddPoll() {
        AddPollCommand command = new AddPollCommand(POLLNAME);
        Person user = new PersonBuilder().build();
        commandHistory.setSelectedPerson(user);
        Person anotherUser = new PersonBuilder(user).withName("Bob").build();
        EventBuilder eventBuilder = new EventBuilder();
        eventBuilder.withOrganiser(anotherUser);
        Event event = eventBuilder.build();
        commandHistory.setSelectedEvent(event);
        String expectedMessage = String.format(Messages.MESSAGE_NOT_EVENT_ORGANISER);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }
}
