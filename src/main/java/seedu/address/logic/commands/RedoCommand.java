package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAddressBook() && !model.canRedoArchiveList()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        if (model.canRedoAddressBook()) {
            model.redoAddressBook();
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }

        if (model.canRedoArchiveList()) {
            model.redoArchiveList();
            model.updateArchivedPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
