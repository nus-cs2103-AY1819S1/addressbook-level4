package seedu.address.storage.util;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.util.MarkdownConverter.toMarkdown;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Model;
import seedu.address.model.UserParticulars;
import seedu.address.model.category.Category;
import seedu.address.model.entry.EntryDescription;
import seedu.address.model.entry.EntryInfo;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.resume.Resume;
import seedu.address.model.resume.ResumeHeader;
import seedu.address.model.resume.ResumeSection;
import seedu.address.model.template.Template;
import seedu.address.model.template.TemplateSection;
import seedu.address.testutil.TypicalEntrys;
import seedu.address.testutil.TypicalResumeModel;

public class MarkdownConverterTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private EntryInfo testInfo = new EntryInfo("entry title", "entry subheader", "entry duration");

    @Test
    public void entryInfoToMarkdown() {
        // All elements present
        assertEquals("##### entry title (_entry duration_)" + System.lineSeparator()
                        + System.lineSeparator()
                        + "entry subheader" + System.lineSeparator()
                        + System.lineSeparator(),
                toMarkdown(testInfo));
    }

    @Test
    public void entryDescriptionToMarkdown() {
        EntryDescription testDescription = new EntryDescription();
        // Empty list
        assertEquals("",
                toMarkdown(testDescription));

        // One element
        testDescription.addBullet("first bullet");
        assertEquals("- first bullet",
                toMarkdown(testDescription));

        // More elements
        testDescription.addBullet("second bullet");
        testDescription.addBullet("third bullet");
        assertEquals("- first bullet" + System.lineSeparator()
                        + "- second bullet" + System.lineSeparator()
                        + "- third bullet",
                toMarkdown(testDescription));
    }

    @Test
    public void entryToMarkdown() {
        // Has Info, no Description (non-minor)
        ResumeEntry testEntry = new ResumeEntry(new Category("testCategory"), testInfo, new HashSet<>());
        assertEquals(toMarkdown(testInfo)
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator(),
                toMarkdown(testEntry));

        // Has Info, has Description (non-minor)
        testEntry.getDescription().addBullet("first bullet");
        testEntry.getDescription().addBullet("second bullet");
        assertEquals(toMarkdown(testInfo)
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + toMarkdown(testEntry.getDescription())
                        + System.lineSeparator()
                        + System.lineSeparator(),
                toMarkdown(testEntry));

        // No Info, no Description (minor entry)
        ResumeEntry minorEntry = new ResumeEntry(new Category("testCategory"), new EntryInfo(), new HashSet<>());
        assertEquals(System.lineSeparator()
                        + System.lineSeparator(),
                toMarkdown(minorEntry));

        // Minor entry - no description
        minorEntry.getDescription().addBullet("minor bullet");
        assertEquals(toMarkdown(minorEntry.getDescription())
                        + System.lineSeparator()
                        + System.lineSeparator(),
                toMarkdown(minorEntry));
    }

    @Test
    public void resumeSectionToMarkdown() {
        // No entries
        assertEquals("test section" + System.lineSeparator()
                        + "------------" + System.lineSeparator()
                        + System.lineSeparator(),
                MarkdownConverter.toMarkdown(new ResumeSection("test section", new ArrayList<>())));

        // Some entries with no description
        List<ResumeEntry> testEntryList = new ArrayList<>();
        ResumeEntry testEntryOne = new ResumeEntry(new Category("testCategory"), testInfo, new HashSet<>());
        EntryInfo testInfoTwo = new EntryInfo("second title", "second subheader", "second duration");
        ResumeEntry testEntryTwo = new ResumeEntry(new Category("testCategory"), testInfoTwo, new HashSet<>());
        testEntryList.add(testEntryOne);
        testEntryList.add(testEntryTwo);
        ResumeSection testSection = new ResumeSection("test section", testEntryList);
        assertEquals("test section" + System.lineSeparator()
                        + "------------" + System.lineSeparator()
                        + System.lineSeparator()
                        + toMarkdown(testEntryOne)
                        + toMarkdown(testEntryTwo),
                toMarkdown(testSection));

        // Some entries with description
        testEntryOne.getDescription().addBullet("first bullet");
        testEntryTwo.getDescription().addBullet("second bullet");
        testEntryTwo.getDescription().addBullet("third bullet");
        assertEquals("test section" + System.lineSeparator()
                        + "------------" + System.lineSeparator()
                        + System.lineSeparator()
                        + toMarkdown(testEntryOne)
                        + toMarkdown(testEntryTwo),
                toMarkdown(testSection));
    }

    @Test
    public void resumeSectionListToMarkdown() {
        // No sections
        assertEquals(System.lineSeparator()
                        + "---" + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator(),
                toMarkdown(new ArrayList<>()));

        // Some empty sections
        ResumeSection testSectionOne = new ResumeSection("first section", new ArrayList<>());
        ResumeSection testSectionTwo = new ResumeSection("second section", new ArrayList<>());
        List<ResumeSection> testSectionList = new ArrayList<>();
        testSectionList.add(testSectionOne);
        testSectionList.add(testSectionTwo);
        assertEquals(System.lineSeparator()
                        + "---" + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + toMarkdown(testSectionOne)
                        + "---" + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + toMarkdown(testSectionTwo)
                        + "---" + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator(),
                toMarkdown(testSectionList));

        // Mixed empty sections and sections with assorted content
        // Section with empty entry
        ResumeEntry testEntryEmpty = new ResumeEntry(new Category("testCategory"), testInfo, new HashSet<>());
        List<ResumeEntry> testEntryListEmptyEntry = new ArrayList<>();
        testEntryListEmptyEntry.add(testEntryEmpty);
        ResumeSection testSectionEmptyEntry = new ResumeSection("empty entry", testEntryListEmptyEntry);

        // Section with empty entry, entry with single description, entry with multiple descriptions
        ResumeEntry testEntryOne = new ResumeEntry(new Category("testCategory"), testInfo, new HashSet<>());
        testEntryOne.getDescription().addBullet("first bullet");
        ResumeEntry testEntryTwo = new ResumeEntry(new Category("testCategory"), testInfo, new HashSet<>());
        testEntryTwo.getDescription().addBullet("second bullet");
        testEntryTwo.getDescription().addBullet("third bullet");
        List<ResumeEntry> testEntryListMixedEntries = new ArrayList<>();
        testEntryListMixedEntries.add(testEntryEmpty);
        testEntryListMixedEntries.add(testEntryOne);
        testEntryListMixedEntries.add(testEntryTwo);
        ResumeSection testSectionMixedEntries = new ResumeSection("mixed entries", testEntryListMixedEntries);

        // Combine into section list
        testSectionList.add(testSectionEmptyEntry);
        testSectionList.add(testSectionMixedEntries);

        assertEquals(System.lineSeparator()
                        + "---" + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + toMarkdown(testSectionOne)
                        + "---" + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + toMarkdown(testSectionTwo)
                        + "---" + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + toMarkdown(testSectionEmptyEntry)
                        + "---" + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + toMarkdown(testSectionMixedEntries)
                        + "---" + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator(),
                toMarkdown(testSectionList));
    }

    @Test
    public void resumeHeaderToMarkdown() {
        // Default header
        ResumeHeader defaultHeader = new ResumeHeader(new UserParticulars());
        assertEquals("John Doe" + System.lineSeparator()
                        + "--------" + System.lineSeparator()
                        + System.lineSeparator()
                        + "**+65 91234567**" + System.lineSeparator()
                        + System.lineSeparator()
                        + "[johndoe@example.com](mailto:johndoe@example.com)" + System.lineSeparator()
                        + System.lineSeparator()
                        + "412 Kent Ridge Road, #05-03",
                toMarkdown(defaultHeader));

        // Custom header
        ResumeHeader fullHeader = new ResumeHeader(new UserParticulars("testName",
                "testPhone",
                "testEmail",
                "testAddress"));
        assertEquals("testName" + System.lineSeparator()
                        + "--------" + System.lineSeparator()
                        + System.lineSeparator()
                        + "**testPhone**" + System.lineSeparator()
                        + System.lineSeparator()
                        + "[testEmail](mailto:testEmail)" + System.lineSeparator()
                        + System.lineSeparator()
                        + "testAddress",
                toMarkdown(fullHeader));
    }

    @Test
    public void resumeToMarkdown() {
        // No entries, default header
        List<ResumeSection> testSectionList = new ArrayList<>();
        for (TemplateSection templateSection : Template.getDefaultTemplate().getSections()) {
            testSectionList.add(new ResumeSection(templateSection.getTitle(), new ArrayList<ResumeEntry>()));
        }
        ResumeHeader defaultHeader = new ResumeHeader(new UserParticulars());
        assertEquals(toMarkdown(defaultHeader) + System.lineSeparator()
                        + System.lineSeparator()
                        + toMarkdown(testSectionList),
                toMarkdown(new Resume(TypicalResumeModel.getDefaultTemplateModel())));

        // Default resume with entries, no header
        Model testModel = TypicalResumeModel.getDefaultTemplateModel();
        List<ResumeEntry> typicalEntries = TypicalEntrys.getTypicalEntries();
        for (ResumeEntry entry : typicalEntries) {
            testModel.addEntry(entry);
        }
        Resume typicalResume = new Resume(testModel);
        assertEquals(toMarkdown(typicalResume.getHeader())
                        + System.lineSeparator() + System.lineSeparator()
                        + toMarkdown(typicalResume.getSectionList()),
                toMarkdown(typicalResume));
    }
}
