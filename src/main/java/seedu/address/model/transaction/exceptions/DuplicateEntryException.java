package seedu.address.model.transaction.exceptions;

//@author ericyjw
/**
 * Signals that the operation will result in duplicate Transaction Entries (Transaction Entries are considered
 * duplicates if they have the same identity).
 *
 * @author ericyjw
 */
public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException() {
        super("Operation would result in duplicate entries");
    }
}


