package seedu.address.model.receptionist.exceptions;

//@@author jjlee050
/**
 * Signals that the operation will result in duplicate Receptionists
 * (Receptionists are considered duplicates if they have the same identity).
 */
public class DuplicateReceptionistException extends RuntimeException {
    public DuplicateReceptionistException() {
        super("Operation would result in duplicate receptionists");
    }
}
