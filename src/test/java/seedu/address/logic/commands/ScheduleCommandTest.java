package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

//@@author AyushChatto
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * ScheduleCommand.
 */
public class ScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person scheduledPerson = new PersonBuilder(TypicalPersons.ALICE).withMeeting("1202181600").build();
        Meeting meeting = new Meeting("1202181600");
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, meeting);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SCHEDULING_SUCCESS, scheduledPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), scheduledPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(scheduleCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
