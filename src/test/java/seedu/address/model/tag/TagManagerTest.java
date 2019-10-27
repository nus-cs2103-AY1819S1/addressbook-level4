package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;
import static seedu.address.testutil.TypicalEntrys.NUS_EDUCATION;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.entry.ResumeEntry;

public class TagManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TagManager tagManager = new TagManager();

    @Test
    public void getList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        tagManager.getList().remove(0);
    }

    @Test
    public void mkPredicate_working() {
        ResumeEntry[] entries = { NUS_EDUCATION, WORK_FACEBOOK };
        tagManager.setList(Arrays.asList(entries));

        // single tag
        tagManager.setPredicate(tagManager.mkPredicate("java"));
        assertTrue(tagManager.getList().size() == 1);

        // multiple tag
        String[] tags = {"java", "machinelearning"};
        List<String> tagList = Arrays.asList(tags);

        tagManager.setPredicate(tagManager.mkPredicate(tagList));
        assertTrue(tagManager.getList().size() == 2);

        // add on to multiple tags
        tagManager.setPredicate(tagManager.mkPredicate(PREDICATE_SHOW_ALL_ENTRIES, tagList));
        assertTrue(tagManager.getList().size() == 2);

        // add on to existing predicate
        tagManager.setPredicate(tagManager.mkPredicate((ResumeEntry entry) -> false, "java"));
        assertTrue(tagManager.getList().size() == 0);
    }

    @Test
    public void equals() {
        ResumeEntry[] originalEntries = { NUS_EDUCATION, WORK_FACEBOOK };
        List<ResumeEntry> originalList = Arrays.asList(originalEntries);

        ResumeEntry[] alternativeEntries = { NUS_EDUCATION };
        List<ResumeEntry> alternativeList = Arrays.asList(alternativeEntries);

        // same values -> returns true
        tagManager = new TagManager(originalList);
        TagManager tagManagerCopy = new TagManager(originalList);
        assertTrue(tagManager.equals(tagManagerCopy));

        // set list, returns list is equals
        tagManager.setList(originalList);
        assertTrue(tagManager.getList().equals(originalList));

        // same object -> returns true
        assertTrue(tagManager.equals(tagManager));

        // null -> returns false
        assertFalse(tagManager.equals(null));

        // different types -> returns false
        assertFalse(tagManager.equals(5));

        // different list -> returns false
        assertFalse(tagManager.equals(new TagManager(alternativeList)));

        // filteredList actually works
        tagManager.setPredicate(new ContainsTagsPredicate("java"));
        assertTrue(tagManager.getList().size() == 1);

        // different filteredList -> returns false
        assertFalse(tagManager.equals(new TagManager(originalList)));

        // resets modelManager to initial state for upcoming tests
        tagManager.setPredicate(PREDICATE_SHOW_ALL_ENTRIES);

        // similarly filtered -> returns true
        assertTrue(tagManager.equals(new TagManager(originalList)));
    }
}
