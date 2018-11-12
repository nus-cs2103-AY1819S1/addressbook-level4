package seedu.address.model.accounting.exception;

/**
 * Signals that the operation will result in duplicate Debts
 * (Debts are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDebtException extends RuntimeException {
    public DuplicateDebtException() {
        super("Operation would result in duplicate debts");
    }
}
