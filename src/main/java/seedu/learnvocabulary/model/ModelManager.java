package seedu.learnvocabulary.model;

import static java.util.Objects.requireNonNull;
import static seedu.learnvocabulary.commons.util.CollectionUtil.requireAllNonNull;

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

import seedu.learnvocabulary.model.word.Word;

/**
 * Represents the in-memory model of LearnVocabulary data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedLearnVocabulary versionedLearnVocabulary;
    private final FilteredList<Word> filteredWords;

    /**
     * Initializes a ModelManager with the given LearnVocabulary and userPrefs.
     */
    public ModelManager(ReadOnlyLearnVocabulary learnVocabulary, UserPrefs userPrefs) {
        super();
        requireAllNonNull(learnVocabulary, userPrefs);

        logger.fine("Initializing with LearnVocabulary book: " + learnVocabulary + " and user prefs " + userPrefs);

        versionedLearnVocabulary = new VersionedLearnVocabulary(learnVocabulary);
        filteredWords = new FilteredList<>(versionedLearnVocabulary.getWordList());
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
    public void deleteWord(Word target) {

        versionedLearnVocabulary.removeWord(target);

        if (target.equals(versionedLearnVocabulary.getTrivia())) {
            versionedLearnVocabulary.clearTrivia();
        }

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

    /**
     * Returns the current trivia question
     *
     */
    @Override
    public Word getTrivia() {
        return versionedLearnVocabulary.getTrivia();
    }

    /**
     * Sets the trivia question
     */
    @Override
    public void setTrivia() {
        versionedLearnVocabulary.setTrivia();
    }


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
