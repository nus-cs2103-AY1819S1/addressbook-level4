package seedu.address.model.healthplan;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
public class CurrentHeight {


    public static final String MESSAGE_HEIGHT_CONSTRAINTS =
            "Heights should only contain numbers";
    public static final String HEIGHT_VALIDATION_REGEX = "\\d{2,}";
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
