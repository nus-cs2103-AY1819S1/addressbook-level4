package seedu.learnvocabulary.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.UniqueWordList;
import seedu.learnvocabulary.model.word.Word;


/**
 * Wraps all data at LearnVocabulary
 * Duplicates are not allowed (by .isSameWord comparison)
 */
public class LearnVocabulary implements ReadOnlyLearnVocabulary {

    private final UniqueWordList words;
    private ArrayList<Word> triviaQuestionList;
    private boolean triviaMode = false;
    private int currentScore = 0;
    private int maxScore;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        words = new UniqueWordList();
    }
    //@@author Harryqu123
    private final Set<Tag> addedTag = new HashSet<>() {{
            add(new Tag(Tag.DEFAULT_TAG));
        }};
    //@@author
    public LearnVocabulary() {}

    /**
     * Creates LearnVocabulary using the Words in the {@code toBeCopied}
     */
    public LearnVocabulary(ReadOnlyLearnVocabulary toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the word list with {@code words}.
     * {@code words} must not contain duplicate words.
     */
    public void setWords(List<Word> words) {
        this.words.setWords(words);
    }

    /**
     * Resets the existing data of this {@code LearnVocabulary} with {@code newData}.
     */
    public void resetData(ReadOnlyLearnVocabulary newData) {
        requireNonNull(newData);
        setWords(newData.getWordList());
        //@@author Harryqu123
        addedTag.clear();
        addedTag.add(new Tag(Tag.DEFAULT_TAG));
        //@@author
    }

    //// word-level operations

    /**
     * Returns true if a word with the same identity as {@code word} exists in LearnVocabulary.
     */
    public boolean hasWord(Word word) {
        requireNonNull(word);
        return words.contains(word);
    }
    //@@author Harryqu123
    /**
     * Returns true if a tag with the same identity as {@code tag} exists in the address book.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        for (Word word:words) {
            if (word.getTags().contains(tag)) {
                return true;
            }
        }
        for (Tag temptag: addedTag) {
            if (temptag.equals(tag)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Set<Tag> getTags() {
        Set<Tag> totalTag = addedTag;
        for (Word word:words) {
            for (Tag tag: word.getTags()) {
                if (!totalTag.contains(tag)) {
                    totalTag.add(tag);
                }
            }
        }
        return totalTag;
    }

    /**
     * @param toDelete delete the word group from each word
     */
    public void deleteGroup(Tag toDelete) {
        requireNonNull(toDelete);
        final UniqueWordList wordsTemp = new UniqueWordList();
        if (addedTag.contains(toDelete)) {
            addedTag.remove(toDelete);
        }
        for (Word word:words) {
            if (word.getTags().contains(toDelete)) {
                word.deleteTags(toDelete);
            }
            if (word.getTags().isEmpty()) {
                wordsTemp.add(word);
            }
        }
        for (Word person: wordsTemp) {
            words.remove(person);
        }
    }

    public void addGroup(Tag toAdd) {
        addedTag.add(toAdd);
    }
    //@@author
    /**
     * Adds a word to LearnVocabulary.
     * The word must not already exist in LearnVocabulary.
     */
    public void addWord(Word p) {
        words.add(p);
    }

    /**
     * Replaces the given word {@code target} in the list with {@code editedWord}.
     * {@code target} must exist in LearnVocabulary.
     * The word identity of {@code editedWord} must not be
     * the same as another existing word in LearnVocabulary.
     */
    public void updateWord(Word target, Word editedWord) {
        requireNonNull(editedWord);

        words.setWord(target, editedWord);
    }

    /**
     * Sets the trivia question list based on the current vocabulary list
     */
    public void setTriviaList() {
        triviaQuestionList = new ArrayList<Word>();
        ObservableList<Word> triviaRef = words.asUnmodifiableObservableList();
        ArrayList<Word> triviaRefCopy = new ArrayList<Word>();
        this.currentScore = 0;
        int triviaSize = 10;
        int length = triviaRef.size();

        for (int index = 0; index < length; index++) {
            triviaRefCopy.add(triviaRef.get(index));
        }

        Collections.shuffle(triviaRefCopy);

        int copyIndex = 0;
        int copySize = triviaRefCopy.size();
        while (copySize > 0 && triviaSize > 0) {
            Word wordToAdd = triviaRefCopy.get(copyIndex);
            triviaQuestionList.add(wordToAdd);
            copyIndex++;
            triviaSize--;
            copySize--;
        }

        this.maxScore = triviaQuestionList.size();
    }

    /**
     * Returns the current trivia question
     */
    public Word getTrivia() {
        return triviaQuestionList.get(0);
    }

    /**
     * Returns the current trivia question list
     */
    public ArrayList<Word> getTriviaList() {
        return triviaQuestionList;
    }

    public boolean isTriviaMode() {
        return triviaMode; }

    public void toggleTriviaMode() {
        triviaMode = !triviaMode; }

    /**
     * Clears the current trivia question and remove it from the trivia question list
     */
    public void clearTrivia() {
        triviaQuestionList.remove(0);
    }

    public int currentScore() {
        return this.currentScore;
    }

    public int maxScore() {
        return this.maxScore;
    }

    public void updateScore() {
        this.currentScore++;
    }

    /**
     * Removes {@code key} from this {@code LearnVocabulary}.
     * {@code key} must exist in LearnVocabulary.
     */
    public void removeWord(Word key) {
        words.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return words.asUnmodifiableObservableList().size() + " words";
        // TODO: refine later
    }

    @Override
    public ObservableList<Word> getWordList() {
        return words.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LearnVocabulary // instanceof handles nulls
                && words.equals(((LearnVocabulary) other).words));
    }

    @Override
    public int hashCode() {
        return words.hashCode();
    }


}
