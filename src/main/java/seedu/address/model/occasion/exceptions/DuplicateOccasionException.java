package seedu.address.model.occasion.exceptions;

/**
 * Signals that the operation will result in duplicate occasions.
 * Two occasions are the same iff the equals test returns true on them.
 */
public class DuplicateOccasionException extends RuntimeException {

    public DuplicateOccasionException() {
        super("Operation would result in Duplicate occasions");
    }
}
