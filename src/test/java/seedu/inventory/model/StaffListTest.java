package seedu.inventory.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_USERNAME_ZUL;
import static seedu.inventory.testutil.TypicalStaffs.ZUL;
import static seedu.inventory.testutil.TypicalStaffs.getTypicalStaffList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.exceptions.DuplicateStaffException;
import seedu.inventory.testutil.StaffBuilder;

public class StaffListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final StaffList staffList = new StaffList();

    @Test
    public void constructor_withReadOnlyStaffList() {
        StaffList expectedStaffList = getTypicalStaffList();
        StaffListStub listStub = new StaffListStub(expectedStaffList.getStaffList());
        StaffList staffListWithParams = new StaffList(listStub);
        assertEquals(expectedStaffList, staffListWithParams);
    }

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), staffList.getStaffList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        staffList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyStaff_replacesData() {
        StaffList newData = getTypicalStaffList();
        staffList.resetData(newData);
        assertEquals(newData, staffList);
    }

    @Test
    public void resetData_withDuplicateStaffs_throwsDuplicateStaffException() {
        Staff editedZul = new StaffBuilder(ZUL).withRole(Staff.Role.user).build();
        List<Staff> newStaffs = Arrays.asList(ZUL, editedZul);
        StaffListStub newData = new StaffListStub(newStaffs);

        thrown.expect(DuplicateStaffException.class);
        staffList.resetData(newData);
    }

    @Test
    public void hasStaff_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        staffList.hasStaff(null);
    }

    @Test
    public void hasStaff_staffNotInStaffList_returnsFalse() {
        assertFalse(staffList.hasStaff(ZUL));
    }

    @Test
    public void hasStaff_staffInStaffList_returnsTrue() {
        staffList.addStaff(ZUL);
        assertTrue(staffList.hasStaff(ZUL));
    }

    @Test
    public void hasStaff_staffWithSameIdentityFieldsInStaffList_returnsTrue() {
        staffList.addStaff(ZUL);
        Staff editedZul = new StaffBuilder(ZUL).withRole(Staff.Role.user).build();
        assertTrue(staffList.hasStaff(editedZul));
    }

    @Test
    public void getStaffList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        staffList.getStaffList().remove(0);
    }

    @Test
    public void updateStaff_nullTarget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        staffList.updateStaff(null, ZUL);
    }

    @Test
    public void updateStaff_nullEditedStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        staffList.updateStaff(ZUL, null);
    }

    @Test
    public void updateStaff() {
        staffList.addStaff(ZUL);
        Staff editedZul = new StaffBuilder().withName(VALID_NAME_ZUL).withUsername(VALID_USERNAME_ZUL)
                .withPassword(VALID_PASSWORD_ZUL).build();
        StaffList expectedStaffList = new StaffList();
        expectedStaffList.addStaff(editedZul);
        staffList.updateStaff(ZUL, editedZul);
        assertEquals(expectedStaffList, staffList);
    }

    @Test
    public void removeStaff_nullKey_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        staffList.removeStaff(null);
    }

    @Test
    public void removeStaff() {
        staffList.addStaff(ZUL);
        StaffList expectedStaffList = new StaffList();
        staffList.removeStaff(ZUL);
        assertEquals(expectedStaffList, staffList);
    }

    /**
     * A stub ReadOnlyStaffList whose items list can violate interface constraints.
     */
    private static class StaffListStub implements ReadOnlyStaffList {
        private final ObservableList<Staff> staffs = FXCollections.observableArrayList();

        StaffListStub(Collection<Staff> staffs) {
            this.staffs.setAll(staffs);
        }

        @Override
        public ObservableList<Staff> getStaffList() {
            return staffs;
        }
    }
}
