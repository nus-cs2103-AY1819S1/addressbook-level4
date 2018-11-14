package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalAddressbook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        sortCommand = new SortCommand();
    }

    @Test
    public void execute_sortEmptyAddressBook_success() {
        Model model = new ModelManager();
        SortCommand command = new SortCommand();
        CommandResult result = command.execute(model, new CommandHistory());
        assertEquals(result.feedbackToUser, SortCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void execute_sortNonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SortCommand command = new SortCommand();
        CommandResult result = command.execute(model, new CommandHistory());
        assertEquals(result.feedbackToUser, SortCommand.MESSAGE_SUCCESS);
    }
}
