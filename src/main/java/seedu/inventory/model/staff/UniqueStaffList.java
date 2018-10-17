package seedu.inventory.model.staff;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.model.staff.exceptions.DuplicateStaffException;
import seedu.inventory.model.staff.exceptions.StaffNotFoundException;

/**
 * A list of staffs that enforces uniqueness between its elements and does not allown nulls.
 * A person is considered unique by comparing user {@code Staff#isSameStaff(Staff)}. As such,
 * adding and updating of staffs uses Staff#isSameStaff(Staff) for equality so as to ensure that
 * the staff being ensure that the staff being added or updated is as to ensure that the staff
 * with exactly the same fields will be removed.
 *
 * Supports minimal set of list operations.
 *
 * @see Staff#isSameStaff(Staff)
 */
public class UniqueStaffList implements Iterable<Staff> {

    private final ObservableList<Staff> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent staff as the given argument.
     */
    public boolean contains(Staff toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameStaff);
    }

    /**
     * Adds a staff to the list.
     * @param toAdd A staff to add into the list.
     */
    public void add(Staff toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStaffException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the staff {@code target} in the list with {@code editedStaff}.
     * {@code target} must exist in the list.
     * The staff identity of {@code editedStaff} must not be the same as another existing staff in the list.
     *
     * @param target the targeted staff to be changes.
     * @param editedStaff the staff with updated information.
     */
    public void setStaff(Staff target, Staff editedStaff) {
        requireAllNonNull(target, editedStaff);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StaffNotFoundException();
        }

        if (!target.isSameStaff(editedStaff) && contains(editedStaff)) {
            throw new DuplicateStaffException();
        }

        internalList.set(index, editedStaff);
    }

    /**
     * Removes the equivalent staff from the list.
     * The staff must exist in the list.
     *
     * @param toRemove the staff to be removed.
     */
    public void remove(Staff toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StaffNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     *
     * @param replacement the unique staff list created to replace the current list.
     */
    public void setStaffs(UniqueStaffList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code staffs}/
     * {@code staffs} must not contain duplicate staffs.
     */
    public void setStaffs(List<Staff> staffs) {
        requireAllNonNull(staffs);
        if (!staffsAreUnique(staffs)) {
            throw new DuplicateStaffException();
        }

        internalList.setAll(staffs);
    }

    /**
     * Returns the backing list as an umodifiable {@code ObservableList}.
     * @return the ObservableList of staffs.
     */
    public ObservableList<Staff> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Staff> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueStaffList
                        && internalList.equals(((UniqueStaffList) other).internalList));
    }

    /**
     * Returns true if {@code staffs} contains only unique staffs.
     *
     * @param staffs the list of staffs to be verified.
     *
     * @return true if the list of staffs are unique else false
     */
    private boolean staffsAreUnique(List<Staff> staffs) {
        for (int i = 0; i < staffs.size() - 1; i++) {
            for (int j = i + 1; j < staffs.size(); j++) {
                if (staffs.get(i).isSameStaff(staffs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
