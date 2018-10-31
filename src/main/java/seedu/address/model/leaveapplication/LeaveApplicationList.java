package seedu.address.model.leaveapplication;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.leaveapplication.exceptions.LeaveNotFoundException;

/**
 * A list of leave applications that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class LeaveApplicationList implements Iterable<LeaveApplicationWithEmployee> {

    private final ObservableList<LeaveApplicationWithEmployee> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent LeaveApplicationWithEmployees as the given argument.
     */
    public boolean contains(LeaveApplicationWithEmployee toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a LeaveApplicationWithEmployee to the list.
     */
    public void add(LeaveApplicationWithEmployee toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the leave application {@code target} in the list with {@code editedLeave}.
     * {@code target} must exist in the list.
     */
    public void setLeaveApplication(LeaveApplicationWithEmployee target,
                                    LeaveApplicationWithEmployee editedLeave) {
        requireAllNonNull(target, editedLeave);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LeaveNotFoundException();
        }

        internalList.set(index, editedLeave);
    }

    /**
     * Removes the equivalent LeaveApplicationWithEmployee from the list.
     * The LeaveApplicationWithEmployee must exist in the list.
     */
    public void remove(LeaveApplicationWithEmployee toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LeaveNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement} leave application list.
     */
    public void setLeaveApplications(LeaveApplicationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code leaveApplications}.
     */
    public void setLeaveApplications(List<LeaveApplicationWithEmployee> leaveApplications) {
        requireAllNonNull(leaveApplications);
        internalList.setAll(leaveApplications);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<LeaveApplicationWithEmployee> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<LeaveApplicationWithEmployee> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveApplicationList // instanceof handles nulls
                        && internalList.equals(((LeaveApplicationList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
