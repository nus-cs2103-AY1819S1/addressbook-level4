package seedu.souschef.model.planner;

import seedu.souschef.model.recipe.Recipe;

/**
 * Represents the lunch meal slot.
 * Contains its predefined slot name and index number.
 */
public class Lunch extends Meal {

    public static final String SLOT = "lunch";
    public static final int INDEX = 1;

    public Lunch() {
        super(SLOT, INDEX);
    }

    public Lunch(Recipe recipe) {
        super(SLOT, INDEX, recipe);
    }
}
