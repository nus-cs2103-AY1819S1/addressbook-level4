package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalGroups.GROUP_2101;
import static seedu.address.testutil.TypicalMeetings.URGENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.GroupHasNoMeetingException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.util.PersonNameContainsKeywordsPredicate;
import seedu.address.model.shared.Title;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.GroupBuilder;
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
    public void addPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void deletePerson_personNotInAddressBook_returnsFalse() {
        Person person = new PersonBuilder().withName("Derek").build();
        modelManager.addPerson(person);
        modelManager.deletePerson(person);
        assertFalse(modelManager.hasPerson(person));
    }

    @Test
    public void updatePerson_personInAddressBook_returnsTrue() {
        Person derek = new PersonBuilder().withName("Derek").build();
        modelManager.addPerson(ALICE);
        modelManager.updatePerson(ALICE, derek);
        assertTrue(modelManager.hasPerson(derek));
    }

    @Test
    public void updatePerson_personNotInAddressBook_returnsFalse() {
        Person derek = new PersonBuilder().withName("Derek").build();
        modelManager.addPerson(ALICE);
        modelManager.updatePerson(ALICE, derek);
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasGroup_groupNotInAddressBook_returnsFalse() {
        Group group = new GroupBuilder().withTitle("Not here").build();
        assertFalse(modelManager.hasGroup(group));
    }

    @Test
    public void addGroup_groupInAddressBook_returnsTrue() {
        Group group = new GroupBuilder().withTitle("class").build();
        modelManager.addGroup(group);
        assertTrue(modelManager.hasGroup(group));
    }

    @Test
    public void deleteGroup_groupNotInAddressBook_returnsFalse() {
        Group group = new GroupBuilder().withTitle("class").build();
        modelManager.addGroup(group);
        modelManager.removeGroup(group);
        assertFalse(modelManager.hasGroup(group));
    }

    @Test
    public void updateGroup_groupInAddressBook_returnsTrue() {
        Group randomChat = new GroupBuilder().withTitle("chat").build();
        Group formalDiscussion = new GroupBuilder().withTitle("discuss").build();
        modelManager.addGroup(randomChat);
        modelManager.updateGroup(randomChat, formalDiscussion);
        assertTrue(modelManager.hasGroup(formalDiscussion));
    }

    @Test
    public void updateGroup_groupNotInAddressBook_returnsFalse() {
        Group randomChat = new GroupBuilder().withTitle("chat").build();
        Group formalDiscussion = new GroupBuilder().withTitle("discuss").build();
        modelManager.addGroup(randomChat);
        modelManager.updateGroup(randomChat, formalDiscussion);
        assertFalse(modelManager.hasGroup(randomChat));
    }

    @Test
    public void getPersonByName_equals_returnsTrue() {
        Person person = new PersonBuilder().withName("Pakorn").build();
        Name name = new Name("Pakorn");

        modelManager.addPerson(person);
        Person match = modelManager.getPersonByName(name);

        assertTrue(match.equals(person));
    }

    @Test
    public void getGroupByTitle_equals_returnsTrue() {
        Group group = new GroupBuilder().withTitle("tutorial").build();
        Title title = new Title("tutorial");

        modelManager.addGroup(group);
        Group match = modelManager.getGroupByTitle(title);

        assertTrue(match.equals(group));
    }

    @Test
    public void joinGroup_personInGroup_returnsTrue() {
        Group puzzle = new GroupBuilder().withTitle("puzzle").build();
        Person sherlock = new PersonBuilder().withName("Sherlock").build();
        modelManager.addGroup(puzzle);
        modelManager.addPerson(sherlock);
        modelManager.joinGroup(sherlock, puzzle);
        assertTrue(sherlock.hasGroup(puzzle));
    }

    @Test
    public void leaveGroup_personNotInGroup_returnsFalse() {
        Group game = new GroupBuilder().withTitle("game").build();
        Person watson = new PersonBuilder().withName("Watson").build();
        modelManager.addGroup(game);
        modelManager.addPerson(watson);
        modelManager.joinGroup(watson, game);
        modelManager.leaveGroup(watson, game);
        assertFalse(watson.hasGroup(game));
    }

    // @@author NyxF4ll
    @Test
    public void setMeeting_groupExists_success() {
        modelManager.addGroup(GROUP_2101.copy());
        modelManager.setMeeting(GROUP_2101, URGENT);
        assertEquals(modelManager.getGroupByTitle(GROUP_2101.getTitle()).getMeeting(), URGENT);
    }

    @Test
    public void setMeeting_groupDoesNotExists_throwsGroupNotFoundException() {
        thrown.expect(GroupNotFoundException.class);
        modelManager.setMeeting(GROUP_2101, URGENT);
    }

    @Test
    public void cancelMeeting_hasMeeting_success() {
        Group editedGroup = GROUP_2101.copy();
        editedGroup.setMeeting(URGENT);

        modelManager.addGroup(editedGroup);
        modelManager.cancelMeeting(editedGroup);

        assertFalse(editedGroup.hasMeeting());
    }

    @Test
    public void cancelMeeting_groupDoesNotExists_throwsGroupNotFoundException() {
        thrown.expect(GroupNotFoundException.class);
        modelManager.cancelMeeting(GROUP_2101);
    }

    @Test
    public void cancelMeeting_groupHasNoMeeting_throwsGroupHasNoMeetingException() {
        thrown.expect(GroupHasNoMeetingException.class);

        Group groupWithoutMeeting = new Group(GROUP_2101.getTitle());

        modelManager.addGroup(groupWithoutMeeting);
        modelManager.cancelMeeting(groupWithoutMeeting);
    }
    // @@author

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
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

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Arrays.asList(keywords), Collections.emptyList()));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
