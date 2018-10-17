package seedu.inventory.model.staff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.testutil.TypicalStaffs.DARREN;
import static seedu.inventory.testutil.TypicalStaffs.ESMOND;
import static seedu.inventory.testutil.TypicalStaffs.TENGXIONG;
import static seedu.inventory.testutil.TypicalStaffs.ZUL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.staff.exceptions.DuplicateStaffException;
import seedu.inventory.model.staff.exceptions.StaffNotFoundException;
import seedu.inventory.testutil.StaffBuilder;

public class UniqueStaffListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueStaffList uniqueStaffList = new UniqueStaffList();

    @Test
    public void contains_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.contains(null);
    }

    @Test
    public void containsStaffNotInList_returnsFalse() {
        assertFalse(uniqueStaffList.contains(ZUL));
    }

    @Test
    public void contains_itemInList_returnsTrue() {
        uniqueStaffList.add(ZUL);
        assertTrue(uniqueStaffList.contains(ZUL));
    }

    @Test
    public void contains_itemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStaffList.add(ZUL);
        Staff editedZul = new StaffBuilder(ZUL).withRole(Staff.Role.user).build();
        assertTrue(uniqueStaffList.contains(editedZul));
    }

    @Test
    public void add_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.add(null);
    }

    @Test
    public void add_duplicateStaff_throwsDuplicateStaffException() {
        uniqueStaffList.add(ZUL);
        thrown.expect(DuplicateStaffException.class);
        uniqueStaffList.add(ZUL);
    }

    @Test
    public void setStaff_nullTargetStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.setStaff(null, ZUL);
    }

    @Test
    public void setStaff_nullEditedStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.setStaff(ZUL, null);
    }

    @Test
    public void setStaff_targetStaffNotInList_throwsStaffNotFoundException() {
        thrown.expect(StaffNotFoundException.class);
        uniqueStaffList.setStaff(ESMOND, ESMOND);
    }

    @Test
    public void setStaff_editedStaffIsSameStaff_success() {
        uniqueStaffList.add(ZUL);
        uniqueStaffList.setStaff(ZUL, ZUL);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(ZUL);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaff_editedStaffHasSameIdentity_success() {
        uniqueStaffList.add(ZUL);
        Staff editedZul = new StaffBuilder(ZUL).withRole(Staff.Role.user).build();
        uniqueStaffList.setStaff(ZUL, editedZul);
        UniqueStaffList expecetedUniqueStaffList = new UniqueStaffList();
        expecetedUniqueStaffList.add(editedZul);
        assertEquals(expecetedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaff_editedStaffHasDifferentIdentity_success() {
        uniqueStaffList.add(ZUL);
        uniqueStaffList.setStaff(ZUL, DARREN);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(DARREN);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaff_editedStaffHasNonUniqueIdentity_throwsDuplicateStaffException() {
        uniqueStaffList.add(ZUL);
        uniqueStaffList.add(TENGXIONG);
        thrown.expect(DuplicateStaffException.class);
        uniqueStaffList.setStaff(ZUL, TENGXIONG);
    }

    @Test
    public void remove_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.remove(null);
    }

    @Test
    public void remove_staffDoesNotExist_throwsStaffNotFoundException() {
        thrown.expect(StaffNotFoundException.class);
        uniqueStaffList.remove(ZUL);
    }

    @Test
    public void remove_existingStaff_removesStaff() {
        uniqueStaffList.add(ZUL);
        uniqueStaffList.remove(ZUL);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaffs_nullUniqueStaffList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.setStaffs((UniqueStaffList) null);
    }

    @Test
    public void setStaffs_uniqueStaffList_replacesOwnListWithProvidedUniqueStaffList() {
        uniqueStaffList.add(ZUL);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(ESMOND);
        uniqueStaffList.setStaffs(expectedUniqueStaffList);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaffs_nulList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.setStaffs((List<Staff>) null);
    }

    @Test
    public void setStaffs_list_replacesOwnListWithProvidedList() {
        uniqueStaffList.add(ZUL);
        List<Staff> staffList = Collections.singletonList(TENGXIONG);
        uniqueStaffList.setStaffs(staffList);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(TENGXIONG);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaffs_listWithDuplicateStaffs_throwsDuplicateStaffException() {
        List<Staff> listWithDuplicateStaffs = Arrays.asList(ZUL, ZUL);
        thrown.expect(DuplicateStaffException.class);
        uniqueStaffList.setStaffs(listWithDuplicateStaffs);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueStaffList.asUnmodifiableObservableList().remove(0);
    }
}
