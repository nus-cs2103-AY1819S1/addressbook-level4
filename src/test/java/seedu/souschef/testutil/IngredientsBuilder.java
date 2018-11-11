package seedu.souschef.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.souschef.model.ingredient.IngredientPortion;

/**
 * A utility class to help with building Instruction objects.
 */
public class IngredientsBuilder {

    private Set<IngredientPortion> ingredients;

    public IngredientsBuilder() {
        ingredients = new HashSet<>();
    }

    /**
     * Initializes the IngredientBuilder with the data of {@code Set<IngredientPortion>}.
     */
    public IngredientsBuilder(Set<IngredientPortion> ingredients) {
        ingredients = new HashSet<>(ingredients);
    }

    /**
     * Sets the {@code name, unit, amt} of the {@code IngredientPortion} that we are building.
     */
    public IngredientsBuilder addIngredient(String name, String unit, double amt) {
        ingredients.add(new IngredientPortion(name, unit, amt));
        return this;
    }


    public Set<IngredientPortion> build() {
        return ingredients;
    }
}
