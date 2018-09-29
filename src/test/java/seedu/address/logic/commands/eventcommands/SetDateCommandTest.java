//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

public class SetDateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private LocalDate date = LocalDate.of(2018, 1, 1);

    @Test
    public void execute_dateAcceptedSetDate() {
        SetDateCommand command = new SetDateCommand(date);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Person user = new PersonBuilder().build();
        commandHistory.setSelectedPerson(user);
        EventBuilder eventBuilder = new EventBuilder();
        eventBuilder.withOrganiser(user);
        Event event = eventBuilder.build();
        commandHistory.setSelectedEvent(event);
        String expectedMessage = String.format(command.MESSAGE_SUCCESS, date.format(dateFormat), event);
        expectedModel.commitAddressBook();
        expectedModel.updateEvent(event, event);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noUserSetDate() {
        SetDateCommand command = new SetDateCommand(date);
        Event event = new EventBuilder().build();
        commandHistory.setSelectedEvent(event);
        String expectedMessage = String.format(Messages.MESSAGE_NO_USER_LOGGED_IN);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noEventSetDate() {
        SetDateCommand command = new SetDateCommand(date);
        String expectedMessage = String.format(Messages.MESSAGE_NO_EVENT_SELECTED);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_notEventOrganiserSetDate() {
        SetDateCommand command = new SetDateCommand(date);
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

    @Test
    public void equals() {
        LocalDate dateOne = LocalDate.of(2018, 1, 1);
        LocalDate dateTwo = LocalDate.of(2018, 2, 2);
        SetDateCommand setDateOneCommand = new SetDateCommand(dateOne);
        SetDateCommand setDateTwoCommand = new SetDateCommand(dateTwo);

        // same object -> returns true
        assertTrue(setDateOneCommand.equals(setDateOneCommand));

        // same values -> returns true
        SetDateCommand setDateOneCommandCopy = new SetDateCommand(dateOne);
        assertTrue(setDateOneCommand.equals(setDateOneCommandCopy));

        // different types -> returns false
        assertFalse(setDateOneCommand.equals(1));

        // null -> returns false
        assertFalse(setDateOneCommand.equals(null));

        // different date -> returns false
        assertFalse(setDateOneCommand.equals(setDateTwoCommand));
    }
}
