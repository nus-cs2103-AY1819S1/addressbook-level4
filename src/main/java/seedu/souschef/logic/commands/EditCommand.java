package seedu.souschef.logic.commands;

import static seedu.souschef.model.Model.PREDICATE_SHOW_ALL;

import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;

/**
 * Edits the details of an existing recipe in the address book.
 */
public class EditCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_EDIT_SUCCESS = "Edited %1$s: %2$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Model model;
    private final T toEdit;
    private final T edited;

    /**
     * @param model
     * @param toEdit of the recipe in the filtered recipe list to edit
     * @param edited details to edit the recipe with
     */
    public EditCommand(Model model, T toEdit, T edited) {
        this.model = model;
        this.toEdit = toEdit;
        this.edited = edited;
    }

    @Override
    public CommandResult execute(History history) throws CommandException {
        model.update(toEdit, edited);
        model.updateFilteredList(PREDICATE_SHOW_ALL);
        model.commitAppContent();
        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS,
                history.getContextString(), edited));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand<T> e = (EditCommand<T>) other;
        return model.equals(((EditCommand<T>) other).model)
                && toEdit.equals(e.toEdit)
                && edited.equals(e.edited);
    }
}
