package seedu.address.model.word;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.word.exceptions.DuplicatePersonException;
import seedu.address.model.word.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A word is considered unique by comparing using {@code Word#isSameWord(Word)}. As such, adding and updating of
 * persons uses Word#isSameWord(Word) for equality so as to ensure that the word being added or updated is
 * unique in terms of identity in the UniqueWordList. However, the removal of a word uses Word#equals(Object) so
 * as to ensure that the word with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Word#isSameWord(Word)
 */
public class UniqueWordList implements Iterable<Word> {

    private final ObservableList<Word> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent word as the given argument.
     */
    public boolean contains(Word toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameWord);
    }

    /**
     * Adds a word to the list.
     * The word must not already exist in the list.
     */
    public void add(Word toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the word {@code target} in the list with {@code editedWord}.
     * {@code target} must exist in the list.
     * The word identity of {@code editedWord} must not be the same as another existing word in the list.
     */
    public void setWord(Word target, Word editedWord) {
        requireAllNonNull(target, editedWord);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameWord(editedWord) && contains(editedWord)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedWord);
    }

    /**
     * Removes the equivalent word from the list.
     * The word must exist in the list.
     */
    public void remove(Word toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setWords(UniqueWordList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code words}.
     * {@code words} must not contain duplicate words.
     */
    public void setWords(List<Word> words) {
        requireAllNonNull(words);
        if (!wordsAreUnique(words)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(words);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Word> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Word> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueWordList // instanceof handles nulls
                        && internalList.equals(((UniqueWordList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code words} contains only unique words.
     */
    private boolean wordsAreUnique(List<Word> words) {
        for (int i = 0; i < words.size() - 1; i++) {
            for (int j = i + 1; j < words.size(); j++) {
                if (words.get(i).isSameWord(words.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
