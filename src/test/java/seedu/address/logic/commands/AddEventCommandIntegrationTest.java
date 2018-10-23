package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_CONTACT_INDEX_1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.testutil.ScheduledEventBuilder;

public class AddEventCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newEvent_success() {
        Event validEvent = new ScheduledEventBuilder().withEventContacts(ALICE).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addEvent(validEvent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddEventCommand(validEvent, new HashSet<>(Arrays.asList(
                Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_1))))), model, commandHistory,
                String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), expectedModel);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event eventInList = model.getAddressBook().getEventList().get(0);
        assertCommandFailure(new AddEventCommand(eventInList, new HashSet<>(Arrays.asList(
                Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_1))))), model, commandHistory,
                AddEventCommand.MESSAGE_DUPLICATE_EVENT);
    }
}
