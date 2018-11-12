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

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstWord(model);
        deleteFirstWord(model);

        deleteFirstWord(expectedModel);
        deleteFirstWord(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoLearnVocabulary();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoLearnVocabulary();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
