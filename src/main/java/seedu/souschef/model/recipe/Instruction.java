package seedu.souschef.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.util.AppUtil.checkArgument;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.souschef.model.ingredient.Ingredient;

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
    public final Set<Ingredient> ingredients = new HashSet<>();

    /**
     * Constructs a {@code CookTime}.
     *
     * @param instruction A valid cook time.
     */
    public Instruction(String instruction, String time, Set<Ingredient> ingredients) {
        requireNonNull(instruction);
        checkArgument(isValidInstruction(instruction), MESSAGE_INSTRUCTION_CONSTRAINTS);
        value = instruction;
        cookTime = new CookTime(time);
        this.ingredients.addAll(ingredients);
    }

    public Instruction(String instruction, Set<Ingredient> ingredients) {
        this(instruction, "PT0M", ingredients);
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
