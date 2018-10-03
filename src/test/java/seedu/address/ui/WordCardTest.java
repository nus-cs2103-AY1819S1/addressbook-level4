package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.word.Word;
import seedu.address.testutil.PersonBuilder;

public class WordCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Word wordWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(wordWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, wordWithNoTags, 1);

        // with tags
        Word wordWithTags = new PersonBuilder().build();
        personCard = new PersonCard(wordWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, wordWithTags, 2);
    }

    @Test
    public void equals() {
        Word word = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(word, 0);

        // same word, same index -> returns true
        PersonCard copy = new PersonCard(word, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different word, same index -> returns false
        Word differentWord = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentWord, 0)));

        // same word, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(word, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedWord} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Word expectedWord, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify word details are displayed correctly
        assertCardDisplaysPerson(expectedWord, personCardHandle);
    }
}
