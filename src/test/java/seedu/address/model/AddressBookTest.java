package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalGroups.GROUP_2101;
import static seedu.address.testutil.TypicalGroups.PROJECT_2103T;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.shared.Title;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
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
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        // HACK
        // TODO: change to correctly take in groups
        AddressBookStub newData = new AddressBookStub(newPersons);

        thrown.expect(DuplicatePersonException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasGroup_groupNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void addPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
        addressBook.removePerson(ALICE); //reset
    }

    @Test
    public void addGroup_groupInAddressBook_returnsTrue() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(ALICE);
        addressBook.addGroup(PROJECT_2103T);
        assertTrue(addressBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void removePerson_personIsRemoved_returnsFalse() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(BOB);
        addressBook.removePerson(BOB);
        assertFalse(addressBook.hasPerson(BOB));
    }

    @Test
    public void removeGroup_groupIsRemoved_returnsFalse() {
        AddressBook addressBook = new AddressBook();
        addressBook.addGroup(PROJECT_2103T);
        addressBook.removeGroup(PROJECT_2103T);
        assertFalse(addressBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void updatePerson_personIsUpdated_returnsTrue() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(ALICE);
        addressBook.updatePerson(ALICE, BOB);
        assertTrue(addressBook.hasPerson(BOB));
    }

    @Test
    public void updatePerson_personIsUpdated_returnsFalse() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(ALICE);
        addressBook.updatePerson(ALICE, BOB);
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void updateGroup_groupIsUpdated_returnsTrue() {
        AddressBook addressBook = new AddressBook();
        addressBook.addGroup(GROUP_2101);
        addressBook.updateGroup(GROUP_2101, PROJECT_2103T);
        assertTrue(addressBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void updateGroup_groupIsUpdated_returnsFalse() {
        AddressBook addressBook = new AddressBook();
        addressBook.addGroup(GROUP_2101);
        addressBook.updateGroup(GROUP_2101, PROJECT_2103T);
        assertFalse(addressBook.hasGroup(GROUP_2101));
    }

    @Test
    public void joinGroup_personNotExist_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        AddressBook addressBook = new AddressBook();
        Person outsider = new PersonBuilder().withName("outsider").build();
        Group group = new GroupBuilder().withTitle("class").build();
        addressBook.addGroup(group);
        addressBook.joinGroup(outsider, group);
    }

    @Test
    public void joinGroup_groupNotExist_throwsGroupNotFoundException() {
        thrown.expect(GroupNotFoundException.class);
        AddressBook addressBook = new AddressBook();
        Person person = new PersonBuilder().withName("Derek").build();
        Group other = new GroupBuilder().withTitle("outlier").build();
        addressBook.addPerson(person);
        addressBook.joinGroup(person, other);

    }

    @Test
    public void joinGroup_personInGroup_returnsTrue() {
        AddressBook addressBook = new AddressBook();
        Person person = new PersonBuilder().withName("Derek").build();
        Group group = new GroupBuilder().withTitle("class").build();
        addressBook.addPerson(person);
        addressBook.addGroup(group);
        addressBook.joinGroup(person, group);
        assertTrue(person.hasGroup(group));
    }

    @Test
    public void joinGroup_groupHasPerson_returnsTrue() {
        AddressBook addressBook = new AddressBook();
        Person person = new PersonBuilder().withName("Derek").build();
        Group group = new GroupBuilder().withTitle("class").build();
        addressBook.addPerson(person);
        addressBook.addGroup(group);
        addressBook.joinGroup(person, group);
        assertTrue(group.hasMember(person));
    }

    @Test
    public void leaveGroup_personNotExist_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        AddressBook addressBook = new AddressBook();
        Person outsider = new PersonBuilder().withName("outsider").build();
        Group group = new GroupBuilder().withTitle("class").build();
        addressBook.addGroup(group);
        addressBook.leaveGroup(outsider, group);
    }


    @Test
    public void leaveGroup_personNotInGroup_returnsFalse() {
        AddressBook addressBook = new AddressBook();
        Person person = new PersonBuilder().withName("Derek").build();
        Group group = new GroupBuilder().withTitle("class").build();
        addressBook.addPerson(person);
        addressBook.addGroup(group);
        addressBook.joinGroup(person, group);
        addressBook.leaveGroup(person, group);
        assertFalse(person.hasGroup(group));
    }

    @Test
    public void leaveGroup_groupHasNoPerson_returnsFalse() {
        AddressBook addressBook = new AddressBook();
        Person person = new PersonBuilder().withName("Derek").build();
        Group group = new GroupBuilder().withTitle("class").build();
        addressBook.addPerson(person);
        addressBook.addGroup(group);
        addressBook.joinGroup(person, group);
        addressBook.leaveGroup(person, group);
        assertFalse(group.hasMember(person));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonByName_equals_returnsTrue() {
        Person person = new PersonBuilder().withName("Pakorn").build();
        Name name = new Name("Pakorn");

        addressBook.addPerson(person);
        Person match = addressBook.getPersonByName(name);

        assertTrue(match.equals(person));
    }

    @Test
    public void getGroupByTitle_equals_returnsTrue() {
        Group group = new GroupBuilder().withTitle("tutorial").build();
        Title title = new Title("tutorial");

        addressBook.addGroup(group);
        Group match = addressBook.getGroupByTitle(title);

        assertTrue(match.equals(group));
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
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Group> groups = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return groups;
        }

        @Override
        public ObservableList<Meeting> getMeetingList() {
            List<Meeting> meetingList = groups.stream().map(Group::getMeeting).collect(Collectors.toList());
            return FXCollections.observableArrayList(meetingList);
        }
    }

}
