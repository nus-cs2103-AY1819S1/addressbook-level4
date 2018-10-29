package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalCards.getTypicalTriviaBundle;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.test.TriviaResults;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(),
            new TriviaResults(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(),
            new TriviaResults(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    /*
    TODO do this after deleteFirstPerson is implemented
    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);
        model.undoTriviaBundle();
        model.undoTriviaBundle();

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        expectedModel.undoTriviaBundle();
        expectedModel.undoTriviaBundle();
    }
    */

    @Test
    public void execute() {
        /*
        // multiple redoable states in model
        expectedModel.redoTriviaBundle();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoTriviaBundle();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
        */
    }
}
