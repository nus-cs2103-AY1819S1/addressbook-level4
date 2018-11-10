package seedu.souschef.logic.commands;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;

/**
 * Deletes a recipe identified using it's displayed index from the address book.
 */
public class DeleteCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_DELETE_SUCCESS = "Deleted %1$s: %2$s";

    private final Model model;
    private final T toDelete;

    public DeleteCommand(Model model, T toDelete) {
        this.model = model;
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(History history) {
        model.delete(toDelete);
        model.commitAppContent();
        return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS,
                history.getContextString(), toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && model.equals(((DeleteCommand<T>) other).model)
                && toDelete.equals(((DeleteCommand<T>) other).toDelete)); // state check
    }
}
