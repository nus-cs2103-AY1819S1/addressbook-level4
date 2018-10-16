package seedu.learnvocabulary.model;

import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.learnvocabulary.model.tag.Tag;

import seedu.learnvocabulary.model.word.Word;



/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Word> PREDICATE_SHOW_ALL_WORDS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyLearnVocabulary newData);

    /** Returns the LearnVocabulary */
    ReadOnlyLearnVocabulary getLearnVocabulary();

    /**
     * Returns true if a word with the same identity as {@code word} exists in the learnvocabulary book.
     */
    boolean hasWord(Word word);

    /**
     * Deletes the given word.
     * The word must exist in the learnvocabulary book.
     */
    void deleteWord(Word target);

    /**
     * Adds the given word.
     * {@code word} must not already exist in the learnvocabulary book.
     */
    void addWord(Word word);

    /**
     * Replaces the given word {@code target} with {@code editedWord}.
     * {@code target} must exist in the learnvocabulary book.
     * The word identity of {@code editedWord} must not be the same as another existing word in learnvocabulary book.
     */
    void updateWord(Word target, Word editedWord);

    /** Returns an unmodifiable view of the filtered word list */
    ObservableList<Word> getFilteredWordList();

    /**
     * Outputs the trivia question
     *
     */
    Word getTrivia();

    /**
     * Sets a trivia question based on the vocabulary list;
     */
    void setTrivia();

    /**
     * Updates the filter of the filtered word list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWordList(Predicate<Word> predicate);

    /**
     * Returns true if the model has previous learnvocabulary book states to restore.
     */
    boolean canUndoLearnVocabulary();

    /**
     * Returns true if the model has undone learnvocabulary book states to restore.
     */
    boolean canRedoLearnVocabulary();

    /**
     * Restores the model's learnvocabulary book to its previous state.
     */
    void undoLearnVocabulary();

    /**
     * Restores the model's learnvocabulary book to its previously undone state.
     */
    void redoLearnVocabulary();

    /**
     * Saves the current learnvocabulary book state for undo/redo.
     */
    void commitLearnVocabulary();

    /**
     *
     * @param tags set of tags that is entered by the user
     * @return whether the tag has existed in the list
     */
    boolean hasTag(Set<Tag> tags);
}
