package seedu.address.model.AnakinDeck;

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
public class AnakinUniqueCardList implements Iterable<AnakinCard> {

    public final ObservableList<AnakinCard> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent deck as the given argument.
     */
    public boolean contains(AnakinCard toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCard);
    }

    /**
     * Adds a deck to the list.
     * The deck must not already exist in the list.
     */
    public void add(AnakinCard toAdd) {
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
    public void setCard(AnakinCard target, AnakinCard editedCard) {
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
    public void remove(AnakinCard toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            // TODO
            //throw new PersonNotFoundException();
        }
    }

    public void setCards(AnakinUniqueCardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code cards}.
     * {@code cards} must not contain duplicate cards.
     */
    public void setCards(List<AnakinCard> cards) {
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
    public ObservableList<AnakinCard> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<AnakinCard> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnakinUniqueDeckList // instanceof handles nulls
                && internalList.equals(((AnakinUniqueDeckList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code cards} contains only unique cards.
     * @param cards
     */
    private boolean cardsAreUnique(List<AnakinCard> cards) {
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
