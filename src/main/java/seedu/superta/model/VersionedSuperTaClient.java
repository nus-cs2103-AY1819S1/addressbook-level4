package seedu.superta.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code SuperTaClient} that keeps track of its own history.
 */
public class VersionedSuperTaClient extends SuperTaClient {

    private final List<ReadOnlySuperTaClient> superTaClientStateList;
    private int currentStatePointer;

    public VersionedSuperTaClient(ReadOnlySuperTaClient initialState) {
        super(initialState);

        superTaClientStateList = new ArrayList<>();
        superTaClientStateList.add(new SuperTaClient(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code SuperTaClient} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        superTaClientStateList.add(new SuperTaClient(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        superTaClientStateList.subList(currentStatePointer + 1, superTaClientStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(superTaClientStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(superTaClientStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has SuperTA client states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has SuperTA client states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < superTaClientStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedSuperTaClient)) {
            return false;
        }

        VersionedSuperTaClient otherVersionedSuperTaClient = (VersionedSuperTaClient) other;

        // state check
        return super.equals(otherVersionedSuperTaClient)
                && superTaClientStateList.equals(otherVersionedSuperTaClient.superTaClientStateList)
                && currentStatePointer == otherVersionedSuperTaClient.currentStatePointer;
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
