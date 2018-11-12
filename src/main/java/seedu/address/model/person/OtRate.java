package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * OtRate class to store the necessary information about overtime rate
 */

public class OtRate {

    public static final OtRate DEFAULT_INITIAL_OTRATE = new OtRate("0");
    public static final String MESSAGE_CONSTRAINTS =
        "Overtime hours should only contain numbers up to two decimal places "
        + "with no spaces or commas. Decimal places allowed";
    public static final String TWO_DECIMAL_DIGITS_VALIDATION_REGEX = "^\\d+(\\.\\d{1,2})?$";

    public final String overTimeRate;

    public OtRate(String value) {
        requireNonNull(value);
        checkArgument(isValidTwoDecimalNumber(value));
        overTimeRate = value;
    }

    public static boolean isValidTwoDecimalNumber(String test) {
        return test.matches(TWO_DECIMAL_DIGITS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return overTimeRate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof OtRate // instanceof handles nulls
            && overTimeRate.equals(((OtRate) other).overTimeRate)); // state check
    }
}
