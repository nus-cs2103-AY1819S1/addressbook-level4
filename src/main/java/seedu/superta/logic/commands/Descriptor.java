package seedu.superta.logic.commands;

/**
 * A descriptor interface for updating.
 */
public interface Descriptor {
    /**
     * This should return true if any field is edited.
     */
    boolean isAnyFieldEdited();
}
