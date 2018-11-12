package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Ssenisub} that keeps track of its own history.
 */
public class VersionedSsenisub extends Ssenisub {

    private final List<ReadOnlySsenisub> ssenisubStateList;
    private int currentStatePointer;

    public VersionedSsenisub(ReadOnlySsenisub initialState) {
        super(initialState);

        ssenisubStateList = new ArrayList<>();
        ssenisubStateList.add(new Ssenisub(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code Ssenisub} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        ssenisubStateList.add(new Ssenisub(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        ssenisubStateList.subList(currentStatePointer + 1, ssenisubStateList.size()).clear();
    }

    /**
     * Restores Ssenisub to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(ssenisubStateList.get(currentStatePointer));
    }

    /**
     * Restores Ssenisub to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(ssenisubStateList.get(currentStatePointer));
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
        return currentStatePointer < ssenisubStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedSsenisub)) {
            return false;
        }

        VersionedSsenisub otherVersionedSsenisub = (VersionedSsenisub) other;

        // state check
        return super.equals(otherVersionedSsenisub)
                && ssenisubStateList.equals(otherVersionedSsenisub.ssenisubStateList)
                && currentStatePointer == otherVersionedSsenisub.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of SSENISUB State list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of SSENISUB State list, unable to redo.");
        }
    }
}
