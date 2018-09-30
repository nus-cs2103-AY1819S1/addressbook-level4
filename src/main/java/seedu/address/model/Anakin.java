package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.Anakin_deck.Anakin_Deck;

/**
 * Wraps all data at the Anakin level
 * Duplicates are not allowed (by .isSameDeck comparison)
 */
public class Anakin implements Anakin_ReadOnlyAnakin {

    public Anakin() { }

    public Anakin(Anakin_ReadOnlyAnakin toBeCopied) {

    }

    @Override
    public ObservableList<Anakin_Deck> getDeckList() {
        return null;
    }
}
