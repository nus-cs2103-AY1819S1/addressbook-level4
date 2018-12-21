package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalAddressbook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class AssignmentStatsCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invalidAssignmentIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAssignmentList().size() + 1);
        AssignmentStatsCommand command = new AssignmentStatsCommand(outOfBoundIndex);
        assertCommandFailure(command, model, commandHistory, Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validAssignmentIndex_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        if (!model.getFilteredAssignmentList().isEmpty()) {
            try {
                Assignment assignment = model.getFilteredAssignmentList()
                    .get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
                AssignmentStatsCommand command = new AssignmentStatsCommand(INDEX_FIRST_ASSIGNMENT);
                CommandResult result = command.execute(model, commandHistory);
                assertTrue(result.feedbackToUser
                    .contains(String.format("Maximum Mark: %.1f", assignment.getMaxMark().getValue())));
            } catch (CommandException ce) {
                throw new AssertionError("Execution of command should not fail.", ce);
            }
        }
    }

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        if (!model.getFilteredAssignmentList().isEmpty()) {
            try {
                Assignment assignment = model.getFilteredAssignmentList()
                    .get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
                AssignmentStatsCommand command = new AssignmentStatsCommand(INDEX_FIRST_ASSIGNMENT);
                CommandResult result = command.execute(model, commandHistory);
                assertEquals(String.format(AssignmentStatsCommand.MESSAGE_SUCCESS,
                    assignment.getName(), 0, assignment.getMaxMark().getValue(), 0f, 0f, 0f, 0f, 0f, 0f),
                    result.feedbackToUser);
            } catch (CommandException ce) {
                throw new AssertionError("Execution of command should not fail.", ce);
            }
        }
    }
}
