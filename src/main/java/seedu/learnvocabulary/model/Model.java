package seedu.learnvocabulary.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.learnvocabulary.model.tag.Tag;

import seedu.learnvocabulary.model.word.TagContainsKeywordsPredicate;
import seedu.learnvocabulary.model.word.Word;



/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Word> PREDICATE_SHOW_ALL_WORDS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyLearnVocabulary newData);

    /** Returns LearnVocabulary */
    ReadOnlyLearnVocabulary getLearnVocabulary();

    /**
     * Returns true if a word with the same identity as {@code word} exists in LearnVocabulary.
     */
    boolean hasWord(Word word);

    /**
     * Deletes the given word.
     * The word must exist in LearnVocabulary.
     */
    void deleteWord(Word target);

    /**
     * Adds the given word.
     * {@code word} must not already exist in LearnVocabulary.
     */
    void addWord(Word word);

    /**
     * Replaces the given word {@code target} with {@code editedWord}.
     * {@code target} must exist in LearnVocabulary.
     * The word identity of {@code editedWord} must not be the same as another existing word in LearnVocabulary.
     */
    void updateWord(Word target, Word editedWord);

    /** Returns an unmodifiable view of the filtered word list */
    ObservableList<Word> getFilteredWordList();

    /** Returns an unmodifiable view of the filtered word list */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Outputs the trivia question
     *
     */
    Word getTrivia();

    ArrayList<Word> getTriviaList();

    /**
     * Sets a trivia question based on the vocabulary list;
     */
    void setTriviaList();

    boolean isTriviaMode();

    void toggleTriviaMode();

    void clearTrivia();

    void updateScore();

    int currentScore();

    int maxScore();


    /**
     * Updates the filter of the filtered word list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWordList(Predicate<Word> predicate);

    /**
     * Returns true if the model has previous LearnVocabulary states to restore.
     */
    boolean canUndoLearnVocabulary();

    /**
     * Returns true if the model has undone LearnVocabulary states to restore.
     */
    boolean canRedoLearnVocabulary();

    /**
     * Restores the model's LearnVocabulary to its previous state.
     */
    void undoLearnVocabulary();

    /**
     * Restores the model's LearnVocabulary to its previously undone state.
     */
    void redoLearnVocabulary();

    /**
     * Saves the current LearnVocabulary state for undo/redo.
     */
    void commitLearnVocabulary();

    /**
     *
     * @param tags set of tags that is entered by the user
     * @return whether the tag has existed in the list
     */
    boolean hasTag(Set<Tag> tags);

    /**
     *
     * @param tag set of tags that is entered by the user
     * @return whether the tag has existed in the list
     */
    boolean hasTag(Tag tag);


    /**
     * @param toDelete tag to delete as a word group
     */
    void deleteGroup(Tag toDelete);

    /**
     * @param toAdd tag to add as a word group
     */
    void addGroup(Tag toAdd);

    /**
     * @param predicate tag to show as a word group
     */
    void updateTag(TagContainsKeywordsPredicate predicate);


    Set<Tag> getTags();


}
