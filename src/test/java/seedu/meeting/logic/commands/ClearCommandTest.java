package seedu.meeting.logic.commands;

import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.meeting.testutil.TypicalMeetingBook.getTypicalMeetingBook;

import org.junit.Test;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.MeetingBook;
import seedu.meeting.model.Model;
import seedu.meeting.model.ModelManager;
import seedu.meeting.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyMeetingBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitMeetingBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyMeetingBook_success() {
        Model model = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
        expectedModel.resetData(new MeetingBook());
        expectedModel.commitMeetingBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
