package seedu.inventory.model.sale;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents an ID in the sale order.
 * Guarantees: immutable; ID is valid as declared in {@link #isValidSaleId(String)}
 */
public class SaleId {

    public static final String MESSAGE_ID_CONSTRAINTS = "ID should be only numbers with at least 1 digit.";
    public static final String ID_VALIDATION_REGEX = "\\d{1,}";

    public final String saleId;

    /**
     * Constructs a {@code SaleId}.
     *
     * @param saleId A valid ID format.
     */
    public SaleId(String saleId) {
        requireNonNull(saleId);
        checkArgument(isValidSaleId(saleId), MESSAGE_ID_CONSTRAINTS);
        this.saleId = saleId;
    }

    /**
     * Returns true if a given string is a valid ID.
     */
    public static boolean isValidSaleId(String test) {
        return test.matches(ID_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaleId // instanceof handles nulls
                && saleId.equals(((SaleId) other).saleId)); // state check
    }

    @Override
    public int hashCode() {
        return saleId.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return saleId;
    }
}
