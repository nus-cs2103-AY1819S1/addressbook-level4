package seedu.inventory.model.staff.exceptions;

/**
 * Signals that the operation will result in duplicate Staff (Staffs are considered duplicates if they have the same
 * identity).
 */
public class DuplicateStaffException extends RuntimeException {
    public DuplicateStaffException() {
        super("Operation would result in duplicate staffs");
    }
}
