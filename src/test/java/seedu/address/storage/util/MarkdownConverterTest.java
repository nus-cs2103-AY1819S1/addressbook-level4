package seedu.address.storage.util;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.category.Category;
import seedu.address.model.entry.EntryDescription;
import seedu.address.model.entry.EntryInfo;
import seedu.address.model.entry.ResumeEntry;

public class MarkdownConverterTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private EntryInfo testInfo = new EntryInfo("entry title", "entry subheader", "entry duration");

    @Test
    public void entryInfoToMarkdown() {
        // All elements present
        assertEquals(MarkdownConverter.toMarkdown(testInfo),
                "###### entry title" + System.lineSeparator()
                        + "entry subheader" + System.lineSeparator() + System.lineSeparator()
                        + "entry duration");
    }

    @Test
    public void entryDescriptionToMarkdown() {
        EntryDescription testDescription = new EntryDescription();
        // Empty list
        assertEquals(MarkdownConverter.toMarkdown(testDescription),
                "");

        // One element
        testDescription.addBullet("first bullet");
        assertEquals(MarkdownConverter.toMarkdown(testDescription),
                "- first bullet");

        // More elements
        testDescription.addBullet("second bullet");
        testDescription.addBullet("third bullet");
        assertEquals(MarkdownConverter.toMarkdown(testDescription),
                "- first bullet" + System.lineSeparator()
                        + "- second bullet" + System.lineSeparator()
                        + "- third bullet");
    }

    @Test
    public void entryToMarkdown() {
        // No description
        ResumeEntry testEntry = new ResumeEntry(new Category("testCategory"), testInfo, new HashSet<>());
        assertEquals(MarkdownConverter.toMarkdown(testEntry),
                "###### entry title" + System.lineSeparator()
                        + "entry subheader" + System.lineSeparator() + System.lineSeparator()
                        + "entry duration" + System.lineSeparator() + System.lineSeparator()
                        + System.lineSeparator() + System.lineSeparator());

        // Add description
        testEntry.getDescription().addBullet("first bullet");
        testEntry.getDescription().addBullet("second bullet");
        assertEquals(MarkdownConverter.toMarkdown(testEntry),
                "###### entry title" + System.lineSeparator()
                        + "entry subheader" + System.lineSeparator() + System.lineSeparator()
                        + "entry duration" + System.lineSeparator() + System.lineSeparator()
                        + "- first bullet" + System.lineSeparator()
                        + "- second bullet" + System.lineSeparator() + System.lineSeparator());
    }
}
