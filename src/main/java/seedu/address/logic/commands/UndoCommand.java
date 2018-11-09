package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoAddressBook() && !model.canUndoArchiveList()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        if (model.canUndoAddressBook()) {
            model.undoAddressBook();
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
        if (model.canUndoArchiveList()) {
            model.undoArchiveList();
            model.updateArchivedPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }

        model.setState(1);
        EventsCenter.getInstance().post(new PersonListEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
