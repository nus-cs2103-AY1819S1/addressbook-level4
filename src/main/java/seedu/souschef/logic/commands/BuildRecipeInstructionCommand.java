package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.souschef.logic.History;
import seedu.souschef.model.recipe.Instruction;

/**
 * Adds a recipe to the address book.
 */
public class BuildRecipeInstructionCommand extends Command {

    public static final String COMMAND_WORD = "cont";

    public static final String MESSAGE_ADD_SUCCESS = "New %1$s contributed: %2$s";

    private final Instruction toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Recipe}
     */
    public BuildRecipeInstructionCommand(Instruction toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(History history) {
        history.contributeRecipe(toAdd);
        return new CommandResult(String.format(MESSAGE_ADD_SUCCESS,
                history.getContext().toString().toLowerCase(), toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BuildRecipeInstructionCommand // instanceof handles nulls
                && toAdd.equals(((BuildRecipeInstructionCommand) other).toAdd));
    }
}
