package seedu.learnvocabulary.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.DESC_FLY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.DESC_LEVITATE;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_LEVITATE;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_ABILITY;
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
import seedu.learnvocabulary.logic.commands.EditCommand.EditWordDescriptor;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.UserPrefs;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.EditWordDescriptorBuilder;
import seedu.learnvocabulary.testutil.WordBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Word editedWord = new WordBuilder().build();
        EditWordDescriptor descriptor = new EditWordDescriptorBuilder(editedWord).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORD, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_WORD_SUCCESS, editedWord);

        Model expectedModel = new ModelManager(new LearnVocabulary(model.getLearnVocabulary()), new UserPrefs());
        expectedModel.updateWord(model.getFilteredWordList().get(0), editedWord);
        expectedModel.commitLearnVocabulary();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastWord = Index.fromOneBased(model.getFilteredWordList().size());
        Word lastWord = model.getFilteredWordList().get(indexLastWord.getZeroBased());

        WordBuilder wordInList = new WordBuilder(lastWord);
        Word editedWord = wordInList.withName(VALID_NAME_LEVITATE)
                .withTags(VALID_TAG_ABILITY).build();

        EditWordDescriptor descriptor = new EditWordDescriptorBuilder().withName(VALID_NAME_LEVITATE)
                .withTags(VALID_TAG_ABILITY).build();
        EditCommand editCommand = new EditCommand(indexLastWord, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_WORD_SUCCESS, editedWord);

        Model expectedModel = new ModelManager(new LearnVocabulary(model.getLearnVocabulary()), new UserPrefs());
        expectedModel.updateWord(lastWord, editedWord);
        expectedModel.commitLearnVocabulary();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORD, new EditWordDescriptor());
        Word editedWord = model.getFilteredWordList().get(INDEX_FIRST_WORD.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_WORD_SUCCESS, editedWord);

        Model expectedModel = new ModelManager(new LearnVocabulary(model.getLearnVocabulary()), new UserPrefs());
        expectedModel.commitLearnVocabulary();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showWordAtIndex(model, INDEX_FIRST_WORD);

        Word wordInFilteredList = model.getFilteredWordList().get(INDEX_FIRST_WORD.getZeroBased());
        Word editedWord = new WordBuilder(wordInFilteredList).withName(VALID_NAME_LEVITATE).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORD,
                new EditWordDescriptorBuilder().withName(VALID_NAME_LEVITATE).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_WORD_SUCCESS, editedWord);

        Model expectedModel = new ModelManager(new LearnVocabulary(model.getLearnVocabulary()), new UserPrefs());
        expectedModel.updateWord(model.getFilteredWordList().get(0), editedWord);
        expectedModel.commitLearnVocabulary();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateWordUnfilteredList_failure() {
        Word firstWord = model.getFilteredWordList().get(INDEX_FIRST_WORD.getZeroBased());
        EditWordDescriptor descriptor = new EditWordDescriptorBuilder(firstWord).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_WORD, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_WORD);
    }

    @Test
    public void execute_duplicateWordFilteredList_failure() {
        showWordAtIndex(model, INDEX_FIRST_WORD);

        // edit word in filtered list into a duplicate in LearnVocabulary.
        Word wordInList = model.getLearnVocabulary().getWordList().get(INDEX_SECOND_WORD.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORD,
                new EditWordDescriptorBuilder(wordInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_WORD);
    }

    @Test
    public void execute_invalidWordIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWordList().size() + 1);
        EditCommand.EditWordDescriptor descriptor = new EditWordDescriptorBuilder()
                .withName(VALID_NAME_LEVITATE).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORDS_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of LearnVocabulary
     */
    @Test
    public void execute_invalidWordIndexFilteredList_failure() {
        showWordAtIndex(model, INDEX_FIRST_WORD);
        Index outOfBoundIndex = INDEX_SECOND_WORD;
        // ensures that outOfBoundIndex is still in bounds of LearnVocabulary.
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLearnVocabulary().getWordList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditWordDescriptorBuilder().withName(VALID_NAME_LEVITATE).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORDS_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Word editedWord = new WordBuilder().build();
        Word wordToEdit = model.getFilteredWordList().get(INDEX_FIRST_WORD.getZeroBased());
        EditWordDescriptor descriptor = new EditWordDescriptorBuilder(editedWord).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORD, descriptor);
        Model expectedModel = new ModelManager(new LearnVocabulary(model.getLearnVocabulary()), new UserPrefs());
        expectedModel.updateWord(wordToEdit, editedWord);
        expectedModel.commitLearnVocabulary();

        // edit -> first word edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts LearnVocabulary back to previous state and filtered word list to show all words
        expectedModel.undoLearnVocabulary();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first word edited again
        expectedModel.redoLearnVocabulary();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWordList().size() + 1);
        EditWordDescriptor descriptor = new EditWordDescriptorBuilder().withName(VALID_NAME_LEVITATE).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> LearnVocabulary state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORDS_DISPLAYED_INDEX);

        // single LearnVocabulary state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Word} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited word in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the word object regardless of indexing.
     */


    @Test
    public void executeUndoRedo_validIndexFilteredList_sameWordEdited() throws Exception {
        Word editedWord = new WordBuilder().build();
        EditCommand.EditWordDescriptor descriptor = new EditWordDescriptorBuilder(editedWord).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORD, descriptor);
        Model expectedModel = new ModelManager(new LearnVocabulary(model.getLearnVocabulary()), new UserPrefs());

        showWordAtIndex(model, INDEX_SECOND_WORD);
        Word wordToEdit = model.getFilteredWordList().get(INDEX_FIRST_WORD.getZeroBased());
        expectedModel.updateWord(wordToEdit, editedWord);
        expectedModel.commitLearnVocabulary();

        // edit -> edits second word in unfiltered word list / first word in filtered word list
        editCommand.execute(model, commandHistory);

        // undo -> reverts learnvocabulary back to previous state and filtered word list to show all words
        expectedModel.undoLearnVocabulary();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredWordList().get(INDEX_FIRST_WORD.getZeroBased()), wordToEdit);
        // redo -> edits same second word in unfiltered word list
        expectedModel.redoLearnVocabulary();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }


    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_WORD, DESC_FLY);

        // same values -> returns true
        EditWordDescriptor copyDescriptor = new EditCommand.EditWordDescriptor(DESC_FLY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_WORD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_WORD, DESC_FLY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_WORD, DESC_LEVITATE)));
    }

}
