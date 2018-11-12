package seedu.superta.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.superta.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.superta.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.superta.model.assignment.exceptions.DuplicateAssignmentNameException;

/**
 * Class for storing the assignments in a list.
 */
public class UniqueAssignmentList implements Iterable<Assignment> {

    private final ObservableList<Assignment> internalList = FXCollections.observableArrayList();

    public UniqueAssignmentList(List<Assignment> source) {
        internalList.addAll(source);
    }

    public UniqueAssignmentList() { }

    /**
     * Returns true if the list contains an equivalent student as the given argument.
     */
    public boolean contains(Assignment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAssignment);
    }

    /**
     * Method to add an assignment to the list.
     * @param toAdd the assignment to add
     */
    public void add(Assignment toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAssignmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the assignment {@code target} in the list with {@code editedAssignment}.
     * {@code target} must exist in the list.
     * The assignment identity of {@code editedAssignment} must not be the same as another existing assignment
     * in the list.
     */
    public void setAssignment(Assignment target, Assignment editedAssignment) {
        requireAllNonNull(target, editedAssignment);
        Assignment editedAssignmentWithGradeBook =
                new Assignment(editedAssignment.getTitle(), editedAssignment.getMaxMarks(), target.getGradebook());

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AssignmentNotFoundException();
        }

        if (!target.isSameAssignment(editedAssignmentWithGradeBook) && contains(editedAssignmentWithGradeBook)) {
            throw new DuplicateAssignmentNameException();
        }

        internalList.set(index, editedAssignmentWithGradeBook);
    }

    /**
     * Method to remove an assignment from the list.
     * @param toRemove the assignment to remove
     */
    public void remove(Assignment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AssignmentNotFoundException();
        }
    }

    public void setAssignments(UniqueAssignmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code assignments}.
     * {@code assignments} must not contain duplicate assignments.
     */
    public void setAssignments(List<Assignment> assignments) {
        requireAllNonNull(assignments);
        if (!assignmentsAreUnique(assignments)) {
            throw new DuplicateAssignmentException();
        }

        internalList.setAll(assignments);
    }

    public Assignment getAssignmentWithTitle(Title title) {
        return internalList.stream().filter(assignments -> assignments.getTitle().equals(title))
            .findFirst().get();
    }

    /**
     * Method to clone this instance.
     * @return a cloned instance
     */
    public UniqueAssignmentList clone() {
        UniqueAssignmentList other = new UniqueAssignmentList();
        other.internalList.addAll(internalList.stream()
            .map(Assignment::new)
            .collect(Collectors.toList()));
        return other;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Assignment> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Returns the size of the internal list.
     */
    public int size() {
        return internalList.size();
    }


    @Override
    public Iterator<Assignment> iterator() {
        return internalList.iterator();
    }

    @Override
    public String toString() {
        return internalList.stream()
            .map(Assignment::toString)
            .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueAssignmentList)) {
            return false;
        }

        return internalList.equals(((UniqueAssignmentList) other).internalList);
    }
    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code assignments} contains only unique assignments.
     */
    private boolean assignmentsAreUnique(List<Assignment> assignments) {
        for (int i = 0; i < assignments.size() - 1; i++) {
            for (int j = i + 1; j < assignments.size(); j++) {
                if (assignments.get(i).isSameAssignment(assignments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
