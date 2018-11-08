package seedu.address.model.occasion;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Occasion's name in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidName(String)}
 *
 * @author KongZijin
 */
public class OccasionName {

    public static final String MESSAGE_OCCASIONNAME_CONSTRAINTS = "Occasion "
        + "names should only contain alphanumeric characters and spaces with length ranges from 3 to 30"
        + ", and it should not be blank.";

    /*
     * The name of an occasion should consist of any number of alphanumeric
     * characters and spaces.
     * Length of the name should be restricted between 3 - 30.
     */
    public static final String OCCASIONNAME_VALIDATION_REGEX = "[a-zA-Z0-9 ]{3,30}";

    public final String fullOccasionName;

    /**
     * Empty Constructor.
     */
    public OccasionName() {
        fullOccasionName = "";
    }

    /**
     * Construct a {@code OccasionName}.
     *
     * @param name An occasion name.
     */
    public OccasionName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_OCCASIONNAME_CONSTRAINTS);
        fullOccasionName = name;
    }

    /**
     * Makes an identical deep copy of this OccasionName.
     */
    public OccasionName makeDeepDuplicate() {
        OccasionName newName = new OccasionName(new String(fullOccasionName));
        return newName;
    }

    /**
     * Check whether a given string is a valid name.
     *
     * @return A boolean value indicating the validation of this occasion name.
     */
    public static boolean isValidName(String test) {
        return test.matches(OCCASIONNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullOccasionName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof OccasionName // instanceof handles nulls
            && fullOccasionName.equals(((OccasionName) other).fullOccasionName));
    }

    @Override
    public int hashCode() {
        return fullOccasionName.hashCode();
    }
}
