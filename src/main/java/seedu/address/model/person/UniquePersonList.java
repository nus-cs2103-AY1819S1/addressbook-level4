package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A word is considered unique by comparing using {@code Word#isSamePerson(Word)}. As such, adding and updating of
 * persons uses Word#isSamePerson(Word) for equality so as to ensure that the word being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a word uses Word#equals(Object) so
 * as to ensure that the word with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Word#isSamePerson(Word)
 */
public class UniquePersonList implements Iterable<Word> {

    private final ObservableList<Word> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent word as the given argument.
     */
    public boolean contains(Word toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
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
    public void setPerson(Word target, Word editedWord) {
        requireAllNonNull(target, editedWord);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedWord) && contains(editedWord)) {
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

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code words}.
     * {@code words} must not contain duplicate words.
     */
    public void setPersons(List<Word> words) {
        requireAllNonNull(words);
        if (!personsAreUnique(words)) {
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
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code words} contains only unique words.
     */
    private boolean personsAreUnique(List<Word> words) {
        for (int i = 0; i < words.size() - 1; i++) {
            for (int j = i + 1; j < words.size(); j++) {
                if (words.get(i).isSamePerson(words.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
