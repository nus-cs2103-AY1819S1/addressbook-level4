package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalDecks.DECK_WITH_CARDS;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.deck.Deck;
import seedu.address.storage.portmanager.PortManager;

public class PortManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "PortManagerTest");

    public static final PortManager portManager = new PortManager(TEST_DATA_FOLDER);


    private Deck testDeck = DECK_WITH_CARDS;

    @Test
    public void exportDeck_success() throws Exception {
        portManager.exportDeck(testDeck);
    }

        @Test
    public void exportImportDeck_success() throws Exception{
        String deckName = testDeck.getName().toString();
        portManager.exportDeck(testDeck);
        Path testFilePath = TEST_DATA_FOLDER.resolve(deckName + ".xml");
        Deck importedDeck = portManager.importDeck(testFilePath);
        assertEquals(importedDeck,testDeck);
    }
}
