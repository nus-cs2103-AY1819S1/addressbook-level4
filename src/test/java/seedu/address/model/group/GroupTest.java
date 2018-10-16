package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalGroups.GROUP_2101;
import static seedu.address.testutil.TypicalGroups.PROJECT_2103T;
import static seedu.address.testutil.TypicalMeetings.URGENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.testutil.GroupBuilder;


public class GroupTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asUnmodifiableList_modifyList_throwsUnsupportedOperationException() {
        Group group = new GroupBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        group.getMembersView().remove(0);
    }

    @Test
    public void hasMember_personNotInGroup_returnsFalse() {
        Group group = new GroupBuilder().build();
        assertFalse(group.hasMember(ALICE));
    }

    @Test
    public void addMember_personInGroup_returnsTrue() {
        Group group = new GroupBuilder().build();
        group.addMember(ALICE);
        assertTrue(group.hasMember(ALICE));
    }

    @Test
    public void removeMember_personNotInGroup_returnsFalse() {
        Group group = new GroupBuilder().withNewPerson(BENSON).build();
        group.removeMember(BENSON);
        assertFalse(group.hasMember(BENSON));
    }

    @Test
    public void clearMembers_personNotInGroup_returnsFalse() {
        Group group = new GroupBuilder().withNewPerson(BENSON).withNewPerson(CARL).build();
        group.clearMembers();
        assertFalse(group.hasMember(CARL));
    }

    @Test
    public void isSameGroup() {
        // same object -> returns true
        assertTrue(PROJECT_2103T.isSameGroup(PROJECT_2103T));

        // null -> returns false
        assertFalse(PROJECT_2103T.isSameGroup(null));

        // different title -> returns false
        Group editedProject = new GroupBuilder(PROJECT_2103T).withTitle("CS2113").build();
        assertFalse(editedProject.isSameGroup(PROJECT_2103T));

        // different description -> returns true
        editedProject = new GroupBuilder(PROJECT_2103T).withDescription("Discussion group for CS2103T").build();
        assertTrue(editedProject.isSameGroup(PROJECT_2103T));

        // same title, same description, different meeting -> returns true
        editedProject = new GroupBuilder(PROJECT_2103T).withMeeting(URGENT).build();
        assertTrue(editedProject.isSameGroup(PROJECT_2103T));

        // same title, same description, different members -> returns true
        editedProject = new GroupBuilder(PROJECT_2103T).withNewPerson(BOB).build();
        assertTrue(editedProject.isSameGroup(PROJECT_2103T));

        // same members -> returns true
        editedProject = new GroupBuilder(PROJECT_2103T).withNewPerson(BOB).withRemovedPerson(BOB).build();
        assertTrue(editedProject.isSameGroup(PROJECT_2103T));
    }


    @Test
    public void equals() {
        // same values -> returns true
        Group projectCopy = new GroupBuilder(GROUP_2101).build();
        assertTrue(GROUP_2101.equals(projectCopy));

        // same object -> returns true
        assertTrue(GROUP_2101.equals(GROUP_2101));

        // null -> returns false
        assertFalse(GROUP_2101.equals(null));

        // different type -> returns false
        assertFalse(GROUP_2101.equals(2));

        // different groups -> returns false
        assertFalse(GROUP_2101.equals(PROJECT_2103T));

        // different title -> returns false
        Group editedGroup = new GroupBuilder(GROUP_2101).withTitle("CS2102").build();
        assertFalse(editedGroup.equals(GROUP_2101));

        // different description -> returns false
        editedGroup = new GroupBuilder(GROUP_2101).withDescription("Discussion group for CS2103T").build();
        assertFalse(editedGroup.equals(GROUP_2101));

        // same title, same description, different meeting -> returns false
        editedGroup = new GroupBuilder(GROUP_2101).withMeeting(URGENT).build();
        assertFalse(editedGroup.equals(GROUP_2101));

        // same title, same description, different members -> returns false
        editedGroup = new GroupBuilder(GROUP_2101).withNewPerson(BOB).build();
        assertFalse(editedGroup.equals(GROUP_2101));

        // same members -> returns true
        editedGroup = new GroupBuilder(GROUP_2101).withNewPerson(BOB).withRemovedPerson(BOB).build();
        assertTrue(editedGroup.equals(GROUP_2101));
    }
}
