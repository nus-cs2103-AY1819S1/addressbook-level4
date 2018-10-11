package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's name in the order book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFoodName(String)}
 */
public class Food {

    public static final String MESSAGE_FOOD_CONSTRAINTS =
            "Food should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the food must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String FOOD_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String foodName;

    /**
     * Constructs a {@code Food}.
     *
     * @param name A valid name.
     */
    public Food(String name) {
        requireNonNull(name);
        checkArgument(isValidFood(name), MESSAGE_FOOD_CONSTRAINTS);
        foodName = name;
    }

    /**
     * Returns true if a given string is a valid Food.
     */
    public static boolean isValidFood(String test) {
        return test.matches(FOOD_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return foodName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Food // instanceof handles nulls
                && foodName.equals(((Food) other).foodName)); // state check
    }

    @Override
    public int hashCode() {
        return foodName.hashCode();
    }

}
