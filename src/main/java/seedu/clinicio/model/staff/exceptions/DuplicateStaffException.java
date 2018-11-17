package seedu.clinicio.model.staff.exceptions;

//@@author jjlee050
/**
 * Signals that the operation will result in duplicate Doctors (Doctors are considered duplicates if they have the same
 * identity).
 */
public class DuplicateStaffException extends RuntimeException {
    public DuplicateStaffException() {
        super("Operation would result in duplicate doctors");
    }
}
