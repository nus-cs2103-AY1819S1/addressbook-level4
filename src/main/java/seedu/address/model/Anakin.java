package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.Anakin_deck.Anakin_Deck;

public class Anakin implements Anakin_ReadOnlyAnakin {

    public Anakin() { }

    public Anakin(Anakin_ReadOnlyAnakin toBeCopied) {

    }

    @Override
    public ObservableList<Anakin_Deck> getDeckList() {
        return null;
    }
}
