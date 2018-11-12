package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.TagContainsKeywordsPredicate;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.WordBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_wordAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingWordAdded modelStub = new ModelStubAcceptingWordAdded();
        Word validWord = new WordBuilder().build();
        if (!validWord.getTags().isEmpty()) {
            thrown.expect(CommandException.class);
            thrown.expectMessage(AddCommand.MESSAGE_NO_GROUP);
            CommandResult commandResult = new AddCommand(validWord).execute(modelStub, commandHistory);

            assertEquals(String.format(AddCommand.MESSAGE_NO_GROUP, validWord), commandResult.feedbackToUser);
            assertEquals(Arrays.asList(validWord), modelStub.wordsAdded);
            assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
        }
    }

    @Test
    public void execute_duplicateWord_throwsCommandException() throws Exception {
        Word validWord = new WordBuilder().build();
        AddCommand addCommand = new AddCommand(validWord);
        ModelStub modelStub = new ModelStubWithWord(validWord);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_WORD);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Word fly = new WordBuilder().withName("Fly").build();
        Word levitate = new WordBuilder().withName("Levitate").build();
        AddCommand addFlyCommand = new AddCommand(fly);
        AddCommand addLevitateCommand = new AddCommand(levitate);

        // same object -> returns true
        assertTrue(addFlyCommand.equals(addFlyCommand));

        // same values -> returns true
        AddCommand addFlyCommandCopy = new AddCommand(fly);
        assertTrue(addFlyCommand.equals(addFlyCommandCopy));

        // different types -> returns false
        assertFalse(addFlyCommand.equals(1));

        // null -> returns false
        assertFalse(addFlyCommand.equals(null));

        // different word -> returns false
        assertFalse(addFlyCommand.equals(addLevitateCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addWord(Word word) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyLearnVocabulary newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyLearnVocabulary getLearnVocabulary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasWord(Word word) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteWord(Word target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateWord(Word target, Word editedWord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Word> getFilteredWordList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Word getTrivia() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<Word> getTriviaList() {
            return null;
        }

        @Override
        public void setTriviaList() {

        }

        @Override
        public boolean isTriviaMode() {
            return false;
        }

        @Override
        public void toggleTriviaMode() {

        }

        @Override
        public void clearTrivia() {

        }

        @Override
        public void updateScore() {

        }

        @Override
        public int currentScore() {
            return 0;
        }

        @Override
        public int maxScore() {
            return 0;
        }

        @Override
        public void updateFilteredWordList(Predicate<Word> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoLearnVocabulary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoLearnVocabulary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoLearnVocabulary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoLearnVocabulary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitLearnVocabulary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTag(Set<Tag> tags) {
            return false;
        }

        @Override
        public boolean hasTag(Tag tag) {
            return false;
        }

        @Override
        public void deleteGroup(Tag toDelete) {

        }

        @Override
        public void addGroup(Tag toAdd) {

        }

        @Override
        public void updateTag(TagContainsKeywordsPredicate predicate) {

        }

        @Override
        public Set<Tag> getTags() {
            return null;
        }
    }

    /**
     * A Model stub that contains a single word.
     */
    private class ModelStubWithWord extends ModelStub {
        private final Word word;

        ModelStubWithWord(Word word) {
            requireNonNull(word);
            this.word = word;
        }

        @Override
        public boolean hasWord(Word word) {
            requireNonNull(word);
            return this.word.isSameWord(word);
        }
    }

    /**
     * A Model stub that always accept the word being added.
     */
    private class ModelStubAcceptingWordAdded extends ModelStub {
        final ArrayList<Word> wordsAdded = new ArrayList<>();

        @Override
        public boolean hasWord(Word word) {
            requireNonNull(word);
            return wordsAdded.stream().anyMatch(word::isSameWord);
        }

        @Override
        public void addWord(Word word) {
            requireNonNull(word);
            wordsAdded.add(word);
        }

        @Override
        public void commitLearnVocabulary() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyLearnVocabulary getLearnVocabulary() {
            return new LearnVocabulary();
        }
    }

}
