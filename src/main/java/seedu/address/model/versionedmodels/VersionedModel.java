package seedu.address.model.versionedmodels;

/**
 * This interface is implemented by classes which keep track of each state of the wishbook.
 */
public interface VersionedModel {

    /**
     * Saves a copy of the current state.
     */
    void commit();

    /**
     * Restores the model to the previous state.
     */
    void undo();

    /**
     * Restores the model to its previously undone state.
     */
    void redo();

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    class NoUndoableStateException extends RuntimeException {
        NoUndoableStateException() {
            super("Current state pointer at start of wishState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    class NoRedoableStateException extends RuntimeException {
        NoRedoableStateException() {
            super("Current state pointer at end of wishState list, unable to redo.");
        }
    }
}
