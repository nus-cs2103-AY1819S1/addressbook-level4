package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;

import guitests.guihandles.EntryCardHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.entry.ResumeEntry;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(EntryCardHandle expectedCard, EntryCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getCategory(), actualCard.getCategory());
        assertEquals(expectedCard.getTitle(), actualCard.getTitle());
        assertEquals(expectedCard.getSubtitle(), actualCard.getSubtitle());
        assertEquals(expectedCard.getDuration(), actualCard.getDuration());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEntry}.
     */
    public static void assertCardDisplaysEntry(ResumeEntry expectedEntry, EntryCardHandle actualCard) {
        assertEquals(expectedEntry.getCategory().cateName, actualCard.getCategory());
        assertEquals(expectedEntry.getEntryInfo().getTitle(), actualCard.getTitle());
        assertEquals(expectedEntry.getEntryInfo().getSubHeader(), actualCard.getSubtitle());
        assertEquals(expectedEntry.getEntryInfo().getDuration(), actualCard.getDuration());
        assertEquals(expectedEntry.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
