package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.Anakin_deck.Anakin_Deck;

/**
 * Unmodifiable view of Anakin
 */
public interface Anakin_ReadOnlyAnakin {
    ObservableList<Anakin_Deck> getDeckList();
}
