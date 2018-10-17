package seedu.learnvocabulary.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.showWordAtIndex;
import static seedu.learnvocabulary.testutil.TypicalIndexes.INDEX_FIRST_WORD;
import static seedu.learnvocabulary.testutil.TypicalIndexes.INDEX_SECOND_WORD;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import org.junit.Test;

import seedu.learnvocabulary.commons.core.Messages;
import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.UserPrefs;
import seedu.learnvocabulary.model.word.Word;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Word wordToDelete = model.getFilteredWordList().get(INDEX_FIRST_WORD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_WORD);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_WORD_SUCCESS, wordToDelete);

        ModelManager expectedModel = new ModelManager(model.getLearnVocabulary(), new UserPrefs());
        expectedModel.deleteWord(wordToDelete);
        expectedModel.commitLearnVocabulary();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWordList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORDS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showWordAtIndex(model, INDEX_FIRST_WORD);

        Word wordToDelete = model.getFilteredWordList().get(INDEX_FIRST_WORD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_WORD);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_WORD_SUCCESS, wordToDelete);

        Model expectedModel = new ModelManager(model.getLearnVocabulary(), new UserPrefs());
        expectedModel.deleteWord(wordToDelete);
        expectedModel.commitLearnVocabulary();
        showNoWord(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showWordAtIndex(model, INDEX_FIRST_WORD);

        Index outOfBoundIndex = INDEX_SECOND_WORD;
        // ensures that outOfBoundIndex is still in bounds of learnvocabulary book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLearnVocabulary().getWordList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORDS_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Word wordToDelete = model.getFilteredWordList().get(INDEX_FIRST_WORD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_WORD);
        Model expectedModel = new ModelManager(model.getLearnVocabulary(), new UserPrefs());
        expectedModel.deleteWord(wordToDelete);
        expectedModel.commitLearnVocabulary();

        // delete -> first word deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts learnvocabulary back to previous state and filtered word list to show all words
        expectedModel.undoLearnVocabulary();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first word deleted again
        expectedModel.redoLearnVocabulary();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWordList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> learnvocabulary book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORDS_DISPLAYED_INDEX);

        // single learnvocabulary book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Word} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted word in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the word object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameWordDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_WORD);
        Model expectedModel = new ModelManager(model.getLearnVocabulary(), new UserPrefs());

        showWordAtIndex(model, INDEX_SECOND_WORD);
        Word wordToDelete = model.getFilteredWordList().get(INDEX_FIRST_WORD.getZeroBased());
        expectedModel.deleteWord(wordToDelete);
        expectedModel.commitLearnVocabulary();

        // delete -> deletes second word in unfiltered word list / first word in filtered word list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts learnvocabulary back to previous state and filtered word list to show all words
        expectedModel.undoLearnVocabulary();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(wordToDelete, model.getFilteredWordList().get(INDEX_FIRST_WORD.getZeroBased()));
        // redo -> deletes same second word in unfiltered word list
        expectedModel.redoLearnVocabulary();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_WORD);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_WORD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_WORD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different word -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoWord(Model model) {
        model.updateFilteredWordList(p -> false);

        assertTrue(model.getFilteredWordList().isEmpty());
    }
}
