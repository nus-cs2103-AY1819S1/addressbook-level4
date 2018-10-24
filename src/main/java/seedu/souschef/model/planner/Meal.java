package seedu.souschef.model.planner;

import java.util.Optional;

import seedu.souschef.model.planner.exceptions.MealRecipeNotFoundException;
import seedu.souschef.model.recipe.Recipe;

/**
 * Represents a meal slot (breakfast, lunch, dinner) of a day.
 * Contains a recipe for the meal, as well as a predefined integer index value.
 */
public class Meal {

    // Attributes
    private final Slot slot;
    private Optional<Recipe> recipe;

    public Meal(Slot slot) {
        this.slot = slot;
        this.recipe = Optional.empty();
    }

    public Meal(Slot slot, Recipe recipe) {
        this.slot = slot;
        this.recipe = Optional.ofNullable(recipe);
    }

    public Recipe getRecipe() {
        if (this.recipe.isPresent()) {
            return this.recipe.get();
        } else {
            throw new MealRecipeNotFoundException("No recipe at selected meal slot.");
        }
    }

    public Slot getSlot() {
        return this.slot;
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
     * @throws IllegalArgumentException
     */
    public static int stringToIntSlot(String s) throws IllegalArgumentException {
        if (s.equalsIgnoreCase("breakfast")) {
            return 0;
        } else if (s.equalsIgnoreCase("lunch")) {
            return 1;
        } else if (s.equalsIgnoreCase("dinner")) {
            return 2;
        } else {
            throw new IllegalArgumentException("Valid meal slots: breakfast, lunch, dinner");
        }
    }

    /**
     * Converts a string token to its Enum counterpart.
     *
     * @param s String token
     * @return Meal.Slot
     * @throws IllegalArgumentException
     */
    public static Slot stringToEnumSlot(String s) throws IllegalArgumentException {
        if (s.equalsIgnoreCase("breakfast")) {
            return Slot.BREAKFAST;
        } else if (s.equalsIgnoreCase("lunch")) {
            return Slot.LUNCH;
        } else if (s.equalsIgnoreCase("dinner")) {
            return Slot.DINNER;
        } else {
            throw new IllegalArgumentException("Valid meal slots: breakfast, lunch, dinner");
        }
    }

    /**
     * Represents the 3 meals of a day.
     */
    public enum Slot {
        BREAKFAST, LUNCH, DINNER;
    }

}
