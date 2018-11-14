package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalAddressbook.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class ClassStatsCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        try {
            Model model = new ModelManager();
            ClassStatsCommand command = new ClassStatsCommand();
            CommandResult result = command.execute(model, commandHistory);
            assertEquals(
                String.format(ClassStatsCommand.MESSAGE_SUCCESS, 0, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
                result.feedbackToUser);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        try {
            Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            ClassStatsCommand command = new ClassStatsCommand();
            CommandResult result = command.execute(model, commandHistory);
            assertTrue(result.feedbackToUser
                .contains(String.format("Students: %d", model.getFilteredPersonList().size())));
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
