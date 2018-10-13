package seedu.souschef.model.planner;

import java.util.Optional;

import seedu.souschef.model.planner.exceptions.MealRecipeNotFoundException;
import seedu.souschef.model.recipe.Recipe;

/**
 * Represents a meal slot (breakfast, lunch, dinner) of a day.
 * Contains a recipe for the meal, as well as a predefined integer index value.
 */
public enum Meal {

    BREAKFAST, LUNCH, DINNER;

    // Attributes
    private Optional<Recipe> recipe;

    Meal() {
        this.recipe = Optional.empty();
    }

    public Recipe getRecipe() throws MealRecipeNotFoundException {
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
    public Meal stringToMealEnum(String s) throws IllegalArgumentException {
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
