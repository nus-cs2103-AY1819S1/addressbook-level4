package seedu.address.model.group;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalGroup.TEMP_GROUP_ASSIGNMENT;
import static seedu.address.testutil.TypicalGroup.TEMP_GROUP_PROJECT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.group.exceptions.DuplicateGroupTagException;
import seedu.address.model.group.exceptions.GroupTagNotFoundException;
import seedu.address.model.tag.Tag;

public class UniqueGroupTagListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueGroupTagList uniqueGroupTagList = new UniqueGroupTagList();

    // TODO remember to add more tests once group is properly implemented

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupTagList.contains(null);
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(uniqueGroupTagList.contains(TEMP_GROUP_PROJECT));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        uniqueGroupTagList.add(TEMP_GROUP_PROJECT);
        assertTrue(uniqueGroupTagList.contains(TEMP_GROUP_PROJECT));
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupTagList.add(null);
    }

    @Test
    public void setGroup_nullTargetGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupTagList.setGroup(null, TEMP_GROUP_PROJECT);
    }

    @Test
    public void setGroup_nullEditedGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupTagList.setGroup(TEMP_GROUP_PROJECT, null);
    }

    @Test
    public void setGroup_targetGroupNotInList_throwsGroupNotFoundException() {
        thrown.expect(GroupTagNotFoundException.class);
        uniqueGroupTagList.setGroup(TEMP_GROUP_PROJECT, TEMP_GROUP_PROJECT);
    }

    @Test
    public void setGroup_editedGroupIsSameGroup_success() {
        uniqueGroupTagList.add(TEMP_GROUP_PROJECT);
        uniqueGroupTagList.setGroup(TEMP_GROUP_PROJECT, TEMP_GROUP_PROJECT);
        UniqueGroupTagList expectedUniqueGroupTagList = new UniqueGroupTagList();
        expectedUniqueGroupTagList.add(TEMP_GROUP_PROJECT);
        assertEquals(expectedUniqueGroupTagList, uniqueGroupTagList);
    }

    @Test
    public void setGroup_editedGroupHasDifferentIdentity_success() {
        uniqueGroupTagList.add(TEMP_GROUP_PROJECT);
        uniqueGroupTagList.setGroup(TEMP_GROUP_PROJECT, TEMP_GROUP_ASSIGNMENT);
        UniqueGroupTagList expectedUniqueGroupTagList = new UniqueGroupTagList();
        expectedUniqueGroupTagList.add(TEMP_GROUP_ASSIGNMENT);
        assertEquals(expectedUniqueGroupTagList, uniqueGroupTagList);
    }

    @Test
    public void setGroup_editedGroupHasNonUniqueIdentity_throwsDuplicateGroupException() {
        uniqueGroupTagList.add(TEMP_GROUP_PROJECT);
        uniqueGroupTagList.add(TEMP_GROUP_ASSIGNMENT);
        thrown.expect(DuplicateGroupTagException.class);
        uniqueGroupTagList.setGroup(TEMP_GROUP_PROJECT, TEMP_GROUP_ASSIGNMENT);
    }

    @Test
    public void remove_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupTagList.remove(null);
    }

    @Test
    public void remove_groupDoesNotExist_throwsGroupNotFoundException() {
        thrown.expect(GroupTagNotFoundException.class);
        uniqueGroupTagList.remove(TEMP_GROUP_PROJECT);
    }

    @Test
    public void remove_existingGroup_removesGroup() {
        uniqueGroupTagList.add(TEMP_GROUP_PROJECT);
        uniqueGroupTagList.remove(TEMP_GROUP_PROJECT);
        UniqueGroupTagList expectedUniqueGroupTagList = new UniqueGroupTagList();
        assertEquals(expectedUniqueGroupTagList, uniqueGroupTagList);
    }

    @Test
    public void setGroups_nullUniqueGroupList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupTagList.setGroups((UniqueGroupTagList) null);
    }

    @Test
    public void setGroups_uniqueGroupList_replaceOwnListWithProvidedUniqueGroupList() {
        uniqueGroupTagList.add(TEMP_GROUP_PROJECT);
        UniqueGroupTagList expectedUniqueGroupTagList = new UniqueGroupTagList();
        expectedUniqueGroupTagList.add(TEMP_GROUP_ASSIGNMENT);
        uniqueGroupTagList.setGroups(expectedUniqueGroupTagList);
        assertEquals(expectedUniqueGroupTagList, uniqueGroupTagList);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupTagList.setGroups((List<Tag>) null);
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        uniqueGroupTagList.add(TEMP_GROUP_PROJECT);
        List<Tag> groupList = Collections.singletonList(TEMP_GROUP_ASSIGNMENT);
        uniqueGroupTagList.setGroups(groupList);
        UniqueGroupTagList expectedUniqueGroupTagList = new UniqueGroupTagList();
        expectedUniqueGroupTagList.add(TEMP_GROUP_ASSIGNMENT);
        assertEquals(expectedUniqueGroupTagList, uniqueGroupTagList);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateGroupException() {
        List<Tag> listWithDuplicateGroups = Arrays.asList(TEMP_GROUP_PROJECT, TEMP_GROUP_PROJECT);
        thrown.expect(DuplicateGroupTagException.class);
        uniqueGroupTagList.setGroups(listWithDuplicateGroups);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupTagList.asUnmodifiableObservableList().remove(0);
    }
}
