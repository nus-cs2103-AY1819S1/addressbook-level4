package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_MEETINGS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.STEVE_MEETING;
import static seedu.address.testutil.TypicalPersons.TYLER_MEETING;
import static seedu.address.testutil.TypicalPersons.getScheduledAddressBook;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.SameMeetingDayPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class MeetingsCommandTest {
    private Model model = new ModelManager(getScheduledAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getScheduledAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        SameMeetingDayPredicate firstPredicate =
                new SameMeetingDayPredicate(new Meeting("1202181800"));
        SameMeetingDayPredicate secondPredicate =
                new SameMeetingDayPredicate(new Meeting("1302181800"));

        MeetingsCommand meetingsFirstCommand = new MeetingsCommand(firstPredicate);
        MeetingsCommand meetingsSecondCommand = new MeetingsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(meetingsFirstCommand.equals(meetingsFirstCommand));

        // same values -> returns true
        MeetingsCommand meetingsFirstCommandCopy = new MeetingsCommand(firstPredicate);
        assertTrue(meetingsFirstCommand.equals(meetingsFirstCommandCopy));

        // different types -> returns false
        assertFalse(meetingsFirstCommand.equals(1));

        // null -> returns false
        assertFalse(meetingsFirstCommand.equals(null));

        // different day -> returns false
        assertFalse(meetingsFirstCommand.equals(meetingsSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_listAllMeetings() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, 2);
        SameMeetingDayPredicate predicate = new SameMeetingDayPredicate(new Meeting("0000000000"));
        MeetingsCommand command = new MeetingsCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(STEVE_MEETING, TYLER_MEETING), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneArgument_listAllMeetings() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, 1);
        SameMeetingDayPredicate predicate = new SameMeetingDayPredicate(new Meeting("1202180000"));
        MeetingsCommand command = new MeetingsCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(STEVE_MEETING), model.getFilteredPersonList());
    }
}
