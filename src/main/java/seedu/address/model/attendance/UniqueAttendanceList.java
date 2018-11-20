package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.DuplicateAttendanceException;

/**
 * A list of attendances that enforces uniqueness between its elements and does not allow nulls.
 * An attendance is considered unique by comparing using {@code Attendance#isSameAttendance(Attendance)}. As such,
 * adding and updating of attendances uses Attendance#isSameAttendance(Attendance) for equality so as to ensure that the
 * attendance being added or updated is unique in terms of identity in the UniqueAttendanceList. However, the removal of
 * an attendance uses Attendance#equals(Object) so as to ensure that the attendance with exactly the same fields will be
 * removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Attendance#isSameAttendance(Attendance)
 */
public class UniqueAttendanceList implements Iterable<Attendance> {

    private final ObservableList<Attendance> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent attendance as the given argument.
     */
    public boolean contains(Attendance toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAttendance);
    }

    /**
     * Adds an attendance to the list.
     * The attendance must not already exist in the list.
     */
    public void add(Attendance toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAttendanceException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedAttendance}.
     * {@code target} must exist in the list.
     * The attendance identity of {@code editedAttendance} must not be the same as another existing attendance in the
     * list.
     */
    public void setAttendance(Attendance target, Attendance editedAttendance) {
        requireAllNonNull(target, editedAttendance);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AttendanceNotFoundException();
        }

        if (!target.isSameAttendance(editedAttendance) && contains(editedAttendance)) {
            throw new DuplicateAttendanceException();
        }

        internalList.set(index, editedAttendance);
    }

    /**
     * Removes the equivalent attendance from the list.
     * The attendance must exist in the list.
     */
    public void remove(Attendance toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AttendanceNotFoundException();
        }
    }

    public void setAttendances(UniqueAttendanceList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code attendances}.
     * {@code attendances} must not contain duplicate attendances.
     */
    public void setAttendances(List<Attendance> attendances) {
        requireAllNonNull(attendances);
        if (!attendancesAreUnique(attendances)) {
            throw new DuplicateAttendanceException();
        }

        internalList.setAll(attendances);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Attendance> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Sorts all attendances in the list according to lexicographical order.
     */
    public void sort() {
        internalList.sort((attendance1, attendance2) -> (
                attendance1.compareTo(attendance2))
        );
    }

    @Override
    public Iterator<Attendance> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAttendanceList // instanceof handles nulls
                        && internalList.equals(((UniqueAttendanceList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code attendances} contains only unique attendances.
     */
    private boolean attendancesAreUnique(List<Attendance> attendances) {
        for (int i = 0; i < attendances.size() - 1; i++) {
            for (int j = i + 1; j < attendances.size(); j++) {
                if (attendances.get(i).isSameAttendance(attendances.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
