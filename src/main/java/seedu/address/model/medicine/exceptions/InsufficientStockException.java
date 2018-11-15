package seedu.address.model.medicine.exceptions;

import seedu.address.model.medicine.Medicine;
/**
 * Signals that the operation will result in having a negative Stock after dispensing
 */
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Medicine medicine) {
        super("Insufficient quantity of " + medicine.getMedicineName() + " in stock.");
    }
}
