package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Represents a Person's tuition fees per hour.
 */
public class Fees {

    public static final String MESSAGE_FEES_CONSTRAINTS =
            "Fees should only contain integer or double values, and it should not be blank";

    public static final String FEES_VALIDATION_REGEX = "\\d+|\\d+\\.\\d+";

    private static DecimalFormat df = new DecimalFormat("#.00");

    public final double feesPerHr;

    public Fees(double amount) {
        requireNonNull(amount);
        checkArgument(isValidFee(amount), MESSAGE_FEES_CONSTRAINTS);
        feesPerHr = amount;
    }

    /**
     * Returns true if a given amount has valid format.
     */
    public static boolean isValidFee(double feeAmount) {
        String fees = String.valueOf(feeAmount);
        return fees.matches(FEES_VALIDATION_REGEX);

    }

    @Override
    public String toString() {
    	return "$" + df.format(feesPerHr) + "/hour"; }
}
