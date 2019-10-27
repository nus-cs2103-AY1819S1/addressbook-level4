package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;

import org.junit.Test;

import guitests.guihandles.EntryCardHandle;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.util.EntryBuilder;

public class EntryCardTest extends GuiUnitTest {
    @Test
    public void display() {
        // no tags
        ResumeEntry entryWithNoTags = new EntryBuilder().withTags(new String[0]).build();
        EntryCard entryCard = new EntryCard(entryWithNoTags, 1);
        uiPartRule.setUiPart(entryCard);
        assertCardDisplay(entryCard, entryWithNoTags, 1);

        // with tags
        ResumeEntry entryWithTags = new EntryBuilder().withTags("JAVA").build();
        entryCard = new EntryCard(entryWithTags, 2);
        uiPartRule.setUiPart(entryCard);
        assertCardDisplay(entryCard, entryWithTags, 2);
    }

    @Test
    public void equals() {
        ResumeEntry entryWithTags = new EntryBuilder().withTags("JAVA").build();
        EntryCard entryCard = new EntryCard(entryWithTags, 0);

        // same Entry, same index -> returns true
        EntryCard copy = new EntryCard(entryWithTags, 0);
        assertTrue(entryCard.equals(copy));

        // same object -> returns true
        assertTrue(entryCard.equals(entryCard));

        // null -> returns false
        assertFalse(entryCard.equals(null));

        // different types -> returns false
        assertFalse(entryCard.equals(0));

        // different person, same index -> returns false
        ResumeEntry differentEntry = new EntryBuilder().withTitle("differentName").build();
        assertFalse(entryCard.equals(new EntryCard(differentEntry, 0)));

        // same person, different index -> returns false
        assertFalse(entryCard.equals(new EntryCard(entryWithTags, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EntryCard entryCard, ResumeEntry expectedEntry, int expectedId) {
        guiRobot.pauseForHuman();

        EntryCardHandle entryCardHandle = new EntryCardHandle(entryCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", entryCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysEntry(expectedEntry, entryCardHandle);
    }
}
