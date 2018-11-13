package seedu.souschef.model.planner;

import seedu.souschef.model.recipe.Recipe;

/**
 * Represents the dinner meal slot.
 * Contains its predefined slot name and index number.
 */
public class Dinner extends Meal {

    public static final String SLOT = "dinner";
    public static final int INDEX = 2;

    public Dinner() {
        super(SLOT, INDEX);
    }

    public Dinner(Recipe recipe) {
        super(SLOT, INDEX, recipe);
    }
}
