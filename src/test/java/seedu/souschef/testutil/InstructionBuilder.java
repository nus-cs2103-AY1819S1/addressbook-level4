package seedu.souschef.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Instruction;

/**
 * A utility class to help with building Instruction objects.
 */
public class InstructionBuilder {

    public static final String DEFAULT_VALUE = "Pre-heat oven for 30M and put the chicken 200 gram into it.";
    public static final String DEFAULT_COOKTIME = "30M";

    private String value;
    private CookTime cookTime;
    private Set<IngredientPortion> ingredients;

    public InstructionBuilder() {
        value = DEFAULT_VALUE;
        cookTime = new CookTime(DEFAULT_COOKTIME);
        ingredients = new HashSet<>();
    }

    /**
     * Initializes the InstructionBuilder with the data of {@code Instruction}.
     */
    public InstructionBuilder(Instruction instruction) {
        value = instruction.value;
        cookTime = instruction.cookTime;
        ingredients = instruction.ingredients;
    }

    /**
     * Sets the {@code value} of the {@code Instruction} that we are building.
     */
    public InstructionBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    /**
     * Sets the {@code cookTime} of the {@code Instruction} that we are building.
     */
    public InstructionBuilder withCooktime(String cooktime) {
        this.cookTime = new CookTime(cooktime);
        return this;
    }

    /**
     * Set the {@code Set<IngredientPortion>} of the {@code Instruction} that we are building
     */
    public InstructionBuilder withIngredients(Set<IngredientPortion> ingredientPortions) {
        this.ingredients = ingredientPortions;
        return this;
    }

    public Instruction build() {
        return new Instruction(value, cookTime, ingredients);
    }
}
