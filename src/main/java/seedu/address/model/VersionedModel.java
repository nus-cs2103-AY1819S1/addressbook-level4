package seedu.address.model;

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
}
