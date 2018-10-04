package seedu.address.model.AnakinDeck.AnakinExceptions;

/**
 * Signals that the operation will result in duplicate Cards (Cards are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCardException extends RuntimeException {
    public DuplicateCardException() {
        super("Operation would result in duplicate cards");
    }
}
