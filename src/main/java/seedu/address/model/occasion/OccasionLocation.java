package seedu.address.model.occasion;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Occasion's location in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidLocation(String)}
 *
 * @author waytan
 */
public class OccasionLocation {

    public static final String MESSAGE_OCCASIONLOCATION_CONSTRAINTS =
            "Locations should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The location of an occasion should consist of any number of alphanumeric
     * characters and spaces.
     */
    public static final String OCCASIONLOCATION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullOccasionLocation;

    /**
     * Empty Constructor.
     */
    public OccasionLocation() {
        fullOccasionLocation = "";
    }
    /**
     * Construct a {@code OccasionLocation}.
     *
     * @param location An occasion location.
     */
    public OccasionLocation(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_OCCASIONLOCATION_CONSTRAINTS);
        fullOccasionLocation = location;
    }

    /**
     * Makes an identical deep copy of this OccasionLocation.
     */
    public OccasionLocation makeDeepDuplicate() {
        OccasionLocation newLocation = new OccasionLocation(new String(fullOccasionLocation));
        return newLocation;
    }

    /**
     * Check whether a given string is a valid location.
     *
     * @return A boolean value indicating the validation of this occasion location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(OCCASIONLOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullOccasionLocation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof OccasionLocation // instanceof handles nulls
            && fullOccasionLocation.equals(((OccasionLocation) other).fullOccasionLocation));
    }

    @Override
    public int hashCode() {
        return fullOccasionLocation.hashCode();
    }
}
