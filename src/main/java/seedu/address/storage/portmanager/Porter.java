package seedu.address.storage.portmanager;

import seedu.address.model.deck.Deck;
import seedu.address.model.deck.anakinexceptions.DeckImportException;

public interface Porter {

    void exportDeck(Deck deck);

    Deck importDeck(String stringPath) throws DeckImportException;
}
