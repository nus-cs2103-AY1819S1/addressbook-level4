package seedu.thanepark.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ThanePark} that keeps track of its own history.
 */
public class VersionedThanePark extends ThanePark {

    private final List<ReadOnlyThanePark> thaneParkStateList;
    private int currentStatePointer;

    public VersionedThanePark(ReadOnlyThanePark initialState) {
        super(initialState);

        thaneParkStateList = new ArrayList<>();
        thaneParkStateList.add(new ThanePark(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ThanePark} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        thaneParkStateList.add(new ThanePark(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        thaneParkStateList.subList(currentStatePointer + 1, thaneParkStateList.size()).clear();
    }

    /**
     * Restores the thanepark book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(thaneParkStateList.get(currentStatePointer));
    }

    /**
     * Restores the thanepark book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(thaneParkStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has thanepark book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has thanepark book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < thaneParkStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedThanePark)) {
            return false;
        }

        VersionedThanePark otherVersionedthanePark = (VersionedThanePark) other;

        // state check
        return super.equals(otherVersionedthanePark)
                && thaneParkStateList.equals(otherVersionedthanePark.thaneParkStateList)
                && currentStatePointer == otherVersionedthanePark.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of thaneParkState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of thaneParkState list, unable to redo.");
        }
    }
}
