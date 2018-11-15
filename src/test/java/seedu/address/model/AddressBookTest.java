package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalGroups.FAMILY;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.exceptions.DuplicateElementException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getGroupList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicateElementException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Group> newGroups = Arrays.asList(FAMILY);
        AddressBookStub newData = new AddressBookStub(newPersons, newGroups);

        thrown.expect(DuplicateElementException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void has_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.has(null);
    }

    @Test
    public void has_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.has(ALICE));
    }

    @Test
    public void has_groupNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.has(FAMILY));
    }

    @Test
    public void has_personInAddressBook_returnsTrue() {
        addressBook.add(ALICE);
        assertTrue(addressBook.has(ALICE));
    }

    @Test
    public void has_groupInAddressBook_returnsTrue() {
        addressBook.add(FAMILY);
        assertTrue(addressBook.has(FAMILY));
    }

    @Test
    public void has_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.has(editedAlice));
    }

    @Test
    public void has_groupWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.add(FAMILY);
        Group editedFamily = new GroupBuilder(FAMILY).withDescription("This description is changed.")
                .build();
        assertTrue(addressBook.has(editedFamily));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getGroupList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getGroupList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and groups list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Group> groups = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Group> groups) {
            this.persons.setAll(persons);
            this.groups.setAll(groups);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return groups;
        }
    }

}
