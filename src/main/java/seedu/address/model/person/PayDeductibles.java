package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * PayDeductibles class to store the necessary information about Pay Deductibles
 */

public class PayDeductibles {

    public static final PayDeductibles DEFAULT_INITIAL_DEDUCTIBLES = new PayDeductibles("0");
    public static final String MESSAGE_CONSTRAINTS =
        "Overtime hours should only contain numbers up to two decimal places "
        + "with no spaces or commas. Decimal places allowed";
    public static final String TWO_DECIMAL_DIGITS_VALIDATION_REGEX = "^\\d+(\\.\\d{1,2})?$";

    public final String payDeductibles;

    public PayDeductibles(String value) {
        requireNonNull(value);
        checkArgument(isValidTwoDecimalNumber(value));
        payDeductibles = value;
    }

    public static boolean isValidTwoDecimalNumber(String test) {
        return test.matches(TWO_DECIMAL_DIGITS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return payDeductibles;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof PayDeductibles // instanceof handles nulls
            && payDeductibles.equals(((PayDeductibles) other).payDeductibles)); // state check
    }

}
