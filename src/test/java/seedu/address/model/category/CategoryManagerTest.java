package seedu.address.model.category;

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

import seedu.address.model.entry.MajorResumeEntry;

public class CategoryManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CategoryManager categoryManager = new CategoryManager();

    @Test
    public void getList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        categoryManager.getList().remove(0);
    }

    @Test
    public void equals() {
        MajorResumeEntry[] raw = { NUS_EDUCATION, WORK_FACEBOOK };
        List<MajorResumeEntry> list = Arrays.asList(raw);

        MajorResumeEntry[] raw2 = { NUS_EDUCATION };
        List<MajorResumeEntry> list2 = Arrays.asList(raw2);

        // same values -> returns true
        categoryManager = new CategoryManager(list);
        CategoryManager categoryManagerCopy = new CategoryManager(list);
        assertTrue(categoryManager.equals(categoryManagerCopy));

        // same object -> returns true
        assertTrue(categoryManager.equals(categoryManager));

        // null -> returns false
        assertFalse(categoryManager.equals(null));

        // different types -> returns false
        assertFalse(categoryManager.equals(5));

        // different addressBook -> returns false
        assertFalse(categoryManager.equals(new CategoryManager(list2)));

        // different filteredList -> returns false
        categoryManager.setFilter(new ContainsCategoryPredicate("work"));
        assertFalse(categoryManager.equals(new CategoryManager(list)));

        // resets modelManager to initial state for upcoming tests
        categoryManager.setFilter(PREDICATE_SHOW_ALL_ENTRIES);

        // similarly filtered -> returns true
        assertTrue(categoryManager.equals(new CategoryManager(list)));
    }
}
