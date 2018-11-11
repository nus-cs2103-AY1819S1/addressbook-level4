package seedu.souschef.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.util.AppUtil.checkArgument;
import static seedu.souschef.model.recipe.CookTime.ZERO_COOKTIME;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.souschef.model.ingredient.IngredientPortion;

/**
 * Represents a Recipe's cook time in the application content.
 * Guarantees: immutable; is valid as declared in {@link #isValidInstruction(String)}
 */
public class Instruction {

    public static final String MESSAGE_INSTRUCTION_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String INSTRUCTION_VALIDATION_REGEX = "^(\\S)(\\s|\\S)*";
    public final String value;
    public final CookTime cookTime;
    public final Set<IngredientPortion> ingredients = new HashSet<>();

    /**
     * Constructs a {@code CookTime}.
     *
     * @param instruction A valid cook time.
     */
    public Instruction(String instruction, CookTime time, Set<IngredientPortion> ingredients) {
        requireNonNull(instruction);
        checkArgument(isValidInstruction(instruction), MESSAGE_INSTRUCTION_CONSTRAINTS);
        value = instruction;
        cookTime = time;
        this.ingredients.addAll(ingredients);
    }

    public Instruction(String instruction, Set<IngredientPortion> ingredients) {
        this(instruction, new CookTime(ZERO_COOKTIME), ingredients);
    }

    public Instruction(String instruction) {
        this(instruction, new CookTime(ZERO_COOKTIME), new HashSet<>());
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidInstruction(String instruction) {
        return instruction.matches(INSTRUCTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(value)
                .append(" CookTime: ")
                .append(cookTime)
                .append(" Ingredients: ");
        ingredients.forEach(builder::append);
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Instruction // instanceof handles nulls
                && value.equals(((Instruction) other).value)
                && cookTime.equals(((Instruction) other).cookTime)
                && ingredients.equals(((Instruction) other).ingredients)
                ); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, cookTime, ingredients);
    }

}
