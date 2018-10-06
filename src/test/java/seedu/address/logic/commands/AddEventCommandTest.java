package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ADDRESS_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.testutil.ScheduledEventBuilder;

public class AddEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_failure() {
        Event defaultEvent = new Event(new EventName(VALID_EVENT_NAME_DOCTORAPPT),
                new EventDescription(VALID_EVENT_DESC_DOCTORAPPT),
                new EventDate(VALID_EVENT_DATE_DOCTORAPPT),
                new EventTime(VALID_EVENT_TIME_DOCTORAPPT), new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT));
        AddEventCommand addEventCommand = new AddEventCommand(defaultEvent);

        assertCommandFailure(addEventCommand, model, commandHistory,
                addEventCommand.MESSAGE_METHOD_NOT_IMPLEMENTED_YET + defaultEvent);
    }

    @Test
    public void equals() {
        Event firstEvent = new ScheduledEventBuilder().withEventName("event").build();
        Event secondEvent = new ScheduledEventBuilder().withEventName("a different event").build();
        AddEventCommand addFirstEventCommand = new AddEventCommand(firstEvent);
        AddEventCommand addSecondEventCommand = new AddEventCommand(secondEvent);

        // same object -> returns true
        assertTrue(addFirstEventCommand.equals(addFirstEventCommand));

        // same values -> returns true
        AddEventCommand addFirstEventCommandCopy = new AddEventCommand(firstEvent);
        assertTrue(addFirstEventCommand.equals(addFirstEventCommandCopy));

        // different types -> returns false
        assertFalse(addFirstEventCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(addFirstEventCommand.equals(null));

        // different event -> returns false
        assertFalse(addFirstEventCommand.equals(addSecondEventCommand));
    }
}
