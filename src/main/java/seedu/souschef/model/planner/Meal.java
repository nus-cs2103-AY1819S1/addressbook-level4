package seedu.souschef.model.planner;

import java.util.Optional;

import seedu.souschef.model.planner.exceptions.MealRecipeNotFoundException;
import seedu.souschef.model.recipe.Recipe;

/**
 * Represents a meal slot (breakfast, lunch, dinner) of a day.
 * Contains a recipe for the meal, as well as a predefined integer index value.
 */
public class Meal {

    public static final int BREAKFAST = 0;
    public static final int LUNCH = 1;
    public static final int DINNER = 2;

    // Attributes
    private final int slot;
    private Optional<Recipe> recipe;

    public Meal(int slot) {
        this.slot = slot;
        this.recipe = Optional.empty();
    }

    public int getSlot() {
        return this.slot;
    }

    public Recipe getRecipe() {
        if (this.recipe.isPresent()) {
            return this.recipe.get();
        } else {
            throw new MealRecipeNotFoundException("No recipe at selected meal slot.");
        }
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = Optional.ofNullable(recipe);
    }

    /**
     * Checks if a recipe is present or not.
     *
     * @return true if no recipe is present, false if a recipe is present.
     */
    public boolean isEmpty() {
        return !this.recipe.isPresent();
    }

    /**
     * Converts a string command token to to its Enum counterpart.
     *
     * @param s Command string token
     * @return Meal
     */
    public static int stringToIntSlot(String s) throws IllegalArgumentException {
        if (s.equalsIgnoreCase("breakfast")) {
            return BREAKFAST;
        } else if (s.equalsIgnoreCase("lunch")) {
            return LUNCH;
        } else if (s.equalsIgnoreCase("dinner")) {
            return DINNER;
        } else {
            throw new IllegalArgumentException("Valid meal slots: breakfast, lunch, dinner");
        }
    }

}
