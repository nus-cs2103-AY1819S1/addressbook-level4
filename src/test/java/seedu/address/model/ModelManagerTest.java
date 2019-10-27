package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;
import static seedu.address.testutil.TypicalEntrys.NUS_EDUCATION;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.awareness.Awareness;
import seedu.address.model.resume.Resume;
import seedu.address.testutil.EntryBookBuilder;
import seedu.address.testutil.TypicalResumeModel;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasEntry_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasEntry(null);
    }

    @Test
    public void hasEntry_entryInEntryBook_returnsTrue() {
        modelManager.addEntry(NUS_EDUCATION);
        assertTrue(modelManager.hasEntry(NUS_EDUCATION));
    }

    @Test
    public void getFilteredEntryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredEntryList().remove(0);
    }

    @Test
    public void getFilteredEntryList_filtered_predicateWorks() {
        modelManager = new ModelManager();

        modelManager.addEntry(NUS_EDUCATION);
        modelManager.addEntry(WORK_FACEBOOK);

        // mkPredicate works for single category and tag
        assertTrue(modelManager.getFilteredEntryList("work", Arrays.asList("java")).size() == 1);

        // mkPredicate works for single category and tag, cannot find
        assertTrue(modelManager.getFilteredEntryList("education", Arrays.asList("java")).size() == 0);
    }

    @Test
    public void mkPredicate_filtered_predicateWorks() {
        modelManager = new ModelManager();

        modelManager.addEntry(NUS_EDUCATION);
        modelManager.addEntry(WORK_FACEBOOK);

        // mkPredicate works for single category and tag
        modelManager.updateFilteredEntryList(modelManager.mkPredicate("work", Arrays.asList("java")));
        assertTrue(modelManager.getFilteredEntryList().size() == 1);

        // mkPredicate works for single category and tag, cannot find
        modelManager.updateFilteredEntryList(modelManager.mkPredicate("work", Arrays.asList("nus")));
        assertTrue(modelManager.getFilteredEntryList().size() == 0);

        // mkPredicate works for tag only
        modelManager.updateFilteredEntryList(modelManager.mkPredicate("", Arrays.asList("machinelearning")));
        assertTrue(modelManager.getFilteredEntryList().size() == 1);

        // mkPredicate works for category only
        modelManager.updateFilteredEntryList(modelManager.mkPredicate("work", new ArrayList<String>()));
        assertTrue(modelManager.getFilteredEntryList().size() == 1);
    }

    @Test
    public void getAndGenerateResume() {
        Model testResumeModel = TypicalResumeModel.getDefaultTemplateModel();
        assertFalse(testResumeModel.getLastResume().isPresent());
        testResumeModel.generateResume();
        Resume actual = testResumeModel.getLastResume().orElseThrow(AssertionError::new);
        assertEquals(actual, new Resume(testResumeModel));
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();
        Awareness awareness = new Awareness();
        EntryBook entryBook = new EntryBookBuilder().withEntry(WORK_FACEBOOK).withEntry(NUS_EDUCATION).build();
        EntryBook differentEntryBook = new EntryBook();

        // same values -> returns true
        modelManager = new ModelManager(entryBook, userPrefs, awareness);
        ModelManager modelManagerCopy = new ModelManager(entryBook, userPrefs, awareness);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(
                        differentEntryBook, userPrefs, awareness)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setEntryBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(entryBook, differentUserPrefs, awareness)));
    }
}
