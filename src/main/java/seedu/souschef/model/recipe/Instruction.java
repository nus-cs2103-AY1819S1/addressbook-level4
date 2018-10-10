package seedu.souschef.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's cook time in the application content.
 * Guarantees: immutable; is valid as declared in {@link #isValidDifficulty(String)}
 */
public class Instruction {

    public static final String MESSAGE_INSTRUCTION_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String INSTRUCTION_VALIDATION_REGEX = "^(\\S)(\\s|\\S)*";
    public final String value;

    /**
     * Constructs a {@code CookTime}.
     *
     * @param instruction A valid cook time.
     */
    public Instruction(String instruction) {
        requireNonNull(instruction);
        checkArgument(isValidDifficulty(instruction), MESSAGE_INSTRUCTION_CONSTRAINTS);
        value = instruction;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidDifficulty(String instruction) {
        return instruction.matches(INSTRUCTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Instruction // instanceof handles nulls
                && value.equals(value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
