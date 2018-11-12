package seedu.learnvocabulary.logic.commands;

import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.deleteFirstWord;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import org.junit.Before;
import org.junit.Test;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstWord(model);
        deleteFirstWord(model);
        model.undoLearnVocabulary();
        model.undoLearnVocabulary();

        deleteFirstWord(expectedModel);
        deleteFirstWord(expectedModel);
        expectedModel.undoLearnVocabulary();
        expectedModel.undoLearnVocabulary();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoLearnVocabulary();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoLearnVocabulary();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
