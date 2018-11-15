//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getAddressBookWithParticipant;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalIndexes;
import seedu.address.testutil.TypicalPersons;

public class JoinEventCommandTest {
    private static final Index INVALID_INDEX = Index.fromOneBased(100);

    private Model model = new ModelManager(getAddressBookWithParticipant(), new UserPrefs());
    private Model expectedModel = new ModelManager(getAddressBookWithParticipant(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_acceptedJoinEvent() {
        JoinEventCommand command = new JoinEventCommand(TypicalIndexes.INDEX_FIRST);
        Person user = TypicalPersons.BENSON;
        model.setCurrentUser(user);
        Event event = model.getEvent(TypicalIndexes.INDEX_FIRST);
        model.setSelectedEvent(event);
        String expectedMessage = String.format(JoinEventCommand.MESSAGE_SUCCESS, event);
        expectedMessage += "\n" + "People attending: [Alice Pauline, Benson Meier]";
        expectedModel.setCurrentUser(user);
        expectedModel.setSelectedEvent(event);
        expectedModel.joinEvent(TypicalIndexes.INDEX_FIRST);
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_indexInvalidJoinEvent() {
        JoinEventCommand command = new JoinEventCommand(INVALID_INDEX);
        Person user = new PersonBuilder().build();
        model.setCurrentUser(user);
        String expectedMessage = Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noUserJoinEvent() {
        JoinEventCommand command = new JoinEventCommand(TypicalIndexes.INDEX_FIRST);
        Event event = new EventBuilder().build();
        model.setSelectedEvent(event);
        String expectedMessage = Messages.MESSAGE_NO_USER_LOGGED_IN;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_alreadyJoinedJoinEvent() {
        JoinEventCommand command = new JoinEventCommand(TypicalIndexes.INDEX_FIRST);
        model.setCurrentUser(ALICE);
        Event event = model.getEvent(TypicalIndexes.INDEX_FIRST);
        model.setSelectedEvent(event);
        String expectedMessage = Messages.MESSAGE_ALREADY_JOINED;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }
}
