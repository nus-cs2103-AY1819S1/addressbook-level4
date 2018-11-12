package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * OtHour class to store the necessary information about overtime hours
 */

public class OtHour {

    public static final OtHour DEFAULT_INITIAL_OTHOUR = new OtHour("0");

    public static final String MESSAGE_CONSTRAINTS =
        "Overtime hours should only contain numbers up to two decimal places "
          + "with no spaces or commas. Decimal places allowed";
    public static final String TWO_DECIMAL_DIGITS_VALIDATION_REGEX = "^\\d+(\\.\\d{1,2})?$";


    public final String overTimeHour;

    public OtHour(String value) {
        requireNonNull(value);
        checkArgument(isValidTwoDecimalNumber(value));
        overTimeHour = value;
    }

    public static boolean isValidTwoDecimalNumber(String test) {
        return test.matches(TWO_DECIMAL_DIGITS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return overTimeHour;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof OtHour // instanceof handles nulls
            && overTimeHour.equals(((OtHour) other).overTimeHour)); // state check
    }
}
