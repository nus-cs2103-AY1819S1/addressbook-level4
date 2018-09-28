package seedu.address.model.group;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the location of upcoming meeting in the group.
 * Guarantees: immutable; is valid as declared in {@link #isValidPlace(String)}
 */
public class Place {

    public static final String MESSAGE_PLACE_CONSTRAINTS =
            "Places should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PLACE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String placeName;

    /**
     * Constructs a {@code Place}.
     *
     * @param place A valid name of the place.
     */
    public Place(String place) {
        requireNonNull(place);
        checkArgument(isValidPlace(place), MESSAGE_PLACE_CONSTRAINTS);
        placeName = place;
    }

    /**
     * Returns true if a given string is a valid name of place.
     */
    public static boolean isValidPlace(String test) {
        return test.matches(PLACE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return placeName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Place // instanceof handles nulls
                && placeName.equals(((Place) other).placeName)); // state check
    }

    @Override
    public int hashCode() {
        return placeName.hashCode();
    }
}
