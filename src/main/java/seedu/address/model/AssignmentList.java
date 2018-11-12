package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.project.Assignment;
import seedu.address.model.project.UniqueAssignmentList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameAssignment comparison)
 */
public class AssignmentList implements ReadOnlyAssignmentList {

    private final UniqueAssignmentList assignments;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        assignments = new UniqueAssignmentList();
    }

    public AssignmentList() {}

    /**
     * Creates an AddressBook using the Assignments in the {@code toBeCopied}
     */
    public AssignmentList(ReadOnlyAssignmentList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setAssignments(List<Assignment> assignments) {
        this.assignments.setAssignments(assignments);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAssignmentList newData) {
        requireNonNull(newData);

        setAssignments(newData.getAssignmentList());
    }

    //// assignment-level operations

    /**
     * Returns true if a assignment with the same identity as {@code assignment} exists in the address book.
     */
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return assignments.contains(assignment);
    }

    /**
     * Adds an assignment to the address book.
     * The assignment must not already exist in the address book.
     */
    public void addAssignment(Assignment p) {
        assignments.add(p);
    }

    /**
     * Replaces the given assignment {@code target} in the list with {@code editedAssignment}.
     * {@code target} must exist in the address book.
     * The assignment identity of {@code editedAssignment} must not be the same as another existing assignment
     * in the address book.
     */
    public void updateAssignment(Assignment target, Assignment editedAssignment) {
        requireNonNull(editedAssignment);

        assignments.setAssignment(target, editedAssignment);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeAssignmnet(Assignment key) {
        assignments.remove(key);
    }

    @Override
    public ObservableList<Assignment> getAssignmentList() {
        return assignments.asUnmodifiableObservableList();
    }

}
