package seedu.address.storage.portmanager;

import java.nio.file.Path;

import seedu.address.model.deck.Deck;
import seedu.address.model.deck.anakinexceptions.DeckImportException;

public interface Porter {

    void exportDeck(Deck deck);

    Deck importDeck(Path filepath) throws DeckImportException;
}
