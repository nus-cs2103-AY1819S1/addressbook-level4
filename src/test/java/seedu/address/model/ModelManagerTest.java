package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.HealthBaseBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInHealthBase_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInHealthBase_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasCheckedOutPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasCheckedOutPerson(null);
    }

    @Test
    public void hasCheckedOutPerson_personNotInHealthBase_returnsFalse() {
        assertFalse(modelManager.hasCheckedOutPerson(ALICE));
    }

    @Test
    public void hasCheckedOutPerson_personInHealthBase_returnsTrue() {
        HealthBase internalHealthBase = new HealthBase();
        internalHealthBase.addCheckedOutPerson(ALICE);
        modelManager.resetData(internalHealthBase);
        assertTrue(modelManager.hasCheckedOutPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void getFilteredCheckedOutPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredCheckedOutPersonList().remove(0);
    }

    @Test
    public void equals() {
        HealthBase healthBase = new HealthBaseBuilder()
            .withPerson(ALICE).withPerson(BENSON)
            .withCheckedOutPerson(CARL).withCheckedOutPerson(DANIEL).build();
        HealthBase differentHealthBase = new HealthBase();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(healthBase, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(healthBase, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different healthBase -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentHealthBase, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(healthBase, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setHealthBaseFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(healthBase, differentUserPrefs)));
    }
}
