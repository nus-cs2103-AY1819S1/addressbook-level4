package seedu.meeting.logic.commands;

import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.meeting.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.meeting.testutil.TypicalMeetingBook.getTypicalMeetingBook;

import org.junit.Before;
import org.junit.Test;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.ModelManager;
import seedu.meeting.model.UserPrefs;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoMeetingBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoMeetingBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
