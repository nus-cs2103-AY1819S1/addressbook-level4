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

import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.tag.Tag;

public class UniqueGroupListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueGroupList uniqueGroupList = new UniqueGroupList();

    // TODO remember to add more tests once group is properly implemented

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.contains(null);
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(uniqueGroupList.contains(TEMP_GROUP_PROJECT));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        uniqueGroupList.add(TEMP_GROUP_PROJECT);
        assertTrue(uniqueGroupList.contains(TEMP_GROUP_PROJECT));
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.add(null);
    }

    @Test
    public void setGroup_nullTargetGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroup(null, TEMP_GROUP_PROJECT);
    }

    @Test
    public void setGroup_nullEditedGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroup(TEMP_GROUP_PROJECT, null);
    }

    @Test
    public void setGroup_targetGroupNotInList_throwsGroupNotFoundException() {
        thrown.expect(GroupNotFoundException.class);
        uniqueGroupList.setGroup(TEMP_GROUP_PROJECT, TEMP_GROUP_PROJECT);
    }

    @Test
    public void setGroup_editedGroupIsSameGroup_success() {
        uniqueGroupList.add(TEMP_GROUP_PROJECT);
        uniqueGroupList.setGroup(TEMP_GROUP_PROJECT, TEMP_GROUP_PROJECT);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(TEMP_GROUP_PROJECT);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasDifferentIdentity_success() {
        uniqueGroupList.add(TEMP_GROUP_PROJECT);
        uniqueGroupList.setGroup(TEMP_GROUP_PROJECT, TEMP_GROUP_ASSIGNMENT);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(TEMP_GROUP_ASSIGNMENT);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasNonUniqueIdentity_throwsDuplicateGroupException() {
        uniqueGroupList.add(TEMP_GROUP_PROJECT);
        uniqueGroupList.add(TEMP_GROUP_ASSIGNMENT);
        thrown.expect(DuplicateGroupException.class);
        uniqueGroupList.setGroup(TEMP_GROUP_PROJECT, TEMP_GROUP_ASSIGNMENT);
    }

    @Test
    public void remove_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.remove(null);
    }

    @Test
    public void remove_groupDoesNotExist_throwsGroupNotFoundException() {
        thrown.expect(GroupNotFoundException.class);
        uniqueGroupList.remove(TEMP_GROUP_PROJECT);
    }

    @Test
    public void remove_existingGroup_removesGroup() {
        uniqueGroupList.add(TEMP_GROUP_PROJECT);
        uniqueGroupList.remove(TEMP_GROUP_PROJECT);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullUniqueGroupList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroups((UniqueGroupList) null);
    }

    @Test
    public void setGroups_uniqueGroupList_replaceOwnListWithProvidedUniqueGroupList() {
        uniqueGroupList.add(TEMP_GROUP_PROJECT);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(TEMP_GROUP_ASSIGNMENT);
        uniqueGroupList.setGroups(expectedUniqueGroupList);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroups((List<Tag>) null);
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        uniqueGroupList.add(TEMP_GROUP_PROJECT);
        List<Tag> groupList = Collections.singletonList(TEMP_GROUP_ASSIGNMENT);
        uniqueGroupList.setGroups(groupList);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(TEMP_GROUP_ASSIGNMENT);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateGroupException() {
        List<Tag> listWithDuplicateGroups = Arrays.asList(TEMP_GROUP_PROJECT, TEMP_GROUP_PROJECT);
        thrown.expect(DuplicateGroupException.class);
        uniqueGroupList.setGroups(listWithDuplicateGroups);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asUnmodifiableObservableList().remove(0);
    }
}
