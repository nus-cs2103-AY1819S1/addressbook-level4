package seedu.address.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Carpark's type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCarType(String)}
 */
public class CarparkType {

    public static final String MESSAGE_CAR_TYPE_CONSTRAINTS =
            "Carpark type can take any values, and it should not be blank";

    /*
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CAR_TYPE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code CarparkType}.
     *
     * @param carType A valid carpark type.
     */
    public CarparkType(String carType) {
        requireNonNull(carType);
        checkArgument(isValidCarType(carType), MESSAGE_CAR_TYPE_CONSTRAINTS);
        value = carType;
    }

    /**
     * Returns true if a given string is a valid carpark type.
     */
    public static boolean isValidCarType(String test) {
        return test.matches(CAR_TYPE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CarparkType // instanceof handles nulls
                && value.equals(((CarparkType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
