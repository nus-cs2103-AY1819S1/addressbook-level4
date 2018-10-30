/* //TODO: Left as an example, to be deleted/replaced.
package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UndoCommandTest {

    private final Model model = new ModelManager(new UserPrefs());
    private final Model expectedModel = new ModelManager(new UserPrefs());
    //private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
    }

    @Test
    public void execute() {

        //UNDO COMMAND ASSERTION FAIL
        // multiple undoable states in model
        //expectedModel.undoAddressBook();
        //assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        //expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);*//*

    }
}
*/
