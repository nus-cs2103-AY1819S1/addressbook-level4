package seedu.inventory.model.sale;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents a Date in the sale order.
 * Guarantees: immutable; date is valid as declared in {@link #isValidSaleDate(String)}
 */
public class SaleDate {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be in YYYY-mm-dd format.";
    public static final String DATE_VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    public final String saleDate;

    /**
     * Constructs a {@code SaleDate}.
     *
     * @param saleDate A valid date format.
     */
    public SaleDate(String saleDate) {
        requireNonNull(saleDate);
        checkArgument(isValidSaleDate(saleDate), MESSAGE_DATE_CONSTRAINTS);
        this.saleDate = saleDate;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidSaleDate(String test) {
        if (test.matches(DATE_VALIDATION_REGEX)) {
            String[] parts = test.split("-");

            if (parts.length < 3) {
                return false;
            }

            return validateYear(Integer.parseInt(parts[0])) && validateMonth(Integer.parseInt(parts[1]))
                && validateDay(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]));
        }

        return false;
    }

    /**
     * Returns true if year is valid
     */
    private static boolean validateYear(Integer year) {
        // Prevent years older than 2018
        return year >= 2018;
    }

    /**
     * Returns true if month is valid
     */
    private static boolean validateMonth(Integer month) {
        // Prevent months in invalid range
        return month >= 1 && month <= 12;
    }

    /**
     * Returns true if day and month is valid
     */
    private static boolean validateDay(Integer day, Integer month) {
        // Prevent days in invalid range
        if (day < 1 || day > 31) {
            return false;
        }

        if (month == 2 || month == 4 || month == 6
                || month == 9 || month == 11) {
            if (day > 30) {
                return false;
            }

            if (day > 29 && month == 2) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaleDate // instanceof handles nulls
                && saleDate.equals(((SaleDate) other).saleDate)); // state check
    }

    @Override
    public int hashCode() {
        return saleDate.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return saleDate;
    }
}
