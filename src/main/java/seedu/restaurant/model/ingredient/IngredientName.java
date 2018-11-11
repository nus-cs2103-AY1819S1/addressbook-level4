package seedu.restaurant.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.AppUtil.checkArgument;

/**
 * Represents an Ingredient's name in the restaurant management app.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class IngredientName {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Ingredient names should only contain alphabet characters and spaces, and it should not be blank";

    /*
     * The first character of the ingredient name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[A-Za-z][( )A-Za-z]*";

    private final String fullName;

    /**
     * Constructs an {@code IngredientName}.
     *
     * @param name A valid ingredient name.
     */
    public IngredientName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid ingredient name.
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
                || (other instanceof IngredientName // instanceof handles nulls
                && fullName.equals(((IngredientName) other).toString())); // state check
    }

    /**
     * Returns true if this {@code IngredientName} has the same string value {@fullName} ignoring case compared to
     * {@code other}.
     */
    public boolean equalsIgnoreCase(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientName // instanceof handles nulls
                && fullName.equalsIgnoreCase(((IngredientName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
