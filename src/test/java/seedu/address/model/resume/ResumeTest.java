package seedu.address.model.resume;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.InvalidTemplateFileException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserParticulars;
import seedu.address.model.category.Category;
import seedu.address.model.entry.EntryInfo;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.template.Template;
import seedu.address.model.template.TemplateSection;
import seedu.address.testutil.TypicalEntrys;
import seedu.address.testutil.TypicalResumeModel;
import seedu.address.testutil.UserParticularsModel;

/**
 * All Resume related model classes (Resume, ResumeHeader, ResumeSection) are tested in this file.
 */
public class ResumeTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void resumeHeaderConstructorTest() {
        ResumeHeader testHeader = new ResumeHeader(new UserParticulars());
        // State test
        assertEquals("John Doe", testHeader.getName());
        assertEquals("+65 91234567", testHeader.getPhone());
        assertEquals("johndoe@example.com", testHeader.getEmail());
        assertEquals("412 Kent Ridge Road, #05-03", testHeader.getAddress());
    }

    @Test
    public void resumeSectionConstructorTest() {
        // Empty entry list
        ResumeSection testSection = new ResumeSection("test title", new ArrayList<>());
        assertEquals("test title", testSection.title);
        assertEquals(0, testSection.getEntryList().size());

        // Contains entry
        List<ResumeEntry> testEntryList = new ArrayList<>();
        ResumeEntry testEntry = new ResumeEntry(new Category("category"),
                new EntryInfo(),
                new HashSet<>());
        testEntryList.add(testEntry);
        assertEquals(testEntry,
                new ResumeSection("test title", testEntryList).getEntryList().get(0));
    }

    @Test
    public void resumeConstructorTest() {
        // no template loaded
        assertThrows(IllegalArgumentException.class, "Template cannot be blank.", () -> new Resume(new ModelManager()));

        Model testModel = TypicalResumeModel.getDefaultTemplateModel();

        // default template, no entries
        Resume blankResume = new Resume(testModel);
        List<ResumeSection> expectedBlankSectionList = new ArrayList<>();
        Template defaultTemplate = Template.getDefaultTemplate();
        for (TemplateSection templateSection : defaultTemplate.getSections()) {
            expectedBlankSectionList.add(new ResumeSection(templateSection.getTitle(), new ArrayList<ResumeEntry>()));
        }
        assertEquals(expectedBlankSectionList, blankResume.getSectionList());

        // default template, typical entries
        List<ResumeEntry> typicalEntries = TypicalEntrys.getTypicalEntries();
        for (ResumeEntry entry : typicalEntries) {
            testModel.addEntry(entry);
        }
        Resume typicalResume = new Resume(testModel);
        // filtering entries manually to get expected section list
        List<ResumeSection> expectedTypicalSectionList = new ArrayList<>();
        for (TemplateSection templateSection : defaultTemplate.getSections()) {
            List<ResumeEntry> testEntryList = new ArrayList<>();
            for (ResumeEntry entry : typicalEntries) {
                if (templateSection.getCategoryPredicate().test(entry)
                        && templateSection.getTagPredicate().test(entry)) {
                    testEntryList.add(entry);
                }
            }
            expectedTypicalSectionList.add(new ResumeSection(templateSection.getTitle(), testEntryList));
        }
        assertEquals(expectedTypicalSectionList, typicalResume.getSectionList());
    }

    @Test
    public void resumeHeaderEqualsTest() {
        ResumeHeader testHeaderOne = new ResumeHeader(
                new UserParticulars("name", "phone", "email", "address"));

        // Same object check
        assertEquals(testHeaderOne, testHeaderOne);

        // Type check
        assertNotEquals(testHeaderOne, new ArrayList<>());

        // Same state
        assertEquals(testHeaderOne, new ResumeHeader(
                new UserParticulars("name", "phone", "email", "address")));

        // Different state
        // One part different
        assertNotEquals(testHeaderOne, new ResumeHeader(
                new UserParticulars("not name", "phone", "email", "address")));
        // All parts different
        assertNotEquals(testHeaderOne, new ResumeHeader(
                new UserParticulars("not name", "not phone", "not email", "not address")));
    }

    @Test
    public void resumeSectionEqualsTest() {
        ResumeSection testSectionOne = new ResumeSection("section 1", new ArrayList<>());

        // Same object check
        assertEquals(testSectionOne, testSectionOne);

        // Type check
        assertNotEquals(testSectionOne, new ArrayList<>());

        // Same state
        assertEquals(testSectionOne, new ResumeSection("section 1", new ArrayList<>()));

        // Different state
        // Different title, same entries (empty)
        ResumeSection testSectionTwo = new ResumeSection("section 2", new ArrayList<>());
        assertNotEquals(testSectionOne, testSectionTwo);
        // Same title, different entries (one entry)
        List<ResumeEntry> testSectionList = new ArrayList<>();
        testSectionList.add(new ResumeEntry(new Category("Test"), new EntryInfo(), new HashSet<>()));
        ResumeSection testSectionThree = new ResumeSection("section 1", testSectionList);
        assertNotEquals(testSectionOne, testSectionThree);
        // Both different
        assertNotEquals(testSectionTwo, testSectionThree);
    }

    @Test
    public void resumeEqualsTest() {
        Resume testResumeOne = new Resume(TypicalResumeModel.getDefaultTemplateModel());

        // Same object check
        assertEquals(testResumeOne, testResumeOne);

        // Type check
        assertNotEquals(testResumeOne, new ArrayList<>());

        // Same state
        // Identical state
        Resume testResumeTwo = new Resume(TypicalResumeModel.getDefaultTemplateModel());
        assertEquals(testResumeOne, testResumeTwo);
        // Different templates but identical sections
        Model testModelOne = TypicalResumeModel.getDefaultTemplateModel();
        Template testTemplate = new Template("testpath");
        try {
            testTemplate.addSection("Work Experience:~work:hard");
            testTemplate.addSection("Education:~education:");
            testTemplate.addSection("Projects:~projects:nus&soc");
        } catch (InvalidTemplateFileException e) {
            //Exception will never occur in this case
        }
        Model testModelTwo = new TypicalResumeModel(testTemplate);
        assertEquals(new Resume(testModelOne), new Resume(testModelTwo));
        // Different model states but identical sections
        testModelTwo.addEntry(TypicalEntrys.WORK_FACEBOOK_WITH_DESC);
        assertEquals(new Resume(testModelOne), new Resume(testModelTwo));


        // Different state
        // Different header
        Model particularsModel = new UserParticularsModel(
                new UserParticulars("name", "phone", "email", "address")
        );
        assertNotEquals(new Resume(particularsModel),
                new Resume(TypicalResumeModel.getDefaultTemplateModel()));
        // One different section (other empty)
        Model typicalModel = TypicalResumeModel.getDefaultTemplateModel();
        typicalModel.addEntry(TypicalEntrys.NUS_EDUCATION);
        assertNotEquals(new Resume(TypicalResumeModel.getDefaultTemplateModel()), new Resume(typicalModel));
        // Different header and different section
        assertNotEquals(new Resume(particularsModel), new Resume(typicalModel));
    }
}
