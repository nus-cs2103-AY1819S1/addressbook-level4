package seedu.souschef.logic.commands;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;

/**
 * Deletes a recipe identified using it's displayed index from the address book.
 */
public class DeleteCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the recipe identified by the index number used in the displayed recipe list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RECIPE_SUCCESS = "Deleted Recipe: %1$s";

    private final Model model;
    private final T toDelete;

    public DeleteCommand(Model model, T toDelete) {
        this.model = model;
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(CommandHistory history) {
        model.delete(toDelete);
        model.commitAppContent();
        return new CommandResult(String.format(MESSAGE_DELETE_RECIPE_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && toDelete.equals(((DeleteCommand<T>) other).toDelete)); // state check
    }
}
