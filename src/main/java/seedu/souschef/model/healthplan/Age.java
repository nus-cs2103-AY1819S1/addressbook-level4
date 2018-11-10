package seedu.souschef.model.healthplan;
import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.util.AppUtil.checkArgument;

/**
 * Age class
 */
public class Age {

    public static final String MESSAGE_AGE_CONSTRAINTS =
            "Age should only contain numbers and minimum of 2 digits";
    public static final String AGE_VALIDATION_REGEX = "^[1-9]\\d{1,}$";
    public final String value;


    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_AGE_CONSTRAINTS);
        value = age;
    }

    public static boolean isValidAge(String test) {
        return test.matches(AGE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Age // instanceof handles nulls
                && value.equals(((Age) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
