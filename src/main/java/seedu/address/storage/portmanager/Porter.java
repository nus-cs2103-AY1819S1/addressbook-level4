package seedu.address.storage.portmanager;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import seedu.address.model.deck.Deck;

public interface Porter {

    void exportDeck(Deck deck) throws Exception;

    Deck importDeck(Path filepath) throws FileNotFoundException;
}
