package seedu.address.model.Anakin_deck;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of cards that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class Anakin_UniqueCardList implements Iterable<Anakin_Card> {

    public final ObservableList<Anakin_Card> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent deck as the given argument.
     */
    public boolean contains(Anakin_Card toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCard);
    }

    /**
     * Adds a deck to the list.
     * The deck must not already exist in the list.
     */
    public void add(Anakin_Card toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            // TODO: throw exception
            //throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the card {@code target} in the list with {@code editedCard}.
     * {@code target} must exist in the list.
     * The card identity of {@code editedCard} must not be the same as another existing card in the list.
     */
    public void setCard(Anakin_Card target, Anakin_Card editedCard) {
        requireAllNonNull(target, editedCard);

        int index = internalList.indexOf(target);
        if (index == -1) {
            //TODO
            //throw new PersonNotFoundException();
        }

        if (!target.isSameCard(editedCard) && contains(editedCard)) {
            // TODO
            //throw new DuplicatePersonException();
        }

        internalList.set(index, editedCard);
    }

    /**
     * Removes the equivalent card from the list.
     * The card must exist in the list.
     */
    public void remove(Anakin_Card toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            // TODO
            //throw new PersonNotFoundException();
        }
    }

    public void setCards(Anakin_UniqueCardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code cards}.
     * {@code cards} must not contain duplicate cards.
     */
    public void setCards(List<Anakin_Card> cards) {
        requireAllNonNull(cards);
        if (!cardsAreUnique(cards)) {
            // TODO
            //throw new DuplicatePersonException();
        }

        internalList.setAll(cards);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Anakin_Card> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Anakin_Card> iterator() {
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
     * Returns true if {@code cards} contains only unique cards.
     * @param cards
     */
    private boolean cardsAreUnique(List<Anakin_Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                if (cards.get(i).isSameCard(cards.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
