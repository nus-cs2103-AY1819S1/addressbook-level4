package seedu.meeting.model.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.testutil.TypicalGroups.GROUP_2101;
import static seedu.meeting.testutil.TypicalGroups.PROJECT_2103T;
import static seedu.meeting.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.meeting.model.group.exceptions.DuplicateGroupException;
import seedu.meeting.model.group.exceptions.GroupNotFoundException;
import seedu.meeting.model.shared.Title;
import seedu.meeting.testutil.GroupBuilder;


/**
 * {@author Derek-Hardy}
 */
public class UniqueGroupListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueGroupList uniqueGroupList = new UniqueGroupList();

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.contains(null);
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(uniqueGroupList.contains(PROJECT_2103T));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        uniqueGroupList.add(PROJECT_2103T);
        assertTrue(uniqueGroupList.contains(PROJECT_2103T));
    }

    @Test
    public void contains_groupWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGroupList.add(PROJECT_2103T);
        Group editedProject = new GroupBuilder(PROJECT_2103T).withNewPerson(BOB)
                .build();
        assertTrue(uniqueGroupList.contains(editedProject));
    }

    @Test
    public void clear_groupNotInList_returnsFalse() {
        uniqueGroupList.add(PROJECT_2103T);
        uniqueGroupList.clear();
        assertFalse(uniqueGroupList.contains(PROJECT_2103T));
    }

    @Test
    public void clear_groupInList_returnsTrue() {
        uniqueGroupList.add(GROUP_2101);
        uniqueGroupList.clear();
        uniqueGroupList.add(PROJECT_2103T);
        assertTrue(uniqueGroupList.contains(PROJECT_2103T));
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.add(null);
    }

    @Test
    public void add_duplicateGroup_throwsDuplicateGroupException() {
        uniqueGroupList.add(PROJECT_2103T);
        thrown.expect(DuplicateGroupException.class);
        uniqueGroupList.add(PROJECT_2103T);
    }

    @Test
    public void setGroup_nullTargetGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroup(null, PROJECT_2103T);
    }

    @Test
    public void setGroup_nullEditedGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroup(PROJECT_2103T, null);
    }

    @Test
    public void setGroup_targetGroupNotInList_throwsGroupNotFoundException() {
        thrown.expect(GroupNotFoundException.class);
        uniqueGroupList.setGroup(PROJECT_2103T, PROJECT_2103T);
    }

    @Test
    public void setGroup_editedGroupIsSameGroup_success() {
        uniqueGroupList.add(PROJECT_2103T);
        uniqueGroupList.setGroup(PROJECT_2103T, PROJECT_2103T);
        UniqueGroupList expectedUniquePersonList = new UniqueGroupList();
        expectedUniquePersonList.add(PROJECT_2103T);
        assertEquals(expectedUniquePersonList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasSameIdentity_success() {
        uniqueGroupList.add(PROJECT_2103T);
        Group editedProject = new GroupBuilder(PROJECT_2103T).withNewPerson(BOB)
                .build();
        uniqueGroupList.setGroup(PROJECT_2103T, editedProject);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(editedProject);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasDifferentIdentity_success() {
        uniqueGroupList.add(PROJECT_2103T);
        uniqueGroupList.setGroup(PROJECT_2103T, GROUP_2101);
        UniqueGroupList expectedUniquePersonList = new UniqueGroupList();
        expectedUniquePersonList.add(GROUP_2101);
        assertEquals(expectedUniquePersonList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasNonUniqueIdentity_throwsDuplicateGroupException() {
        uniqueGroupList.add(PROJECT_2103T);
        uniqueGroupList.add(GROUP_2101);
        thrown.expect(DuplicateGroupException.class);
        uniqueGroupList.setGroup(PROJECT_2103T, GROUP_2101);
    }

    @Test
    public void remove_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.remove(null);
    }

    @Test
    public void remove_groupDoesNotExist_throwsGroupNotFoundException() {
        thrown.expect(GroupNotFoundException.class);
        uniqueGroupList.remove(PROJECT_2103T);
    }

    @Test
    public void remove_existingGroup_removesGroup() {
        uniqueGroupList.add(PROJECT_2103T);
        uniqueGroupList.remove(PROJECT_2103T);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullUniqueGroupList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroups((UniqueGroupList) null);
    }

    @Test
    public void setGroups_uniqueGroupList_replacesOwnListWithProvidedUniqueGroupList() {
        uniqueGroupList.add(PROJECT_2103T);
        UniqueGroupList expectedUniquePersonList = new UniqueGroupList();
        expectedUniquePersonList.add(GROUP_2101);
        uniqueGroupList.setGroups(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroups((List<Group>) null);
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        uniqueGroupList.add(PROJECT_2103T);
        List<Group> groupList = Collections.singletonList(GROUP_2101);
        uniqueGroupList.setGroups(groupList);
        UniqueGroupList expectedUniquePersonList = new UniqueGroupList();
        expectedUniquePersonList.add(GROUP_2101);
        assertEquals(expectedUniquePersonList, uniqueGroupList);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateGroupException() {
        List<Group> listWithDuplicateGroups = Arrays.asList(PROJECT_2103T, PROJECT_2103T);
        thrown.expect(DuplicateGroupException.class);
        uniqueGroupList.setGroups(listWithDuplicateGroups);
    }

    @Test
    public void getPersonByName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.getGroupByTitle(null);
    }

    @Test
    public void getGroupByTitle_equals_returnsTrue() {
        Group group = new GroupBuilder().withTitle("project").build();
        Title title = new Title("project");

        uniqueGroupList.add(group);
        Group match = uniqueGroupList.getGroupByTitle(title);

        assertTrue(match.equals(group));
    }

    @Test
    public void getGroupByTitle_noMatch_returnsTrue() {
        Group group = new GroupBuilder().withTitle("Demo").build();
        Title title = new Title("not demo");

        uniqueGroupList.add(group);
        Group match = uniqueGroupList.getGroupByTitle(title);

        assertTrue(match == null);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asUnmodifiableObservableList().remove(0);
    }
}
