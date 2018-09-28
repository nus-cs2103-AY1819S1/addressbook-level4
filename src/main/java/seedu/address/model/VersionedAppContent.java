package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AppContent} that keeps track of its own history.
 */
public class VersionedAppContent extends AppContent {

    private final List<ReadOnlyAppContent> appContentStateList;
    private int currentStatePointer;

    public VersionedAppContent(ReadOnlyAppContent initialState) {
        super(initialState);

        appContentStateList = new ArrayList<>();
        appContentStateList.add(new AppContent(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AppContent} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        appContentStateList.add(new AppContent(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        appContentStateList.subList(currentStatePointer + 1, appContentStateList.size()).clear();
    }

    /**
     * Restores the application content to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(appContentStateList.get(currentStatePointer));
    }

    /**
     * Restores the application content to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(appContentStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has application content states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has application content states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < appContentStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAppContent)) {
            return false;
        }

        VersionedAppContent otherVersionedAppContent = (VersionedAppContent) other;

        // state check
        return super.equals(otherVersionedAppContent)
                && appContentStateList.equals(otherVersionedAppContent.appContentStateList)
                && currentStatePointer == otherVersionedAppContent.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of appContentState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of appContentState list, unable to redo.");
        }
    }
}
