package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.Anakin_deck.Anakin_Deck;

/**
 * Unmodifiable view of Anakin
 */
public interface Anakin_ReadOnlyAnakin {

    /**
     * Returns an unmodifiable view of the decks list.
     * This list will not contain any duplicate decks.
     */
    ObservableList<Anakin_Deck> getDeckList();
}
