package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalTriviaBundle;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TriviaBundle;
import seedu.address.model.UserPrefs;
import seedu.address.model.test.TriviaResults;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyTriviaBundle_failure() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitTriviaBundle();

        assertCommandFailure(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_NO_CARDS);
    }

    @Test
    public void execute_nonEmptyTriviaBundle_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(),
                new TriviaResults(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(),
                new TriviaResults(), new UserPrefs());
        expectedModel.resetData(new TriviaBundle());
        expectedModel.commitTriviaBundle();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
