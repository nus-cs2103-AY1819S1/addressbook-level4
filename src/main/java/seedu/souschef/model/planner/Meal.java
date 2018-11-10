package seedu.souschef.model.planner;

import java.util.Optional;

import seedu.souschef.model.planner.exceptions.MealRecipeNotFoundException;
import seedu.souschef.model.recipe.Recipe;

/**
 * Represents a meal (breakfast, lunch, dinner) of a day.
 * Contains a recipe for the meal, as well as the corresponding meal slot and index.
 */
public abstract class Meal {

    public final String slot;
    public final int index;
    private Optional<Recipe> recipe;

    public Meal(String slot, int index) {
        this.recipe = Optional.empty();
        this.slot = slot;
        this.index = index;
    }

    public Meal(String slot, int index, Recipe recipe) {
        this.recipe = Optional.ofNullable(recipe);
        this.slot = slot;
        this.index = index;
    }

    public Recipe getRecipe() {
        if (this.recipe.isPresent()) {
            return this.recipe.get();
        } else {
            throw new MealRecipeNotFoundException("No recipe at selected meal index.");
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
        return !recipe.isPresent();
    }

    /**
     * Converts a string command token to to its integer index counterpart.
     *
     * @param s Command string token
     * @return Meal
     * @throws IllegalArgumentException
     */
    public static int stringToIntSlot(String s) throws IllegalArgumentException {
        if (s.equalsIgnoreCase(Breakfast.SLOT)) {
            return Breakfast.INDEX;
        } else if (s.equalsIgnoreCase(Lunch.SLOT)) {
            return Lunch.INDEX;
        } else if (s.equalsIgnoreCase(Dinner.SLOT)) {
            return Dinner.INDEX;
        } else {
            throw new IllegalArgumentException("Valid meal slots: breakfast, lunch, dinner");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Meal)) {
            return false;
        }

        Meal otherMeal = (Meal) other;
        return (otherMeal.slot.equals(slot))
            && (otherMeal.index == index)
            && (otherMeal.recipe.equals(recipe));
    }

    @Override
    public String toString() {
        String recipeName;
        if (recipe.isPresent()) {
            recipeName = recipe.get().getName().fullName;
        } else {
            recipeName = "No recipe";
        }

        return slot + ": " + recipeName;
    }
}
