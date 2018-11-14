package seedu.parking.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.parking.commons.util.AppUtil.checkArgument;

/**
 * Represents a car park's number.
 * Guarantees: immutable; is valid as declared in {@link #isValidCarparkNumber(String)}
 */
public class CarparkNumber {

    public static final String MESSAGE_CAR_NUM_CONSTRAINTS =
            "Car park number should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank";

    /*
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CAR_NUM_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}]*";

    public final String value;

    /**
     * Constructs an {@code CarparkNumber}.
     *
     * @param carNum A valid carpark number.
     */
    public CarparkNumber(String carNum) {
        requireNonNull(carNum);
        checkArgument(isValidCarparkNumber(carNum), MESSAGE_CAR_NUM_CONSTRAINTS);
        this.value = carNum;
    }

    public static boolean isValidCarparkNumber(String test) {
        return test.matches(CAR_NUM_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CarparkNumber // instanceof handles nulls
                && value.equals(((CarparkNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
