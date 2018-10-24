package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstExpense;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseTracker;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.NoUserSelectedException;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalExpenseTracker(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalExpenseTracker(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() throws NoUserSelectedException {
        // set up of both models' undo/redo history
        deleteFirstExpense(model);
        deleteFirstExpense(model);
        model.undoExpenseTracker();
        model.undoExpenseTracker();

        deleteFirstExpense(expectedModel);
        deleteFirstExpense(expectedModel);
        expectedModel.undoExpenseTracker();
        expectedModel.undoExpenseTracker();
    }

    @Test
    public void execute() throws NoUserSelectedException {
        // multiple redoable states in model
        expectedModel.redoExpenseTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoExpenseTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
