package seedu.learnvocabulary.model;

import static java.util.Objects.requireNonNull;
import static seedu.learnvocabulary.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.learnvocabulary.commons.core.ComponentManager;
import seedu.learnvocabulary.commons.core.LogsCenter;
import seedu.learnvocabulary.commons.events.model.LearnVocabularyChangedEvent;
import seedu.learnvocabulary.model.tag.Tag;

import seedu.learnvocabulary.model.word.TagContainsKeywordsPredicate;
import seedu.learnvocabulary.model.word.Word;

/**
 * Represents the in-memory model of LearnVocabulary data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedLearnVocabulary versionedLearnVocabulary;
    private final FilteredList<Word> filteredWords;
    private final FilteredList<Tag> filteredTags;


    /**
     * Initializes a ModelManager with the given LearnVocabulary and userPrefs.
     */
    public ModelManager(ReadOnlyLearnVocabulary learnVocabulary, UserPrefs userPrefs) {
        super();
        requireAllNonNull(learnVocabulary, userPrefs);

        logger.fine("Initializing with LearnVocabulary book: " + learnVocabulary + " and user prefs " + userPrefs);

        versionedLearnVocabulary = new VersionedLearnVocabulary(learnVocabulary);
        filteredWords = new FilteredList<>(versionedLearnVocabulary.getWordList());
        filteredTags = new FilteredList<>(versionedLearnVocabulary.getTagList());

    }

    public ModelManager() {
        this(new LearnVocabulary(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyLearnVocabulary newData) {
        versionedLearnVocabulary.resetData(newData);
        indicateLearnVocabularyChanged();
    }

    @Override
    public ReadOnlyLearnVocabulary getLearnVocabulary() {
        return versionedLearnVocabulary;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateLearnVocabularyChanged() {
        raise(new LearnVocabularyChangedEvent(versionedLearnVocabulary));
    }

    @Override
    public boolean hasWord(Word word) {
        requireNonNull(word);
        return versionedLearnVocabulary.hasWord(word);
    }
    //@@author Harryqu123
    @Override
    public boolean hasTag(Set<Tag> tags) {
        requireNonNull(tags);
        boolean check = true;
        for (Tag tag: tags) {
            if (!versionedLearnVocabulary.hasTag(tag)) {
                check = false;
            }
        }
        return check;
    }
    @Override
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        boolean check = true;
        if (!versionedLearnVocabulary.hasTag(tag)) {
            check = false;
        }
        return check;
    }

    @Override
    public void deleteGroup(Tag toDelete) {
        requireNonNull(toDelete);
        versionedLearnVocabulary.deleteGroup(toDelete);
    }

    @Override
    public void addGroup(Tag toAdd) {
        requireNonNull(toAdd);
        if (!versionedLearnVocabulary.hasTag(toAdd)) {
            versionedLearnVocabulary.addGroup(toAdd);
        }
    }

    @Override
    public void updateTag(TagContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        filteredWords.setPredicate(predicate);
    }

    @Override
    public Set<Tag> getTags() {
        return versionedLearnVocabulary.getTags();
    }
    //@@author
    @Override
    public void deleteWord(Word target) {

        versionedLearnVocabulary.removeWord(target);
        indicateLearnVocabularyChanged();
    }

    @Override
    public void addWord(Word word) {
        versionedLearnVocabulary.addWord(word);
        updateFilteredWordList(PREDICATE_SHOW_ALL_WORDS);
        indicateLearnVocabularyChanged();
    }

    @Override
    public void updateWord(Word target, Word editedWord) {
        requireAllNonNull(target, editedWord);

        versionedLearnVocabulary.updateWord(target, editedWord);
        indicateLearnVocabularyChanged();
    }

    //=========== Filtered Word List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Word} backed by the internal list of
     * {@code versionedLearnVocabulary}
     */
    @Override
    public ObservableList<Word> getFilteredWordList() {
        return FXCollections.unmodifiableObservableList(filteredWords);
    }

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return FXCollections.unmodifiableObservableList(filteredTags);
    }

    /**
     * Returns the current trivia question
     *
     */
    @Override
    public Word getTrivia() {
        return versionedLearnVocabulary.getTrivia();
    }

    @Override
    public ArrayList<Word> getTriviaList() {
        return versionedLearnVocabulary.getTriviaList();
    }

    /**
     * Sets the trivia question
     */
    @Override
    public void setTriviaList() {
        versionedLearnVocabulary.setTriviaList();
    }

    public int currentScore() {
        return versionedLearnVocabulary.currentScore(); }

    public int maxScore() {
        return versionedLearnVocabulary.maxScore(); }

    public void updateScore() {
        versionedLearnVocabulary.updateScore(); }

    public boolean isTriviaMode() {
        return versionedLearnVocabulary.isTriviaMode(); }

    public void toggleTriviaMode() {
        versionedLearnVocabulary.toggleTriviaMode(); }

    public void clearTrivia() {
        versionedLearnVocabulary.clearTrivia(); }


    @Override
    public void updateFilteredWordList(Predicate<Word> predicate) {
        requireNonNull(predicate);
        filteredWords.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoLearnVocabulary() {
        return versionedLearnVocabulary.canUndo();
    }

    @Override
    public boolean canRedoLearnVocabulary() {
        return versionedLearnVocabulary.canRedo();
    }

    @Override
    public void undoLearnVocabulary() {
        versionedLearnVocabulary.undo();
        indicateLearnVocabularyChanged();
    }

    @Override
    public void redoLearnVocabulary() {
        versionedLearnVocabulary.redo();
        indicateLearnVocabularyChanged();
    }

    @Override
    public void commitLearnVocabulary() {
        versionedLearnVocabulary.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedLearnVocabulary.equals(other.versionedLearnVocabulary)
                && filteredWords.equals(other.filteredWords);
    }

}
