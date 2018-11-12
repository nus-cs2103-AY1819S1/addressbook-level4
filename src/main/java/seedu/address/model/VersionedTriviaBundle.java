package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code TriviaBundle} that keeps track of its own history.
 */
public class VersionedTriviaBundle extends TriviaBundle {
    private final List<ReadOnlyTriviaBundle> triviaBundleList;
    private int currentStatePointer;

    public VersionedTriviaBundle(ReadOnlyTriviaBundle initialState) {
        super(initialState);

        triviaBundleList = new ArrayList<>();
        triviaBundleList.add(new TriviaBundle(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        triviaBundleList.add(new TriviaBundle(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        triviaBundleList.subList(currentStatePointer + 1, triviaBundleList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new VersionedTriviaBundle.NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(triviaBundleList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new VersionedTriviaBundle.NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(triviaBundleList.get(currentStatePointer));
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
        return currentStatePointer < triviaBundleList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedTriviaBundle)) {
            return false;
        }

        VersionedTriviaBundle otherVersionedTriviaBundle = (VersionedTriviaBundle) other;

        // state check
        return super.equals(otherVersionedTriviaBundle)
                && triviaBundleList.equals(otherVersionedTriviaBundle.triviaBundleList)
                && currentStatePointer == otherVersionedTriviaBundle.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of trivia bundle list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of Trivia bundle list, unable to redo.");
        }
    }
}
