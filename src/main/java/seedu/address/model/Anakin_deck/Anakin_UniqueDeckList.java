package seedu.address.model.Anakin_deck;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of decks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class Anakin_UniqueDeckList implements Iterable<Anakin_Deck> {

    public final ObservableList<Anakin_Deck> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent deck as the given argument.
     */
    public boolean contains(Anakin_Deck toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDeck);
    }

    /**
     * Adds a deck to the list.
     * The deck must not already exist in the list.
     */
    public void add(Anakin_Deck toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            // TODO: throw exception
            //throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setDeck(Anakin_Deck target, Anakin_Deck editedDeck) {
        requireAllNonNull(target, editedDeck);

        int index = internalList.indexOf(target);
        if (index == -1) {
            //TODO
            //throw new PersonNotFoundException();
        }

        if (!target.isSameDeck(editedDeck) && contains(editedDeck)) {
            // TODO
            //throw new DuplicatePersonException();
        }

        internalList.set(index, editedDeck);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Anakin_Deck toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            // TODO
            //throw new PersonNotFoundException();
        }
    }

    public void setDecks(Anakin_UniqueDeckList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Anakin_Deck> decks) {
        requireAllNonNull(decks);
        if (!decksAreUnique(decks)) {
            // TODO
            //throw new DuplicatePersonException();
        }

        internalList.setAll(decks);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Anakin_Deck> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Anakin_Deck> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Anakin_UniqueDeckList // instanceof handles nulls
                && internalList.equals(((Anakin_UniqueDeckList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     * @param decks
     */
    private boolean decksAreUnique(List<Anakin_Deck> decks) {
        for (int i = 0; i < decks.size() - 1; i++) {
            for (int j = i + 1; j < decks.size(); j++) {
                if (decks.get(i).isSameDeck(decks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
