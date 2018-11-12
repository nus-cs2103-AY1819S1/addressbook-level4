package seedu.superta.model.assignment.exceptions;

/**
 * Signals that the operation will result in duplicate Assignments names.
 */
public class DuplicateAssignmentNameException extends RuntimeException {
    public DuplicateAssignmentNameException() {
        super("Operation would result in duplicate assignment names");
    }
}
