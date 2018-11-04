package seedu.restaurant.model.menu;

import static java.util.Objects.requireNonNull;

//@@author yican95
/**
 * Represents an Item's recipe in the menu.
 * Guarantees: immutable; Always valid
 */
public class Recipe {
    private final String value;

    /**
     * Constructs a recipe.
     *
     * @param recipe A valid recipe.
     */
    public Recipe(String recipe) {
        requireNonNull(recipe);
        value = recipe;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recipe // instanceof handles nulls
                    && value.equals(((Recipe) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
