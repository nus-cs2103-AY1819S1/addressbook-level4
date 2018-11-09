package seedu.restaurant.model.sales;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.AppUtil.checkArgument;

//@@author HyperionNKJ
/**
 * Represents a Record's name in the restaurant book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ItemName {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Menu item names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public ItemName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.restaurant.model.sales.ItemName // instanceof handles nulls
                    && fullName.equalsIgnoreCase(other.toString()));
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
