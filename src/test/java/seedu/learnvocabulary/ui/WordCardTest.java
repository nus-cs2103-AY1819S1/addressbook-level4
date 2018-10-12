package seedu.learnvocabulary.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.ui.testutil.GuiTestAssert.assertCardDisplaysWord;

import org.junit.Test;

import guitests.guihandles.WordCardHandle;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.WordBuilder;

public class WordCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Word wordWithNoTags = new WordBuilder().withTags(new String[0]).build();
        WordCard wordCard = new WordCard(wordWithNoTags, 1);
        uiPartRule.setUiPart(wordCard);
        assertCardDisplay(wordCard, wordWithNoTags, 1);

        // with tags
        Word wordWithTags = new WordBuilder().build();
        wordCard = new WordCard(wordWithTags, 2);
        uiPartRule.setUiPart(wordCard);
        assertCardDisplay(wordCard, wordWithTags, 2);
    }

    @Test
    public void equals() {
        Word word = new WordBuilder().build();
        WordCard wordCard = new WordCard(word, 0);

        // same word, same index -> returns true
        WordCard copy = new WordCard(word, 0);
        assertTrue(wordCard.equals(copy));

        // same object -> returns true
        assertTrue(wordCard.equals(wordCard));

        // null -> returns false
        assertFalse(wordCard.equals(null));

        // different types -> returns false
        assertFalse(wordCard.equals(0));

        // different word, same index -> returns false
        Word differentWord = new WordBuilder().withName("differentName").build();
        assertFalse(wordCard.equals(new WordCard(differentWord, 0)));

        // same word, different index -> returns false
        assertFalse(wordCard.equals(new WordCard(word, 1)));
    }

    /**
     * Asserts that {@code wordCard} displays the details of {@code expectedWord} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(WordCard wordCard, Word expectedWord, int expectedId) {
        guiRobot.pauseForHuman();

        WordCardHandle wordCardHandle = new WordCardHandle(wordCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", wordCardHandle.getId());

        // verify word details are displayed correctly
        assertCardDisplaysWord(expectedWord, wordCardHandle);
    }
}
