package seedu.address.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Carpark's coordinate in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCoord(String)}
 */
public class Coordinate {

    public static final String MESSAGE_COORD_CONSTRAINTS =
            "Coordinate should only contain decimal numbers";

    public static final String COORD_VALIDATION_REGEX = "^(\\d+(\\.\\d+)?),\\s*(\\d+(\\.\\d+)?)$";

    public final String value;

    /**
     * Constructs a {@code Coordinate}.
     *
     * @param coord A valid Coordinate.
     */
    public Coordinate(String coord) {
        requireNonNull(coord);
        checkArgument(isValidCoord(coord), MESSAGE_COORD_CONSTRAINTS);
        value = coord;
    }

    /**
     * Returns true if a given string is a valid coordinate.
     */
    public static boolean isValidCoord(String test) {
        return test.matches(COORD_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Coordinate // instanceof handles nulls
                && value.equals(((Coordinate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
