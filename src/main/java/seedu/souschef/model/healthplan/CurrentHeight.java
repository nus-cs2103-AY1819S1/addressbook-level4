package seedu.souschef.model.healthplan;
import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.util.AppUtil.checkArgument;

/**
 * Class to handle the current height component of the health plans
 */
public class CurrentHeight {

    public static final String MESSAGE_HEIGHT_CONSTRAINTS =
            "Heights should only contain numbers and be at least 3 digits";
    public static final String HEIGHT_VALIDATION_REGEX = "^[1-9]\\d{2,}$";
    public final String value;


    public CurrentHeight(String height) {
        requireNonNull(height);
        checkArgument(isValidHeight(height), MESSAGE_HEIGHT_CONSTRAINTS);
        value = height;
    }

    public static boolean isValidHeight(String test) {
        return test.matches(HEIGHT_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CurrentHeight // instanceof handles nulls
                && value.equals(((CurrentHeight) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }





}
