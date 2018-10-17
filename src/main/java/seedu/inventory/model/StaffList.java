package seedu.inventory.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.UniqueStaffList;

/**
 * Wraps all data at the StaffList level
 * Duplicates are not allowed (by .isSameStaff comparison)
 */
public class StaffList implements ReadOnlyStaffList {

    private final UniqueStaffList staffs;

    {
        staffs = new UniqueStaffList();
    }

    public StaffList() {}

    /**
     * Creates a StaffList using the Staffs in the {@code toBeCopied}.
     *
     * @param toBeCopied the ReadOnlyStaffList to be copied into the staff list
     */
    public StaffList(ReadOnlyStaffList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the staff list with {@code staffs}.
     * {@code staffs} must not contain duplicate staffs.
     *
     * @param staffs the staffs to be set into the UniqueStaffList.
     */
    public void setStaffs(List<Staff> staffs) {
        this.staffs.setStaffs(staffs);
    }

    /**
     * Resets the existing data of this {@code StaffList} with {@code newData}.
     *
     * @param newData the ReadOnlyStaffList to be reset as a new data into the staff list.
     */
    public void resetData(ReadOnlyStaffList newData) {
        requireNonNull(newData);

        setStaffs(newData.getStaffList());
    }

    /**
     * Returns true if the staff exists in the staff list.
     *
     * @param staff to check whether it exists in staff list.
     * @return true if it has the staff else false.
     */
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return staffs.contains(staff);
    }

    /**
     * Adds a staff to the staff list.
     * The staff must not already exist in the staff list.
     *
     * @param staff the staff to be added.
     */
    public void addStaff(Staff staff) {
        staffs.add(staff);
    }

    /**
     * Replaces the given staff {@code target} in the list with {@code editedStaff}.
     * {@code target} must exist in the staff list.
     * The staff identity of {@code editedStaff} must not be the same as another exiistng staff in the staff list.
     *
     * @param target the targeted staff to be updated
     * @param editedStaff the updated staff with updated information
     */
    public void updateStaff(Staff target, Staff editedStaff) {
        requireNonNull(editedStaff);

        staffs.setStaff(target, editedStaff);
    }

    /**
     * Removes {@code key} from this {@code StaffList}.
     * {@code key} must exist in the staff list.
     *
     * @param key the staff to be removed.
     */
    public void removeStaff(Staff key) {
        staffs.remove(key);
    }

    @Override
    public String toString() {
        return staffs.asUnmodifiableObservableList().size() + " staffs";
    }

    @Override
    public ObservableList<Staff> getStaffList() {
        return staffs.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof StaffList
                        && staffs.equals(((StaffList) other).staffs));
    }
}
