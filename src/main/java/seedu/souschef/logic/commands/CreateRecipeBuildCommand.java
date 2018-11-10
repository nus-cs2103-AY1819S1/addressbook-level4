package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.souschef.logic.History;
import seedu.souschef.model.recipe.RecipeBuilder;

/**
 * Adds a recipe to the address book.
 */
public class CreateRecipeBuildCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_ADD_SUCCESS = "Constructing %1$s in progress: %2$s\nTo add please include "
            + "at least one instruction using " + BuildRecipeInstructionCommand.COMMAND_WORD
            + " command followed by " + AddCommand.COMMAND_WORD_END + " command.";

    private final RecipeBuilder toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Recipe}
     */
    public CreateRecipeBuildCommand(RecipeBuilder toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(History history) {
        history.createRecipeBuilder(toAdd);
        return new CommandResult(String.format(MESSAGE_ADD_SUCCESS,
                history.getContextString(), toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateRecipeBuildCommand // instanceof handles nulls
                && toAdd.equals(((CreateRecipeBuildCommand) other).toAdd));
    }
}
