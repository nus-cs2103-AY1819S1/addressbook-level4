package seedu.souschef.logic.commands;

import static seedu.souschef.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.souschef.model.Model.PREDICATE_SHOW_ALL;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;

/**
 * Edits the details of an existing recipe in the address book.
 */
public class EditCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recipe identified "
            + "by the index number used in the displayed recipe list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_RECIPE_SUCCESS = "Edited Recipe: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the address book.";

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
    public CommandResult execute(CommandHistory history) throws CommandException {
        model.update(toEdit, edited);
        model.updateFilteredList(PREDICATE_SHOW_ALL);
        model.commitAppContent();
        return new CommandResult(String.format(MESSAGE_EDIT_RECIPE_SUCCESS, edited));
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
        return toEdit.equals(e.toEdit)
                && edited.equals(e.edited);
    }
}
