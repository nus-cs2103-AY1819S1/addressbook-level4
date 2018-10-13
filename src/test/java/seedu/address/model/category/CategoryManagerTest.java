package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.NUS_EDUCATION;
import static seedu.address.testutil.TypicalPersons.WORK_FACEBOOK;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.NameContainsKeywordsPredicate;

import seedu.address.model.category.CategoryManager;

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
        List<MajorResumeEntry> list = Arrays.asList([NUS_EDUCATION, WORK_FACEBOOK]);
        List<MajorResumeEntry> list2 = Arrays.asList([NUS_EDUCATION]);

        // same values -> returns true
        categoryManager = new CategoryManager(list);
        ModelManager categoryManagerCopy = new CategoryManager(list);
        assertTrue(categoryManager.equals(categoryManagerCopy));

        // same object -> returns true
        assertTrue(categoryManager.equals(categoryManager));

        // null -> returns false
        assertFalse(categoryManager.equals(null));

        // different types -> returns false
        assertFalse(categoryManager.equals(5));

        // different addressBook -> returns false
        assertFalse(categoryManager.equals(new categoryManager(list2)));

        // different filteredList -> returns false
        categoryManager.setFilter(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
