package seedu.souschef.model.planner;

import seedu.souschef.model.recipe.Recipe;

/**
 * Represents the breakfast meal slot.
 * Contains its predefined slot name and index number.
 */
public class Breakfast extends Meal {

    public static final String SLOT = "breakfast";
    public static final int INDEX = 0;

    public Breakfast() {
        super(SLOT, INDEX);
    }

    public Breakfast(Recipe recipe) {
        super(SLOT, INDEX, recipe);
    }
}
