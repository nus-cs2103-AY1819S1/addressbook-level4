package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LEAVEAPPLICATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalAssignment.FALCON;
import static seedu.address.testutil.TypicalAssignment.OASIS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalUsernameException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ArchiveListBuilder;
import seedu.address.testutil.AssignmentListBuilder;
import seedu.address.testutil.PersonBuilder;

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
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void addPerson_usernameInAddressBook_throwsExecption() {
        modelManager.addPerson(ALICE);
        Person nextPerson = new PersonBuilder().withUsername(ALICE.getUsername().username).build();
        try {
            modelManager.addPerson(nextPerson);
            //should throw exception
            assert false;
        } catch (IllegalUsernameException iue) {
            assert iue.getUsername().equals(ALICE.getUsername().username);
        }
        assert modelManager.getAddressBook().getPersonList().size() == 1;
    }

    @Test
    public void updatePerson_usernameInAddressBook_throwsExecption() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BOB);
        Person nextPerson = new PersonBuilder(BOB).withUsername(ALICE.getUsername().username).build();
        try {
            modelManager.updatePerson(BOB, nextPerson);
            //should throw exception
            assert false;
        } catch (IllegalUsernameException iue) {
            assert iue.getUsername().equals(ALICE.getUsername().username);
        }
        assert modelManager.hasPerson(BOB);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void getFilteredLeaveApplicationList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredLeaveApplicationList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        ArchiveList archiveList = new ArchiveListBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AssignmentList assignmentList =
                new AssignmentListBuilder().withAssignment(OASIS).withAssignment(FALCON).build();
        AddressBook differentAddressBook = new AddressBook();

        ArchiveList differentArchiveList = new ArchiveList();
        AssignmentList differentAssignmentList = new AssignmentList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, assignmentList, archiveList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, assignmentList, archiveList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, differentAssignmentList,
                differentArchiveList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, assignmentList,
                archiveList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.setLoggedInUser(new User(ALICE));
        modelManager.updateFilteredLeaveApplicationListForPerson(ALICE);
        modelManager.updateFilteredLeaveApplicationList(PREDICATE_SHOW_ALL_LEAVEAPPLICATIONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, assignmentList,
                archiveList, differentUserPrefs)));
    }
}
