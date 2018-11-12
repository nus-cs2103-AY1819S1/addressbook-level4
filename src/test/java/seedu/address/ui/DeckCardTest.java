package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.ui.testutil.GuiTestAssert.assertDeckCardDisplaysDeck;

import org.junit.Test;

import guitests.guihandles.DeckCardHandle;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.DeckBuilder;


public class DeckCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Deck deckWithNoCards = new DeckBuilder().build();
        DeckCard deckCard = new DeckCard(deckWithNoCards, 1);
        uiPartRule.setUiPart(deckCard);
        assertCardDisplay(deckCard, deckWithNoCards, 1);

        // with cards
        Deck deckWithCards = getTypicalDeck();
        deckCard = new DeckCard(deckWithCards, 2);
        uiPartRule.setUiPart(deckCard);
        assertCardDisplay(deckCard, deckWithCards, 2);
    }

    @Test
    public void equals() {
        Deck deck = new DeckBuilder().build();
        DeckCard deckCard = new DeckCard(deck, 0);

        // same deck, same index -> returns true
        DeckCard copy = new DeckCard(deck, 0);
        assertTrue(deckCard.equals(copy));

        // same object -> returns true
        assertTrue(deckCard.equals(deckCard));

        // null -> returns false
        assertFalse(deckCard.equals(null));

        // different types -> returns false
        assertFalse(deckCard.equals(0));

        // different deck, same index -> returns false
        Deck differentDeck = new DeckBuilder().withName("differentName").build();
        assertFalse(deckCard.equals(new DeckCard(differentDeck, 0)));

        // same deck, different index -> returns false
        assertFalse(deckCard.equals(new DeckCard(deck, 1)));
    }

    /**
     * Asserts that {@code deckCard} displays the details of {@code expectedDeck} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(DeckCard deckCard, Deck expectedDeck, int expectedId) {
        guiRobot.pauseForHuman();

        DeckCardHandle deckCardHandle = new DeckCardHandle(deckCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", deckCardHandle.getId());

        // verify deck details are displayed correctly
        assertDeckCardDisplaysDeck(expectedDeck, deckCardHandle);
    }
}
