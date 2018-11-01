package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetingBook.getTypicalMeetingBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.MeetingBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

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
