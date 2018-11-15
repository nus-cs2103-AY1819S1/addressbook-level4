package seedu.address.model.medicine.exceptions;

/**
 * Signals that the operation will result in duplicate Medicines (Medicines are considered duplicates if they have
 * the same identity).
 */
public class DuplicateMedicineException extends RuntimeException {
    public DuplicateMedicineException() {
            super("Operation would result in duplicate medicines");
    }
}

