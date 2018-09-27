package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.expense.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ModelUtil;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = (ModelManager) ModelUtil.modelWithTestUser();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        try {
            modelManager.hasPerson(null);
        } catch (NoUserSelectedException e) {
            Assert.fail("Model has no user selected.");
        }
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        try {
            assertFalse(modelManager.hasPerson(ALICE));
        } catch (NoUserSelectedException e) {
            Assert.fail("Model has no user selected.");
        }
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        try {
            modelManager.addPerson(ALICE);
            assertTrue(modelManager.hasPerson(ALICE));
        } catch (NoUserSelectedException e) {
            Assert.fail("Model has no user selected.");
        }
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        try {
            thrown.expect(UnsupportedOperationException.class);
            modelManager.getFilteredPersonList().remove(0);
        } catch (NoUserSelectedException e) {
            Assert.fail("Model has no user selected.");
        }
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook(ModelUtil.TEST_USERNAME);
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        try {
            // different filteredList -> returns false
            String[] keywords = ALICE.getName().expenseName.split("\\s+");
            modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
            assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

            // resets modelManager to initial state for upcoming tests
            modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (NoUserSelectedException e) {
            Assert.fail("Model has no user selected.");
        }

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookDirPath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
