package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AssignmentList} that keeps track of its own history.
 */
public class VersionedAssignmentList extends AssignmentList {

    private final List<ReadOnlyAssignmentList> assignmentListStateList;
    private int currentStatePointer;

    public VersionedAssignmentList(ReadOnlyAssignmentList initialState) {
        super(initialState);

        assignmentListStateList = new ArrayList<>();
        assignmentListStateList.add(new AssignmentList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AssignmentList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        assignmentListStateList.add(new AssignmentList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        assignmentListStateList.subList(currentStatePointer + 1, assignmentListStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new VersionedAssignmentList.NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(assignmentListStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new VersionedAssignmentList.NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(assignmentListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < assignmentListStateList.size() - 1;
    }

    /**
     * Restarts the versioned address book as if the application was just restarted (i.e. with no history
     * or redo functionality kept)
     */
    public void restart() {
        ReadOnlyAssignmentList currentState = assignmentListStateList.get(currentStatePointer);
        assignmentListStateList.clear();
        assignmentListStateList.add(currentState);
        currentStatePointer = 0;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAssignmentList)) {
            return false;
        }

        VersionedAssignmentList otherVersionedAssignmentList = (VersionedAssignmentList) other;

        // state check
        return super.equals(otherVersionedAssignmentList)
                && assignmentListStateList.equals(otherVersionedAssignmentList.assignmentListStateList)
                && currentStatePointer == otherVersionedAssignmentList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
